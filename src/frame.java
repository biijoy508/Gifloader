import java.awt.Dimension;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author datampq
 */
public class frame extends JFrame implements Runnable {

    private final String status = "idle";
    private JLabel stat;
    private JLabel preview;
    public int numDowns = 0;
    private long totalDownloaded = 0;
    public String tag;
    JTextField field;
    String folder;
    Thread t;

    public frame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(content());
        pack();
        this.setVisible(true);

    }

    private JPanel content() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(buttonPanel());
        field = new JTextField(20);
        content.add(field);
        content.add(statusPanel());
        content.add(previewPanel());
        return content;
    }

    private JPanel buttonPanel() {
        JPanel content = new JPanel();
        button b1 = new button(this, "folder", "Select folder");
        content.add(b1);
        button b2 = new button(this, "st", "Start");
        content.add(b2);
        return content;
    }

    private JPanel statusPanel() {
        JPanel content = new JPanel();
        stat = new JLabel(status);
        content.add(stat);
        return content;
    }

    private JPanel previewPanel() {
        JPanel content = new JPanel();
        content.setPreferredSize(new Dimension(600, 500));
        preview = new JLabel();
        content.add(preview);
        return content;
    }

    @Override
    public void run() {
        int id = 0;
        while (true) {
            id++;
            downloader down = new downloader("id_" + id, folder, this);
            while (!down.done) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    System.out.println("Thread Interrupted");
                }
            }
            numDowns++;
            stat.setText("Downloading, files downloaded:" + numDowns + " (" + totalDownloaded + "bytes)");
            revalidate();

        }
    }

    public void folder() {
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.showSaveDialog(null);

        folder = f.getSelectedFile().getAbsolutePath() + "\\";
    }

    public void st() {
        if (!field.getText().isEmpty()) {
            tag = field.getText();
        }
        if (t == null) {
            t = new Thread(this, "main");
            t.start();
        } else {

            t.interrupt();
        }

    }

    public void Save(final String id, final String url) {
        try {
            byte[] b = new byte[1];
            URL ur = new URL(url);
            URLConnection urlConnection = ur.openConnection();
            urlConnection.connect();
            DataInputStream di = new DataInputStream(urlConnection.getInputStream());
            File file=null;
            if(tag.isEmpty()){
             file = new File(folder +"mpq_" + id + ".gif");
            }else{
             File f = new File(folder + tag + "\\");
             f.mkdirs();
             file = new File(folder + tag + "\\"+"_" + id + ".gif");
            }


            FileOutputStream fo = new FileOutputStream(file);
            while (-1 != di.read(b, 0, 1)) {
                fo.write(b, 0, 1);
            }
            di.close();
            fo.close();
            ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
            totalDownloaded += file.length();
            preview.setIcon(imageIcon);
            this.revalidate();
        } catch (MalformedURLException ex) {

        } catch (IOException ex) {

        }

    }
}