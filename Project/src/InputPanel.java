import javax.swing.*;
import java.awt.*;

public class InputPanel extends CustomPanel {
    JTextField inputField;
    CustomButton sendButton;
    CustomButton emojiButton;
    ChatPanel chatPanel;
    TCPClient client;
    JDialog emojiPickerDialog;

    
    // Common emojis organized by category
    private static final String[][] EMOJIS = {
        {"😊", "😂", "😍", "🤔", "😎", "😴", "😡", "😢"},
        {"👋", "👍", "👏", "🙌", "✋", "👌", "🤝", "🤲"},
        {"❤️", "💔", "💕", "💖", "💗", "💝", "💘", "💞"},
        {"🔥", "⭐", "✨", "💫", "🌟", "💯", "✅", "❌"},
        {"🎉", "🎊", "🎈", "🎁", "🏆", "🎖️", "🥇", "🥈"},
        {"😂", "🤣", "😆", "😄", "😃", "😀", "🙂", "😌"},
        {"🤷", "🤨", "😒", "😏", "🤐", "😬", "🤥", "😔"},
        {"🍕", "🍔", "🍟", "🌭", "🍿", "🍩", "🍪", "☕"}
    };
    
    public InputPanel(ChatPanel chatPanel, TCPClient client, Color color) {
        super(color);
        this.chatPanel = chatPanel;
        this.client = client;
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        // Input field
        inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        
        // Emoji button
        emojiButton = new CustomButton("😀");
        emojiButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 10));
        emojiButton.setPreferredSize(new Dimension(50, 35));
        emojiButton.addActionListener(e -> openEmojiPicker());
        
        // Send button
        sendButton = new CustomButton("Send");
        sendButton.setPreferredSize(new Dimension(80, 35));
        sendButton.addActionListener(e -> sendMessage());
        
        // Allow Enter key to send
        inputField.addActionListener(e -> sendMessage());
        
        // Panel for buttons
        CustomPanel buttonPanel = new CustomPanel(new FlowLayout(FlowLayout.LEFT, 5, 0), Utilities.BACKGROUND);
        buttonPanel.add(emojiButton);
        buttonPanel.add(sendButton);
        
        add(inputField, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }
    public void setClient(TCPClient client) {
        this.client = client;
    }
    private void openEmojiPicker() {
        emojiPickerDialog = new JDialog();
        emojiPickerDialog.setTitle("Pick an Emoji");
        emojiPickerDialog.setSize(450, 400);
        emojiPickerDialog.setLocationRelativeTo(null);
        emojiPickerDialog.setResizable(false);
        
        // Main panel with tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Create panels for each emoji category
        String[] categories = {"Smileys", "Gestures", "Hearts", "Symbols", "Celebration", "Laughs", "Expressions", "Food"};
        
        for (int i = 0; i < EMOJIS.length; i++) {
            CustomPanel categoryPanel = createEmojiPanel(EMOJIS[i]);
            tabbedPane.addTab(categories[i], categoryPanel);
        }
        
        emojiPickerDialog.add(tabbedPane);
        emojiPickerDialog.setVisible(true);
    }
    
    private CustomPanel createEmojiPanel(String[] emojis) {
        CustomPanel panel = new CustomPanel(new GridLayout(2, 4, 10, 10), Utilities.BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        for (String emoji : emojis) {
            CustomButton emojiBtn = new CustomButton(emoji);
            emojiBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
            emojiBtn.setFocusPainted(false);
            emojiBtn.addActionListener(e -> {
                inputField.setText(inputField.getText() + emoji);
                emojiPickerDialog.dispose();
                inputField.requestFocus();
            });
            panel.add(emojiBtn);
        }
        Utilities.changeComponentTheme(panel);
        
        return panel;
    }
    
    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            String recipient = chatPanel.currentContact; // Get current contact
            
            // Display message in chat UI
            chatPanel.addMessage("You", message);
            
            // Save outgoing message to database with proper format
            
            // Clear input field
            inputField.setText("");
            
            // Send to server via TCP (this also sets currentChannel in client)
            if (client != null && recipient != null) {
                client.sendMessage(recipient, message);
            }
        }
    }
    
    public void setChatPanel(ChatPanel chatPanel) {
        this.chatPanel = chatPanel;
    }
    



}