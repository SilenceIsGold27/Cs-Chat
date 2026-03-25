import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;

public class CustomSlider extends BasicSliderUI{
    static final int T_HEIGHT = 8;
    static final int T_ARC = 5;
    static final Dimension THUMB_SIZE = new Dimension(20, 20);
    static final RoundRectangle2D.Float trackShape = new RoundRectangle2D.Float();

    public CustomSlider(final JSlider slider){
        super(slider);
    }

    @Override
    protected void calculateTrackRect(){
        super.calculateTrackRect();
        trackRect.y += (trackRect.height - T_HEIGHT) / 2;
        trackRect.height = T_HEIGHT;
        trackShape.setRoundRect(trackRect.x, trackRect.y, trackRect.width, trackRect.height, T_ARC, T_ARC);
    }

    @Override
    protected void calculateThumbLocation(){
        super.calculateThumbLocation();
        thumbRect.y += (trackRect.height - thumbRect.height) / 2;
        

    }

    @Override
    protected Dimension getThumbSize(){
        return THUMB_SIZE;
    }

    @Override
    public void paint(final Graphics g, final JComponent c){
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g, c);
    }

    @Override
    public void paintTrack(final Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        Shape clip = g2.getClip();


        //paint shadow
        g2.setColor(Utilities.TRACK_SHADOW);
        g2.fill(trackShape);

        //paint track bg
        g2.setColor(Utilities.TRACK_BG);
        g2.setClip(trackShape);
        trackShape.y += 1;
        g2.fill(trackShape);
        trackShape.y = trackRect.y;

        g2.setClip(clip);

        int thumbPosition = thumbRect.x + thumbRect.width / 2;
        g2.clipRect(0, 0, thumbPosition, slider.getHeight());
            
        g2.setColor(Utilities.TRACK_PROGRESS);//Progress before the ball
        g2.fill(trackShape);
        g2.setClip(clip);
    }

    @Override
    public void paintThumb(final Graphics g){
        g.setColor(Utilities.TRACK_BALL);
        g.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
    }

    @Override
    public void paintFocus(final Graphics g){}
}