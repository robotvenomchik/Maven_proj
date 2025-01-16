package org.example.Homework33;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static final Map<String, ClientInfo> activeConnections = new HashMap<>();
    private static int clientCounter = 0;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[SERVER] Server started and waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientCounter++;
                String clientName = "client-" + clientCounter;
                ClientInfo clientInfo = new ClientInfo(clientName, new Date(), clientSocket);
                activeConnections.put(clientName, clientInfo);

                System.out.println("[SERVER] " + clientName + " успішно підключився");

                new Thread(new ClientHandler(clientInfo)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private final ClientInfo clientInfo;

        public ClientHandler(ClientInfo clientInfo) {
            this.clientInfo = clientInfo;
        }

        @Override
        public void run() {
            try (ObjectInputStream input = new ObjectInputStream(clientInfo.getSocket().getInputStream());
                 ObjectOutputStream output = new ObjectOutputStream(clientInfo.getSocket().getOutputStream())) {

                clientInfo.setOutputStream(output);
                output.writeObject("[SERVER] Welcome, " + clientInfo.getName() + "! Type 'help' for commands.");

                String message;
                while ((message = (String) input.readObject()) != null) {
                    String response = handleCommand(message, clientInfo);
                    output.writeObject(response);
                    if ("exit".equalsIgnoreCase(message.trim())) {
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("[SERVER] " + clientInfo.getName() + " відключився через помилку.");
            } finally {
                activeConnections.remove(clientInfo.getName());
                try {
                    clientInfo.getSocket().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("[SERVER] " + clientInfo.getName() + " відключився.");
            }
        }

        private String handleCommand(String message, ClientInfo sender) {
            String[] parts = message.trim().split(" ", 3);
            String command = parts[0].toLowerCase();

            switch (command) {
                case "help":
                    return "[SERVER] Commands: time, list, message <client-name> <message>, exit";

                case "time":
                    return "[SERVER] Current time: " + new Date();

                case "list":
                    return "[SERVER] Active clients: " + String.join(", ", activeConnections.keySet());

                case "message":
                    if (parts.length < 3) {
                        return "[SERVER] Usage: message <client-name> <message>";
                    }
                    String targetName = parts[1];
                    String privateMessage = parts[2];
                    ClientInfo targetClient = activeConnections.get(targetName);
                    if (targetClient != null) {
                        try {
                            ObjectOutputStream targetOutput = targetClient.getOutputStream();
                            targetOutput.writeObject("[PRIVATE] " + sender.getName() + ": " + privateMessage);
                            targetOutput.flush(); // Ensure the message is sent immediately
                            return "[SERVER] Message sent to " + targetName;
                        } catch (IOException e) {
                            return "[SERVER] Failed to send message to " + targetName;
                        }
                    } else {
                        return "[SERVER] Client " + targetName + " not found.";
                    }

                case "exit":
                    return "[SERVER] Goodbye, " + sender.getName();

                default:
                    return "[SERVER] Unknown command. Type 'help' for a list of commands.";
            }
        }
    }

    private static class ClientInfo {
        private final String name;
        private final Date connectionTime;
        private final Socket socket;
        private ObjectOutputStream outputStream;

        public ClientInfo(String name, Date connectionTime, Socket socket) {
            this.name = name;
            this.connectionTime = connectionTime;
            this.socket = socket;
        }

        public String getName() {
            return name;
        }

        public Socket getSocket() {
            return socket;
        }

        public void setOutputStream(ObjectOutputStream outputStream) {
            this.outputStream = outputStream;
        }

        public ObjectOutputStream getOutputStream() {
            return outputStream;
        }
    }
}
