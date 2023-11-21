import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class DrawPanel extends JPanel implements MouseWheelListener, ActionListener {


    public static final int MARGIN = 4;
    public static final int WIDTH = 512;
    public static final int HEIGHT = 512;

    public static float[] lightColor = new float[]{1, 1, 1};

    public static float[] objectColor = new float[]{0.5f, 0.5f, 0.5f};


    public static float[] ligthPosition = new float[]{0.5f, 0.5f, 0.5f};

    public float originX = 0.5f;
    public float originY = 0.5f;

    public float angle = 0;

    public Mesh mesh = new Mesh();

    public static BufferedImage image;

    public static Timer timer;
    public DrawPanel() {
        String path = System.getProperty("user.dir") + "/RadialCracks_N.jpg";
        File file = new File(path);
        try {
            BufferedImage img = ImageIO.read(file);
            image = OptionsPanel.resize(img, DrawPanel.HEIGHT, DrawPanel.WIDTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.setFocusable(true);
        this.addMouseWheelListener(this);
        timer = new Timer(100, this);
        timer.start();
    }

    @Override
    public void paint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        //if(Mesh.numberInRow != 2 * OptionsPanel.triangleSlider.getValue()) {
            //Mesh.numberInRow = 2 * OptionsPanel.triangleSlider.getValue();
            //mesh = new Mesh();
        //}
        try {
            mesh.drawMesh(g);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        g.setColor(Color.GREEN);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                g.drawOval(MARGIN + i * WIDTH / 3, MARGIN + j * WIDTH / 3, 8, 8);
                g.fillOval(MARGIN + i * WIDTH / 3, MARGIN + j * WIDTH / 3, 8, 8);
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if((i == 0 && j == 0) ||(i == 3 && j == 0) || (i == 0 && j == 3) || (i == 3 && j == 3)) continue;
                if (dist(e.getX(), e.getY(), 2 * MARGIN + i * WIDTH / 3, 2 * MARGIN + j * WIDTH / 3) <= 4) {
                    mesh.z[i][j] -= (float) (0.1 * notches);
                }
            }
        }
        MainPanel.drawPanel.repaint();
    }

    public float dist(int x1, int y1, int x2, int y2) {
        return (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ligthPosition[0] = (float) (originX + Math.cos(angle) * 0.3);
        ligthPosition[1] = (float) (originY + Math.sin(angle) * 0.3);
        angle += 0.5f;
        repaint();
    }
}
