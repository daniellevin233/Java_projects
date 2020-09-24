/**
 * Drunkard ship class, weird type of enemy ship
 */
public class Drunkard extends EnemyShip
{
    /*----=  Static Fields  =----*/

    /** Minimal distance considered as dangerous */
    private static final double DANGER_DISTANCE = 0.12;

    /** Number od possible directions of turning */
    private static final int NUM_OF_TURN_DIRECTIONS = 2;

    /** Value signing the frequency of teleporting for drunkard ship */
    private static final int TELEPORT_DIVISOR_VALUE = 7;

    /** Factor value to get integer between 0 to 10 from double between 0 to 1 */
    private static final int DOT_FACTOR = 10;

    /** Counter of created drunkard ships */
    private static int drunkardCounter = 0;

    /**
     * Constructor for drunkard ship, add counter for random features
     */
    Drunkard()
    {
        super();
        drunkardCounter++;
    }

    /**
     * Attempts to teleport drunkard ship, it's teleported when its health points divided by some constant
     * value without reminder
     */
    protected void teleportEnemyShip(SpaceWars game)
    {
        if(this.getCurHealth() % TELEPORT_DIVISOR_VALUE == 0)
        {
            this.teleport();
        }
    }

    /**
     * Method decides if the ship should be accelerated
     * @param game - game object which contains this ship
     * @return boolean - true if ship should be accelerated, false otherwise
     */
    protected boolean accelerateShip(SpaceWars game)
    {
        return false;
    }

    /**
     * Return value that means direction for turn of the ship - left, right or forward
     * every even drunkard ship that is created turn in one direction, every odd - in another
     * @param game - game object which contains this ship
     * @return - integer signing turn direction
     */
    protected int turnShip(SpaceWars game)
    {
        return (int)Math.pow(-1, drunkardCounter % NUM_OF_TURN_DIRECTIONS);
    }

    /**
     * Attempts to turn on the shield for drunkard ship when it feels danger (too close to another ship)
     * @param game the game object, to which this ship belongs
     */
    protected void shieldOnEnemyShip(SpaceWars game)
    {
        SpaceShip closestShip = game.getClosestShipTo(this);
        if(this.getPhysics().distanceFrom(closestShip.getPhysics()) <= DANGER_DISTANCE)
        {
            this.shieldOn();
        }
    }

    /**
     * Attempts to fire a shot, drunkard shoots when it's close to die in random terms:
     * when its health less then random number from 0 to 10
     * @param game the game object.
     */
    protected void fireEnemyShip(SpaceWars game)
    {
        if(this.getCurHealth() <= (Math.random() * DOT_FACTOR))
        {
            this.fire(game);
        }
    }
}
