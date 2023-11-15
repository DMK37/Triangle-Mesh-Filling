import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsPanel extends JPanel implements ActionListener, ChangeListener {

    public static JSlider KdSlider = new JSlider(0, 100);
    public static JSlider KsSlider = new JSlider(0, 100);
    public static JSlider mSlider = new JSlider(0, 100);

    public  JButton lightColorButton = new JButton("Pick light color");

    public  JButton objectColorButton = new JButton("Pick object color");

    public OptionsPanel() {

        this.setLayout(new GridLayout(2,3));
        KdSlider.setMajorTickSpacing(20);
        KdSlider.setPaintTicks(true);
        KdSlider.setPaintLabels(true);
        KdSlider.addChangeListener(this);

        KsSlider.setMajorTickSpacing(20);
        KsSlider.setPaintTicks(true);
        KsSlider.setPaintLabels(true);
        KsSlider.addChangeListener(this);

        mSlider.setMajorTickSpacing(20);
        mSlider.setPaintTicks(true);
        mSlider.setPaintLabels(true);
        mSlider.addChangeListener(this);

        lightColorButton.addActionListener(this);
        objectColorButton.addActionListener(this);

        var panel1 = new JPanel();
        panel1.add(new JLabel("Kd"));
        panel1.add(KdSlider);
        this.add(panel1);
        var panel2 = new JPanel();
        panel2.add(new JLabel("Ks"));
        panel2.add(KsSlider);
        this.add(panel2);
        var panel3 = new JPanel();
        panel3.add(new JLabel("m"));
        panel3.add(mSlider);
        this.add(panel3);
        this.add(lightColorButton);
        this.add(objectColorButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == lightColorButton) {
            JColorChooser colorChooser = new JColorChooser();
            Color color = JColorChooser.showDialog(null, "Pick a Color", Color.white);
            int r = color.getRed();
            int g = color.getGreen();
            int b = color.getBlue();
            DrawPanel.lightColor = new double[]{(double) r / 255, (double) g / 255, (double) b / 255};
            MainPanel.drawPanel.repaint();
        }

        if(e.getSource() == objectColorButton) {
            JColorChooser colorChooser = new JColorChooser();
            Color color = JColorChooser.showDialog(null, "Pick a Color", Color.white);
            int r = color.getRed();
            int g = color.getGreen();
            int b = color.getBlue();
            DrawPanel.objectColor = new double[]{(double) r / 255, (double) g / 255, (double) b / 255};
           MainPanel.drawPanel.repaint();
        }
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        MainPanel.drawPanel.repaint();
    }
}
