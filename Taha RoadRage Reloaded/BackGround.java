import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class BackGround extends Thing
{
    private BufferedImage img;
    public BackGround( int xPos, int yPos, int xVel, int yVel)
    {
        super( xPos, yPos, xVel, yVel);
        try
        {
            img = ImageIO.read(new File("Background.png"));
        }catch(Exception e){}
    }
    public void moveY()
    {
        y += 3;
    }// end method moveDown
    public void draw( Graphics page, boolean over )
    {

        page.drawImage(img,x,y,null); 
    }// end method draw
}