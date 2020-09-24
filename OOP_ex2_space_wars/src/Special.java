/**
 * Special ship class that integrates different features, but is balanced enough to not be too strong
 */
public class Special extends EnemyShip
{
    /*----=  Static Fields  =----*/

    /** Threshold for hp points that are considered as dying state */
    private static final int HP_THRESHOLD = 3;

    /** Minimal distance that is considered as safe */
    private static final double MIN_SAFETY_DISTANCE = 0.1;

    /** Minimal angle for switching front attack mode */
    private static final double MIN_ANGLE_TO_SHOOT = Math.PI / 3;

    /*----=  Fields  =----*/

    /** Boolean variable stating current mode of the special ship */
    private boolean isFrontAttack = false;

    /*----=  Methods  =----*/

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game)
    {
        this.shieldDown();
        this.teleportEnemyShip(game);
        SpaceShip closestShip = game.getClosestShipTo(this);
        if(Math.abs(this.getPhysics().getAngle() +
                closestShip.getPhysics().getAngle()) <= MIN_ANGLE_TO_SHOOT && isAliveEnough())
        {
            isFrontAttack = true;
        }
        this.getPhysics().move(this.accelerateShip(game), this.turnShip(game));
        this.shieldOnEnemyShip(game);
        this.fireEnemyShip(game);
        this.energyRegeneration();
        isFrontAttack = false;
    }

    /**
     * Attempts to teleport enemy ship, special try to teleport when dying
     */
    protected void teleportEnemyShip(SpaceWars game)
    {
        if(!isAliveEnough())
        {
            this.teleport();
        }
    }

    /**
     * Method decides if the ship should be accelerated,
     * The special ship accelerates during the front attack
     * @param game - game object which contains this ship
     * @return boolean - true if ship should be accelerated, false otherwise
     */
    protected boolean accelerateShip(SpaceWars game)
    {
        return isFrontAttack;
    }

    /**
     * Return value that means direction for turn of the ship - left, right or forward,
     * Special ship turns only if it has enough health to try to kill another ships
     * @param game - game object which contains this ship
     * @return - integer signing turn direction
     */
    protected int turnShip(SpaceWars game)
    {
        if(isAliveEnough())
        {
            SpaceShip closestShip = game.getClosestShipTo(this);
            return SpaceShip.getTurnDirection(this.getPhysics().angleTo(closestShip.getPhysics()));
        }
        else
        {
            return NO_TURN_ANGLE;
        }
    }

    /**
     * Attempts to turn on the shield for special ship, when it's in the mode of front attack
     * @param game the game object, to which this ship belongs
     */
    protected void shieldOnEnemyShip(SpaceWars game)
    {
        if(isFrontAttack || isDangerDistance(game))
        {
            this.shieldOn();
        }
    }

    /**
     * Attempts to fire a shot, special shoots when it close to other ship and its mode is front attack
     *
     * @param game the game object.
     */
    protected void fireEnemyShip(SpaceWars game)
    {
        if(isFrontAttack || isDangerDistance(game))
        {
            this.fire(game);
        }
    }

    /**
     * Check if this ship is alive enough
     * @return - true if ship's health bigger then some constant threshold, false otherwise
     */
    private boolean isAliveEnough()
    {
        return (this.getCurHealth() > HP_THRESHOLD);
    }

    /**
     * Check if this ship is located in dangerous distance from the closest ship in the game
     * @return - true if ship's distance from the closest ship is smaller then some constant threshold, false
     * otherwise
     */
    private boolean isDangerDistance(SpaceWars game)
    {
        return (this.getPhysics().distanceFrom(game.getClosestShipTo(this).getPhysics())
                < MIN_SAFETY_DISTANCE);
    }
}