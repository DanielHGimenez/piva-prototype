package org.pivaprototype.client;

import org.pivaprototype.client.exception.CantSendRequestException;
import org.pivaprototype.client.socket.Client;
import org.pivaprototype.socket.payload.Request;
import org.pivaprototype.socket.payload.Response;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Client client = new Client("127.0.0.1", 3001);
        Request<String> request = new Request<String>("teste", "Ola back");
        try {
            client.sendRequest(request, (Response<Integer> res) -> System.out.println(res.getData()));
            System.out.println("Request sended");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CantSendRequestException e) {
            e.printStackTrace();
        }
    }
}
