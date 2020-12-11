package org.pivaprototype.piv;

import org.pivaprototype.piv.socket.Server;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        Server server = new Server(3001);
        server.addSolver("teste", new FirstSolver());

        try {
            server.start();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
