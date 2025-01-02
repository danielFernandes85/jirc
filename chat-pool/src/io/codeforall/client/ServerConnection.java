package io.codeforall.client;

import java.io.*;

public class ServerConnection {
    private BufferedReader in;
    private PrintWriter out;

    public ServerConnection (InputStream in, OutputStream out) {
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = new PrintWriter(out, true);
    }

    public String listen() {
        String serverMessage;

        try {
            serverMessage = this.in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return serverMessage;
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}