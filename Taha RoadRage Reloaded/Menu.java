import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.image.*;

public class Menu extends JPanel implements KeyListener, Runnable, ActionListener
{
    private int WIDTH = 800;
    private int HEIGHT = 600;
    private int bestScore = 0, bulletCount, maxBullets, count, newTime, bombs;
    private boolean start = true;
    private JLabel message;
    private Thread theGame;
    private boolean left, right, up, down, over, end;
    private double time;
    private JButton buttonZero, buttonOne;
    private Image img;
    
    public Menu()
    {
        setPreferredSize( new Dimension( WIDTH, HEIGHT ) );
        img = Toolkit.getDefaultToolkit().createImage("Screenshot1.png");
        buttonZero = new JButton( "Quit" );
        buttonOne = new JButton( "Play" );
        this.add( buttonZero );
        this.add( buttonOne );
        buttonZero.addActionListener(this);
        buttonOne.addActionListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);
        message = new JLabel( "Main Menu" );
        this.add( message );

        message.setFont( new Font("Times New Roman", Font.BOLD, 24 ) );
        message.setForeground(Color.WHITE);
        message.setVisible(true);

        theGame = new Thread(this);
        //this.reset();
        //this.repaint();
    }


    public void actionPerformed( ActionEvent event )
    {
        JButton button = (JButton)event.getSource();
        if( button == buttonZero )
        {
            end = false; 
            //this.reset();
            
        }
        else
        {
            message.setText( "Tahahahaha." );
            buttonZero.setVisible(true);
            buttonOne.setVisible(true);
            //this.repaint();
            end = true;
            JFrame frame = new JFrame("XTREME ROAD RAGE: TAHA EDITION");
            frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
            frame.setLocation( 100, 100 );
            Game game = new Game();
            frame.getContentPane().add(game);
            frame.pack();
            frame.setVisible(true);        
            game.setFocusable(true);
        }
    }


    public void reset()
    {
        buttonZero.setVisible(true);
        buttonOne.setVisible(true);
        buttonZero.setFocusable(false);
        buttonOne.setFocusable(false);
        this.setFocusable(true);
        over = false;

    }


    public void run()
    {
        while(!end)
        {
            
            while(!over)
            {
                

                if( (int)time / newTime == count )
                {

                    count++;
                }
                //this.repaint();
                try
                {
                    Thread.sleep(10);
                }
                catch(InterruptedException e){}
                time += 0.010;
                message.setText( "Don't drop that Tahaha." );
            }
            //this.repaint();
            try
            {
                Thread.sleep(2000);
            }
            catch(InterruptedException e){}
        }


    }


    public void collide()
    {
        for( int i = 0; i < 2; )
        {   
            over = true;
            buttonZero.setVisible(true);
            buttonOne.setVisible(true);
        }


    }


    public void keyPressed( KeyEvent event )
    {

    }


    public void keyTyped( KeyEvent event )//required method that does nothing


    {

    }
    public void keyReleased( KeyEvent event )//required method that does nothing
    {

    }
    public void paintComponent( Graphics page )
    {
        //super.paintComponent( page );
        page.drawImage(img,0,0,null);

    }
}

