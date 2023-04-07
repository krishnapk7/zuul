
/**
 *  This class is the class that runs the "Secret Chambers" application. 
 * 
 *  To play this game, call the main method on the game class or create an instance of this class and call the "play"
 *  method.
 * 
 * This class contains the main while loop that checks whether the game should continue running.
 * 
 * 
 * @author  Michael Kölling, David J. Barnes and Krishna Prasanna Kumar(K21004839)
 * @version 02/12/2021
 */

public class Game 
{
    private World world; // Create World
    
    /**
     * Runs the game without the need of creating the game object.
     */
    public static void main(String[] args)
    {
        Game game = new Game();
        game.play();
    }
    
    /**
     * Create the world by creating the world and the objects in the world.
     */
    public Game() 
    {
        world = new World();
        world.createRooms();
        world.createItems();
        world.addItems();
        world.setStartingLocation();
    }

    /**
     *  Main play routine.  Loops until end of play.
     *  Prints the Welcome message and the goodbye message.
     */
    public void play() 
    {            
        printWelcome();
        
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            finished = world.parserInfinite(finished);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println("Welcome to the Secret Chambers!"
                            + "\nThe Secret Chambers is a medieval dungeon set far far away in the country of Zuul. "
                            + "\nYour only hope on surviving and winning the game is to find the special key and"
                            + "\nunlocking the Secret Treasure Room to unlock the vast riches the Dungeon offers!");
        System.out.println("\nEnter your Name:");
        world.setPlayerName();
        System.out.println("Hello " + world.getPlayerName());
        
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(world.getCurrentRoomDetails());
        world.createEnding();
    }
}

