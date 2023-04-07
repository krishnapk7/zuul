import java.util.HashSet;
import java.util.Iterator;

/**
 * The Backpack class is responsible for holding all the items that are picked up
 * by the player. It calculates the weights of the items put together and checks
 * whether the backpack is full or not.
 *
 * @author Krishna Prasanna Kumar (K21004839)
 * @version 02/12/2021
 */
public class Backpack
{
    private static final int weightMax = 50;    //Constant
    private HashSet<Item> backpackItems;
    private int totalWeight;

    /**
     * Creates a backpack with the starting weight of 0 and creates the HashSet of
     * Items that will hold all items that are picked up.
     */
    public Backpack()
    {
        // initialise instance variables
        totalWeight = 0;
        backpackItems = new HashSet<>();
    }

    /**
     * This method accesses an item from the backpack if it exists by taking in
     * the item's name a string.
     * @param itemName The name of the item as a String.
     * @return The item if it is in the backpack or null if it's not.
     */
    public Item accessBackpackItem(String itemName)
    {
        Item finalItem = null;
        for (Item item : backpackItems)
        {
            String nameItem = item.getName();
            if ((nameItem.equals(itemName)))
            {
                finalItem = item;
            }
        }
        return finalItem;
    }

    /**
     * Checks what type of item it is and checks whether or not if there is enough space
     * in the backpack as you can only hold one key and one weapon.
     * @param itemName Name of the item as a String
     * @param currentRoom The room that the player is currently in to access the item
     * before adding it to the backpack.
     * @return Null if there is space or a String which describes the issue.
     */
    public String checkItemType(String itemName, Room currentRoom)
    {    
        Item item = currentRoom.accessItem(itemName);
        if(item.isItemKey() && (checkKeyExists() == null))
        {
            return null;
        }
        else if(item.isItemKey() && (checkKeyExists() != null))
        {
            return "You are holding too many keys in your backpack, you can only hold one key.";
        }
        else if(item.isItemWeapon() && (checkWeaponExists() == null))
        {
            return null;
        }
        else if(item.isItemWeapon() && (checkWeaponExists() != null))
        {
            return "You are holding too many weapons in your backpack, you can only hold one weapon.";
        }
        else
        {
            return null;
        }
    }

    /**
     * Adds the item to the HashSet and updates the backpack weight.
     * @param item Takes in a item object.
     */
    public void addItem(Item item)
    {
        backpackItems.add(item);
        updateBackpackWeight(item.getItemWeight());
    }

    /**
     * Checks if there is space in the backpack before removing it from the current
     * room.
     * @param itemName Name of the item as a String
     * @param currentRoom The room that the player is currently in.
     * @return True if there is space and false if not.
     */
    public boolean checkSpace(String itemName, Room currentRoom)
    {
        Item item = currentRoom.accessItem(itemName);
        if (getRemainingSpace() >= item.getItemWeight())
        {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Updates the bacpack weight
     * @param weight Thakes in the weight of the item as an integer.
     */
    private void updateBackpackWeight(int weight)
    {
        totalWeight += weight;
    }

    /**
     * Accessor Method
     * @return totalWeight The overall weight of the backpack.
     */
    public int getBackpackWeight()
    {
        return totalWeight;
    }

    /**
     * Accessor Method
     * Returns the space left in the backpack.
     */
    public int getRemainingSpace()
    {
        return (weightMax - getBackpackWeight());
    }

    /**
     * Returns all the items that are currently in the backpack and the remaining
     * space as a string.
     */
    public String backpackDetails()
    {
        if (totalWeight == 0)
        {
            return "There are no items in your backpack, remaining space: " + getRemainingSpace();
        }
        else
        {
            String itemString = "Items in your backpack: ";
            for (Item backpackItem : backpackItems)
            {
                itemString = itemString + backpackItem.getName() + " ";
            }
            return itemString + "\nRemaining space: " + getRemainingSpace();
        }
    }

    /**
     * Checks whether a Weapon is present in the backpack and returns it if it is
     * or returns null.
     */
    public Item checkWeaponExists()
    {
        for(Item backpackItem : backpackItems)
        {
            if(backpackItem.isItemWeapon())
            {
                return backpackItem;
            }
        }
        return null;
    }

    /**
     * Checks whether a Weapon is present in the backpack and returns it if it is
     * or returns null.
     */
    public Item checkKeyExists()
    {
        for(Item backpackItem : backpackItems)
        {
            if(backpackItem.isItemKey())
            {
                return backpackItem;
            }
        }
        return null;
    }
    
    /**
     * Removes an item from the backpack and updates the weight of the backpack
     * accordingly.
     */
    public void deleteItem(String itemName)
    {
        Item item = accessBackpackItem(itemName);
        if(item != null)
        {
            backpackItems.remove(item);
            totalWeight -= item.getItemWeight();
        }
    }
}
