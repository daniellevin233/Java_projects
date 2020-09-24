/*-----=  imports  =-----*/

import oop.ex3.spaceship.Item;

/**
 * Class for testing LongTermStorage class
 */
public class LongTermStorage extends Storage
{
    /*-----=  static fields  =-----*/

    /** capacity of storage */
    private static final int capacity = 1000;

    /*-----=  methods  =-----*/

    /**
     * Constructor initializing the storage object
     */
    public LongTermStorage()
    {
        super(capacity);
    }

    /**
     * Method cleans the LongTernStorage up, resets its content
     */
    public void resetInventory()
    {
        this.getInventory().clear();
        this.setCurOccupiedCapacity(VALID_VALUE);
    }

    /**
     * Manage additional functionality for adding n items to the storage
     * LongTermStorage doesn't have any additional functionality
     * @param item - item to be added
     * @param n - number of items to be added
     * @return - 0
     */
    protected int addItemAdditionalFunctionality(Item item, int n)
    {
        return VALID_VALUE;
    }

    /**
     * Manage additional constraints on the items being added to storage
     * LongTermStorage doesn't have any constraints
     * @param item - item to be added
     * @param n - number of items to be added
     * @return - always 0
     */
    protected int additionalConstraints(Item item, int n)
    {
        return VALID_VALUE;
    }
}
