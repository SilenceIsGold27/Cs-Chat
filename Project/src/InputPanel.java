import javax.swing.*;
import java.awt.*;

public class InputPanel extends CustomPanel {
    JTextField inputField;
    CustomButton sendButton;
    CustomButton emojiButton;
    ChatPanel chatPanel;
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
    
    public InputPanel(ChatPanel chatPanel, Color color) {
        super(color);
        this.chatPanel = chatPanel;
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
        CustomPanel buttonPanel = new CustomPanel(new FlowLayout(FlowLayout.LEFT, 5, 0) ,Utilities.BACKGROUND);
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
        
        // Main panel with tabs oooo fancyy
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Create panels for each emoji category
        String[] categories = {"Smileys", "Gestures", "Hearts", "Symbols", "Celebration", "Laughs", "Expressions", "Food"};
        
        for (int i = 0; i < EMOJIS.length; i++) {
            JPanel categoryPanel = createEmojiPanel(EMOJIS[i]);
            tabbedPane.addTab(categories[i], categoryPanel);
        }
        
        emojiPickerDialog.add(tabbedPane);
        emojiPickerDialog.setVisible(true);
    }
    
    private CustomPanel createEmojiPanel(String[] emojis) {
        CustomPanel panel = new CustomPanel(new GridLayout(2,4,10,10), Utilities.BACKGROUND);
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
            chatPanel.addMessage("You", message);
            inputField.setText("");
        }
    }
    
    public void setChatPanel(ChatPanel chatPanel) {
        this.chatPanel = chatPanel;
    }
}