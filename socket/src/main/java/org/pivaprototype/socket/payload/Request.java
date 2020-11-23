package org.pivaprototype.socket.payload;

import java.io.Serializable;

public class Request implements Serializable {

    private String resource;
    private byte[] data;

    public Request(String resource, byte[] data) {
        this.resource = resource;
        this.data = data;
    }

}
