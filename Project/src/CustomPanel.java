import javax.swing.*;
import java.awt.*;

public class CustomPanel extends JPanel{
    Color color;

    public CustomPanel(LayoutManager layout, Color color){
        super(layout);
        this.color = color;
    }

    public CustomPanel(Color color){
        super();
        this.color = color;
    }

    public void changeColor(Color color){
        this.color = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();

        g2.setPaint(color);
        g2.fillRect(0, 0, width, height);
        g2.dispose();
    }
}
