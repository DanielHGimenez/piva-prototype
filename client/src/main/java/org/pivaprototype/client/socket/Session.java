package org.pivaprototype.client.socket;

import org.pivaprototype.socket.payload.Response;

import java.util.function.Consumer;

public class Session<T> {

    private Integer id;
    private Consumer<Response<T>> onSucess;

    public Session(Integer id, Consumer<Response<T>> onSucess) {
        this.id = id;
        this.onSucess = onSucess;
    }

    public Integer getId() {
        return id;
    }

    public Consumer<Response<T>> getOnSucess() {
        return onSucess;
    }

}
