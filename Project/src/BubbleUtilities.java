import java.awt.*;
import java.util.List;

public class BubbleUtilities {
    //default is blue
    public static Color TOP = new Color(153, 104, 255);
    public static Color BOTTOM = new Color(0, 102, 104);

    //red
    public static Color[] flame(){
        TOP = new Color(255,0,0);
        BOTTOM = new Color(253,207,88);
        return new Color[] {TOP, BOTTOM};
    }

    public static Color[] pureL(){
        TOP = new Color(51,51,51);
        BOTTOM = new Color(221,24,24);
        return new Color[] {TOP, BOTTOM};
    }

    public static Color[] sinCityRed(){
        TOP = new Color(237,33,58);
        BOTTOM = new Color(147,41,30);
        return new Color[] {TOP, BOTTOM};
    }
    //orange
    public static Color[] citrusPeel(){
        TOP = new Color(253, 200, 48);
        BOTTOM = new Color(243,115,53);
        return new Color[] {TOP, BOTTOM};
    }

    public static Color[] soundCloud(){
        TOP = new Color(254,140,0);
        BOTTOM = new Color(248,54,0);
        return new Color[] {TOP, BOTTOM};
    }
    //yellow
    public static Color[] rea(){
        TOP = new Color(255,224,0);
        BOTTOM = new Color(121, 159, 12);
        return new Color[] {TOP, BOTTOM};
    }
    //blue
    public static Color[] hyperBlue(){
        TOP = new Color(89,205,233);
        BOTTOM = new Color(10,42,136);
        return new Color[] {TOP, BOTTOM};
    }
    public static Color[] visualBlue() {
        TOP = new Color(0,61,77);
        BOTTOM = new Color(0, 201, 150);
        return new Color[] {TOP, BOTTOM};
    }
    public static Color[] bleem(){
        TOP = new Color(66,132,219);
        BOTTOM = new Color(41,234,196);
        return new Color[] {TOP, BOTTOM};
    }
    //green
    public static Color[] dustyGrass(){
        TOP = new Color(212, 252, 121);
        BOTTOM = new Color (150, 230, 161);
        return new Color[] {TOP, BOTTOM};
    }
    public static Color[] neonGreen(){
        TOP = new Color(129,255,138);
        BOTTOM = new Color (100,154,94);
        return new Color[] {TOP, BOTTOM};
    }
    public static Color[] mojito(){
        TOP = new Color(29,151,108);
        BOTTOM = new Color(147,249,185);
        return new Color[] {TOP, BOTTOM};
    }
    //pink
    public static Color[] shifter(){
        TOP = new Color(188,78,156);
        BOTTOM = new Color(248,7,89);
        return new Color[] {TOP, BOTTOM};
    }
    public static Color[] yoda(){
        TOP = new Color(255,0,153);
        BOTTOM = new Color(73,50,64);
        return new Color[] {TOP, BOTTOM};
    }
    public static Color[] vanusa(){
        TOP = new Color(218,68,83);
        BOTTOM = new Color(137,33,107);
        return new Color[] {TOP, BOTTOM};
    }
    //grey
    public static Color[] gradeGrey(){
        TOP = new Color(189, 195, 199);
        BOTTOM = new Color(44,62,80);
        return new Color[] {TOP, BOTTOM};
    }
    public static Color[] afternoon(){
        TOP = new Color(0,12,64);
        BOTTOM = new Color(96,125,139);
        return new Color[] {TOP, BOTTOM};
    }

    public static final List<Color[]> THEMES = List.of(
        flame(),
        pureL(),
        sinCityRed(),
        citrusPeel(),
        soundCloud(),
        rea(),
        hyperBlue(),
        visualBlue(),
        bleem(),
        dustyGrass(),
        neonGreen(),
        mojito(),
        shifter(),
        yoda(),
        vanusa(),
        gradeGrey(),
        afternoon()
    );
}
