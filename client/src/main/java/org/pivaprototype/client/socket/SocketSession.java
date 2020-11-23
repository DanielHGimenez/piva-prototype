package org.pivaprototype.client.socket;

import org.pivaprototype.socket.payload.Response;

import java.util.function.Consumer;

public class SocketSession {

    private Integer id;
    private Consumer<Response> onSucess;

    public SocketSession(Integer id, Consumer<Response> onSucess) {
        this.id = id;
        this.onSucess = onSucess;
    }

    public Integer getId() {
        return id;
    }

    public Consumer<Response> getOnSucess() {
        return onSucess;
    }

}
