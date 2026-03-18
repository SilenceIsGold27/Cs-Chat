import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JPanel {

    JPanel messageArea;
    JPanel headerPanel;
    JLabel contactNameLabel;
    String currentContact = "Tommy";

    public ChatPanel() {
        setLayout(new BorderLayout());

        // Create header to show current contact
        headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contactNameLabel = new JLabel("Chat with: " + currentContact);
        contactNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(contactNameLabel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // Message area
        messageArea = new JPanel();
        messageArea.setLayout(new BoxLayout(messageArea, BoxLayout.Y_AXIS));
        messageArea.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setCurrentContact(String contactName) {
        this.currentContact = contactName;
        contactNameLabel.setText("Chat with: " + currentContact);
    }

    public void clearMessages() {
        messageArea.removeAll();
        messageArea.revalidate();
        messageArea.repaint();
    }

    public void addMessage(String sender, String message) {
        createBubble(sender, message);
    }

    public void createBubble(String sender, String message) {
        // Determine if this is the current user or the contact
        boolean isCurrentUser = sender.equals("You");

        // Create a wrapper panel for alignment
        JPanel bubbleWrapper = new JPanel();
        bubbleWrapper.setLayout(new BorderLayout());
        bubbleWrapper.setBackground(Color.WHITE);
        bubbleWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        bubbleWrapper.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Create the bubble panel
        JPanel bubble = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw rounded rectangle background
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
        };

        // Set bubble colors based on sender
        if (isCurrentUser) {
            bubble.setBackground(new Color(100, 150, 255)); // Blue for current user
        } else {
            bubble.setBackground(new Color(230, 230, 230)); // Light gray for contact
        }

        bubble.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        bubble.setOpaque(true);

        // Create message label
        JLabel messageLabel = new JLabel("<html><body style='width: 200px'>" + message + "</body></html>");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        if (isCurrentUser) {
            messageLabel.setForeground(Color.WHITE);
        } else {
            messageLabel.setForeground(Color.WHITE);
        }

        bubble.add(messageLabel);

        // Add bubble to wrapper with proper alignment
        if (isCurrentUser) {
            bubbleWrapper.add(Box.createHorizontalGlue(), BorderLayout.WEST);
            bubbleWrapper.add(bubble, BorderLayout.EAST);
        } else {
            bubbleWrapper.add(bubble, BorderLayout.WEST);
            bubbleWrapper.add(Box.createHorizontalGlue(), BorderLayout.EAST);
        }

        messageArea.add(bubbleWrapper);
        messageArea.revalidate();
        messageArea.repaint();

        // Auto scroll to bottom
        SwingUtilities.invokeLater(() -> {
            JScrollPane scrollPane = (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, messageArea);
            if (scrollPane != null) {
                JScrollBar vertical = scrollPane.getVerticalScrollBar();
                vertical.setValue(vertical.getMaximum());
            }
        });
    }
}