import java.util.Stack;
import java.util.ArrayList;

/**
 * This class is part of the "Secret Chambers" application.
 * A "Player" is the main character which does all the inputs when the playing the
 * game. It consists of the player's backpack which holds the items and deals with
 * the weight. The player also has its own Strength System which deals with the
 * health and damage of the player.
 *
 * @author Krishna Prasanna Kumar (K21004839)
 * @version 01/12/2021
 */
public class Player
{
    private Room currentRoom;
    private Backpack bag; // Player holds a bag with items
    private String name;
    private Strength playerStrength;
    private Stack<Room> stack;  //Stack used to hold all the rooms that have been visited by the player
    private boolean isDead;
    
    /**
     * Create a player, initially it has an empty backpack and no name.
     */
    public Player()
    {
        bag = new Backpack();
        name = null;
        playerStrength = new Strength(100, 0);  // Creates the strength system with 100 health and 0 damage
        stack = new Stack();
    }
    
    /**
     * Updates the player's damage when either a potion is used or a weapon has been acquired.
     * @param damage Takes in the damage that has to be added as an integer.
     */
    public void updatePlayerDamage(int damage)
    {
        playerStrength.gainDamage(damage);
    }
    
    /**
     * Accessor Method
     * Returns the player damage using a method in the Strength class
     */
    public int getPlayerDamage()
    {
        return playerStrength.getDamage();
    }
    
    /**
     * Gains health when a potion has beeen used in the World Class
     * @param health Takes in the additional health that is to be added to the current health.
     */
    public void gainPlayerHealth(int health)
    {
        playerStrength.gainHealth(health);
    }
    
    /**
     * Accessor Method
     * Returns the current health of the player as an integer.
     */
    public int getHealth()
    {
        return playerStrength.getCurrentHealth();
    }
    
    /**
     * Loses health when attacking an enemy, updates the health of the player using
     * the Strength class.
     * @param damage Takes in the damage dealt as an integer.
     */
    public void losePlayerHealth(int damage)
    {
        playerStrength.loseHealth(damage);
    }
    
    /**
     * Sets the username for the user by taking in the input.
     * @param userName New name for the user as a String.
     */
    public void setName(String userName)
    {
        name = userName;
    }
    
    /**
     * Accessor Method
     * @return name The name of the player as a String
     */
    public String getPlayerName()
    {
        return name;
    }
    
    /**
     * Set a new as the current room for the player.
     * @param room The new room of the player.
     */
    public void setCurrentRoom(Room room)
    {
        currentRoom = room;
    }
    
    /**
     * Accessor method
     * @return currentRoom The room that the player is currently in.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    /**
     * Accessor Method
     * Returns the Long description of the current room that the player is in.
     */
    public String getCurrentRoomDetails()
    {
        return currentRoom.getLongDescription();
    }
    
    /**
    * Checks if there are items in the current room and prints the names of the items.
    */
    public void checkItem()
    {
        System.out.println(currentRoom.getItems());
    }

    /**
     * Accessor Method
     * Returns whether the current room is the transporter room as a boolean, true
     * if it is and false if not.
     */
    public boolean playerTransporterRoom()
    {
        return currentRoom.isTransporter();
    }
    
    /**
     * Accessor Method
     * @param allRooms Takes in an ArrayList of Room
     * Returns a new room tha is randomly generated from the ArrayList given.
     */
    public Room transport(ArrayList<Room> allRooms)
    {
        return currentRoom.findRandomRoom(allRooms);
    }
    
    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * If there is a door then it will print a different message.
     * Adds the current room to a stack which is then used for the "back" command
     * @param command Takes in the user's command
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        Door door = currentRoom.getDoor();  // Creates a local variable and takes in the current rooms door, if there isn't one then null is returned
        String doorDirection = " ";
        if(door != null)
        {
            doorDirection = door.getDirection();    // Gets the direction in which the locked door is placed in the room
        }
        
        if (nextRoom == null && !(doorDirection.equals(direction))) {
            System.out.println("There is no door!");
        }
        else if(nextRoom == null && (doorDirection.equals(direction)))
        {
            System.out.println("You cannot leave in this direction because of a locked door!");
        }
        else {
            stack.push(currentRoom);
            currentRoom = nextRoom;
            if(checkTreasureRoom()) // If the new room is a treasure room then the game should end
            {
                TreasureRoom room = (TreasureRoom) currentRoom;
                System.out.println(room.getSpeech());
                return;
            }
            System.out.println(currentRoom.getLongDescription());
            System.out.println(currentRoom.hasLockedDoor());    //Prints if the new room has a locked door or not
            checkItem();
        }
    }
    
    /**
     * Back method used to go back to previous rooms, uses a stack and pops the top room.
     * @param command Takes in the user's command.
     */
    public void back(Command command)
    {
        if(command.hasSecondWord()) 
        {
            System.out.println("This command only takes one word");
        }
        else if (!(stack.empty()))
        {
            currentRoom = stack.pop();  // Current room is the room that was as the top of the stack
            System.out.println(currentRoom.getLongDescription());
            currentRoom.hasLockedDoor();    // Check if the new room has a locked door
            checkItem();
        }
        else
        {
            System.out.println("You cannot go further back");
        }
    }
    
    /**
     * Takes the item from the current room and places it in the bag if the 
     * item exists and is in the current room.
     * @param command Takes in the user's input.
     */
    public void takeItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take
            System.out.println("Take what?");
            return;
        }
        else {
            String itemName = command.getSecondWord();
            if (!(currentRoom.doesItemExist(itemName)))
            {
                System.out.println("Item does not exist in this room");
            }
            else if (bag.checkSpace(itemName, currentRoom))
            {
                String details = bag.checkItemType(itemName, currentRoom);  //Checks what type of item it is
                if(details == null) //If null then we know that there is space for a key or a weapom
                {
                    Item item = currentRoom.accessItem(itemName);
                    bag.addItem(item);  // Adds the item to the bag
                    updateWeaponDamage(item);
                    currentRoom.removeItem(itemName);   //Remove the item from the current Room
                    System.out.println(bag.backpackDetails());  
                }
                else
                {
                    System.out.println(details);
                }
            }
            else {
                System.out.println("There is not enough space in the bag.");
            }
        }
    }
    
    /**
     * Updates the player damage if the item is a weapon if its not a weapon then
     * it skips over this method.
     * @param item Takes in the item which is to be checked if its a weapon
     */
    private void updateWeaponDamage(Item item)
    {
        if(item.isItemWeapon())
        {
            Weapon weapon = (Weapon) item;
            updatePlayerDamage(weapon.getWeaponDamage());   // Update the player damage with the weapon damage
        }
    }
    
    /**
     * Drops the item that is specified from the user, it removes the item from
     * the backpack and adds it to the current room.
     * @param command Takes in the user's input as a command.
     */
    public void dropItem(Command command)
    {
        if(!command.hasSecondWord())
        {
            // if there is no second word, we don't know what to take
            System.out.println("Drop what?");
            return;
        }
        String itemName = command.getSecondWord();
        Item item = bag.accessBackpackItem(itemName);   // Takes the item from the backpack
        if(item != null)
        {
            if(item.isItemWeapon()) //Check if the user has dropped a weapon, if so then set the player damage to 0.
            {
                playerStrength.setDamage(0);
            }
            bag.deleteItem(itemName);   //Deletes the item from the backpack
            currentRoom.addItem(item);
            System.out.println(itemName + " has been removed from your backpack and is now in the " + currentRoom.getShortDescription());
        }
        else 
        {
            System.out.println("Backpack does not contain " + itemName + ".");
        }
    }
    
    /**
     * Look method that looks around the room to see what's in the current room.
     * Prints whatever is in the current room.
     * @param command Takes in the user's input as a command.
     */
    public void look(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("This command only takes in one word");
        }
        else {  
            System.out.println(currentRoom.getLongDescription());
            currentRoom.hasLockedDoor();
            System.out.println(currentRoom.getItems());
        }
    }
    
    /**
     * Prints whatever the backpack contains and the remaining weight.
     * @param command Takes in the user's input as a command.
     */
    public void checkBackpackItems(Command command)
    {
        if(command.hasSecondWord())
        {
            System.out.println("This command only takes one word");
        }
        else
        {
            System.out.println(bag.backpackDetails());
        }
    }
    
    /**
     * Accessor Method
     * Returns an item if it is a weapon and is present in the backpack, if not
     * it returns null.
     */
    public Item checkWeapons()
    {
        return bag.checkWeaponExists();
    }
    
    /**
     * Check if the item is in the bag
     * @param itemName Takes in the name of the item as a String
     * @return True if the item is in the backpack and false if not.
     */
    public boolean doesItemExist(String itemName)
    {
        if(bag.accessBackpackItem(itemName) != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Removes the item from the backpack.
     * @param itemName Takes in the user's name as a String.
     */
    public void deletePlayerItem(String itemName)
    {
        bag.deleteItem(itemName);
    }
    
    /**
     * Opens the door if the player has a key in the backpack.
     * @param command Takes in the user's input as a command.
     */
    public void openRoom(Command command)
    {
        Item key = bag.checkKeyExists();    //Takes a key if it exists in the bag or null if not
        if(!command.hasSecondWord())
        {
            System.out.println("Open what?");
            return;
        }
        else if(!command.getSecondWord().equals("door"))
        {
            System.out.println("This command only works on doors!");
            return;
        }
        else if(key == null)
        {
            System.out.println("You have no key to open the door!");
            return;
        }
        
        key = (Key) key;
        if(key.getFirstRoom() != currentRoom)
        {
            System.out.println("This key only works in the " + key.getFirstRoom().getShortDescription());
        }
        else
        {
            Door door = currentRoom.getDoor();
            currentRoom.setExit(door.getDirection(), door.getSecondRoom()); //Sets the exits for the rooms after the door is opened
            currentRoom.unlockRoomDoor();
            System.out.println("The door has opened, you can now enter " + door.getSecondRoom().getShortDescription());
        }
    }
    
    /**
     * Inspects an item that is in the current room and prints the details of the
     * item.
     * @param command Takes in the user's input as a command
     */
    public void inspect(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Inspect what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        Item item = currentRoom.accessItem(itemName);
        if(item == null)
        {
            System.out.println("There is no item with that name in this room!");
        }
        else
        {
            System.out.println("name: " + item.getName() + "\ndescription: " + item.getDescription() + "\nweight: " + item.getItemWeight());
        }
    }
    
    /**
     * Checks whether the current room is a treasure room
     * @return True if it is and false if not
     */
    public boolean checkTreasureRoom()
    {
        if(currentRoom.isTreasureRoom())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Empties the stack, used when the player has transported and cannot go back.
     */
    public void emptyStack()
    {
        stack.removeAllElements();
    }
}
