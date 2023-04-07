
/**
 * This class is part of the "Secret Chambers" application
 * This class is a sub class of the Room class and inherits its methods. This class
 * creates the treasure rooms and is one way that the player can end the game by 
 * entering either treasure room.
 *
 * @author Krishna Prasanna Kumar (K21004839)
 * @version 02/12/2021
 */
public class TreasureRoom extends Room
{
    private String ending;

    /**
     * Creates the Treasure Rooms with the same parameters as the Room class
     * @param shortDescription
     * @param longDescription
     */
    public TreasureRoom(String shortDescription, String longDescription)
    {
        super(shortDescription, longDescription);
        ending = " ";
    }
    
    /**
     * Accessor Method
     * Overriding the method in the room class.
     * Returns true if the room is a treasure room and is called from this class.
     */
    public boolean isTreasureRoom()
    {   
        return true;
    }
    
    /**
     * Sets the ending speech
     * @param sentence
     */
    public void endSpeech(String sentence)
    {
        ending = sentence;
    }
    
    /**
     * Accessor Method
     * @return ending The ending speech as a String.
     */
    public String getSpeech()
    {
        return ending;
    }
}
