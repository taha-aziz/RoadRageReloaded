import java.awt.*;
public abstract class Thing
{
    protected int x;//creates a private variable for the player's x position
    protected int y;//creates a private variable for the player's y position
    protected int size, height;
    protected int speedX, speedY;
    public Thing( int xPos, int yPos, int howFastX, int howFastY )
    {
        //but how would i know how large hitboxes in cars have to be like can we try it out on car class
        x = xPos;
        y = yPos;
        speedX = howFastX;
        speedY = howFastY;
    }// end constructor Player

    public void moveX()
    {
        x = x + 20;//increases the x value by 10; i.e., moves to the right
    }// end method moveX

    public void moveX(boolean right)
    {
        if(right==true)
        {
            x = x + speedX;
        }
        else
        {
            x = x - speedX;
        }
    }// end method moveX

    public void moveY()
    {
        y = y - 20;
    }// end method moveDown

    public void moveY(boolean up)
    {
        if(up==true)
        {
            y = y - speedY;
        }
        else
        {
            y = y + speedY;
        }
    }// end method moveUp

    public void setSpeedX( int change )
    {
        speedX += change;
    }

    public void setSpeedY( int change )
    {
        speedX += change;
    }

    public int getLeft()
    {
        return x;//returns the position of the player's left edge
    }// end method getLeft

    public int getRight()
    {
        return x + size;//returns the position of the player's right edge
    }// end method getRight

    public int getTop()
    {
        return y;// returns the player's y position
    }// end method getTop

    public int getBottom()
    {
        return y + height;// returns the player's y position
    }// end method getBottom    
}