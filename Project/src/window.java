import javax.swing.*;
import java.awt.*;

public class window {

    public static void main(String[] args) {
        // Ask for a username at startup
        String inputName = JOptionPane.showInputDialog("Enter your username:");
        if (inputName == null || inputName.trim().isEmpty()) {
            inputName = "User_" + (int)(Math.random() * 1000);
        }

        final String username = inputName;

        

        SwingUtilities.invokeLater(() -> {

         
            ChatPanel chatPanel = new ChatPanel();
            TCPClient client = null;

            JFrame frame = new JFrame("Messenger - " + username);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 500);
            frame.setLayout(new BorderLayout());

            try {
    client = new TCPClient(username);

// ONE listener to rule them all - FIXED
client.setMessageListener((channel, msg) -> {
    String[] parts = msg.split(":", 2);
    if (parts.length == 2) {
        String sender = parts[0];
        String content = parts[1];
        
        chatPanel.addMessage(sender, content);
        
        // SAVE INCOMING MESSAGES TO DB with proper channel

    }
});

} catch (Exception e) {
    chatPanel.addMessage("SERVER", "Could not connect to server.");
}

            InputPanel inputPanel = new InputPanel(chatPanel, client,Utilities.BACKGROUND);
            ProfilePanel profilePanel = new ProfilePanel(chatPanel,Utilities.BACKGROUND);
         
            JPanel centerPanel = new JPanel(new BorderLayout());
            centerPanel.add(chatPanel, BorderLayout.CENTER);
            centerPanel.add(inputPanel, BorderLayout.SOUTH);

            frame.add(profilePanel, BorderLayout.WEST);
            frame.add(centerPanel, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
    
}