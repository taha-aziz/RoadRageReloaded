import javax.swing.*;
public class Driver
{

    public static String response = "";
    //the main method is where the program starts
    public static void main( String[] args )
    {
         response = JOptionPane.showInputDialog(null,
                "What is your name?",
                "Enter your name",
                JOptionPane.QUESTION_MESSAGE);
        //creates a frame to show the game instructions
        JFrame info = new JFrame( "Control Buttons" );
        info.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        info.setLocation(950, 100);
        Instructions buttons = new Instructions();
        info.getContentPane().add(buttons);
        info.pack();
        info.setVisible(true);

        JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        frame.setLocation( 100, 100 );
        Menu menu = new Menu();
        frame.getContentPane().add(menu);
        frame.pack();
        frame.setVisible(true);        
        menu.setFocusable(true);

        menu.setFocusable(true); //points the focus to the game
    }
}