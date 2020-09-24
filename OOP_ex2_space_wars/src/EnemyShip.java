/*----=  Imports  =----*/

import java.awt.*;

import static oop.ex2.GameGUI.ENEMY_SPACESHIP_IMAGE;
import static oop.ex2.GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;

/**
 * Abstract class for every enemy ship in the game
 */
public abstract class EnemyShip extends SpaceShip
{
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
        this.getPhysics().move(this.accelerateShip(game), this.turnShip(game));
        this.shieldOnEnemyShip(game);
        this.fireEnemyShip(game);
        this.energyRegeneration();
    }

    /**
     * Attempts to teleport enemy ship.
     */
    protected abstract void teleportEnemyShip(SpaceWars game);

    /**
     * Method decides if the ship should be accelerated
     * @param game - game object which contains this ship
     * @return boolean - true if ship should be accelerated, false otherwise
     */
    protected abstract boolean accelerateShip(SpaceWars game);

    /**
     * Return value that means direction for turn of the ship - left, right or forward
     * @param game - game object which contains this ship
     * @return - integer signing turn direction
     */
    protected abstract int turnShip(SpaceWars game);

    /**
     * Attempts to turn on the shield for every enemy ships
     * @param game the game object, to which this ship belongs
     */
    protected abstract void shieldOnEnemyShip(SpaceWars game);

    /**
     * Attempts to fire a shot for every enemy ship
     *
     * @param game the game object.
     */
    protected abstract void fireEnemyShip(SpaceWars game);

    /*----=  Getters  =----*/

    /**
     * @return the default image of the ship, without any upgrades
     */
    public Image getDefaultImage()
    {
        return ENEMY_SPACESHIP_IMAGE;
    }

    /**
     * @return the image of the ship with shield being activated on it
     */
    public Image getShieldImage()
    {
        return ENEMY_SPACESHIP_IMAGE_SHIELD;
    }
}
