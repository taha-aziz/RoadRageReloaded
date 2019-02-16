import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class Player extends Thing
{
    private int points = 0;
     private BufferedImage img;
    public Player(int x, int y, int speedX, int speedY)
    {
        super(x, y, 6, 6);
        try
        {
            img = ImageIO.read(new File("MainCar.png"));
        }catch(Exception e){}
        size = img.getWidth();
        height = img.getHeight();
    }// end constructor Player
    
    public void moveX()
    {
        x = x - 6;
    }
    
    public void moveY()
    {
        
        y = y - 20;
    }
    
    public void changePoints(int change)
    {
        points += change;//changes the points according to the parameter passed into this
        //metod; that is, the value of change
    }// end method changePoints
    
    public int getPoints()
    {
        return points;// returns the player's current points
    }// end method getPoints

    public void draw( Graphics page, boolean over )
    {

        page.drawImage(img,x,y,null); 
    }// end method draw
}