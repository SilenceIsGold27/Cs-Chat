import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel {
    JTextField inputField;
    JButton sendButton;
    JButton emojiButton;
    ChatPanel chatPanel;
    TCPClient client;
    JDialog emojiPickerDialog;
    DatabaseManager db;

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

    public InputPanel(ChatPanel chatPanel, TCPClient client) {
        this.chatPanel = chatPanel;
        this.client = client;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        // Input field
        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Emoji button - Using text label if emoji fails to render in UI
        emojiButton = new JButton("☺");
        emojiButton.setPreferredSize(new Dimension(50, 35));
        emojiButton.addActionListener(e -> openEmojiPicker());

        // Send button
        sendButton = new JButton("Send");
        sendButton.setPreferredSize(new Dimension(80, 35));
        sendButton.addActionListener(e -> sendMessage());

        // Allow Enter key to send
        inputField.addActionListener(e -> sendMessage());

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        buttonPanel.add(emojiButton);
        buttonPanel.add(sendButton);

        add(inputField, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }

    private void openEmojiPicker() {
        emojiPickerDialog = new JDialog();
        emojiPickerDialog.setTitle("Pick an Emoji");
        emojiPickerDialog.setSize(450, 400);
        emojiPickerDialog.setLocationRelativeTo(null);
        emojiPickerDialog.setResizable(false);

        JTabbedPane tabbedPane = new JTabbedPane();
        String[] categories = {"Smileys", "Gestures", "Hearts", "Symbols", "Celebration", "Laughs", "Expressions", "Food"};

        for (int i = 0; i < EMOJIS.length; i++) {
            JPanel categoryPanel = createEmojiPanel(EMOJIS[i]);
            tabbedPane.addTab(categories[i], categoryPanel);
        }

        emojiPickerDialog.add(tabbedPane);
        emojiPickerDialog.setVisible(true);
    }

    private JPanel createEmojiPanel(String[] emojis) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 4, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        for (String emoji : emojis) {
            JButton emojiBtn = new JButton(emoji);
            // Use a font that supports emojis better if Segoe UI Emoji is available
            emojiBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
            emojiBtn.setFocusPainted(false);
            emojiBtn.addActionListener(e -> {
                inputField.setText(inputField.getText() + emoji);
                emojiPickerDialog.dispose();
                inputField.requestFocus();
            });
            panel.add(emojiBtn);
        }
        return panel;
    }

    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            String recipient = chatPanel.currentContact; // Get current contact
            chatPanel.addMessage("You", message);
            
            // SAVE OUTGOING MESSAGE TO DB
            if (db != null) {
                db.saveMessage(recipient, "You: " + message);
            }

            inputField.setText("");
            if (client != null) {
                client.sendMessage(recipient, message);
            }
        }
    }

    public void setChatPanel(ChatPanel chatPanel) {
        this.chatPanel = chatPanel;
    }

    public void setDatabaseManager(DatabaseManager db) {
        this.db = db;
    }
}