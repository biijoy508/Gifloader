
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Test extends JFrame{
    JPanel contentPane;
    JLabel imageLabel = new JLabel();
    JLabel headerLabel = new JLabel();

    public Test() {
        /**
         * Example of showing gif in JPanel
         */
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            contentPane = (JPanel) getContentPane();
            contentPane.setLayout(new BorderLayout());
            setSize(new Dimension(600, 600));
            setTitle("Your Job Crashed!");
            // add the header label
            headerLabel.setFont(new java.awt.Font("Comic Sans MS", Font.BOLD, 16));
            headerLabel.setText("   Ivar loves massage!");
            contentPane.add(headerLabel, java.awt.BorderLayout.NORTH);
            // add the image label
            URL url = new URL("https://static1.squarespace.com/static/552a5cc4e4b059a56a050501/565f6b57e4b0d9b44ab87107/56602a71e4b0b44fe6b93117/1449143126373/NYCGifathon24.gif");
            //URL url = new URL("https://media.giphy.com/media/xUA7aV0Qt03RXTHQ76/source.gif");
            ImageIcon ii = new ImageIcon(url);
            Thread.sleep(1500);
            imageLabel.setIcon(ii);
            contentPane.add(imageLabel, java.awt.BorderLayout.CENTER);
            // show it
            this.setLocationRelativeTo(null);
            this.setVisible(true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void main(final String[] args) throws Exception {

       Test http = new Test();
       http.sendGet();

    }

    private void sendGet() throws Exception {
/**
 * Here i took api calling string which returns a JSON string which is saved in string builder, using Gson, i have
 * parsed the string and saved only url s in an array
 */
        String url = "http://api.giphy.com/v1/gifs/search?q=ryan+gosling&api_key=PHnI1QxTOXHELS263ouMQrxTBVJY3JNY&limit=5";
        URL obj = new URL(url);
        URLConnection urlConnection = obj.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String line;
        StringBuilder sBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null){
            sBuilder.append(line);
        }

        JsonParser jParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jParser.parse(sBuilder.toString());
        JsonArray jArray = jsonObject.get("data").getAsJsonArray();

        ArrayList<String> urList = new ArrayList<String>();
        for (JsonElement jsObject : jArray){
            urList.add(jsObject.getAsJsonObject().get("url").toString());
        }

        urList.forEach(System.out::println);

        bufferedReader.close();

    }

    /**
     * This method somply downloads images and gifs
     * @param urlGiphy
     * @throws InterruptedException
     * @throws IOException
     */
    public void showLoader() throws InterruptedException, IOException{
        URL url = new URL("https://yt3.ggpht.com/-mZZQOxiRkUA/AAAAAAAAAAI/AAAAAAAAAAA/KmWyE1njhGs/s900-c-k-no-mo-rj-c0xffffff/photo.jpg");
        Thread.sleep(1000);
         ImageIcon icon = new ImageIcon(url);
         Image  image = icon.getImage();
         RenderedImage rendered = null;
         if (image instanceof RenderedImage)
         {
             rendered = (RenderedImage)image;
         }
         else
         {
             BufferedImage buffered = new BufferedImage(
                 icon.getIconWidth(),
                 icon.getIconHeight(),
                 BufferedImage.TYPE_INT_RGB
             );
             Graphics2D g = buffered.createGraphics();
             g.drawImage(image, 0, 0, null);
             g.dispose();
             rendered = buffered;
         }
         ImageIO.write(rendered, "JPEG", new File("image.jpg"));
         System.out.println("done");
    }


}
