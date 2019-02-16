import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class Secret extends Thing
{
    private BufferedImage img;
    public Secret( int x, int y, int speedX, int speedY )
    {
        super(x, y, speedX, 50);
        size = 100000;
        try
        {
            img = ImageIO.read(new File("MainCar.png"));
        }catch(Exception e){}
        size = img.getWidth();
        height = img.getHeight();
    }

    public void moveY()
    {
        y = y - 15;
    }

    /*public void draw( Graphics page )
    {
        page.setColor( Color.green );
        page.fillRect( x, y, size, size);
    }*/
    public void draw( Graphics page)
    {

        page.drawImage(img,x,y,null); 
    }// end method draw
}
