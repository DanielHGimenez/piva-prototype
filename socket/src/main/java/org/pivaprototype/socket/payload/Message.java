package org.pivaprototype.socket.payload;

import java.io.Serializable;

public class Message<T> implements Serializable {

    private int sessionId;
    private T data;

    public Message(int sessionId, T data) {
        this.sessionId = sessionId;
        this.data = data;
    }

    public int getSessionId() {
        return sessionId;
    }
    public T getData() {
        return data;
    }

}
