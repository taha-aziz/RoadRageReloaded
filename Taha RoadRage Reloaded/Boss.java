import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class Boss extends Police
{
    private Color color;
    private BufferedImage img;
    public Boss( int x, int y, int speedX, int speedY)
    {
        super(x, y, speedX, speedY);
        try
        {
            img = ImageIO.read(new File("PoliceTank.png"));
        }catch(Exception e){}
        size = img.getWidth();
        height = img.getHeight();
        speedX = (int)(Math.random()*speedX + 1) * (int)Math.pow(-1, (int)(Math.random()*2+1) );
        speedY = (int)(Math.random()*speedY + 1) * (int)Math.pow(-1, (int)(Math.random()*2+1) );
    }
    
     
    public void moveUp()
    {
        y -= 1;
    }
    
    public void moveDown()
    {
        y += 1;
    }
    
    
    public void moveLeft()
    {
        x = x - 1;
    }
    
    public void moveRight()
    {
        x = x + 1;
        
        
    }
    
    public void draw( Graphics page , boolean spawned)
    {
        page.drawImage(img,x,y,null); 
    }
    
      public boolean deleteCar() {

        if(y>720) {
            return true;
        }
        return false;
    }
}