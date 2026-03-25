import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.*;

public class ProfilePanel extends CustomPanel{
    CustomPanel contactlist;
    CustomPanel contentPanel;
    CustomButton toggleButton;
    CustomPanel recentChatsPanel;
    LinkedList<String> recentChats;
    ChatPanel chatPanel; // Reference to ChatPanel
    HashMap<String, LinkedList<String>> contactMessages; // Store messages per contact
  
    
    public ProfilePanel(ChatPanel chatPanel, Color color) {
        super(color);
        this.chatPanel = chatPanel;
        this.contactMessages = new HashMap<>();
        setBackground(Utilities.BACKGROUND);

        initialSampleMessages(); // Load initial messages for sample contacts
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 0));
        
        recentChats = new LinkedList<>();
        
        // Create toggle button
        toggleButton = new CustomButton("▼ Contacts");
      
        toggleButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        CustomPanel wrapperButton = new CustomPanel(new BorderLayout(), Utilities.BACKGROUND);
        wrapperButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        wrapperButton.setBorder(App.emptyBorder);
        wrapperButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapperButton.add(toggleButton);
        
        // Create content panel that will be toggled
        contentPanel = new CustomPanel(Utilities.BACKGROUND);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        CustomLabel title = new CustomLabel("Contacts", 0, Utilities.SUBHEADING, Font.BOLD);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(title);
        contentPanel.add(Box.createVerticalStrut(10));
        
        // Add contact list
        contactlist = new CustomPanel(Utilities.BACKGROUND);
        contactlist.setLayout(new BoxLayout(contactlist, BoxLayout.Y_AXIS));
        contactlist.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(contactlist);
        
        CustomButton createContactButton = new CustomButton("Create new Contact");
        createContactButton.addActionListener(e -> openContactDialog());
        createContactButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        CustomPanel ccbPanel = new CustomPanel(new BorderLayout(), Utilities.BACKGROUND);
        ccbPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        ccbPanel.setBorder(App.emptyBorder);
        ccbPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ccbPanel.add(createContactButton);
        contentPanel.add(ccbPanel);
        
        toggleButton.addActionListener(e -> toggleContent());
        
        // Create Recent Chats Panel
        recentChatsPanel = new CustomPanel(Utilities.BACKGROUND);
        recentChatsPanel.setLayout(new BoxLayout(recentChatsPanel, BoxLayout.Y_AXIS));
        recentChatsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        recentChatsPanel.setVisible(false); // Hidden by default
        
        CustomLabel recentChatsTitle = new CustomLabel("Recent Chats", 0, Utilities.SUBHEADING, Font.BOLD);
        recentChatsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        recentChatsPanel.add(recentChatsTitle);
        recentChatsPanel.add(Box.createVerticalStrut(10));
        
        // Add components to main panel
        add(wrapperButton);
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
        
        CustomPanel panel = new CustomPanel(new GridLayout(5, 2, 10, 10), Utilities.BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Name field (required)
        CustomLabel nameLabel = new CustomLabel("Name:", 0, Utilities.BODY, Font.PLAIN);
        JTextField nameField = new JTextField();
        panel.add(nameLabel);
        panel.add(nameField);
        
        // Phone field (optional)
        CustomLabel phoneLabel = new CustomLabel("Phone:", 0, Utilities.BODY, Font.PLAIN);
        JTextField phoneField = new JTextField();
        panel.add(phoneLabel);
        panel.add(phoneField);
        
        // Email field (optional)
        CustomLabel emailLabel = new CustomLabel("Email:", 0, Utilities.BODY, Font.PLAIN);
        JTextField emailField = new JTextField();
        panel.add(emailLabel);
        panel.add(emailField);
        
        // Address field (optional)
        CustomLabel addressLabel = new CustomLabel("Address:", 0, Utilities.BODY, Font.PLAIN);
        JTextField addressField = new JTextField();
        panel.add(addressLabel);
        panel.add(addressField);
        
        // Notes field (optional)
        CustomLabel notesLabel = new CustomLabel("Port ID:", 0, Utilities.BODY, Font.PLAIN);
        JTextField notesField = new JTextField();
        panel.add(notesLabel);
        panel.add(notesField);
        
        // Button panel
        CustomPanel buttonPanel = new CustomPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0), Utilities.BACKGROUND);        
        CustomButton saveButton = new CustomButton("Save");
        CustomButton cancelButton = new CustomButton("Cancel");
        
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
        CustomPanel contactRow = new CustomPanel(Utilities.BACKGROUND);
        contactRow.setLayout(new BorderLayout(5, 5));
        contactRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contactRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        contactRow.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Info button to show details
        CustomButton detailsButton = new CustomButton("i");
        detailsButton.setPreferredSize(new Dimension(30, 30));
        detailsButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        detailsButton.addActionListener(e -> showContactDetails(name, phone, email, address, notes));

        // Profile picture
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/users.png"));
        Image img = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel profilePic = new JLabel(scaledIcon);
        
        // Contact info panel
        CustomPanel infoPanel = new CustomPanel(Utilities.BACKGROUND);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        CustomButton contactButton = new CustomButton(name);
        contactButton.addActionListener(e -> openChat(name));
        
        CustomLabel phoneLabel = new CustomLabel(phone.isEmpty() ? "No phone" : phone, 0, 10, Font.PLAIN);
        phoneLabel.setForeground(Utilities.TEXT_MUTED);
        
        infoPanel.add(contactButton);
        infoPanel.add(phoneLabel);

        // Button panel to hold info and details
        CustomPanel buttonPanel = new CustomPanel(new BorderLayout(5, 0), Utilities.BACKGROUND);
        buttonPanel.add(infoPanel, BorderLayout.CENTER);
        buttonPanel.add(detailsButton, BorderLayout.EAST);
        
        // Add to contact row (profile pic on left, button panel in center)
        contactRow.add(profilePic, BorderLayout.WEST);
        contactRow.add(buttonPanel, BorderLayout.CENTER);
        
        contactlist.add(contactRow);
        revalidate();
        repaint();
    }
    
    private void openChat(String contactName) {
        addToRecentChats(contactName);
        chatPanel.setCurrentContact(contactName);
        chatPanel.clearMessages();
        
     
        // 2. Fallback to the HashMap if DB was empty (for your sample messages)
       if(contactMessages.containsKey(contactName)) {
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
        
        CustomLabel recentChatsTitle = new CustomLabel("Recent Chats", 0, Utilities.SUBHEADING, Font.BOLD);
        recentChatsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        recentChatsPanel.add(recentChatsTitle);
        recentChatsPanel.add(Box.createVerticalStrut(10));
        
        for(String chatName : recentChats) {
            CustomButton recentChatButton = new CustomButton(chatName);
            recentChatButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            recentChatButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            recentChatButton.addActionListener(e -> openChat(chatName));
            recentChatsPanel.add(recentChatButton);
        }
        
        revalidate();
        repaint();
    }

    private void showContactDetails(String name, String phone, String email, String address, String notes) {
        StringBuilder details = new StringBuilder();
        details.append("Name: ").append(name).append("\n");
        details.append("Phone: ").append(phone.isEmpty() ? "N/A" : phone).append("\n");
        details.append("Email: ").append(email.isEmpty() ? "N/A" : email).append("\n");
        details.append("Address: ").append(address.isEmpty() ? "N/A" : address).append("\n");
        details.append("Port ID: ").append(notes.isEmpty() ? "N/A" : notes);
        
        JOptionPane.showMessageDialog(this, details.toString(), name + " - Contact Details", JOptionPane.INFORMATION_MESSAGE);
    }


}