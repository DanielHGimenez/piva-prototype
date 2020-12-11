package org.pivaprototype.piv.socket;

import org.pivaprototype.socket.payload.ByteAssembler;
import org.pivaprototype.socket.payload.Message;
import org.pivaprototype.socket.payload.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Listener implements Runnable {

    private static int NUMBER_OF_THREADS = 10;

    private Socket client;
    private Map<String, Solver> solvers;
    private ExecutorService threadpool;

    public Listener(Socket client, Map<String, Solver> solvers) {
        this.client = client;
        this.solvers = solvers;
        threadpool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    }

    @Override
    public void run() {
        try {
            System.out.println("Listener started");
            ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());
            while (client.isConnected()) {
                Object object = objectInputStream.readObject();
                if (null != object) {
                    Message<Request> message = (Message<Request>) object;
                    Request request = message.getData();

                    Solver solver = solvers.get(request.getResource());

                    SolverWrapper wrapper = new SolverWrapper(message, request, client, solver);
                    threadpool.execute(wrapper);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
