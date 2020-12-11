package org.pivaprototype.client.socket;

import org.pivaprototype.socket.payload.Message;
import org.pivaprototype.socket.payload.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Listener implements Runnable {

    private Socket socket;
    private Session[] sessions;

    public Listener(Socket socket, Session[] sessions) {
        this.socket = socket;
        this.sessions = sessions;
    }

    @Override
    public void run() {
        try {
            System.out.println("Listener started");
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            while (true) {
                System.out.println("Package available");
                Object object = objectInputStream.readObject();
                if (null != object) {
                    Message<Response> message = (Message<Response>) object;
                    Response response = message.getData();

                    sessions[message.getSessionId()].getOnSucess().accept(response);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
