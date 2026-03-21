import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

public class App {
    public static Border emptyBorder = BorderFactory.createEmptyBorder(5,5,5,5);
    public static Utilities palette = new Utilities();
    public static JFrame frame;
    public static CustomPanel panel;
    public static CustomButton settingButton;
    public static CustomPanel settingBar = createSettingGUI();
    public static CustomPanel northPanel;
    public static ChatPanel chatPanel;
    public static ProfilePanel profilePanel;
    public static InputPanel inputPanel;

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            createGUI();
            Utilities.changeComponentTheme(frame);
        });
    }
    public static void createGUI(){
        frame = new JFrame("CS Messenger");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        
        //main panel
        panel = new CustomPanel(new BorderLayout(), Utilities.BACKGROUND_DARK);
        panel.setName("panel");
        panel.setBorder(emptyBorder);

        //Setting Button
        settingButton = new CustomButton("Settings");
        northPanel = new CustomPanel(new BorderLayout(), Utilities.BACKGROUND);
        northPanel.add(settingButton, BorderLayout.EAST);
        northPanel.setBorder(emptyBorder);
        northPanel.setName("northPanel");
        panel.add(northPanel, BorderLayout.NORTH);

        settingButton.addActionListener(e -> {
            settingButton.setVisible(false);
            frame.getContentPane().add(settingBar, BorderLayout.EAST);
            Utilities.changeComponentTheme(frame);
            frame.revalidate();
            frame.repaint();

        });
        
        createChatPanel();
        //set colors and default theme
        Utilities.changeComponentTheme(frame);
        //add panels to frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static CustomPanel createSettingGUI(){
        //set the setting panel
        settingBar = new CustomPanel(new BorderLayout(), Utilities.BACKGROUND);
        settingBar.setPreferredSize(new Dimension(300, 0));
        settingBar.setName("settingBar");
        settingBar.setBorder(emptyBorder);

        //Settings Heading
        CustomLabel settingText = new CustomLabel("SETTINGS", SwingConstants.CENTER, Utilities.HEADING, Font.BOLD);
        settingText.setName("settingText");
        settingBar.add(settingText, BorderLayout.NORTH);

        //close button
        CustomButton closeButton = new CustomButton("Close");
        closeButton.setName("closeButton");
        closeButton.setCustomGradient(new Color(255, 153, 153), new Color(204, 0, 0), new Color(255,139,142), new Color(255,0,0));
        settingBar.add(closeButton, BorderLayout.SOUTH);

        //main panel of the setting panel (CENTER)
        CustomPanel mainSettingBarPanel = new CustomPanel(Utilities.BACKGROUND);
        mainSettingBarPanel.setLayout(new BoxLayout(mainSettingBarPanel, BoxLayout.Y_AXIS));
        mainSettingBarPanel.setName("mainSettingBarPanel");
        mainSettingBarPanel.setBorder(emptyBorder);
        mainSettingBarPanel.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
        settingBar.add(mainSettingBarPanel, BorderLayout.CENTER);

        //Theme heading
        CustomLabel themeLabel = new CustomLabel("Theme", SwingConstants.CENTER, Utilities.SUBHEADING, Font.BOLD);
        mainSettingBarPanel.add(themeLabel);

        //change to dark/white mode
        CustomButton themeButton = new CustomButton("Dark/White Mode");
        themeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        themeButton.setMaximumSize(themeButton.getPreferredSize());
        themeButton.setBorder(emptyBorder);
        mainSettingBarPanel.add(themeButton);

        //theme circle panel
        CustomPanel circlePanel = new CustomPanel(new GridLayout(3,6,7,7), Utilities.BACKGROUND);
        CircleDrawing[] circList = new CircleDrawing[17];
        for (int i  = 0; i < circList.length; i++){
            Color[] c = BubbleUtilities.THEMES.get(i);
            circList[i] = new CircleDrawing(c);

            circList[i].addActionListener(e -> {
                Bubble.changeColor(c);
            });

            circList[i].getPreferredSize();
            circlePanel.add(circList[i]);
        }
        circlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        circlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, circlePanel.getPreferredSize().height + 15));
        circlePanel.setBorder(emptyBorder);
        circlePanel.setName("circlePanel");
        mainSettingBarPanel.add(circlePanel);

        //font label
        CustomLabel fontLabel = new CustomLabel("Font", 0, Utilities.SUBHEADING, Font.BOLD);
        fontLabel.setName("fontLabel");
        mainSettingBarPanel.add(fontLabel);

        //panel to have both the slider and size display
        CustomPanel sliderText = new CustomPanel(Utilities.BACKGROUND);
        sliderText.setLayout(new BoxLayout(sliderText, BoxLayout.X_AXIS));
        sliderText.setBorder(emptyBorder);
        sliderText.setName("sliderText");
        //size label goes in the sliderText panel
        CustomLabel sizeLabel = new CustomLabel("Size", 0, Utilities.BODY, Font.PLAIN);
        sizeLabel.setBorder(emptyBorder);
        sizeLabel.setName("sizeLabel");
        sliderText.add(sizeLabel);

        //Slider after the label
        JSlider multiplierSlider = new JSlider(JSlider.HORIZONTAL, 75, 150, 100){
            @Override
            public Dimension getPreferredSize(){
                Dimension dim = super.getPreferredSize();
                dim.height = Math.max(dim.height, 40);
                return dim;
            }
        };
        multiplierSlider.setOpaque(false);
        multiplierSlider.setUI(new CustomSlider(multiplierSlider));
        multiplierSlider.setMaximumSize(multiplierSlider.getPreferredSize());
        multiplierSlider.setName("multiplierSlider");
        sliderText.add(multiplierSlider);

        sliderText.setAlignmentX(Component.LEFT_ALIGNMENT);
        sliderText.setMaximumSize(new Dimension(Integer.MAX_VALUE, sliderText.getPreferredSize().height));
        mainSettingBarPanel.add(sliderText);

        // Font button
        CustomButton fontButton = new CustomButton("Change Font");
        fontButton.setBorder(emptyBorder);
        fontButton.setMaximumSize(themeButton.getPreferredSize());
        fontButton.setName("fontButton");
        mainSettingBarPanel.add(fontButton);

        themeButton.addActionListener(e -> {
            if (Utilities.modeCheck() == false){
                //change to dark mode
                Utilities.setDarkTheme();
                panel.repaint();
                settingBar.repaint();
                palette.modeChange(true);
                
                Utilities.changeComponentTheme(frame);

            }
            else {
                //change to white mode
                Utilities.setLightTheme();
                panel.repaint();
                settingBar.repaint();
                palette.modeChange(false);

                Utilities.changeComponentTheme(frame);
            }
        });

        closeButton.addActionListener(e -> {
            settingButton.setVisible(true);
            frame.getContentPane().remove(settingBar);
            frame.revalidate();
            frame.repaint();
        });

        multiplierSlider.addChangeListener(e ->{
            int value =multiplierSlider.getValue();
            CustomLabel.MULTIPLIER = (float)value / 100;
            CustomLabel.changeFont();
        });

        fontButton.addActionListener(e ->{
            String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
            JList<String> listFont = new JList<>(fonts);
            listFont.setVisibleRowCount(12); // show 12 at a time
            listFont.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listFont.setFixedCellHeight(20);
            //get font and written in their style
            listFont.setCellRenderer(new DefaultListCellRenderer(){
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object val, int index, boolean isSelected, boolean cellHasFocus){
                    JLabel lab = (JLabel) super.getListCellRendererComponent(list, val, index, isSelected, cellHasFocus);
                    String name = val.toString();
                    lab.setFont(new Font(name, Font.PLAIN, 14));

                    return lab;
                }
            });

            //
            JScrollPane scroll = new JScrollPane(listFont);
            scroll.setPreferredSize(new Dimension(250, listFont.getFixedCellHeight() * 12));

            JPopupMenu pop = new JPopupMenu();
            pop.setLayout(new BorderLayout());
            pop.add(scroll, BorderLayout.CENTER);

            listFont.addListSelectionListener(ex -> {
                if (!ex.getValueIsAdjusting()){
                    String sel = listFont.getSelectedValue();
                    CustomLabel.applyFont(sel);
                    pop.setVisible(false);
                }
            });
            pop.show(fontButton, 0, fontButton.getHeight());
        });

        return settingBar;
    }

    public static void createChatPanel(){
        chatPanel = new ChatPanel();
        inputPanel = new InputPanel(chatPanel, Utilities.BACKGROUND);
        profilePanel = new ProfilePanel(chatPanel, Utilities.BACKGROUND);

        panel.add(chatPanel, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);
        panel.add(profilePanel, BorderLayout.WEST);

        chatPanel.setCurrentContact("Tommy");
        chatPanel.addMessage("Tommy", "Hello! 👋");
        chatPanel.addMessage("You", "Hi Tommy! 😊");
        chatPanel.addMessage("Tommy", "How are you? 🤔");
    }
}
