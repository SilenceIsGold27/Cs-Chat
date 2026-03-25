import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class TCPServerMain {
    // Stores username -> PrintWriter for routing
    ConcurrentHashMap<String, PrintWriter> clients = new ConcurrentHashMap<>();

    public TCPServerMain() throws IOException {
        ServerSocket serverSocket = new ServerSocket(2026);
        System.out.println("Server started on Port 2026");

        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new TCPServerThread(socket, this)).start();
        }
    }

    public void routeMessage(String from, String message) {
        String[] parts = message.split(":", 3);
        
        if (parts.length == 3 && parts[0].equals("TO")) {
            String recipient = parts[1];
            String content = parts[2];
            
            PrintWriter writer = clients.get(recipient);
            if (writer != null) {
                // Format sent to recipient: "SenderName:Message"
                writer.println(from + ":" + content);
            } else {
                PrintWriter senderWriter = clients.get(from);
                if (senderWriter != null) {
                    senderWriter.println("SERVER: User " + recipient + " is offline.");
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            new TCPServerMain();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class TCPServerThread implements Runnable {
        private Socket socket;
        private TCPServerMain server;
        private String username;

        public TCPServerThread(Socket socket, TCPServerMain server) {
            this.socket = socket;
            this.server = server;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                
                // First line from client is the username
                username = in.readLine();
                if (username == null) return;

                server.clients.put(username, out);
                System.out.println(username + " joined the chat.");
                out.println("SERVER: Welcome " + username + "!");

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("exit")) break;
                    server.routeMessage(username, message);
                }
            } catch (IOException e) {
                System.err.println(username + " connection lost.");
            } finally {
                if (username != null) {
                    server.clients.remove(username);
                }
                try { socket.close(); } catch (IOException ignored) {}
            }
        }
    }
}