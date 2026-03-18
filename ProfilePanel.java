import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.HashMap;

public class ProfilePanel extends JPanel{
    JPanel contactlist;
    JPanel contentPanel;
    JButton toggleButton;
    JPanel recentChatsPanel;
    LinkedList<String> recentChats;
    ChatPanel chatPanel; // Reference to ChatPanel
    HashMap<String, LinkedList<String>> contactMessages; // Store messages per contact
    
    public ProfilePanel(ChatPanel chatPanel) {
        this.chatPanel = chatPanel;
        this.contactMessages = new HashMap<>();

        initialSampleMessages(); // Load initial messages for sample contacts
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 0));
        
        recentChats = new LinkedList<>();
        
        // Create toggle button
        toggleButton = new JButton("▼ Contacts");
        toggleButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        toggleButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        // Create content panel that will be toggled
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel title = new JLabel("Contacts");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(title);
        contentPanel.add(Box.createVerticalStrut(10));
        
        // Add contact list
        contactlist = new JPanel();
        contactlist.setLayout(new BoxLayout(contactlist, BoxLayout.Y_AXIS));
        contactlist.setAlignmentX(Component.LEFT_ALIGNMENT);
        addContact("Tommy", "555-1234", "tommy@email.com");
        addContact("Tommy2", "555-5678", "tommy2@email.com");
        addContact("Tommy3", "555-9999", "tommy3@email.com");
        contentPanel.add(contactlist);
        
        JButton createContactButton = new JButton("Create new Contact");
        createContactButton.addActionListener(e -> openContactDialog());
        createContactButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(createContactButton);
        
        toggleButton.addActionListener(e -> toggleContent());
        
        // Create Recent Chats Panel
        recentChatsPanel = new JPanel();
        recentChatsPanel.setLayout(new BoxLayout(recentChatsPanel, BoxLayout.Y_AXIS));
        recentChatsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        recentChatsPanel.setVisible(false); // Hidden by default
        
        JLabel recentChatsTitle = new JLabel("Recent Chats");
        recentChatsTitle.setFont(new Font("Arial", Font.BOLD, 20));
        recentChatsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        recentChatsPanel.add(recentChatsTitle);
        recentChatsPanel.add(Box.createVerticalStrut(10));
        
        // Add components to main panel
        add(toggleButton);
        add(contentPanel);
        add(recentChatsPanel);
    }

    private void initialSampleMessages() {
        LinkedList<String> tommyMessages = new LinkedList<>();
        tommyMessages.add("Hello!");
        tommyMessages.add("How are you?");
        tommyMessages.add("Let's catch up soon.");
        contactMessages.put("Tommy", tommyMessages);

        LinkedList<String> tommy2Messages = new LinkedList<>();
        tommy2Messages.add("Hi there!");
        tommy2Messages.add("Long time no talk!");
        contactMessages.put("Tommy2", tommy2Messages);
        
        LinkedList<String> tommy3Messages = new LinkedList<>();
        tommy3Messages.add("Hey everyone!");
        tommy3Messages.add("What's up?");
        contactMessages.put("Tommy3", tommy3Messages);
    }
    
    private void openContactDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Add New Contact");
        dialog.setModal(true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Name field (required)
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        panel.add(nameLabel);
        panel.add(nameField);
        
        // Phone field (optional)
        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField();
        panel.add(phoneLabel);
        panel.add(phoneField);
        
        // Email field (optional)
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        panel.add(emailLabel);
        panel.add(emailField);
        
        // Address field (optional)
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();
        panel.add(addressLabel);
        panel.add(addressField);
        
        // Notes field (optional)
        JLabel notesLabel = new JLabel("Port ID:");
        JTextField notesField = new JTextField();
        panel.add(notesLabel);
        panel.add(notesField);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String address = addressField.getText().trim();
            String notes = notesField.getText().trim();
            
            if(name.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Name is required!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                addContact(name, phone, email, address, notes);
                dialog.dispose();
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        dialog.setLayout(new BorderLayout());
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.setVisible(true);
    }
    
    private void toggleContent() {
        boolean isVisible = contentPanel.isVisible();
        contentPanel.setVisible(!isVisible);
        recentChatsPanel.setVisible(isVisible); // Show recent chats when contacts are hidden
        
        if (isVisible) {
            toggleButton.setText("► Contacts");
        } else {
            toggleButton.setText("▼ Contacts");
        }
        
        revalidate();
        repaint();
    }
    
    private void addContact(String name, String phone, String email) {
        addContact(name, phone, email, "", "");
    }
    
    private void addContact(String name, String phone, String email, String address, String notes) {
        JPanel contactRow = new JPanel();
        contactRow.setLayout(new BorderLayout(5, 5));
        contactRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contactRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        contactRow.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // --- FIXED: Placeholder instead of ImageIcon ---
        JPanel profilePicPlaceholder = new JPanel();
        profilePicPlaceholder.setPreferredSize(new Dimension(30, 30));
        profilePicPlaceholder.setBackground(Color.LIGHT_GRAY);
        JLabel initial = new JLabel(name.substring(0, 1).toUpperCase());
        profilePicPlaceholder.add(initial);
        // -----------------------------------------------
        
        // Contact info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        JButton contactButton = new JButton(name);
        contactButton.addActionListener(e -> openChat(name)); 
        
        JLabel phoneLabel = new JLabel(phone.isEmpty() ? "No phone" : phone);
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        phoneLabel.setForeground(Color.GRAY);
        
        infoPanel.add(contactButton);
        infoPanel.add(phoneLabel);
        
        contactRow.add(profilePicPlaceholder, BorderLayout.WEST); // Added placeholder here
        contactRow.add(infoPanel, BorderLayout.CENTER);
        
        contactlist.add(contactRow);
        revalidate();
        repaint();
    }
    
    private void openChat(String contactName) {
    addToRecentChats(contactName);
    chatPanel.setCurrentContact(contactName);
    chatPanel.clearMessages();
    
    // 1. Try to load from Database FIRST
    if (db != null) {
        java.util.List<String> history = db.getHistory(contactName);
        for (String entry : history) {
            String[] parts = entry.split(": ", 2);
            String sender = parts[0];
            String content = (parts.length > 1) ? parts[1] : "";
            chatPanel.addMessage(sender, content);
        }
    } 
    
    // 2. Fallback to the HashMap if DB was empty (for your sample messages)
    else if(contactMessages.containsKey(contactName)) {
        for(String message : contactMessages.get(contactName)) {
            chatPanel.addMessage(contactName, message);
        }
    }
}
    
    private void addToRecentChats(String name) {
        recentChats.remove(name);
        recentChats.addFirst(name);
        
        if(recentChats.size() > 10) {
            recentChats.removeLast();
        }
        updateRecentChatsDisplay();
    }
    
    private void updateRecentChatsDisplay() {
        recentChatsPanel.removeAll();
        
        JLabel recentChatsTitle = new JLabel("Recent Chats");
        recentChatsTitle.setFont(new Font("Arial", Font.BOLD, 20));
        recentChatsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        recentChatsPanel.add(recentChatsTitle);
        recentChatsPanel.add(Box.createVerticalStrut(10));
        
        for(String chatName : recentChats) {
            JButton recentChatButton = new JButton(chatName);
            recentChatButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            recentChatButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            recentChatButton.addActionListener(e -> openChat(chatName)); // Open chat instead of dialog
            recentChatsPanel.add(recentChatButton);
        }
        
        revalidate();
        repaint();
    }

    DatabaseManager db;
        public void setDatabaseManager(DatabaseManager db) {
    this.db = db;
}
}