import java.awt.event.*;//needed for KeyListener and ActionListener
import javax.swing.*;//needed for JPanel
import java.awt.*;//needed to draw shapes on JPanel
import java.util.ArrayList;//needed to create ArrayLists

public class Game extends JPanel implements KeyListener, Runnable, ActionListener
{
    //all of the instance variables for this class
    private boolean Recharged;
    private boolean moveRQ;
    private int policeReset;
    private boolean moveLQ;
    private Player one;
    private int WIDTH = 720;
    int ecount;
    private int HEIGHT = 760;
    private int bestScore = 0, bulletCount, maxBullets, count, newTime, bombs;
    private boolean start = true;
    private JLabel message;
    private ArrayList<emp> EMP;
    private  boolean emper = false;
    private  boolean empera = false;
    private  boolean em = true;
    private Thread theGame;
    private boolean left, right, up, down, over, end;
    private double time;
    private ArrayList<Car> Cars;
    private ArrayList<Truck> trucks;
    private ArrayList<Police> PoliceList;
    private ArrayList<TahaCannon> Laser;
    private ArrayList<PoliceGun> Gun;
    private ArrayList<BackGround> BackGroundList;
    private JButton buttonZero, buttonOne;
    private int newtime = 1;
    private Image img;
    private int PoliceSpawnCount = 0;
    private int killCount;
    private int[][] LaneBounds = new int[4][2];
    private int SCORE;
    private boolean sleepQ;
    private boolean sent = false;
    private Sound sound;
    public Boss boss;
    public boolean Bspawned;
    public boolean bmove;
    public int BBulletCount;
    boolean reloaded;
    public int bossLife;
    private ArrayList<Secret> secret;

    //the constructor, which is run when the Game object is first created
    public Game()
    {
        sound = new Sound();
        //sets the dimensions of the game area
        setPreferredSize( new Dimension( WIDTH, HEIGHT ) );
        img = Toolkit.getDefaultToolkit().createImage("Background.png");
        //creates two buttons that will be hidden until the game ends
        buttonZero = new JButton( "Play Again" );
        buttonOne = new JButton( "Quit" );
        this.add( buttonZero );
        this.add( buttonOne );
        buttonZero.addActionListener(this);
        buttonOne.addActionListener(this);
        LaneDimensions();
        PoliceList = new ArrayList<Police>();

        //causes the program to "listen" for keystrokes from the keyboard
        this.addKeyListener(this);
        this.setFocusable(true);

        //creates a label to display a text message on the screen
        message = new JLabel( "Avoid the angry drivers...\nPress \"ENTER\" to begin the race" );
        this.add( message );
        message.setFont( new Font("Arial", Font.BOLD, 24 ) );
        message.setForeground(Color.WHITE);
        message.setVisible(true);

        //creates a thread to run the animated portions of the game
        theGame = new Thread(this);

        //calls the reset method in this class to get the game started
        this.reset();
    }// end constructor Game

    //this method is automatically run when a JButton is clicked
    public void actionPerformed( ActionEvent event )
    {
        JButton button = (JButton)event.getSource();
        if( button == buttonZero )
        {
            //restarts the game
            end = false; 
            this.reset();
        }
        else
        {
            //ends the game
            message.setText( "Your best killstreak was " + bestScore + " kills when you used " + maxBullets + " lasers." );
            message.setForeground(Color.WHITE);
            buttonZero.setVisible(false);
            buttonOne.setVisible(false);
            this.repaint();
            end = true;
        }
    }// end method actionPerformed

    //this method is called to start the game over
    public void reset()
    { 

        //creates or recreates the Player object
        one = new Player( WIDTH/2, 3*HEIGHT/4, 4, 4 );
        //creates or recreates the Arraylists for enemies and bullets
        Cars = new ArrayList<Car>();
        PoliceList = new ArrayList<Police>();
        trucks = new ArrayList<Truck>();
        Laser = new ArrayList<TahaCannon>();
        Gun = new ArrayList <PoliceGun>();
        secret = new ArrayList<Secret>();
        EMP = new ArrayList<emp>();

        boss = null;
        BackGroundList = new ArrayList<BackGround>();
        BackGroundList.add( new BackGround(0, 0, 0, 3));
        BackGroundList.add(new BackGround(0, -1080, 0, 3));
        //makes the buttons disappear and makes the program "listen" for key strokes again
        buttonZero.setVisible(false);
        buttonOne.setVisible(false);
        buttonZero.setFocusable(false);
        buttonOne.setFocusable(false);
        this.setFocusable(true);
        bmove = false;

        //resets variables to beginning values
        over = false;
        sent = false;
        time = 0;
        newTime = 1;
        bulletCount = 0;
        count=1;
        killCount = 0;
        SCORE = 0;
        policeReset = 0;
        Recharged = true;
        sleepQ = false;
        Bspawned = false;
        BBulletCount = 0;
        reloaded = true;
        bossLife = 15;
        ecount = 0;
    }

    //this method runs when the Thread starts and keeps the shapes moving throughout the game
    public void run()
    {
        while(!end)
        {
            sound.loop();
            while(!over)
            {
                if(sleepQ == true)
                {
                    try
                    {
                        Thread.sleep(30);
                    }
                    catch(InterruptedException e){}
                }
                SCORE += 1;

                //System.out.println(SCORE);
                if( (int)time / newTime == count && SCORE < 10000)
                {
                    spawnNow();
                    Recharged = true;

                    if(PoliceList.size() < 1 && policeReset > 2)
                    {
                        PoliceList.add ( new Police( (int)(Math.random()*(WIDTH - 30)), 720,  count, count ));
                        policeReset = 0;
                    }
                    count++;
                }
                if(!Bspawned && SCORE > 10000)
                {
                    boss = new Boss( (int)(Math.random()*(WIDTH - 30)), -50,  0, 0 );
                    Bspawned = true;
                }
                if(Bspawned)
                {
                    bossMoves();
                }
                if(left && one.getLeft() > 120 )
                {
                    one.moveX(false);
                }
                if(right && one.getRight() < 600)
                {
                    one.moveX(true);
                }
                if(up && one.getTop() > 0 )
                {
                    one.moveY(true);
                }
                if(down && one.getBottom() < HEIGHT )
                {
                    one.moveY(false);
                }

                for(int i = 0; i < Gun.size(); i++)
                {
                    Gun.get(i).moveY();
                    if( Gun.get(i).getBottom() < 0 || Gun.get(i).getTop() > HEIGHT ||
                    Gun.get(i).getLeft() > WIDTH || Gun.get(i).getRight() < 0 || Gun.get(i).getTop() > 760)
                    {

                        if(!Bspawned)
                        {
                            Gun.remove( i );
                        }
                    }
                }

                for( int i = 0; i < Laser.size(); i++ )
                {
                    Laser.get(i).moveY();
                    if( Laser.get(i).getBottom() < 0 || Laser.get(i).getTop() > HEIGHT ||
                    Laser.get(i).getLeft() > WIDTH || Laser.get(i).getRight() < 0 )
                    {
                        Laser.remove( i );
                    }
                }

                if( (int)time%5==count)
                {
                    for(Car Car:Cars)
                    {
                        Car.setSpeedX((int)time);
                        Car.setSpeedY((int)time);
                        count += 3;
                    }
                }

                for( int i = 0; i < Cars.size(); i++ )
                {
                    //squares.get(i).moveX();
                    Cars.get(i).moveY();
                    if(Cars.get(i).deleteCar()) {
                        Cars.remove(i);
                        //System.out.println("Removed a car!");
                        i--;
                    }

                }

                for( int i = 0; i < secret.size(); i++ )
                {
                    secret.get(i).moveY();
                    if( secret.get(i).getBottom() < 0 || secret.get(i).getTop() > HEIGHT ||
                    secret.get(i).getLeft() > WIDTH || secret.get(i).getRight() < 0 )
                    {
                        secret.remove( i );
                    }
                }

                if( (int)time%5==count)
                {
                    for(Truck i: trucks)
                    {
                        i.setSpeedX((int)time);
                        i.setSpeedY((int)time);
                        count += 3;
                    }
                }

                for( int i = 0; i < trucks.size(); i++ )
                {
                    //squares.get(i).moveX();
                    trucks.get(i).moveY();
                    if(trucks.get(i).deleteCar()) {
                        trucks.remove(i);
                        //System.out.println("Removed a truck!");
                        i--;
                    }

                }

                for( int i = 0; i < PoliceList.size(); i++ )
                {
                    moveLQ = false;
                    moveRQ = false;

                    if( PoliceList.get(i).getTop() > one.getBottom() + 10)
                    {
                        PoliceList.get(i).moveUp();
                    }
                    else if(PoliceList.get(i).getTop() < one.getBottom() + 10)
                    {
                        PoliceList.get(i).moveDown();
                    }
                    if( PoliceList.get(i).getLeft() == one.getLeft()  && Gun.size() < 1 || PoliceList.get(i).getRight() == one.getRight())
                    {
                        Gun.add( new PoliceGun( (PoliceList.get(i).getLeft() + PoliceList.get(i).getRight())/2, (PoliceList.get(i).getTop() + PoliceList.get(i).getBottom())/2, 0, -2 ));
                        bulletCount++;
                    }
                    if(PoliceList.size() > 0)
                    {
                        for(int C = 0; C < Cars.size(); C++)
                        {
                            if(PoliceList.get(0).getLeft() > Cars.get(C).getLeft() && PoliceList.get(0).getLeft() < Cars.get(C).getRight() && Gun.size() < 1
                            || PoliceList.get(0).getRight() < Cars.get(C).getRight() && PoliceList.get(0).getRight() > Cars.get(C).getLeft() && Gun.size() < 1)
                            {
                                Gun.add( new PoliceGun( (PoliceList.get(i).getLeft() + PoliceList.get(i).getRight())/2, (PoliceList.get(i).getTop() + PoliceList.get(i).getBottom())/2, 0, -2 ));
                                bulletCount++;
                            }
                        }
                    }
                    if(PoliceList.size() > 0)
                    {
                        for(int C = 0; C < trucks.size(); C++)
                        {
                            if(PoliceList.get(0).getLeft() > trucks.get(C).getLeft() && PoliceList.get(0).getLeft() < trucks.get(C).getRight() && Gun.size() < 1
                            || PoliceList.get(0).getRight() < trucks.get(C).getRight() && PoliceList.get(0).getRight() > trucks.get(C).getLeft() && Gun.size() < 1)
                            {
                                Gun.add( new PoliceGun( (PoliceList.get(i).getLeft() + PoliceList.get(i).getRight())/2, (PoliceList.get(i).getTop() + PoliceList.get(i).getBottom())/2, 0, -2 ));
                                bulletCount++;
                            }
                        }
                    }

                    for(int j = 0; j < PoliceList.size(); j++)
                    {

                        if(PoliceList.get(i).getLeft() < one.getLeft() && PoliceList.get(i).getRight() < PoliceList.get(j).getRight()-1 && PoliceList.get(i).getRight() < PoliceList.get(j).getLeft())
                        {
                            moveRQ = true;
                        }

                        else if(PoliceList.get(i).getRight() > one.getRight() && PoliceList.get(i).getRight() > PoliceList.get(j).getRight()+1 && PoliceList.get(i).getLeft() > PoliceList.get(j).getRight()+1)
                        {
                            moveLQ = true;
                        }
                        else if(PoliceList.size() < 2)
                        {
                            if(PoliceList.get(i).getLeft() < one.getLeft())
                            {
                                moveRQ = true;
                            }
                            if(PoliceList.get(i).getLeft() > one.getLeft())
                            {
                                moveLQ = true;
                            }

                        }

                    }
                    if(moveLQ)
                    {
                        PoliceList.get(i).moveLeft();
                    }
                    else if(moveRQ)
                    {
                        PoliceList.get(i).moveRight();
                    }

                    if( one.getRight() > PoliceList.get(i).getLeft() && one.getLeft() < PoliceList.get(i).getRight() &&
                    one.getTop() < PoliceList.get(i).getBottom() && one.getBottom() > PoliceList.get(i).getTop() )
                    {
                        //ends the game and makes buttons visible so user can play again if desired
                        over = true;
                        buttonZero.setVisible(true);
                        buttonOne.setVisible(true);
                    }

                }
                for(int i = 0; i < BackGroundList.size(); i++)
                {
                    BackGroundList.get(i).moveY();
                    if(BackGroundList.get(i).getTop() == 0)
                    {
                        BackGroundList.add( new BackGround(0, -1080, 0, 3));
                    }
                    else if(BackGroundList.get(i).getTop() > 760)
                    {
                        BackGroundList.remove(i);
                        i--;
                    }

                }
                this.collide();
                this.repaint();

                try
                {
                    Thread.sleep(7);
                }
                catch(InterruptedException e){}
                time += 0.010;
                message.setText( "You have " + (one.getPoints()*100 + SCORE) + " points and " + one.getPoints() + " kills." );
                message.setForeground(Color.WHITE);

            }
            sound.stop();
            if( one.getPoints() > bestScore )
            {
                bestScore = one.getPoints();
                maxBullets = bulletCount;
            }
            message.setText( "You got killed with " + (one.getPoints()*100 + SCORE) + " points and " + one.getPoints() + " kills." );
            message.setForeground(Color.WHITE);
            this.repaint();
            if(!sent) {
                sendScore();
                sent = true;
            }
            try
            {
                Thread.sleep(2000);
            }
            catch(InterruptedException e){}
        }

    }

    public void bossMoves()
    {
        for(int i = 0; i < Gun.size(); i++)
        {
            if( Gun.get(i).getBottom() < 0 || Gun.get(i).getTop() > HEIGHT ||
            Gun.get(i).getLeft() > WIDTH || Gun.get(i).getRight() < 0)
            {
                Gun.remove( i );
                reloaded = true;
            }
        }
        if( boss.getRight() > one.getLeft() && boss.getLeft() < one.getRight() && reloaded)
        {
            Gun.add( new PoliceGun( (boss.getLeft() + boss.getRight())/2, (boss.getTop() + boss.getBottom())/2, 0, -22 ));
            reloaded = false;
        }
        if(boss.getLeft() < 0)
        {
            bmove = true;
        }
        else if(boss.getRight() > 720)
        {
            bmove = false;
        }
        if(boss.getTop() < 0)
        {
            boss.moveDown();
        }
        if((boss.getLeft() + boss.getRight())/2 < (one.getLeft() + one.getRight())/2)
        {
            boss.moveRight();
        }
        else
        {
            boss.moveLeft();
        }
        if( one.getRight() > boss.getLeft() && one.getLeft() < boss.getRight() &&
        one.getTop() < boss.getBottom() && one.getBottom() > boss.getTop() )
        {
            over = true;
            buttonZero.setVisible(true);
            buttonOne.setVisible(true);
        }

        for( int j = 0; j < Laser.size(); j++ )
        {
            //checks to see if a bullet and a square collid
            if( Laser.get(j).getRight() > boss.getLeft() && Laser.get(j).getLeft() < boss.getRight() &&
            Laser.get(j).getTop() < boss.getBottom() && Laser.get(j).getBottom() > boss.getTop() )
            {
                Laser.remove(j);
                j--;
                bossLife--;
            }
        }

        if(bossLife < 1)
        {
            over = true;
        }
    }

    //this method checks for collisions between bullets/squares and squares/player 
    public void collide()
    {
        for( int i = 0; i < Gun.size(); i++ )
        {
            //checks to see if a bullet and a square collide;
            if( one.getRight() > Gun.get(i).getLeft() && one.getLeft() < Gun.get(i).getRight() &&
            one.getTop() < Gun.get(i).getBottom() && one.getBottom() > Gun.get(i).getTop() )
            {
                over = true;
                buttonZero.setVisible(true);
                buttonOne.setVisible(true);
            }
        }
        for( int i = 0; i < Cars.size(); i++ )
        {

            //checks to see if the player and a square collide
            if( one.getRight() > Cars.get(i).getLeft() && one.getLeft() < Cars.get(i).getRight() &&
            one.getTop() < Cars.get(i).getBottom() && one.getBottom() > Cars.get(i).getTop() )
            {
                //ends the game and makes buttons visible so user can play again if desired
                over = true;
                buttonZero.setVisible(true);
                buttonOne.setVisible(true);
            }

            for(int j = 0; j < PoliceList.size(); j++)
            {
                if(PoliceList.get(j).getRight() > Cars.get(i).getLeft() && PoliceList.get(j).getLeft() < Cars.get(i).getRight() &&
                PoliceList.get(j).getTop() < Cars.get(i).getBottom() && PoliceList.get(j).getBottom() > Cars.get(i).getTop())
                {
                    Cars.remove(i);
                    PoliceList.remove(j);
                    i--;
                    j--;
                }
            }

            for( int j = 0; j < Laser.size(); j++ )
            {
                //checks to see if a bullet and a square collid
                if( Laser.get(j).getRight() > Cars.get(i).getLeft() && Laser.get(j).getLeft() < Cars.get(i).getRight() &&
                Laser.get(j).getTop() < Cars.get(i).getBottom() && Laser.get(j).getBottom() > Cars.get(i).getTop() )
                {
                    one.changePoints(1);
                    policeReset ++;
                    Laser.remove( j );
                    Cars.remove( i );
                    i--;
                    j--;
                    break;
                }
            }
            for( int j = 0; j < secret.size(); j++ )
            {
                //checks to see if a bullet and a square collid
                if( secret.get(j).getRight() > Cars.get(i).getLeft() && secret.get(j).getLeft() < Cars.get(i).getRight() &&
                secret.get(j).getTop() < Cars.get(i).getBottom() && secret.get(j).getBottom() > Cars.get(i).getTop() )
                {
                    one.changePoints(1);
                    policeReset ++;
                    secret.remove( j );
                    Cars.remove( i );
                    i--;
                    j--;
                    break;
                }
            }
            for( int j = 0; j < EMP.size(); j++ )
            {
                //checks to see if a bullet and a square collid
                if( EMP.get(j).getRight() > Cars.get(i).getLeft() && EMP.get(j).getLeft() < Cars.get(i).getRight() &&
                EMP.get(j).getTop() < Cars.get(i).getBottom() && EMP.get(j).getBottom() > Cars.get(i).getTop() )
                {

                    EMP.remove(j);
                    ecount--;
                    Cars.remove( i );
                    i--;
                    j--;
                    break;
                }
            }
        }
        for(int i = 0; i < Cars.size(); i++)
        {
            for( int j = 0; j < Gun.size(); j++ )
            {
                //checks to see if a bullet and a square collid
                if(Gun.size() > 0 && Cars.size() > 0)
                {
                    if( Gun.get(j).getRight() > Cars.get(i).getLeft() && Gun.get(j).getLeft() < Cars.get(i).getRight() &&
                    Gun.get(j).getTop() < Cars.get(i).getBottom() && Gun.get(j).getBottom() > Cars.get(i).getTop() )
                    {

                        Gun.remove( j );
                        Cars.remove( i );
                        i--;
                        j--;
                        break; 
                    }
                }
            }
        }

        for( int i = 0; i < trucks.size(); i++ )
        {
            //checks to see if the player and a square collide
            if( one.getRight() > trucks.get(i).getLeft() && one.getLeft() < trucks.get(i).getRight() &&
            one.getTop() < trucks.get(i).getBottom() && one.getBottom() > trucks.get(i).getTop() )
            {
                //ends the game and makes buttons visible so user can play again if desired
                over = true;
                buttonZero.setVisible(true);
                buttonOne.setVisible(true);
            }
            for( int j = 0; j < EMP.size(); j++ )
            {
                //checks to see if a bullet and a square collid
                if( EMP.get(j).getRight() > trucks.get(i).getLeft() && EMP.get(j).getLeft() < trucks.get(i).getRight() &&
                EMP.get(j).getTop() < trucks.get(i).getBottom() && EMP.get(j).getBottom() > trucks.get(i).getTop() )
                {

                    EMP.remove(j);
                    ecount--;

                    trucks.remove( i );
                    i--;
                    j--;
                    break;
                }
            }
            for(int j = 0; j < PoliceList.size(); j++)
            {
                if(PoliceList.get(j).getRight() > trucks.get(i).getLeft() && PoliceList.get(j).getLeft() < trucks.get(i).getRight() &&
                PoliceList.get(j).getTop() < trucks.get(i).getBottom() && PoliceList.get(j).getBottom() > trucks.get(i).getTop())
                {
                    trucks.remove(i);
                    PoliceList.remove(j);
                    i--;
                    j--;
                }
            }
            for( int j = 0; j < Laser.size(); j++ )
            {
                //checks to see if a bullet and a square collid
                if( Laser.get(j).getRight() > trucks.get(i).getLeft() && Laser.get(j).getLeft() < trucks.get(i).getRight() &&
                Laser.get(j).getTop() < trucks.get(i).getBottom() && Laser.get(j).getBottom() > trucks.get(i).getTop() )
                {
                    one.changePoints(1);
                    policeReset++;
                    Laser.remove( j );
                    trucks.remove( i );
                    i--;
                    j--;
                    break;
                }
            }
            for( int j = 0; j < secret.size(); j++ )
            {
                //checks to see if a bullet and a square collid
                if( secret.get(j).getRight() > trucks.get(i).getLeft() && secret.get(j).getLeft() < trucks.get(i).getRight() &&
                secret.get(j).getTop() < trucks.get(i).getBottom() && secret.get(j).getBottom() > trucks.get(i).getTop() )
                {
                    one.changePoints(1);
                    policeReset++;
                    secret.remove( j );
                    trucks.remove( i );
                    i--;
                    j--;
                    break;
                }
            }
        }
        for(int i = 0; i < trucks.size(); i++)
        {

            for( int j = 0; j < Gun.size(); j++ )
            {
                //checks to see if a bullet and a square collid
                if( Gun.get(j).getRight() > trucks.get(i).getLeft() && Gun.get(j).getLeft() < trucks.get(i).getRight() &&
                Gun.get(j).getTop() < trucks.get(i).getBottom() && Gun.get(j).getBottom() > trucks.get(i).getTop() )
                {

                    Gun.remove( j );
                    trucks.remove( i );
                    i--;
                    j--;
                    break;
                }
            }

        }
        if(PoliceList.size() > 0)
        {
            for(int i = 0; i < Laser.size(); i++)
            {
                if( PoliceList.get(0).getRight() > Laser.get(i).getLeft() && PoliceList.get(0).getLeft() < Laser.get(i).getRight() &&
                PoliceList.get(0).getTop() < Laser.get(i).getBottom() && PoliceList.get(0).getBottom() > Laser.get(i).getTop() )
                {
                    //ends the game and makes buttons visible so user can play again if desired
                    one.changePoints(5);
                    PoliceList.remove(0);
                    Laser.remove(i);
                    i--;
                }
            }
        }

    }

    //this method is called each time a key is pressed
    public void keyPressed( KeyEvent event )//method that determines which key was pressed
    //and calls the appropriate method
    {
        if( event.getKeyCode() == KeyEvent.VK_ENTER && start == true )
        {
            start = false;
            theGame.start();
        }
        if( !over )
        {
            //these four if statements adjust the motion of the player
            if( event.getKeyCode() == KeyEvent.VK_LEFT && one.getLeft() > 120)
            {
                left = true;
                right = false;
            }
            if( event.getKeyCode() == KeyEvent.VK_RIGHT && one.getRight() < 600)
            {
                right = true;
                left = false;
            }
            if( event.getKeyCode() == KeyEvent.VK_UP )
            {
                up = true;
                down = false;
            }
            if( event.getKeyCode() == KeyEvent.VK_DOWN )
            {
                down = true;
                up = false;
            }
            if( event.getKeyCode() == KeyEvent.VK_Z )
            {
                sleepQ = true;
            }
            //these four nested if statements create bullets that travel up, down, left, or right
            if( event.getKeyCode() == KeyEvent.VK_SPACE )
            {

                Laser.add( new TahaCannon( (one.getLeft() + one.getRight())/2, (one.getTop() + one.getBottom())/2, 0, -2 ));
                bulletCount++;

            }
            if( event.getKeyCode() == KeyEvent.VK_X )
            {

                secret.add( new Secret( one.getLeft(), one.getTop(), 0, -2 ));
                bulletCount++;

            }
            if( event.getKeyCode() == KeyEvent.VK_E)
            {
                if( em = true && ecount < 5)
                {
                    EMP.add( new emp( (one.getLeft() + one.getRight())/2, (one.getTop() + one.getBottom())/2, 0, 0));
                    ecount++;
                }

            }
            if( event.getKeyCode() == KeyEvent.VK_R)
            {
                for( int i = 0; i < EMP.size(); i++)
                {
                    EMP.get(i).setSize(70);
                    this.repaint();
                    try
                    {
                        Thread.sleep(4);
                    }catch( InterruptedException ex ){}
                }
                emper = true;
            }
            if( event.getKeyCode() == KeyEvent.VK_S)
            {
                sleepQ = true;
            }
        }
    }//end method keyPressed

    public void spawnNow()
    {
        int random;
        for(int i = 0; i < 4; i++)
        {

            random = (int)(Math.random()*4);
            int L = (int)(Math.random()*3);
            int rand = 0;
            if(L == 0)
            {
                rand = -8;
            }
            else if(L == 1)
            {
                rand = 8;
            }
            else if(L == 2)
            {
                rand = 0;
            }
            if(checkSpawn(i))
            {
                if(random== 1)
                {
                    Cars.add( new Car( (int)((LaneBounds[i][0] + LaneBounds[i][1]) / 2 + rand), -550,  count, count, Color.green ) ); 
                }
            }
            random = (int)(Math.random()*25);
            if(checkSpawn(i))
            {
                if(random == 0)
                {
                    trucks.add ( new Truck( (int)((LaneBounds[i][0] + LaneBounds[i][1]) / 2 + rand), -550,  count, count, Color.red ) );
                }
            }

        }
    }

    public void LaneDimensions()
    {

        LaneBounds[ 0 ][ 0 ] =  60;
        LaneBounds[ 0 ][ 1 ] =  180;
        LaneBounds[ 1 ][ 0 ] =  180;
        LaneBounds[ 1 ][ 1 ] =  305;
        LaneBounds[ 2 ][ 0 ] =  331;
        LaneBounds[ 2 ][ 1 ] =  440;
        LaneBounds[ 3 ][ 0 ] =  465;
        LaneBounds[ 3 ][ 1 ] = 560 ;

    }

    public boolean checkSpawn(int Lane)
    { 
        //sets boundaries for the specific lane in question
        int rightBound = LaneBounds[Lane][1];
        int leftBound = LaneBounds[Lane][0];

        //Checks for cars and trucks close to the spawn in the current lane
        for(int i = 0; i < Cars.size(); i++)
        {
            if(leftBound < Cars.get(i).getLeft() && Cars.get(i).getLeft() < rightBound && Cars.get(i).getTop() < 500)
            {
                return false; 
            }
        }
        for(int i = 0; i < trucks.size(); i++)
        {
            if(leftBound < trucks.get(i).getLeft() && trucks.get(i).getLeft() < rightBound && trucks.get(i).getTop() < 700)
            {
                return false;
            }
        }

        return true;
    }

    //this method is required because this class implements KeyListener
    public void keyTyped( KeyEvent event )//required method that does nothing
    {}//end method keyTyped

    //this method makes the player stop moving when a button is released
    public void keyReleased( KeyEvent event )//required method that does nothing
    {
        if( event.getKeyCode() == KeyEvent.VK_LEFT )
        {
            left = false;
        }
        if( event.getKeyCode() == KeyEvent.VK_RIGHT )
        {
            right = false;
        }
        if( event.getKeyCode() == KeyEvent.VK_UP )
        {
            up = false;
        }
        if( event.getKeyCode() == KeyEvent.VK_DOWN )
        {
            down = false;
        }
        if( event.getKeyCode() == KeyEvent.VK_Z)
        {
            sleepQ = false;
        }
        if( event.getKeyCode() == KeyEvent.VK_R)
        {
            if( emper = true)
            {
                for( int i = 0; i < EMP.size(); i++)
                {
                    EMP.remove(i);
                    ecount = 0;
                }
            }
            emper = false;
        }
    }// end method keyReleased

    //this method paints the changes in position to the screen
    public void paintComponent( Graphics page )
    {
        //calls the paintComponent method in the super class to clear the exisitng features on the screen
        super.paintComponent( page );
        page.drawImage(img,0,0,null);  
        for(BackGround a: BackGroundList)
        {
            a.draw(page, over);
        }  
        one.draw( page, over );  
        for( emp emp:EMP )
        {
            emp.draw( page );
        }
        //sets the background color

        //calls the method that draws the player
        if(boss != null)
        {
            boss.draw( page,Bspawned );
        }

        for( Secret Secret:secret)
        {
            Secret.draw( page );
        }
        //calls the method to draw each square using an enhanced for loop
        for(Car Car:Cars )
        {
            Car.draw( page );

        }

        for (Truck i: trucks) {
            i.draw ( page );
        }

        for(Police Police : PoliceList)
        {
            Police.draw( page);
        }

        //calls the method to draw each laser using a crapy for loop
        //this method is the cause of all evil. 
        //it is a testimate to the failure of generations of programmers
        //great people have waisted away their lives attempting to fix this loop.
        for( TahaCannon TahaCannon:Laser )
        {
            TahaCannon.draw( page );
        }
        for( PoliceGun PoliceGun:Gun)
        {
            PoliceGun.draw( page );
        }
    }// end method paintComponent
    public boolean SpawnIsClear(int spawnSpace)
    {
        boolean clear = true; 

        for(int i = 0; i < Cars.size(); i++)
        {
            if(spawnSpace == Cars.get(i).getLeft())
            {
                clear = false; 
            }
        }
        for(int i = 0; trucks.size() > i; i++)
        {
            if(spawnSpace == trucks.get(i).getLeft())
            {
                clear = false; 
            }
        }
        return clear; 
    }

    public void sendScore() {
        Runnable r = new ScoreThread(SCORE, Driver.response);
        new Thread(r).start();
    }
}