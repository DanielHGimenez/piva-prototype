package org.pivaprototype.socket.payload;

import java.io.Serializable;

public class Request<T> implements Serializable {

    private String resource;
    private T data;

    public Request(String resource, T data) {
        this.resource = resource;
        this.data = data;
    }

    public String getResource() {
        return resource;
    }
    public T getData() {
        return data;
    }
}
