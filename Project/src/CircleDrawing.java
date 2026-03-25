import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class CircleDrawing extends JButton {
    private Color top;
    private Color bottom;
    private float ratio = 0.7f;
    private Timer hoverTimer;
    private float scale = 1.0f; // to get the bubble slightly bigger when hover
    private Timer scaleTimer;
    //private Timer pressTimer;

    public CircleDrawing(Color[] color){
        super("");
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setPreferredSize(new Dimension(34, 34));
        top = color[0];
        bottom = color[1];

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startHoverAnimation(true);
                startScaleAnimation(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startHoverAnimation(false);
                startScaleAnimation(false);
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

    public Color[] getTheme(){
        Color[] theme = {top, bottom};
        return theme;
    }

    @Override
    public Dimension getPreferredSize(){
        int base = 36;
        int extra = (int)(base * (scale - 1.0f));
        return new Dimension(base + extra, base + extra);
    }

    public void startHoverAnimation(boolean enter){
        float target = enter ? 0.5f : 0.8f; //so top takes 3/4

        if (hoverTimer != null && hoverTimer.isRunning()){
            hoverTimer.stop();
        }
        hoverTimer = new Timer(15, e -> {
            float speed = 0.03f;

            if (Math.abs(ratio - target) < speed){
                ratio = target;
                hoverTimer.stop();
            }
            else if (ratio < target){
                ratio += speed;
            }
            else {
                ratio -= speed;
            }
            repaint();
        });
        hoverTimer.start();
    }

    public void startScaleAnimation(boolean grow){
        float target = grow ? 1.07f : 1.0f;

        if (scaleTimer != null && scaleTimer.isRunning()){
            scaleTimer.stop();
        }

        scaleTimer = new Timer(15, e -> {
            float speed = 0.01f;
            if (Math.abs(scale - target) < speed){
                scale = target;
                scaleTimer.stop();
            }
            else if (scale < target){
                scale += speed;
            }
            else {
                scale -= speed;
            }
            repaint();
        });
        scaleTimer.start();
    }
    public void animationPress(){

    }
    public void startPressAnimation(boolean isPressed){

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = 17;
        float scaledRadius = radius * scale;
        int diameter = Math.round(scaledRadius * 2);
        int topLeftX = Math.round(centerX - scaledRadius);
        int topLeftY = Math.round(centerY - scaledRadius);

        float shift = 20f * (float)(0.5 - 0.5*Math.cos(Math.PI*ratio)); //prominency of the effect
        float startX = topLeftX + shift - 5;
        float startY = topLeftY + shift - 5;
        float endX = topLeftX + diameter + 5;
        float endY = topLeftY + diameter + 5;
        float[] frac = {0f, 0.98f};
        Color[] colors = {top, bottom};

        LinearGradientPaint gradient = new LinearGradientPaint(startX, startY, endX, endY, frac, colors);
        g2.setPaint(gradient);
        g2.fillOval(topLeftX, topLeftY, diameter, diameter);

        Color border = Utilities.BORDER;
        g2.setColor(border);
        g2.setStroke(new BasicStroke(1f));
        g2.drawOval(topLeftX,topLeftY,diameter-1, diameter-1);
    }
}