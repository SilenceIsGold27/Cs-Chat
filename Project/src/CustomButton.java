import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CustomButton extends JButton {
    private float hovProg  = 0f;
    private float targetHov = 0f;
    private float pressProg = 0f;
    private float targetPress = 0f;
    private Timer pressTimer;
    private Timer hovTime;
    private Color gradientTop = null;
    private Color gradientBottom = null;
    private Color gradientTopAccent = null;
    private Color gradientBottomAccent = null;

    public CustomButton(String text) {
        super(text);
        UIManager.put("Button.select", new Color(0,0,0,0));
        setContentAreaFilled(true);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);

        this.putClientProperty("baseFontSize", getFont().getSize());

        hovTime = new Timer(15, e -> animationHover());
        hovTime.setRepeats(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startHoverAnimation(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startHoverAnimation(false);
            }

            @Override
            public void mousePressed(MouseEvent e){
                startPressAnimation(true);
            }

            @Override
            public void mouseReleased(MouseEvent e){
                startPressAnimation(false);
            }
        });
    }

    @Override
    public Dimension getPreferredSize(){
        Dimension size = super.getPreferredSize();
        int paddingX = (int)(10*CustomLabel.MULTIPLIER);
        return new Dimension((size.width) + paddingX, size.height);
    }

    @Override
    public Dimension getMaximumSize(){
        Dimension pre = getPreferredSize();
        return new Dimension(pre.width, pre.height);
    }


    public void setCustomGradient(Color top, Color bottom, Color topAccent, Color bottomAccent) {
        gradientTop = top;
        gradientBottom = bottom;
        gradientTopAccent = topAccent;
        gradientBottomAccent = bottomAccent;
        repaint();
    }

    private void animationHover() {
        float speed = 0.08f;

        if (Math.abs(hovProg - targetHov) < speed) {
            hovProg = targetHov;
            hovTime.stop();
        }
        else if (hovProg < targetHov) {
            hovProg += speed;
        }
        else {
            hovProg -= speed;
        }
        repaint();
    }

    private void startHoverAnimation(boolean enter){
        targetHov = enter ? 1f : 0f;
        hovTime.start();
    }

    private void animationPress(){
        float speed = 0.15f;

        if (Math.abs(pressProg - targetPress) < speed){
            pressProg = targetPress;
            pressTimer.stop();
        }
        else if (pressProg < targetPress) {
            pressProg += speed;
        }
        else {
            pressProg -= speed;
        }
        repaint();
    }

    private void startPressAnimation(boolean isPressed) {
        targetPress = isPressed ? 1f : 0f;

        if (pressTimer == null) {
            pressTimer = new Timer(15, e -> animationPress());
            pressTimer.setRepeats(true);
        }
        pressTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();
        int arc = 12;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color top;
        Color bottom;
        if (gradientTop != null && gradientBottom != null) {
            top = blend(gradientTop, gradientTopAccent, hovProg);
            bottom = blend(gradientBottom, gradientBottomAccent, hovProg);
        } 
        else {
            top = blend(Utilities.BACKGROUND_LIGHT, Utilities.ACCENT_LIGHT, hovProg);
            bottom = blend(Utilities.BACKGROUND, Utilities.ACCENT, hovProg);
        }

        float depth = pressProg * 0.3f;
        top = moreDepth(top, depth);
        bottom = moreDepth(bottom, depth);

        GradientPaint gradient = new GradientPaint(0, 0, top, 0, height, bottom);
        g2.setPaint(gradient);
        g2.fillRoundRect(0, 0, width, height, arc, arc);
        
        int shrink = (int)(pressProg *2); //shrink 0-2 pix
        if (shrink > 0){
            g2.setClip(shrink, shrink, width - shrink*2, height - shrink*2);
        }

        //create a border for the button
        Color border = blend(Utilities.BORDER, Utilities.ACCENT, hovProg);
        g2.setColor(border);
        g2.setStroke(new BasicStroke(1f));
        g2.drawRoundRect(0,0,width-1, height -1, arc, arc);
        
        g2.dispose();
        super.paintComponent(g);
    }

    private Color blend(Color a, Color b, float t){
        float num = 1f - t;
        return new Color(
            (int)(a.getRed()* num+b.getRed() * t),
            (int)(a.getGreen()* num+b.getGreen() * t),
            (int)(a.getBlue()* num+b.getBlue() * t)
        );
    }

    private Color moreDepth(Color color, float amount) {
        amount = Math.min(1f, Math.max(0f, amount));
        return new Color(
            (int)(color.getRed()* (1f - amount)),
            (int)(color.getGreen()* (1f - amount)),
            (int)(color.getBlue()* (1f - amount))
        );
    }
}