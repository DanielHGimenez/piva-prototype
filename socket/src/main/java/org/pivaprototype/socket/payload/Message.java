package org.pivaprototype.socket.payload;

import java.nio.ByteBuffer;

public class Message {

    private int sessionId;
    private byte[] data;

    public Message(int sessionId, byte[] data) {
        this.sessionId = sessionId;
        this.data = data;

    }

}
