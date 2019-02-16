import java.awt.*;
public class TahaCannon extends Thing
{
    public TahaCannon( int x, int y, int speedX, int speedY )
    {
        super(x, y, speedX, 50);
        size = 10;
    }
    
    public void moveY()
    {
        y = y - 15;
    }
    
    public void draw( Graphics page )
    {
        page.setColor( Color.red );
        page.fillOval( x, y, size, size);
    }
}