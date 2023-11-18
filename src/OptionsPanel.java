import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OptionsPanel extends JPanel implements ActionListener, ChangeListener {

    public static JSlider KdSlider = new JSlider(0, 100);
    public static JSlider KsSlider = new JSlider(0, 100);
    public static JSlider mSlider = new JSlider(0, 100);

    public  JButton lightColorButton = new JButton("Pick light color");

    public  JButton objectColorButton = new JButton("Pick object color");

    public  JButton imageButton = new JButton("Pick image");

    public static JCheckBox normalMapBox = new JCheckBox("NormalMap");

    public OptionsPanel() {

        this.setLayout(new GridLayout(3,3));
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
        imageButton.addActionListener(this);
        normalMapBox.addActionListener(this);

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
        this.add(imageButton);
        this.add(normalMapBox);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == lightColorButton) {
            JColorChooser colorChooser = new JColorChooser();
            Color color = JColorChooser.showDialog(null, "Pick a Color", Color.white);
            if(color == null)
                return;
            int r = color.getRed();
            int g = color.getGreen();
            int b = color.getBlue();
            DrawPanel.lightColor = new double[]{(double) r / 255, (double) g / 255, (double) b / 255};
            MainPanel.drawPanel.repaint();
        }

        if(e.getSource() == objectColorButton) {
            JColorChooser colorChooser = new JColorChooser();
            Color color = JColorChooser.showDialog(null, "Pick a Color", Color.white);
            if(color == null)
                return;
            int r = color.getRed();
            int g = color.getGreen();
            int b = color.getBlue();
            DrawPanel.objectColor = new double[]{(double) r / 255, (double) g / 255, (double) b / 255};
            DrawPanel.image = null;
           MainPanel.drawPanel.repaint();
        }
        if(e.getSource() == imageButton) {
            String userhome = System.getProperty("user.dir");
            JFileChooser fileChooser = new JFileChooser(userhome);
            int response = fileChooser.showOpenDialog(null);
            if(response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    BufferedImage img = ImageIO.read(file);
                    DrawPanel.image = resize(img, DrawPanel.HEIGHT, DrawPanel.WIDTH);
                } catch (IOException ex) {
                    System.out.println("exception");
                }
                MainPanel.drawPanel.repaint();
            }
        }
        if(e.getSource() == normalMapBox) {
            MainPanel.drawPanel.repaint();
        }
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        MainPanel.drawPanel.repaint();
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
