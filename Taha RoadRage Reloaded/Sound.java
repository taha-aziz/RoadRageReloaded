import java.applet.*;
import java.net.URL;
public class Sound
{
    private AudioClip a;
    
    public Sound()
    {
        a = Applet.newAudioClip(getClass().getResource("Space.wav"));
        
    }
    
    public void loop()
    {
        a.loop();
    }
    
    public void stop()
    {
        a.stop();
    }
    
    public void play()
    {
        a.play();
    }
}
