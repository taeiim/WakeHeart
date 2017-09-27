package com.dsm.wakeheart.Arduino;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;


public class SendModel implements Serializable {
    private InputStream inputStream;
    private OutputStream outputStream;

    public SendModel(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
