import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel implements MouseWheelListener {
    public DrawPanel() {
        this.setBackground(Color.LIGHT_GRAY);
        this.addMouseWheelListener(this);
    }

    public static final int MARGIN = 4;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    public static double[] lightColor = new double[] {1, 1, 1};

    public static double[] objectColor = new double[] { 0.5, 0.5, 0.5};


    public static double[] ligthPosition = new double[] {0.5, 0.5, 5};

    public Mesh mesh = new Mesh();;

    @Override
    public void paint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        mesh.drawMesh(g);

        g.setColor(Color.GREEN);
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                g.drawOval(MARGIN + i*200, MARGIN + j*200,8,8);
                g.fillOval(MARGIN + i*200, MARGIN + j*200,8,8);
            }
            g.drawLine(2*MARGIN + i * 200, 2*MARGIN, 2*MARGIN + i * 200, 2*MARGIN + WIDTH);
        }
        for(int j = 0; j < 4; j++) {
            g.drawLine(2*MARGIN,2*MARGIN + j * 200,  2*MARGIN + WIDTH, 2*MARGIN + j * 200);
        }

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(dist(e.getX(), e.getY(), 2*MARGIN + i * 200, 2*MARGIN + j*200) <= 4) {
                    mesh.z[i][j] -=  0.1 * notches;
                }
            }
        }
        MainPanel.drawPanel.repaint();
    }

    public double dist(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }
}
