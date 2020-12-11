package org.pivaprototype.socket.payload;

import java.io.Serializable;

public class Response<T> implements Serializable {

    private static final int DATA_MAX_LENGTH = 4950;

    private int status;
    private T data;

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

}
