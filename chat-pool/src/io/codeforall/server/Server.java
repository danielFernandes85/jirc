package io.codeforall.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server {

    private final List<ServerWorker> serverWorkersManager = new LinkedList<>();

    public void init() {

        int localPort = 8585;

        try {
            ServerSocket serverSocket = new ServerSocket(localPort);
            System.out.println("Server started, listening on port " + localPort + ".");

            // accepting clients
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected");

                // creating serverWorkers, adding to list and creating new threads
                ServerWorker clientManager = new ServerWorker("Mimi", clientSocket);
                serverWorkersManager.add(clientManager);
                Thread thread = new Thread(clientManager);
                thread.start();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void sendToAll(String message) {
        for (int i = 0; i < serverWorkersManager.size(); i++) {
            ServerWorker serSave = serverWorkersManager.get(i);
            serSave.sendSomethingSpecific(message);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.init();
    }

    private class ServerWorker implements Runnable {

        //create server worker when connect

        private String name;
        private final Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public ServerWorker(String name, Socket socket) {
            this.name = name;
            this.socket = socket;
        }

        @Override
        public void run() {

            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Welcome to my beautiful chatroom. What is your name? ");
                name = in.readLine(); // client writes their name


                while (true) {
                    String message = in.readLine();
                    if (message.startsWith("/dadjoke")) {

                        sendToAll(name + " wants to tell a dad joke. Get ready everyone!");
                        sendToAll("Qual o pequeno almoço preferido do Dracula? Aveia!");

                    } else {
                        sendToAll(name + ": " + message);
                    }
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        public void sendSomethingSpecific(String message) {
            out.println(message);
        }

    }
}





