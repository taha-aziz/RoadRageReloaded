import java.io.*; 
import java.net.*;

public class ScoreThread implements Runnable {
    int score;
    String name;
    public ScoreThread(int inputScore, String inputName) {
        try {
            score = inputScore;
            name = URLEncoder.encode(inputName, "UTF-8");
        } catch(Exception e) {
        }
    }

    public void run() {
        try {
            String url = "http://taha.comuv.com/score.php?name=" + name +"&score=" + Integer.toString(score);
            URLConnection connection = new URL(url).openConnection();  
            int length = connection.getContentLength();  
            InputStreamReader in = new InputStreamReader(connection.getInputStream());  
            StringWriter out = new StringWriter(length);  

        } catch (Exception e) {
        }
    }
}
