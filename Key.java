
/**
 * This class is part of the "Secret Chambers" application.
 * This class is a sub class of the Item class, it inherits its methods.
 * It is used to create a Key which is specific to doors that have been created in the game.
 *
 * @author Krishna Prasanna Kumar (K21004839)
 * @version 02/12/2021
 */
public class Key extends Item
{
    private Room firstRoom;

    /**
     * Creates the Key by taking in the same parameters as the Item class
     * but also takes the firstRoom as a parameter.
     * @param name
     * @param weight
     * @param description
     * @param firstRoom
     */
    public Key(String name, int weight, String description, Room firstRoom)
    {
        super(name, weight, description);
        this.firstRoom = firstRoom;
    }
    
    /**
     * Accessor Method
     * @return firstRoom The room that the key works in
     */
    public Room getFirstRoom()
    {
        return firstRoom;
    }
    
    /**
     * Method that overrides the method in the Item class
     * Return true if it is a key and is called from this class.
     */
    public boolean isItemKey()
    {
        return true;
    }
}

