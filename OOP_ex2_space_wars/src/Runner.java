/**
 * Runner ship class, type of enemy ship
 */
public class Runner extends EnemyShip
{

    /*----=  Static Fields  =----*/

    /** Minimal distance considered safe */
    private static final double MIN_SAFETY_DISTANCE = 0.25;

    /** Minimal angle from another ships considered safe */
    private static final double MIN_SAFETY_ANGLE = 0.23;

    /*----=  Methods  =----*/

    /**
     * Attempts to teleport runner ship when it's located in front of the closest ship in small distance
     */
    protected void teleportEnemyShip(SpaceWars game)
    {
        SpaceShip dangerShip = game.getClosestShipTo(this);
        if(this.getPhysics().distanceFrom(dangerShip.getPhysics()) < MIN_SAFETY_DISTANCE &&
                this.getPhysics().angleTo(dangerShip.getPhysics()) < MIN_SAFETY_ANGLE)
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
        return true;
    }

    /**
     * Return value that means direction for turn of the ship - left, right or forward
     * Runner always try to get closest ship
     * @param game - game object which contains this ship
     * @return - integer signing turn direction
     */
    protected int turnShip(SpaceWars game)
    {
        SpaceShip dangerShip = game.getClosestShipTo(this);
        return SpaceShip.getTurnDirection(-this.getPhysics().angleTo(dangerShip.getPhysics()));
    }

    /**
     * Runner doesn't use the shield
     * @param game the game object, to which this ship belongs
     */
    protected void shieldOnEnemyShip(SpaceWars game)
    {}

    /**
     * Runner doesn't shoot at all
     *
     * @param game the game object.
     */
    protected void fireEnemyShip(SpaceWars game)
    {}

}
