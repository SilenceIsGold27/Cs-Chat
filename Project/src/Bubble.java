import java.awt.*;
import java.awt.geom.Point2D;

import javax.swing.*;

public class Bubble extends JPanel{
    public static Color[] theme= BubbleUtilities.THEMES.get(1);;
    public String message;
    
    public Bubble(String message){
        this.message = message;
        //theme = BubbleUtilities.THEMES.get(1);
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setOpaque(true);

        CustomLabel messageLabel = new CustomLabel("<html><body style='width: 200px'>" + this.message + "</body></html>", 0, Utilities.BODY, Font.PLAIN);
        messageLabel.setName("messageLabel");
        messageLabel.setForeground(Color.white);
        add(messageLabel);

    }

    @Override
    public Dimension getMaximumSize(){
        Dimension pref = super.getPreferredSize();
        int paddingX = (int)(10*CustomLabel.MULTIPLIER);
        return new Dimension(pref.width+paddingX, pref.height);
    }

    @Override
    public Dimension getPreferredSize(){
        int bubbleWidth = calculateBubbleWidth(message);
        int bubbleHeight = calculateBubbleHeight(message);
        return new Dimension(bubbleWidth, bubbleHeight);
    }

    private int calculateBubbleWidth(String message) {
        int charCount = message.length();

        int estimatedWidth = charCount * 8 + 10; // 10 for padding just guessing amount of text

        int minWidth = 100;
        int maxWidth = 500;
        return Math.max(minWidth, Math.min(estimatedWidth, maxWidth));
    }
    private int calculateBubbleHeight(String message) {
        int charCount = message.length();

        int lines = Math.max(1, (charCount / 50) + 1); // guessing amount of lines

        int baseHeight = 35;
        int heightPerLine = 20;

        return baseHeight + lines * heightPerLine;
    }

    public static void changeColor(Color[] t){
        theme = t;
        App.chatPanel.updateBubbleColors();
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        JViewport viewport = App.chatPanel.scrollPane.getViewport();
        int viewportHeight = viewport.getExtentSize().height;

        Point bubblePos = SwingUtilities.convertPoint(this, 0, 0, App.chatPanel);
        int bubbleY = bubblePos.y;
        int viewportY = viewport.getViewPosition().y;
        int bubbleYInViewport = bubbleY - viewportY;

        float angle = (float) Math.tan(Math.toRadians(20));
        float[] fractions = {0f, 1f};

        float startX = -bubbleYInViewport*angle;
        float startY = -bubbleYInViewport;

        float endX = startX + viewportHeight*angle;
        float endY = startY + viewportHeight;

        Point2D start = new Point2D.Float(startX, startY);
        Point2D end = new Point2D.Float(endX, endY);

        LinearGradientPaint gradient = new LinearGradientPaint(start, end, fractions, theme);
        // Draw rounded rectangle background
        g2d.setPaint(gradient);
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
    }
}
