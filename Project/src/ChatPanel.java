import java.awt.*;
import javax.swing.*;

public class ChatPanel extends JPanel {
    
    CustomPanel messageArea;
    //JPanel headerPanel;
    JLabel contactNameLabel;
    String currentContact = "Tommy";
    public JScrollPane scrollPane;
    
    public ChatPanel() {
        setLayout(new BorderLayout());
        
        contactNameLabel = new JLabel("Chat with: " + currentContact);
        contactNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        App.northPanel.add(contactNameLabel, BorderLayout.WEST);
        
        // Message area
        messageArea = new CustomPanel(Utilities.BACKGROUND_DARK);
        messageArea.setName("messageArea");
        messageArea.setLayout(new BoxLayout(messageArea, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(messageArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
        
        
        scrollPane.getViewport().addChangeListener(e -> {
            messageArea.repaint();
        });
    }

    public int[] getDimension(){
        int width = getWidth();
        int height = getHeight();
        int[] list = {width, height};
        return list;
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

    public void updateBubbleColors(){
        messageArea.repaint();
    }
    
    public void createBubble(String sender, String message) {
        // Determine if this is the current user or the contact
        boolean isCurrentUser = sender.equals("You");
        
        // Create a wrapper panel for alignment
        CustomPanel bubbleWrapper = new CustomPanel(new BorderLayout(), Utilities.BACKGROUND_DARK);
        bubbleWrapper.setName("bubbleWrapper");
        bubbleWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        bubbleWrapper.setBorder(App.emptyBorder);
        
        // Create the bubble panel
        Bubble bubble = new Bubble(message);
        bubble.setName("bubble");   
       // bubble.setBackground(Utilities.BACKGROUND);     
                
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