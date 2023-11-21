import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainFrame extends JFrame {
    private final MainPanel mainPanel;


    public MainFrame() {
        this.setSize(1000,650);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new MainPanel();
        this.add(mainPanel);
        this.setVisible(true);
    }

}
