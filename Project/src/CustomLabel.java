import java.awt.*;
import javax.swing.*;

public class CustomLabel extends JLabel{
    public static String currentFont = "Dialog";
    public int fontType;
    public int size;

    public static String[] allFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    public static float MULTIPLIER = 1.0f;

    public CustomLabel(String text,int swing, int size, int type){
        super(text, swing);
        this.putClientProperty("baseFontSize", size);
        this.fontType = type;

        Font ex = new Font(currentFont, type, (int)(size * MULTIPLIER));
        this.setFont(ex);
    }

    public static void changeFont(){
        Window window = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
        if (window != null){
            updateFontRecursively(window);
            window.revalidate();
            window.repaint();
        }
    }

    public static void updateFontRecursively(Component comp){ // this will go through all component and change the font
        if (comp instanceof JComponent jc){
            Object base = jc.getClientProperty("baseFontSize");
        
            if (base instanceof Integer baseSize){
                int newSize = (int)(baseSize * MULTIPLIER);
                Font old = comp.getFont();
                int style = old.getStyle();
                Font newFont = new Font(currentFont, style, newSize);
                comp.setFont(newFont);
            }
        }

        if (comp instanceof Container cont){
            for (Component sub : cont.getComponents()){
                updateFontRecursively(sub);
            }
        }
    }

    public static void applyFont(String font){
        currentFont = font;

        for (Window w : Window.getWindows()){
            updateFontRecursively(w);
            w.revalidate();
            w.repaint();
        }
    }

}