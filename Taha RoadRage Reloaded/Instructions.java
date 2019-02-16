
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.image.*;

public class Instructions extends JPanel
    {
    JLabel left, right, up, down, bomb, Info1, bullets;

    public Instructions()
        {
        setPreferredSize( new Dimension( 200, 150 ) );
        setBackground( Color.green );
        left = new JLabel( "Left Arrow = Move Left" );
        right = new JLabel( "Right Arrow = Move Right" );
        up = new JLabel( "Up Arrow = Move Up");
        down = new JLabel( "Down Arrow = Move Down" );
        bullets = new JLabel( "SPACE = Fire the TahaCannon" );
        Info1 = new JLabel( "Beware: the police are watching...");
        
        this.add(left);
        this.add(right);
        this.add(up);
        this.add(down);
        
        this.add(bullets);
        this.add(Info1);
        
        }
// end constructor Instructions
    }// end class Instructions