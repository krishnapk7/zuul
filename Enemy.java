/**
 * This class is part of the "Secret Chamber" application.
 * 
 * An enemy is a movable character that are placed in a room at the initialisation
 * of the game. Whenever the player moves, all enemies will move randomly to a room
 * that is adjacent to their current room. They all have their own strength system
 * which includes their health and damage. They all spawn with their own unique 
 * item regardless of the weight of the item.
 *
 * @author Krishna Prasanna Kumar (K21004839)
 * @version 02/12/2021
 */
public class Enemy
{
    private Room currentRoom;
    private Item currentItem; // Each enemy only has one item available regardless of weight, it spawns with this item.
    private String name;
    private Strength enemyStrength;
    
    /**
     * Creates the enemy by taking in a name, and the health and damage of the
     * enemy.
     * @param name Name of the enemy
     * @param enemyHealth Initial health of the enemy
     * @param enemyDamage Initial damage of the enemy
     */
    public Enemy(String name, int enemyHealth, int enemyDamage)
    {
        this.name = name;
        enemyStrength = new Strength(enemyHealth, enemyDamage); //Creates a Strength object
    }
    
    /**
     * Updates the enemy health when the player attacks it.
     * @param takenDamage Damage dealt on the enemy as an integer.
     */
    public void updateEnemyHealth(int takenDamage)
    {
        enemyStrength.loseHealth(takenDamage);
    }
    
    /**
     * Accessor Method
     * Returns the current health of the enemy as an integer.
     */
    public int getHealth()
    {
        return enemyStrength.getCurrentHealth();
    }
    
    /**
     * Set the current room of the enemy
     * @param room The new room of the enemy.
     */
    public void setCurrentRoom(Room room)
    {
        currentRoom = room;
    }
    
    /**
     * Accessor Method
     * Returns the damage of the enemy as an integer.
     */
    public int getDamage()
    {
        return enemyStrength.getDamage();
    }
    
    /**
     * Accessor Method
     * @return currentItem
     */
    public Item getEnemyItem()
    {
        return currentItem;
    }
    
    /**
     * Set the item that the enemy is holding
     * @param item The item that the enemy should hold.
     */
    public void setCurrentItem(Item item)
    {
        currentItem = item;
    }
    
    /**
     * Drops the item from the enemy into the current room, called when the 
     * enemy is dead.
     */
    public void dropItem()
    {
        currentRoom.addItem(currentItem);
    }
    
    /**
     * Randomises the movement of the enemy, by calling the autoRoom method
     * in the room class.
     */
    public void npcMovement()
    {
        if (currentRoom != null){
            currentRoom = currentRoom.getExit(currentRoom.autoRoom());
        }
    }
    
    /**
     * Kills the enemy by checking if the health is less than or equal to 0,
     * drops the item and sets the current room to null.
     */
    public void killEnemy()
    {
        if(enemyStrength.getCurrentHealth() <= 0)
        {
            dropItem();
            currentRoom = null;
        }
    }
    
    /**
     * Accessor Method
     * @return currentRoom The room that the enemy is currently in.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    /**
     * Accessor Method
     * @return name The name of the enemy as a String.
     */
    public String getName()
    {
        return name;
    }

}
