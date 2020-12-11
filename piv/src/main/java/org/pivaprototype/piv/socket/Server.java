package org.pivaprototype.piv.socket;

import org.pivaprototype.socket.payload.ByteAssembler;
import org.pivaprototype.socket.payload.Message;
import org.pivaprototype.socket.payload.Request;
import org.pivaprototype.socket.payload.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class Server {

    private static int NUMBER_OF_THREADS = 10;

    private int port;
    private Map<String, Solver> solvers;
    private ExecutorService threadpool;

    public Server(int port) {
        this.port = port;
        solvers = new HashMap<>();
        threadpool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    }

    public void addSolver(String resource, Solver solver) {
        solvers.put(resource, solver);
    }

    public void start() throws IOException, ClassNotFoundException {
        ServerSocket server = new ServerSocket(this.port);
        System.out.println("Server started");

        while (true) {
            Socket client = server.accept();
            System.out.println("connection accepted");
            threadpool.execute(new Listener(client, solvers));
        }
    }

}
