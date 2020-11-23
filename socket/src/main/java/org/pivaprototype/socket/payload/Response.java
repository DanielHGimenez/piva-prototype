package org.pivaprototype.socket.payload;

public class Response {

    private static final int DATA_MAX_LENGTH = 4950;

    private int status;
    private byte[] data;

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }

}
