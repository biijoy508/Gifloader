import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author datampq
 */
public class button extends JPanel implements MouseListener {

    private final frame f;
    private final String action;

    public button(final frame f, final String a, final String label) {
        this.f = f;
        action = a;
        this.setBackground(Color.white);
        this.setPreferredSize(new Dimension(200, 30));
        JLabel la = new JLabel(label);
        la.setForeground(Color.BLACK);

        add(la);

        addMouseListener(this);

    }

    @Override
    public void mouseClicked(final MouseEvent me) {
        if (action.equals("folder")) {
            f.folder();
        } else {
            f.st();
        }
    }

    @Override
    public void mousePressed(final MouseEvent me) {

    }

    @Override
    public void mouseReleased(final MouseEvent me) {

    }

    @Override
    public void mouseEntered(final MouseEvent me) {
        this.setBackground(Color.lightGray);
    }

    @Override
    public void mouseExited(final MouseEvent me) {
        this.setBackground(Color.white);
    }

}