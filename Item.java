/**
 * This class is part of the "Secret Chambers" application.
 * A "Item" represents objects that the player can carry, use and drop.
 * @author Krishna Prasanna Kumar(K21004839)
 * @version 31/11/2021
 */
public class Item
{
    private int weight;
    private String name;
    private String description;

    /**
     * Creates a item with three parameters.
     * @param name The name of the item as a String
     * @param weight The item's weight as an int
     * @param description What the item is, as a String
     */
    public Item(String name, int weight, String description)
    {
        this.weight = weight;
        this.name = name;
        this.description = description;
    }
    
    /**
     * Accessor Method
     * @return The name of the item as a String.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Accessor Method
     * @return The description of the Item as a String
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Checks whether the item is a weapon, this method is overrided by the Weapon
     * sub class if its confirmed to be a weapon.
     * Returns false if the method is called from this class.
     */
    public boolean isItemWeapon()
    {
        return false;
    }

    /**
     * Accesser method for the weight
     * @return weight The weight of the item as an integer.
     */
    public int getItemWeight()
    {
        return weight;
    }
    
    /**
     * Checks whether the item is a key, this method is overrided by the Key
     * sub class if its confirmed to be a key.
     * Returns false if the method is called from this class.
     */
    public boolean isItemKey()
    {
        return false;
    }
    
    /**
     * Returns the first room when a key is initialised. This method is overrided
     * by the key subclass if its confirmed to be a key.
     * Returns null if the method is called from this class.
     */
    public Room getFirstRoom()
    {
        return null;
    }
}
