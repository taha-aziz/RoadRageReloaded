import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class Car extends Thing
{

    private Color color;
    private BufferedImage img;
    
    public Car( int x, int y,  int speedX, int speedY, Color c)
    {
        super(x, y, speedX, 1);
        try
        {
            img = ImageIO.read(new File("car.png"));
        }catch(Exception e){}
        size = img.getWidth();
        height = img.getHeight(); 
        color = c;
        speedX = (int)(Math.random()*speedX + 1) * (int)Math.pow(-1, (int)(Math.random()*2+1) );
        speedY = (int)(Math.random()*speedY + 1) * (int)Math.pow(-1, (int)(Math.random()*2+1) );
    }

    public void moveX()
    {
       // x = 15;
    }

    public void moveY()
    {
        y = y + 6;

    }

    public void draw( Graphics page )
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

