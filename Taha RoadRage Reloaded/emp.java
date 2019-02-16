import java.awt.*;
public class emp extends Thing
{
    int size = 10;
    public emp( int x, int y, int speedX, int speedY )
    {
        super(x, y, 0, 0);
        
    }
      public void setSize( int change )
    {
        size =  change;
    }
    public void moveY()
    {
        y = y ;
    }
    
    public void draw( Graphics page )
    {
        page.setColor( Color.magenta );
        page.fillOval( x, y, size, size);
    }
}