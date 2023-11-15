import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    public static DrawPanel drawPanel;

    private final OptionsPanel optionsPanel;

    public MainPanel() {
        this.setLayout(new BorderLayout());
        drawPanel = new DrawPanel();
        optionsPanel = new OptionsPanel();
        this.add(optionsPanel, BorderLayout.EAST);
        this.add(drawPanel);
    }
}
