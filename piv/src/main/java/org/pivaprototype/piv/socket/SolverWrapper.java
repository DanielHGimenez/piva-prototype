package org.pivaprototype.piv.socket;

import org.pivaprototype.socket.payload.Message;
import org.pivaprototype.socket.payload.Request;
import org.pivaprototype.socket.payload.Response;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SolverWrapper implements Runnable {

    private Message message;
    private Request request;
    private Socket socket;
    private Solver solver;

    public SolverWrapper(Message message, Request request, Socket socket, Solver solver) {
        this.message = message;
        this.socket = socket;
        this.solver = solver;
    }

    @Override
    public void run() {
        try {
            Response response = solver.solve(request);
            System.out.println("Problem solved");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            Message<Response> responseMessage = new Message<Response>(this.message.getSessionId(), response);
            objectOutputStream.writeObject(responseMessage);
            System.out.println(String.format("Response sended to session %d", this.message.getSessionId()));
        } catch (IOException e) {
            e.printStackTrace();

            Response response = new Response();
            response.setStatus(-1);

            try {
                Message message = new Message<Response>(this.message.getSessionId(), response);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(message);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }

    }

}
