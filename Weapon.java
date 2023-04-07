/**
 * The Weapon class is a sub class of the Item class, it inherits the Item classes methods.
 * This class takes in the weapon's damage as a parameter so it can stored separately
 * from other items that are not weapons.
 *
 * @author Krishna Prasanna Kumar (K21004839)
 * @version 02/12/2021
 */
public class Weapon extends Item
{
    private int damage;
    
    /**
     * Creates the weapon by taking in the same parameters that the Item class took
     * but added damage as well.
     */
    public Weapon(String name, int weight, String description, int damage)
    {
        super(name, weight, description);   // Calls the constructor in the Item class
        this.damage = damage;
    }
    
    /**
     * Accessor Method
     * @return damage The damage of the weapon as an intenger
     */
    public int getWeaponDamage()
    {
        return damage;
    }
    
    /**
     * Overriding the superclass, checks if the item is a weapon.
     * Returns true if the method is called from here.
     */
    public boolean isItemWeapon()
    {
        return true;
    }
    
}
