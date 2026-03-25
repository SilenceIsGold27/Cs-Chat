import java.awt.*;
import javax.swing.*;

public class Utilities {
     //white by default
    public static Color TRANSPARENT_COLOR = new Color(0,0,0,0);
    public static Color BACKGROUND_DARK = new Color(230,230,230);
    public static Color BACKGROUND = new Color(242,242,242);
    public static Color BACKGROUND_LIGHT = new Color(255,255,255);
    public static Color TEXT = new Color(13,13,13);
    public static Color TEXT_MUTED = new Color(77,77,77);
    public static Color BORDER = new Color(179, 179, 179);
    public static Color HIGHLIGHT = new Color(255,255,255);
    public static Color ACCENT = new Color(220, 220, 220);
    public static Color ACCENT_LIGHT = new Color(245, 245, 245);
    private static boolean dark = false;

    public static String newFont = "Arial";
    public static int fontType = Font.PLAIN;
    public static int HEADING = 27;
    public static int SUBHEADING = 18;
    public static int BODY = 12;

    public static Color TRACK_SHADOW = new Color(200, 200, 200);
    public static Color TRACK_BG = new Color(220, 220, 220);
    public static Color TRACK_PROGRESS = new Color(160, 160, 160);
    public static Color TRACK_BALL = new Color(255,255, 255);
 
    public static void setLightTheme() {
        TRANSPARENT_COLOR = new Color(0,0,0,0);
        BACKGROUND_DARK = new Color(230,230,230);
        BACKGROUND = new Color(242,242,242);
        BACKGROUND_LIGHT = new Color(255,255,255);
        TEXT = new Color(13,13,13);
        TEXT_MUTED = new Color(77,77,77);
        BORDER = new Color(179, 179, 179);
        HIGHLIGHT = new Color(255,255,255);
        ACCENT = new Color(220, 220, 220);
        ACCENT_LIGHT = new Color(245, 245, 245);

        TRACK_SHADOW = new Color(200, 200, 200);
        TRACK_BG = new Color(220, 220, 220);
        TRACK_PROGRESS = new Color(160, 160, 160);
        TRACK_BALL = new Color(255,255, 255);
    }

    public static void setDarkTheme() {
        BACKGROUND_DARK = new Color(0,0,0);
        BACKGROUND = new Color(13,13,13);
        BACKGROUND_LIGHT = new Color(26,26,26);
        TEXT = new Color(242,242,242);
        TEXT_MUTED = new Color(179, 179, 179);
        BORDER = new Color(77, 77, 77);
        HIGHLIGHT = new Color(153, 153, 153);
        ACCENT = new Color(40,40,40);
        ACCENT_LIGHT = new Color(60,60,60);

        TRACK_SHADOW = new Color(30, 30, 30);
        TRACK_BG = new Color(55, 55, 55);
        TRACK_PROGRESS = new Color(120, 120, 120);
        TRACK_BALL = new Color(220, 220, 220);
    }

    public static void changeComponentTheme(Container container){
        //this will change all the buttons to the right color of theme
        if (container instanceof CustomPanel customPanel) {
            if (container.getName() == "panel" || 
                container.getName() == "messageArea" ||
                container.getName() == "bubbleWrapper" 
            ){
                customPanel.changeColor(BACKGROUND_DARK);
            }
            else{
                customPanel.changeColor(BACKGROUND);
            }
        }
        Component[] components = container.getComponents();
        for (Component component : components) {
            switch (component) {
                case JButton jButton -> {
                    if (component.getName()==("closeButton")) {
                        jButton.setForeground(TEXT);
                    }
                    jButton.setBackground(BACKGROUND);
                    jButton.setForeground(TEXT);
                }
                case JLabel jLabel -> {
                    if (jLabel.getName() != "messageLabel"){
                        jLabel.setForeground(TEXT);
                    }
                }
                case JSlider jSlider -> jSlider.setUI(new CustomSlider(jSlider));
                case JPanel jPanel -> jPanel.setBackground(BACKGROUND_DARK);
                default -> {
                }
            }
            if (component instanceof Container child){
                changeComponentTheme(child);
            }
        }
    }

    public static boolean settingBarPresent(Container container, String target){
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                if ((component.getName()).equals(target)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean modeCheck() {
        return dark;
    }

    public void modeChange(boolean mode){
        dark = mode;
    }
    
}
