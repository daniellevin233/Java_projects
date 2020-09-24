/**
 * Basher ship class, type of enemy ship
 */
public class Basher extends EnemyShip
{

    /*----=  Static Fields  =----*/

    /** Minimal distance to feel safe for basher */
    private static final double MIN_SAFETY_DISTANCE = 0.19;

    /**
     * Basher doesn't use teleport
     */
    protected void teleportEnemyShip(SpaceWars game)
    {}

    /**
     * Method decides if the ship should be accelerated
     * @param game - game object which contains this ship
     * @return boolean - true if ship should be accelerated, false otherwise
     */
    protected boolean accelerateShip(SpaceWars game)
    {
        return true;
    }

    /**
     * Return value that means direction for turn of the ship - left, right or forward
     * Basher always tries to get the closest ship
     * @param game - game object which contains this ship
     * @return - integer signing turn direction
     */
    protected int turnShip(SpaceWars game)
    {
        SpaceShip closestShip = game.getClosestShipTo(this);
        return SpaceShip.getTurnDirection(this.getPhysics().angleTo(closestShip.getPhysics()));
    }

    /**
     * Attempts to turn on the shield when get close enough to some other ship
     * @param game the game object, to which this ship belongs
     */
    protected void shieldOnEnemyShip(SpaceWars game)
    {
        SpaceShip closestShip = game.getClosestShipTo(this);
        if(this.getPhysics().distanceFrom(closestShip.getPhysics()) <= MIN_SAFETY_DISTANCE)
        {
            this.shieldOn();
        }
    }

    /**
     * Basher doesn't shoot at all
     *
     * @param game the game object.
     */
    protected void fireEnemyShip(SpaceWars game)
    {}
}
