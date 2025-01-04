package com.raju.medium.deflate_compression.filter;


import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class DeflateResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream capture;
    private ServletOutputStream outputStream;
    private PrintWriter writer;

    public DeflateResponseWrapper(HttpServletResponse response) {
        super(response);
        capture = new ByteArrayOutputStream();
    }

    @Override
    public ServletOutputStream getOutputStream() {
        if (writer != null) {
            throw new IllegalStateException("Writer already in use");
        }
        if (outputStream == null) {
            outputStream = new ServletOutputStream() {
                @Override
                public void write(int b) throws IOException {
                    capture.write(b);
                }

                @Override
                public void flush() throws IOException {
                    capture.flush();
                }

                @Override
                public void close() throws IOException {
                    capture.close();
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setWriteListener(WriteListener writeListener) {
                    // No-op
                }

            };
        }
        return outputStream;
    }

    @Override
    public PrintWriter getWriter() {
        if (outputStream != null) {
            throw new IllegalStateException("OutputStream already in use");
        }
        if (writer == null) {
            writer = new PrintWriter(capture);
        }
        return writer;
    }

    public byte[] getCapturedData() {
        return capture.toByteArray();
    }

    public int getContentLength() {
        return capture.size();
    }
}
