import java.awt.*;
public class SecretBase extends Secret
{
    public SecretBase( int x, int y,  int speedX, int speedY )
    {
        super(x, y, speedX, speedY);
    }
    
    public void moveY()
    {
        y = y - 10;
    }
    
    public void draw( Graphics page )
    {
        page.setColor( Color.green );
        page.fillRect( x, y, size, size );
    }
}