package org.example.Homework32;

public class Main {
    public static void main(String[] args) {
        CoffeeOrderBoard board = new CoffeeOrderBoard();

        board.add("Alen");
        board.add("Yoda");
        board.add("Obi-van");
        board.add("John Snow");

        board.draw();

        board.deliver();

        board.deliver(3);

        board.deliver(100);

        board.draw();

        board.deliver();
        board.deliver();
        board.deliver();

        board.draw();
    }
}


