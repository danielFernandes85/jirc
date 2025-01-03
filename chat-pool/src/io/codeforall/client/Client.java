package io.codeforall.client;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private ServerConnection serverConnection;
    private String username;

    public Client(String host, int port) {

        try {
            clientSocket = new Socket(host, port);
            System.out.printf("Connected to '%s:%d'.\n", host, port);
            //todo : enviar username para servidor
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void connect() {
        try {
            this.serverConnection = new ServerConnection(clientSocket.getInputStream(), clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Thread thread = new Thread(new SendingMessage());
        thread.start();

        while (true) {
            String message = this.serverConnection.listen();

            System.out.println(message);
        }
    }

    private class SendingMessage implements Runnable {
        @Override
        public void run() {
            BufferedReader bufferedInputReader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                try {
                    String userMessage = bufferedInputReader.readLine();
                    serverConnection.sendMessage(userMessage);
                } catch (IOException e) {
                    System.out.println(e.getMessage());

                }
            }
        }
    }
}
