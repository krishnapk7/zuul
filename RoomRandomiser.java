import java.util.Random;
import java.util.ArrayList;

/**
 * This class is part of the "Secret Chambers" application.
 * The RoomRandomiser class is designed to return a random room when the player enters
 * the transporter room and is willing to transport somewhere.
 *
 * @author Krishna Prasanna Kumar (K21004839)
 * @version 02/12/2021
 */
public class RoomRandomiser
{
    private Random random;
    
    /**
     * Creates the RoomRandomiser by initialising the Random object.
     */
    public RoomRandomiser()
    {
        random = new Random();
    }
    
    /**
     * Returns a random room by taking in an ArrayList of all the rooms except
     * from the transporter room and the treasure rooms.
     */
    public Room getRandomRoom(ArrayList<Room> allRooms)
    {
        int roomIndex = random.nextInt(allRooms.size());
        return allRooms.get(roomIndex);
    }
}
