
/**
 * This class is part of the "Secret Chambers" application.
 * 
 * A door will only be created between two rooms when it is specified in the createRooms
 * method in the world class.
 *
 * @author Krishna Prasanna Kumar (K21004839)
 * @version 02/12/2021
 */
public class Door
{
    private Room secondRoom;
    private String direction;

    /**
     * Creates the door
     * @param secondRoom
     * @param direction
     */
    public Door(Room secondRoom, String direction)
    {
        // initialise instance variables
        this.secondRoom = secondRoom;
        this.direction = direction;
    }
    
    /**
     * Accessor Method
     * @return The direction of the door
     */
    public String getDirection()
    {
        return direction;
    }
    
    /**
     * Accessor Method
     * @return The Room that will be opened when the door is unlocked.
     */
    public Room getSecondRoom()
    {
        return secondRoom;
    }
}

