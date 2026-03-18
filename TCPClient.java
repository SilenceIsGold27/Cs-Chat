import java.io.*;
import java.net.Socket;
import javax.swing.SwingUtilities;

public class TCPClient {
    public interface MessageListener {
        void onMessageReceived(String message);
    }

    private MessageListener listener;
    private PrintWriter out_socket;
    private String username;

    public TCPClient(String username) throws IOException {
        this.username = username;
        
        // Match the server's port 2026
        Socket socket = new Socket("localhost", 2026);
        System.out.println("Connected to server as: " + username);

        BufferedReader in_socket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out_socket = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        
        // Send identity to server immediately
        out_socket.println(username);
        
        new Thread(() -> {
            try {
                String message;
                while ((message = in_socket.readLine()) != null) {
                    if (listener != null) {
                        final String msg = message;
                        SwingUtilities.invokeLater(() -> listener.onMessageReceived(msg));
                    }
                }
            } catch (IOException e) {
                System.err.println("Connection closed.");
            }
        }).start();
    }

    public void sendMessage(String recipient, String message) {
        // Formats as "TO:RecipientName:The Message Content"
        out_socket.println("TO:" + recipient + ":" + message);
    }

    public void setMessageListener(MessageListener listener) {
        this.listener = listener;
    }
}