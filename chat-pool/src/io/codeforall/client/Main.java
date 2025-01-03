package io.codeforall.client;

public class Main {

    public static void main(String[] args) {
        Client c1 = new Client(args[0], Integer.parseInt(args[1]));
        //todo : apanhar host port e username do inicio do programa , remover o hardcode
        c1.connect();
    }
}
