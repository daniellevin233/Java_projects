/**
 * Factory class for creating ship for spaceships war game
 */
public class SpaceShipFactory
{
    /** "Has a" SpaceWars object - game*/
    private SpaceWars shipSpaceWars;

    /**
     * Method creates array of ships for the game instance
     * @param args - console arguments signing ships that should be added to the game,
     *             next ships are available:
     *             Enemy ships:
     *             a - aggressive
     *             b - basher
     *             r - runner
     *             d - drunker
     *             s - special
     *             User ships:
     *             h - human
     * @return - array of ships instances
     */
    public static SpaceShip[] createSpaceShips(String[] args)
    {
        if (args.length < 2)
        {
            return null;
        }
        SpaceShip[] shipsArray = new SpaceShip[args.length];
        int i = 0;
        for(String curShip : args)
        {
            switch (curShip)
            {
                case "h":
                    shipsArray[i] = new HumanShip();
                    break;
                case "r":
                    shipsArray[i] = new Runner();
                    break;
                case "b":
                    shipsArray[i] = new Basher();
                    break;
                case "a":
                    shipsArray[i] = new Aggressive();
                    break;
                case "d":
                    shipsArray[i] = new Drunkard();
                    break;
                case "s":
                    shipsArray[i] = new Special();
                    break;
                default:
                    System.out.print("Available spaceship types:\n" +
                            "\th - Human\n" +
                            "\td - Drunkard\n" +
                            "\tr - Runner\n" +
                            "\ta - Aggressive\n" +
                            "\tb - Basher\n" +
                            "\ts - Special\n " +
                            "\n" +
                            "Please, try again!\n");
                    return null;
            }
            i++;
        }
        return shipsArray;
    }
}
