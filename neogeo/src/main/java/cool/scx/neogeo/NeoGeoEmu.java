package cool.scx.neogeo;

import javax.swing.*;
import java.awt.*;

public class NeoGeoEmu {

    private JFrame frame;
    private JPanel screenPanel;

    public NeoGeoEmu() {
        frame = new JFrame("NeoGeo Emulator");
        frame.setSize(960, 672);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        screenPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.GREEN);
                g.drawString("Neo Geo Emulator", 50, 50);
            }
        };

        frame.add(screenPanel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NeoGeoEmu::new);
    }
}
