/**
 * This class is part of the "Secret Chambers" application.
 * 
 * A strength class is used to organise the health and damage systems of the player
 * and all enemies. It updates the health and damage of the entities by taking in
 * appropriate integers.
 *
 * @author Krishna Prasanna Kumar (K21004839)
 * @version 02/12/2021
 */
public class Strength
{
    private int currentHealth;
    private static final int maxHealth = 100;   //Constant
    private int damage;

    /**
     * Creates the Strength object by taking in the initial health and the initial
     * damage.
     * @param currentHealth The starting health as an integer.
     * @param damage The starting damage as an integer.
     */
    public Strength(int currentHealth, int damage)
    {
        this.currentHealth = currentHealth;
        this.damage = damage;
    }
    
    /**
     * Sets the new damage
     * @param newDamage The new damage as an integer.
     */
    public void setDamage(int newDamage)
    {
        damage = newDamage;
    }
    
    /**
     * Gains damage by adding on the extra damage to the existing damage.
     * @param newDamage The additional damage that is to be added on the existing
     * damage.
     */
    public void gainDamage(int newDamage)
    {
        damage += newDamage;
    }
    
    /**
     * Subtracts health from the current Health
     * @param takenDamage Damage taken as an integer.
     */
    public void loseHealth(int takenDamage)
    {
        currentHealth -= takenDamage;
    }
    
    /**
     * Adds health to the current health if it is below the max health.
     * @param health The health to be added as an integer.
     */
    public void gainHealth(int health)
    {
        if((currentHealth + health) <= maxHealth)
        {
            currentHealth += health;
        }
    }
    
    /**
     * Accessor Method
     * @return currentHealth The current health
     */
    public int getCurrentHealth()
    {
        return currentHealth;
    }
    
    /**
     * Accessor Method
     * @return damage The current Damage
     */
    public int getDamage()
    {
        return damage;
    }
}
