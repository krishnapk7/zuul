import java.util.ArrayList;

/**
 *  This World class creates and initialises all the others: it creates all
 *  rooms, items, enemies, the parser and the player.  It also evaluates and
 *  executes the commands that the parser returns.
 *  
 *  @author Krishna Prasanna Kumar (K21004839)
 *  @version 02/12/2021
 */
public class World
{
    private Room diningRoom, kitchen, infirmary, guardRoom, chapel, laboratory, shrine, masterBedroom, forge;
    private Item bread, health, damage, jewel, gold, uranium;
    private Key treasureKey, fakeKey;
    private Weapon dagger, mace;
    private TreasureRoom fakeTreasureRoom, secretTreasureRoom;
    private TransporterRoom transporter;
    private Player player;
    private Parser parser;
    private Enemy bandit;
    private Enemy goblin;
    private Enemy ogre;
    private ArrayList<Enemy> allNPCs;
    private ArrayList<Room> allRooms;

    /**
     * Constructor for objects of class World
     */
    public World()
    {
        //createRooms();
        //createItems();
        //addItems();
        parser = new Parser();
        player = new Player();
        bandit = new Enemy("bandit", 40, 10);
        goblin = new Enemy("goblin", 40, 15);
        ogre = new Enemy("ogre", 60, 20);
        allNPCs = new ArrayList<>();
        allNPCs.add(bandit);
        allNPCs.add(goblin);
        allNPCs.add(ogre);
        allRooms = new ArrayList<>();
    }

    /**
     * Sets the player's name by using the parser to get the input.
     */
    public void setPlayerName()
    {
        String name = parser.getName();
        player.setName(name);
    }
    
    /**
     * Accessor method
     * @return the player name as a String
     */
    public String getPlayerName()
    {
        return player.getPlayerName();
    }
    
    /**
     * Sets the starting location of the player and the enemies.
     */
    public void setStartingLocation()
    {
        player.setCurrentRoom(diningRoom);
        bandit.setCurrentRoom(infirmary);
        goblin.setCurrentRoom(laboratory);
        ogre.setCurrentRoom(shrine);
    }
    
    /**
     * Accessor Method
     * @return the current room's description as a String
     */
    public String getCurrentRoomDetails()
    {
        return player.getCurrentRoomDetails();
    }
    
    /**
     * Checks if the game has to be ended either by the player dying or the wantToQuit is true.
     * @param finished To check if the game has ended
     * @return true if the game has to be ended, false if the game should continue.
     */
    public boolean parserInfinite(boolean finished)
    {
        Command command = parser.getCommand();
        finished = processCommand(command);
        if(player.getHealth() <= 0)
        {
            System.out.println("You are now dead");
            finished = true;
        }
        return finished;
    }
    
    /**
     * Check whether an npc is in the current room.
     */
    public void checkNPC()
    {
        for(Enemy npc : allNPCs)
        {
            if(player.getCurrentRoom() == npc.getCurrentRoom())
            {
                System.out.println("There is a " + npc.getName() + " in the room!");
            }   
        }
    }
    
    
    /**
     * Gets the enemy as an enemy object from a string.
     * @param enemyName Takes in the name of the enemy as a string
     * @return null if the enemy doesn't exist or the enemy as an object.
     */
    private Enemy getEnemy(String enemyName)
    {
        for(Enemy npc : allNPCs)
        {
            if((npc.getName()).equals(enemyName))
            {
                return npc;
            }
        }
        return null;
    }
    
    /**
     * Everytime the player moves, this method will generate a new room that is adjacent to the current room of the enemy so it can move randomly.
     */
    public void randomMovement()
    {
        for(Enemy enemy : allNPCs)
        {
            enemy.npcMovement();
        }
    }
    
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        switch(commandWord)
        {
            case "help":
                printHelp();
                break;
            case "go":
                player.goRoom(command);
                randomMovement();
                checkNPC();
                wantToQuit = player.checkTreasureRoom(); //Check if the current room is a treasure room and then ends the game if it is.
                break;
            case "take":
                player.takeItem(command);
                break;
                case "look":        //Look around the room for items, exits and enemies.
                player.look(command);   
                checkNPC();
                break;
            case "quit":
                wantToQuit = quit(command);
                break;
            case "back":
                player.back(command);
                randomMovement();
                checkNPC();
                break;
            case "drop":
                player.dropItem(command);
                break;
            case "backpack":
                player.checkBackpackItems(command);
                break;
            case "attack":
                attackNPC(command);
                break;
            case "transport":
                transport(command);     //Called when the user types "transport" in the transporter room
                player.checkItem();
                break;
            case "drink":               
                drink(command);
                break;
            case "open":
                player.openRoom(command);
                break;
            case "inspect":
                player.inspect(command);
                break;
            case "strength":            // Command to check player health and damage
                checkStrength(command);
                break;
        }
        return wantToQuit;
    }
    
    /**
     * Prints the player's current health and damage.
     * @param command Takes in the user's input
     */
    private void checkStrength(Command command)
    {
        if(command.hasSecondWord())
        {
            System.out.println("This command only takes in one word!");
        }
        else
        {
            System.out.println("Your current health is " + player.getHealth()
                                + "\nYour current damage is " + player.getPlayerDamage());
        }
    }
    
    /**
     * Updates the user's health or damage depending on the potion.
     * Prints the new damage or health
     * @param command Takes in the user's input 
     */
    private void drink(Command command)
    {
        if(!command.hasThirdWord()) //Checks whether the user has enetered three words as this only works as a three word command.
        {
            System.out.println("This command is a three word command. Please enter the command as 'drink type potion' where type is the type of potion.");
            return;
        }
        String secondWord = command.getSecondWord();
        String thirdWord = command.getThirdWord();
        
        if(!player.doesItemExist(secondWord))
        {
            System.out.println("Your bag does not contain this item");
            return;
        }
        
        if(thirdWord.equals("potion"))
        {
            switch(secondWord)      //Switch case, either health or damage potion or the default "Only potion" response
            {
                case "damage":
                    player.updatePlayerDamage(damage.getItemWeight());
                    player.deletePlayerItem(secondWord);
                    System.out.println("Your new damage is: " + player.getPlayerDamage());
                    break;
                case "health":
                    player.gainPlayerHealth(health.getItemWeight());
                    player.deletePlayerItem(secondWord);
                    System.out.println("Your new health is: " + player.getHealth());
                    break;
                default:
                    System.out.println("This command is only available for valid potions!");
            }
        }
        else
        {
            System.out.println("This command only works on potions");
        }
    }
    
    /**
     * Updates the player's current room if the input is "transport" at the transporter room.
     * @param command Takes in the user's input.
     */
    private void transport(Command command)
    {
        if(command.hasSecondWord()) 
        {
            System.out.println("This command only takes in one word.");
        }
        else if(!player.playerTransporterRoom())
        {
            System.out.println("This command only works if you are in the transporter room.");
        }
        else
        {
            Room newRoom = player.transport(allRooms);
            player.setCurrentRoom(newRoom);
            player.emptyStack();    // Empties the stack so you cannot go back to the room's before the teleportation.
            System.out.println("You have now transported to " + newRoom.getShortDescription());
            System.out.println(player.getCurrentRoomDetails());
        }
    }
    
    /**
     * Attacks the NPC when the user inputs "attack".
     * Updates both the enemy's health and the player's health.
     * @param command Takes in the user's input.
     */
    private void attackNPC(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Attack What?");
            return;
        }
        
        String enemyName = command.getSecondWord();
        Enemy npc = getEnemy(enemyName);
        
        if(npc == null)
        {
            System.out.println("No such enemy exists");
        }
        else if(player.getCurrentRoom() != npc.getCurrentRoom())    //Checks if the enemy is in the same room as the player.
        {
            System.out.println("There is no " + npc.getName() + " in this room.");
        }
        else if(player.checkWeapons() == null)  
        {
            System.out.println("You cannot attack the " +  npc.getName() + ", as you do not have a weapon");
            player.losePlayerHealth(npc.getDamage());       //If the player attacks an enemy without a weapon then the enemy loses no health but the player does.
            System.out.println("In retaliation you received " + npc.getDamage() + " damage, your new health is " + player.getHealth());
        }
        else {
            npc.updateEnemyHealth(player.getPlayerDamage());
            player.losePlayerHealth(npc.getDamage());
            npc.killEnemy();        //Checks if the enemy has lost all it's health and has died, if so then it will set the current room to null for the enemy.
            
            if(npc.getCurrentRoom() != null)
            {
                System.out.println("You have attacked the " + npc.getName() + "!");
                System.out.println(npc.getName() + "'s remaining health: " + npc.getHealth());
                System.out.println("In retaliation you received " + npc.getDamage() + " damage, your new health is " + player.getHealth());
            }
            else
            {
                System.out.println("In retaliation you received " + npc.getDamage() + " damage, your new health is " + player.getHealth());
                System.out.println("You have killed the " + npc.getName() + "!");
                Item item = npc.getEnemyItem();
                System.out.println("The " + npc.getName() + " dropped a " + item.getName() + "!");
            }
        }
    }

    /**
     * Print out some help information.
     * Here we print a cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around the Secret Chambers.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @param command Takes in the user's command.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * Create all the rooms and link their exits together.
     * Also adds the rooms to the ArrayList of rooms which is used by the transport method to get a random room.
     */
    public void createRooms()
    {
        // create the rooms
        diningRoom = new Room("Dining Room", "in a fairly small Dining Room with only 5 wooden chairs surrounding the Table.");
        kitchen = new Room("Kitchen", "in a rancid Kitchen with rotten food and bottles of cloudy Water scattered about.");
        infirmary = new Room("Infirmary", "shivering in an Infirmary because of a broken window letting in the icy wind." );
        guardRoom = new Room("Guard Room", "in a Guard Room where the weapons of previous guards were stored.");
        chapel = new Room("Chapel", "in an empty Chapel with broken stained glass and no candles to be seen...");
        laboratory = new Room("Laboratory", "in a Laboratory where questionable experiments were conducted resulting in some inhumane creatures...");
        shrine = new Room("Shrine", "in a gigantic shrine with various riches, unfortunately for you, they are too heavy to pick up.");
        transporter = new TransporterRoom("Transporter Room", "in a fairly new room that was designed by scientists, use the correct command to transport...");
        secretTreasureRoom = new TreasureRoom("Secret Treasure Room", "in a Secret Treasure Room");
        masterBedroom = new Room("Master Bedroom", "in the Master Bedroom, with a bed that no one has got up from after a night's sleep...");
        fakeTreasureRoom = new TreasureRoom("Treasure Room", "in a Fake Treasure Room.");
        forge = new Room("Forge", "in a Forgery where mysterious and toxic metals were first created...");
        
        
        // initialise room exits
        diningRoom.setExit("north", guardRoom);
        diningRoom.setExit("east", kitchen);
        diningRoom.setExit("west", chapel);
        
        guardRoom.setExit("south", diningRoom);

        kitchen.setExit("west", diningRoom);
        kitchen.setExit("south", infirmary);
        
        infirmary.setExit("north", kitchen);

        chapel.setExit("east", diningRoom);
        chapel.setExit("north", laboratory);
        chapel.setExit("down", shrine);
        
        laboratory.setExit("south", chapel);
        laboratory.addRoomDoor(fakeTreasureRoom, "north");  // Adds a locked door, currently the fakeTreasureRoom has no exits but this will be updated when the door is unlocked.

        shrine.setExit("up", chapel);
        shrine.setExit("east", forge);
        shrine.setExit("south", masterBedroom);
        shrine.setExit("west", transporter);
        
        masterBedroom.setExit("north", shrine);
        
        transporter.setExit("east", shrine);
        
        forge.setExit("west", shrine);
        forge.addRoomDoor(secretTreasureRoom, "east");  // Adds a locked door, currently the secretTreasureRoom has no exits but this will be updated when the door is unlocked.
        
        
        // Treasure rooms and transporters are not added to the ArrayList.
        allRooms.add(diningRoom);
        allRooms.add(kitchen);
        allRooms.add(infirmary);
        allRooms.add(guardRoom);
        allRooms.add(chapel);
        allRooms.add(laboratory);
        allRooms.add(shrine);
        allRooms.add(masterBedroom);
    }
    
    /**
     * Creates all the Items and their appropriate parameters.
     */
    public void createItems()
    {
        bread = new Item("bread", 10, "a mouldy slice of bread which would be very sickening to eat");
        dagger = new Weapon("dagger", 20, "a sharp, but dusty medieval dagger", 20);
        mace = new Weapon("mace", 30, "a heavy spiked mace that still has some dried blood on the handle", 30);
        health = new Item("health", 10, "a potion that will increase your health");
        damage = new Item("damage", 30, "a potion that will increase your damage");
        jewel = new Item("jewel", 100, "a very heavy jewel that you will not be able to take"); //Unpickable Item
        treasureKey = new Key("keycard", 10, "a mysterious key that will open a locked door somewhere in the dungeon", forge);
        fakeKey = new Key("key", 10,"a mysterious key that will open a locked door somewhere in the dungeon", laboratory);
        uranium = new Item("uranium", 40, "a very heavy and toxic metal that will take up your bag space for no reason"); // Very heavy Item
        gold = new Item("gold", 100, "a beautiful metal that you wish could pick up"); // Unpickable Item
    }
    
    /**
     * Adds the items to the room or enemy that will be holding them at the start of the game.
     */
    public void addItems()
    {
        kitchen.addItem(bread);
        guardRoom.addItem(dagger);
        masterBedroom.addItem(mace);
        shrine.addItem(jewel);
        shrine.addItem(gold);
        forge.addItem(uranium);
        laboratory.addItem(health);
        
        
        bandit.setCurrentItem(fakeKey);
        goblin.setCurrentItem(damage);
        ogre.setCurrentItem(treasureKey);
    }
    
    /**
     * Creates the endings for the treasure rooms, if the player enters these rooms then this speech will be displayed to them.
     */
    public void createEnding()
    {
        fakeTreasureRoom.endSpeech("Wow you really fell for that? Unfortunately that was a trap door to a Fake Treasure Room!"
                                    + "\n" + player.getPlayerName() + " has lost the game...");
    
        secretTreasureRoom.endSpeech("Congratulations, you have entered the Secret Treasure Room! You can retire now with the treasure rooms riches"
                                    + "\n" + player.getPlayerName() + " has won the game.");
    }  
}
