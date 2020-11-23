package org.pivaprototype.client.socket;

import org.pivaprototype.client.exception.CantInitializeConnectionException;
import org.pivaprototype.client.exception.CantSendRequestException;
import org.pivaprototype.socket.payload.ByteAssembler;
import org.pivaprototype.socket.payload.Message;
import org.pivaprototype.socket.payload.Request;
import org.pivaprototype.socket.payload.Response;
import org.pivaprototype.socket.security.Crypt;
import org.pivaprototype.socket.security.KeyGenerator;
import sun.security.rsa.RSAPublicKeyImpl;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Objects;
import java.util.function.Consumer;

public class SocketConnector {

    public static final int MAX_TRIES = 3;
    public static final char MAX_SIMULTANEOUS_SESSIONS = 10;
    public static final int KEY_SIZE = 2048;
    private ConnectionStatus status = ConnectionStatus.STARTING;
    private String ip;
    private Integer port;
    private Socket socket;
    private SocketSession[] sessions;
    private Crypt clientCrypt;
    private Crypt serverCrypt;

    public SocketConnector(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
        this.sessions = new SocketSession[MAX_SIMULTANEOUS_SESSIONS];
        try {
            this.clientCrypt = new Crypt(KeyGenerator.generatePair(KEY_SIZE));
            initializeConnection();
        } catch (NoSuchAlgorithmException | CantInitializeConnectionException e) {
            this.status = ConnectionStatus.ERROR_ON_START;
            e.printStackTrace();
        }
    }

    public void sendRequest(Request request, Consumer<Response> onSucess)
            throws IOException, CantSendRequestException {

        if (!this.status.equals(ConnectionStatus.STARTED))
            throw new CantSendRequestException();

        for (int sessionId = 0; sessionId < MAX_SIMULTANEOUS_SESSIONS; sessionId++) {

            if (Objects.isNull(sessions[sessionId])) {

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(request);

                byte[] payload = byteArrayOutputStream.toByteArray();

                try {
                    payload = this.serverCrypt.encrypt(payload);
                } catch (
                    NoSuchPaddingException |
                    NoSuchAlgorithmException |
                    InvalidKeyException |
                    BadPaddingException |
                    IllegalBlockSizeException e
                ) {
                    e.printStackTrace();
                    throw new CantSendRequestException();
                }

                Message message = new Message(sessionId, payload);
                socket.getOutputStream().write(ByteAssembler.serialize(message));

                sessions[sessionId] = new SocketSession(sessionId, onSucess);

            }
        }
    }

    private void initializeConnection() throws CantInitializeConnectionException {
        try {
            connectSocket();
            exchangeKeys();
        }
        catch (IOException | CantSendRequestException exception) {
            throw new CantInitializeConnectionException();
        }
    }

    private void connectSocket() throws IOException {
        if (!Objects.isNull(socket)) {
            socket.close();
        }

        int triesCounter = 1;
        while (!socket.isConnected()) {
            try {
                socket = new Socket(ip, port);
            }
            catch (IOException e) {
                if (triesCounter == MAX_TRIES) {
                    throw e;
                }
                triesCounter++;
            }
        }
    }

    private void exchangeKeys() throws IOException, CantSendRequestException {
        sendRequest(new Request("keys", clientCrypt.getPublicKey().getEncoded()), (Response res) -> {
            try {
                PublicKey publicKey = new RSAPublicKeyImpl(res.getData());
                this.serverCrypt = new Crypt(publicKey, null);
                status = ConnectionStatus.STARTED;
            } catch (InvalidKeyException e) {
                status = ConnectionStatus.ERROR_ON_START;
                e.printStackTrace();
            }
        });
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        while (!socket.isClosed()) {
            try {
                socket.close();
            }
            catch (IOException e) {}
        }
    }
}
