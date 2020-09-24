/*----=  Imports  =----*/

import java.awt.Image;
import oop.ex2.*;

/**
 * The API spaceships need to implement for the SpaceWars game.
 * It is your decision whether SpaceShip.java will be an interface, an abstract class,
 * a base class for the other spaceships or any other option you will choose.
 *
 * @author oop
 */
public abstract class SpaceShip{

    /*----=  Static Fields  =----*/

    /** starting maximal energy value */
    private static final int MAX_ENERGY_LEVEL_START = 210;

    /** starting current energy value */
    private static final int CUR_ENERGY_START = 190;

    /** starting maximal health value */
    private static final int HEALTH_START = 22;

    /** number of rounds as a pause between shoots */
    private static final int PAUSE_AFTER_SHOOTING = 7;

    /** energy that is necessary for teleporting*/
    private static final int TELEPORT_ENERGY = 140;

    /** minimal relevant value*/
    private static final int MIN_VALUE = 0;

    /** energy that is necessary for shooting*/
    private static final int SHOOT_ENERGY = 19;

    /** energy that is necessary for shield activating*/
    private static final int SHIELD_ENERGY = 3;

    /** energy points descending when getting damage*/
    private static final int SHOT_DAMAGE = 10;

    /** energy points increasing when getting bash*/
    private static final int BASH_ENERGY_UPGRADE = 18;

    /** angle signing "forward moving" */
    protected static final int NO_TURN_ANGLE = 0;

    /** angle signing turning left */
    protected static final int TURN_LEFT_VALUE = 1;

    /*----=  Static Methods  =----*/

    /**
     * Static method for passing angle in radians to binary system - left or right
     * @param angle - double value of angle to be passed
     * @return direction of turning in int representation - left, right or forward
     */
    protected static int getTurnDirection(double angle)
    {
        if (angle > NO_TURN_ANGLE)
        {
            return TURN_LEFT_VALUE;
        }
        else if (angle < NO_TURN_ANGLE)
        {
            return -TURN_LEFT_VALUE;
        }
        else
        {
            return NO_TURN_ANGLE;
        }
    }

    /*----=  Other Fields  =----*/

    /** every ship 'has a' physics */
    private SpaceShipPhysics shipPhysics;

    /** every ship 'has a' its visualisation */
    private Image shipImage;

    /** sign of current maximal energy */
    private int curMaxEnergy;

    /** sign of current energy */
    private int curEnergy;

    /** sign of current health */
    private int curHealth;

    /** sign of current shield state */
    private boolean shieldActivated;

    /** sign of number of rounds passed after last shoot */
    private int roundsAfterLastShots;

    /*----=  Constructors  =----*/

    /**
     * Creates a new space ship for the game
     */
    public SpaceShip()
    {
        this.shipPhysics = new SpaceShipPhysics();
        this.shipImage = this.getDefaultImage();
        this.curMaxEnergy = MAX_ENERGY_LEVEL_START;
        this.curEnergy = CUR_ENERGY_START;
        this.curHealth = HEALTH_START;
        this.shieldActivated = false;
        this.roundsAfterLastShots = MIN_VALUE;
    }


    /*----=  Methods  =----*/

    /**
     * Does the actions of this ship for this round. 
     * This is called once per round by the SpaceWars game driver.
     * 
     * @param game the game object to which this ship belongs.
     */
    public abstract void doAction(SpaceWars game);

    /**
     * This method is called every time a collision with this ship occurs 
     */
    public void collidedWithAnotherShip()
    {
        if(!shieldActivated)
        {
            this.missedHit();
        }
        else // Bashing
        {
            this.setMaxEnergy(this.getCurMaxEnergy() + BASH_ENERGY_UPGRADE);
            this.setCurEnergy(this.getCurEnergy() + BASH_ENERGY_UPGRADE);
        }
    }

    /** 
     * This method is called whenever a ship has died. It resets the ship's 
     * attributes, and starts it at a new random position.
     */
    public void reset()
    {
        this.shipPhysics = new SpaceShipPhysics();
        this.curMaxEnergy = MAX_ENERGY_LEVEL_START;
        this.curEnergy = CUR_ENERGY_START;
        this.curHealth = HEALTH_START;
        this.shieldActivated = false;
        this.roundsAfterLastShots = MIN_VALUE;
    }

    /**
     * Checks if this ship is dead.
     *
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead()
    {
        return (this.curHealth == MIN_VALUE);
    }

    /**
     * Gets the physics object that controls this ship.
     * 
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics()
    {
        return this.shipPhysics;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit()
    {
        if(!this.shieldActivated)
        {
            this.missedHit();
        }
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     * 
     * @return the image of this ship.
     */
    public Image getImage()
    {
        return this.shipImage;
    }

    /**
     * Attempts to fire a shot.
     * 
     * @param game the game object.
     */
    public void fire(SpaceWars game)
    {
        int curRounds = getRoundsAfterLastShots();
        if(curRounds == MIN_VALUE)
        {
            if(isEnoughEnergy(SHOOT_ENERGY))
            {
                game.addShot(this.getPhysics());
                curRounds = PAUSE_AFTER_SHOOTING;
                this.setCurEnergy(this.curEnergy - SHOOT_ENERGY);
            }
        }
        else // ship isn't ready to shoot yet
        {
            curRounds -= 1;
        }
        this.setRoundsAfterLastShot(curRounds);
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn()
    {
        if(isEnoughEnergy(SHIELD_ENERGY))
        {
            this.setImage(this.getShieldImage());
            this.setCurEnergy(this.curEnergy - SHIELD_ENERGY);
            this.shieldActivated = true;
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport()
    {
        if(isEnoughEnergy(TELEPORT_ENERGY))
        {
            this.shipPhysics = new SpaceShipPhysics();
            this.setCurEnergy(this.curEnergy - TELEPORT_ENERGY);
        }
    }

    /**
     * Return value that means direction for turn of the ship - left, right or forward
     * @param game - game object which contains this ship
     * @return - integer signing turn direction
     */
    protected abstract int turnShip(SpaceWars game);

    /**
     * Method decides if the ship should be accelerated
     * @param game - game object which contains this ship
     * @return boolean - true if ship should be accelerated, false otherwise
     */
    protected abstract boolean accelerateShip(SpaceWars game);

    /**
     * Attempts to regenerate the energy
     */
    protected void energyRegeneration()
    {
        if(curEnergy < curMaxEnergy)
        {
            this.curEnergy++;
        }
    }

    /**
     * Method takes the shield out from the ship
     */
    protected void  shieldDown()
    {
        this.setImage(this.getDefaultImage());
        this.shieldActivated = false;
    }

    /**
     * Check if the ship has enough energy to make some action
     * @param actionEnergy - energy points needed to complete the action
     * @return - true if ship has enough energy, false otherwise
     */
    private boolean isEnoughEnergy(int actionEnergy)
    {
        return (actionEnergy <= this.curEnergy);
    }


    /**
     * Update current parameters of the ship when it missed hit
     */
    private void missedHit()
    {
        this.setMaxEnergy(this.getCurMaxEnergy() - SHOT_DAMAGE);
        this.setCurHealth(this.getCurHealth() - 1);
    }

    // getters

    /**
     * @return - current energy points as integer
     */
    protected int getCurEnergy()
    {
        return this.curEnergy;
    }

    /**
     * @return - current maximal energy points as integer
     */
    protected int getCurMaxEnergy()
    {
        return this.curMaxEnergy;
    }

    /**
     * @return - current rounds quality after last shooting as integer
     */
    private int getRoundsAfterLastShots()
    {
        return this.roundsAfterLastShots;
    }

    /**
     * @return - current health points as integer
     */
    protected int getCurHealth()
    {
        return this.curHealth;
    }

    /**
     * @return the default image of the ship, without any upgrades
     */
    protected abstract Image getDefaultImage();

    /**
     * @return the image of the ship with shield being activated on it
     */
    protected abstract Image getShieldImage();

    // setters

    /**
     * Set new current energy points
     * @param newEnergy - new energy to be set
     */
    private void setCurEnergy(int newEnergy)
    {
        this.curEnergy = Math.max(Math.min(newEnergy, curMaxEnergy), MIN_VALUE);
    }

    /**
     * Set new current maximal energy points
     * @param newMaxEnergy - new maximal energy to be set
     */
    private void setMaxEnergy(int newMaxEnergy)
    {
        this.curMaxEnergy = newMaxEnergy;
        this.setCurEnergy(Math.min(newMaxEnergy, this.getCurEnergy()));
    }

    /**
     * Set new current rounds quality after last shooting as integer
     * @param newLeftRounds - rounds quality to be set
     */
    private void setRoundsAfterLastShot(int newLeftRounds)
    {
        this.roundsAfterLastShots = newLeftRounds;
    }

    /**
     * Set new current image of the ship
     * @param newImage - image to be set
     */
    private void setImage(Image newImage)

    {
        this.shipImage = newImage;
    }

    /**
     * Set new current health points as integer
     * @param newHealth - health value to be set
     */
    private void setCurHealth(int newHealth)
    {
        this.curHealth = newHealth;
    }
}
