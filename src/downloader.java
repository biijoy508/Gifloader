import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author datampq
 */
public class downloader implements Runnable {

    public boolean done = false;
    private final frame f;
    Thread t;

    public downloader(final String thread_id, final String folder, final frame f) {
        this.f = f;
        if (t == null) {
            t = new Thread(this, thread_id);
            t.start();;
        }
    }

    @Override
    public void run() {

        try {

            URL yahoo = new URL("http://tv.giphy.com/v1/gifs/tv?api_key=CW27AW0nlp5u0&tag="+f.tag+"&limit=5&internal=yes&callback=?");
            URLConnection yc = yahoo.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine;
            StringBuilder buffer = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                buffer.append(inputLine);
            }

            in.close();
            String toString = buffer.toString();
            toString = toString.replaceAll("\\\\", "");
            String id = "id";
            String id_save = "";
            String url_save = "";
            String url = "image_original_url";
            for (int i = 0; i < toString.length() - 50; i++) {

                String substring = toString.substring(i, i + 2);
                if (substring.equals(id) && i < 40) {
                    id_save = toString.substring(i + 5, i + 18);

                }

                String subs2 = toString.substring(i, i + 18);
                if (subs2.equals(url)) {
                    String[] split = toString.split("image_url");
                    String[] split1 = split[0].split(",");
                    String[] split2 = split1[split1.length - 2].split("\"");
                    // String[] split1 = split[0].split(":");
                    String replaceAll1 = split2[split2.length - 1].replaceAll("\"", "");
                    url_save = replaceAll1;
                }

            }
            f.Save(id_save, url_save);
        } catch (MalformedURLException ex) {
            System.out.println("MalformedURLException");
        } catch (IOException ex) {
            System.out.println("io exception");
        }

        done = true;
    }

}