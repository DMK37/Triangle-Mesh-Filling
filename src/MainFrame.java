import javax.swing.*;

public class MainFrame extends JFrame {
    private final MainPanel mainPanel;


    public MainFrame() {
        this.setSize(1300,750);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new MainPanel();
        this.add(mainPanel);
        this.setVisible(true);
    }
}
