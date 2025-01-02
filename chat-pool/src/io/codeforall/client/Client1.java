package io.codeforall.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client1 {

    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public Client1(String hostCall, int port) {
        try {
            clientSocket = new Socket(hostCall, port);
            System.out.println("Connected to server");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {

        Client1 c1 = new Client1("localhost", 8585);
        c1.start();
    }

    public void settingStreams() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void start() {

        settingStreams();
        SendingMessage sm1 = new SendingMessage();
        Thread thread = new Thread(sm1);
        thread.start();

        while (true) {
            try {
                String message = in.readLine();
                System.out.println(message);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private class SendingMessage implements Runnable {

        @Override
        public void run() {

            BufferedReader bufferedInputReader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.print("Message: ");
                try {

                    String message = bufferedInputReader.readLine();
                    out.println(message);
                } catch (IOException e) {
                    System.out.println(e.getMessage());

                }
            }
        }
    }
}
