/*-----=  imports  =-----*/

import oop.ex3.spaceship.Item;

import java.util.*;

/**
 * Class for Locker implementation extending abstract Storage class
 */
public class Locker extends Storage
{
    /*-----=  static field  =-----*/

    /** 50% double constant */
    private static final double PERCENT_50 = 0.5;

    /** 20% double constant */
    private static final double PERCENT_20 = 0.2;

    /** static constant for LongTermStorage to which all the Lockers connected */
    private static LongTermStorage commonLTS = new LongTermStorage();

    /** Map of contradicting items, which can't be stored simultaneously in the same locker */
    private static Map<String, String> contradictingItemsMap = new HashMap<>(){{
        put("baseball bat", "football");
        put("football", "baseball bat");
    }};

    /*-----=  methods  =-----*/

    /**
     * Constructor of the Locker
     * @param capacity - capacity of the created Locker
     */
    public Locker(int capacity)
    {
        super(capacity);
    }

    /**
     * Method tries to remove n items from the Locker
     * @param item - item to be removed
     * @param n - number of items to be removed
     * @return - 0 if removing succeed, -1 otherwise
     */
    public int removeItem(Item item, int n)
    {
        if(n < VALID_VALUE)
        {
            System.out.println("Error: Your request cannot be completed at this time. Problem: cannot " +
                    "remove a negative number of items of type " + item.getType());
            return INVALID_VALUE;
        }
        if(this.getItemCount(item.getType()) < n)
        {
            System.out.println("Error: Your request cannot be completed at this time. Problem: the locker " +
                     "does not contain " + n + " items of type " + item.getType());
            return INVALID_VALUE;
        }
        this.getInventory().replace(item.getType(), this.getItemCount(item.getType()) - n);
        this.setCurOccupiedCapacity(this.getCurOccupiedCapacity() - n * item.getVolume());
        return VALID_VALUE;
    }

    /**
     * Manage additional functionality for adding n items to the storage.
     * For Locker manages the 20-50% limits, check that ratio of added item is in range 21-50, and if not,
     * trying to send it to LongTermStorage
     * @param item - item to be added
     * @param n - number of items to be added
     * @return - 0 if limits were complied or proper quantity of items was passed to LongTermStorage
     * successfully, -1 otherwise
     */
    protected int addItemAdditionalFunctionality(Item item, int n)
    {
        double ratio =
                (double)(this.getInventory().get(item.getType()) * item.getVolume()) / this.getCapacity();
        return ratio > PERCENT_50 ? this.transferItemsToLTS(item) : VALID_VALUE;
    }

    /**
     * Method tries to transfer items to LongTermStorage which is connected to all the Lockers
     * @param item - item that should be transfer
     * @return - 0 if transfer succeeded, -1 otherwise
     */
    private int transferItemsToLTS(Item item)
    {
        int redundantItems = this.getItemCount(item.getType()) -
                (int)(PERCENT_20 * this.getCapacity() / item.getVolume()); // items to be transfer to LTS
        if(Locker.commonLTS.addItem(item, redundantItems) == VALID_VALUE) // items passed to LTS successfully
        {
            this.removeItem(item, redundantItems);
            System.out.println("Warning: Action successful, but has caused items to be moved to storage");
            return SPECIAL_VALID_VALUE;
        }
        nItemsCantBeInsertedMSG(item, redundantItems);
        return INVALID_VALUE;
    }

    /**
     * Manage additional constraints on the items being added to storage
     * For locker there is a constraint that "football" item can't be contained with "baseball bat" item
     * @param item - item to be added
     * @param n - number of items to be added
     * @return - -1 if some constraint will be violated by adding n items to storage, 0 otherwise
     */
    protected int additionalConstraints(Item item, int n)
    {
        String contradictingItemType = contradictingItemsMap.get(item.getType());
        return this.getInventory().containsKey(contradictingItemType) ? SPECIAL_INVALID_VALUE : VALID_VALUE;
    }
}