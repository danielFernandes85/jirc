package io.codeforall.client;

public class Main {

    public static void main(String[] args) {
        Client c1 = new Client("localhost", 8585);

        c1.connect();
    }
}
