/*----=  Imports  =----*/

import java.awt.*;

import static oop.ex2.GameGUI.SPACESHIP_IMAGE;
import static oop.ex2.GameGUI.SPACESHIP_IMAGE_SHIELD;

/**
 * Class for ship controlled by user/human
 */
public class HumanShip extends SpaceShip
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
        this.teleportHumanShip(game);
        this.getPhysics().move(this.accelerateShip(game), this.turnShip(game));
        this.shieldOnHumanShip(game);
        this.fireHumanShip(game);
        this.energyRegeneration();
    }

    /**
     * Attempts to teleport human ship if the proper command was given
     */
    private void teleportHumanShip(SpaceWars game)
    {
        if(game.getGUI().isTeleportPressed())
        {
            this.teleport();
        }
    }

    /**
     * Return value that means direction for turn of the ship - left, right or forward, depending
     * on user commands
     * @param game - game object which contains this ship
     * @return - integer signing turn direction
     */
    protected int turnShip(SpaceWars game)
    {
        int turn = 0;
        if(game.getGUI().isLeftPressed() != game.getGUI().isRightPressed()) // filter the case either both
            // buttons activated or deactivated
        {
            turn = game.getGUI().isLeftPressed() ? TURN_LEFT_VALUE : -TURN_LEFT_VALUE;
        }
        return turn;
    }

    /**
     * Method decides if the ship should be accelerated
     * @param game - game object which contains this ship
     * @return boolean - true if ship should be accelerated, false otherwise
     */
    protected boolean accelerateShip(SpaceWars game)
    {
        return game.getGUI().isUpPressed();
    }

    /**
     * Attempts to turn on the shield, if the proper command was given
     * @param game the game object, to which this ship belongs
     */
    private void shieldOnHumanShip(SpaceWars game)
    {
        if(game.getGUI().isShieldsPressed())
        {
            this.shieldOn();
        }
    }

    /**
     * Attempts to fire a shot, if the proper command was given
     *
     * @param game the game object, to which this ship belongs
     */
    private void fireHumanShip(SpaceWars game)
    {
        if(game.getGUI().isShotPressed())
        {
            this.fire(game);
        }
    }

    /*----=  getters  =----*/

    /**
     * @return the default image of the ship, without any upgrades
     */
    public Image getDefaultImage()
    {
        return SPACESHIP_IMAGE;
    }

    /**
     * @return the image of the ship with shield being activated on it
     */
    public Image getShieldImage()
    {
        return SPACESHIP_IMAGE_SHIELD;
    }
}