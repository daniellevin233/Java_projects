/**
 * Aggressive ship class, type of enemy ship
 */
public class Aggressive extends EnemyShip
{
    /** Minimal distance to start shooting for aggressive ship */
    private static final double MIN_DISTANCE_TO_FIRE = 0.21;

    protected void teleportEnemyShip(SpaceWars game)
    {}

    /**
     * Method decides if the ship should be accelerated
     * @param game - game object which contains this ship,
     * @return boolean - true if ship should be accelerated, false otherwise
     */
    protected boolean accelerateShip(SpaceWars game)
    {
        return true;
    }

    /**
     * Return value that means direction for turn of the ship - left, right or forward,
     * Aggressive always tries to get the closest ship
     * @param game - game object which contains this ship
     * @return - integer signing turn direction
     */
    protected int turnShip(SpaceWars game)
    {
        SpaceShip closestShip = game.getClosestShipTo(this);
        return SpaceShip.getTurnDirection(this.getPhysics().angleTo(closestShip.getPhysics()));
    }

    /**
     * Aggressive doesn't use the shield
     * @param game the game object, to which this ship belongs
     */
    protected void shieldOnEnemyShip(SpaceWars game)
    {}

    /**
     * Attempts to fire a shot, aggressive shoots when some other ship is close enough
     *
     * @param game the game object.
     */
    protected void fireEnemyShip(SpaceWars game)
    {
        SpaceShip closestShip = game.getClosestShipTo(this);
        if(this.getPhysics().angleTo(closestShip.getPhysics()) < MIN_DISTANCE_TO_FIRE)
        {
            this.fire(game);
        }
    }
}
