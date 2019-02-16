import java.awt.*;
public class PoliceGun extends TahaCannon
{
    public int yspeed;
    public PoliceGun( int x, int y,  int speedX, int speedY )
    {
        super(x, y, speedX, speedY);
        yspeed = 12;
        yspeed += speedY;
    }
    
    public void moveY()
    {
        y = y - yspeed;
    }
    
    public void draw( Graphics page )
    {
        page.setColor( Color.blue );
        page.fillOval( x, y, size, size );
    }
}