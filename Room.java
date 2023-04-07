import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "Secret Chambers" application. 
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling, David J. Barnes and Krishna Prasanna Kumar(K21004839)
 * @version 02/12/2021
 */

public class Room 
{
    private String shortDescription;
    private String longDescription;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> itemLocation;   // Stores the items in the current room
    private Random random;
    private ArrayList<String> exitNames;
    private Door door;
    
    /**
     * Create a room with a "shortDescription" and a "longDescription. Initially, it has
     * no exits.
     * @param shortDescription
     * @param longDescription;
     */
    public Room(String shortDescription, String longDescription) 
    {
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        exits = new HashMap<>();
        itemLocation = new ArrayList<>();
        random = new Random();
        exitNames = new ArrayList<>();
        door = null;    // Has no locked doors at the start of the game.
    }
    
    /**
     * Check if there are any locked doors in this room.
     * Returns a String which states whether or not the current room has a locked door.
     */
    public String hasLockedDoor()
    {
        if(door == null)
        {
            return "There are no locked doors in this room";
        }
        else
        {
            return "There is a locked door in this room in the " + door.getDirection() + " direction";
        }
    }
    
    /**
     * Adds a door to the current room.
     * @param secondRoom The room that is opened when the door is unlocked.
     * @param direction The direction that the door is.
     */
    public void addRoomDoor(Room secondRoom, String direction)
    {
        door = new Door(secondRoom, direction);
    }
    
    /**
     * Accessor Method
     * @return door
     */
    public Door getDoor()
    {
        return door;
    }
    
    /**
     * Unlocks the door by setting it to null.
     */
    public void unlockRoomDoor()
    {
        door = null;
    }
    
    /**
     * Generates a random exit out of all exits that are available for the current room.
     * @return finalExit The randomly chosen exit.
     */
    public String autoRoom()
    {
        Set<String> allExits = exits.keySet();
        for(String exit : allExits)
        {
            exitNames.add(exit);
        }
        
        int num = random.nextInt(allExits.size());
        String finalExit = exitNames.get(num);
        return finalExit;
    }
    
    /**
     * Accessor Method
     * Returns the size of the exits HashMap.
     */
    public int getNumberOfExits()
    {
        return exits.size();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return shortDescription;
    }

    /**
     * Return a longer description of the current room.
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + longDescription + "\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    /**
     * Add item to the room
     * @param item Item to be added to the room
     */
    public void addItem(Item item)
    {
        itemLocation.add(item);
    }

    /**
     * Get all items that are in this room.
     * Returns the item names as a String or that there are
     * no items.
     */
    public String getItems()
    {
        if (itemLocation.size() > 0)
        {
            String output = "Items in room: ";
            for (Item item : itemLocation)
            {
                output = output + item.getName() + " ";
            }
            return output;
        }
        else {
            return "No Items in the " + getShortDescription();
        }
    }

    /**
     * Access Item from the room
     * @param itemName
     * @return finalItem
     */
    public Item accessItem(String itemName)
    {
        Item finalItem = null;
        for (Item item : itemLocation)
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
     * Remove Item from the room
     * @param itemName
     */
    public void removeItem(String itemName)
    {
        Item item = accessItem(itemName);
        if(item != null)
        {
            itemLocation.remove(item);
            
        }
    }

    /**
     * Check if item exists in the room
     * @param itemName Name of the Item
     * @return True or false if the item is present
     */
    public boolean doesItemExist(String itemName)
    {
        Item item = accessItem(itemName);
        if(item != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
     
    /**
     * Method to be overrided by the Transporter Class
     */
    public boolean isTransporter()
    {
        return false;
    }
    
    /**
     * Method to be overrided by the TreasureRoom class
     */
    public boolean isTreasureRoom()
    {
        return false;
    }
    
    /**
     * Method to get overrided.
     */    
    public Room findRandomRoom(ArrayList<Room> allRooms)
    {
        return null;
    }
}
