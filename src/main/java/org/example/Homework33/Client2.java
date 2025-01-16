package org.example.Homework33;

import java.io.*;
import java.net.Socket;

public class Client2 {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println((String) input.readObject());
            String userInput;
            while (true) {
                System.out.print("Enter command: ");
                userInput = consoleReader.readLine();

                output.writeObject(userInput);

                String serverResponse = (String) input.readObject();
                System.out.println(serverResponse);

                if ("exit".equalsIgnoreCase(userInput.trim())) {
                    System.out.println("[CLIENT] Disconnecting...");
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
