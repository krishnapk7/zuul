import java.util.ArrayList;

/**
 * This class is part of the "Secret Chambers" application.
 * 
 * This class is a sub class of the Room class and inherits its methods. The transporter
 * room is different to the other rooms, when the player inputs transport while
 * inside the transporter room they will be transported to a random room.
 *
 * @author Krishna Prasanna Kumar (K21004839)
 * @version 02/12/2021
 */
public class TransporterRoom extends Room
{
    private RoomRandomiser roomRandomiser;
    
    /**
     * Creates the Transporter Room by taking in the same parameters as the Room
     * class constructor.
     * @param shortDescription
     * @param longDescription
     */
    public TransporterRoom(String shortDescription, String longDescription)
    {
        super(shortDescription, longDescription); // Calls the constructor of the Room class
        roomRandomiser = new RoomRandomiser();
    }
    
    /**
     * Overriding the superclass method, returns true if the room is the Transporter
     * room and is called from this class.
     */
    public boolean isTransporter()
    {
        return true;
    }
    
    /**
     * Calls the RoomRandomiser method and obtains a Random room which the player
     * can be transported to.
     */
    public Room findRandomRoom(ArrayList<Room> allRooms)
    {
        return roomRandomiser.getRandomRoom(allRooms);
    }
}