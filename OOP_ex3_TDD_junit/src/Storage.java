/*-----=  imports  =-----*/

import oop.ex3.spaceship.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class for storage - implements some common methods that storage is able to perform
 */
public abstract class Storage
{
    /*-----=  static fields  =-----*/

    /** Failure value*/
    protected static final int INVALID_VALUE = -1;

    /** Default value */
    protected static final int VALID_VALUE = 0;

    /** Success value*/
    protected static final int SPECIAL_VALID_VALUE = 1;

    /** Second type of failure value*/
    protected static final int SPECIAL_INVALID_VALUE = -2;

    /*-----=  fields  =-----*/

    /** Capacity of the storage object */
    private final int capacity;

    /** Map representing current inventory of this storage object */
    private Map<String, Integer> inventory;

    /** Integer representing current occupied capacity of the storage in volumes */
    private int curOccupiedCapacity;

    /*-----=  methods  =-----*/

    /**
     * Cobstructor for storage object
     * @param capacity - capacity of the storage being initialized
     */
    public Storage(int capacity)
    {
        this.inventory = new HashMap<>();
        this.curOccupiedCapacity = VALID_VALUE;
        this.capacity = capacity;
    }

    /**
     * Methods adds item into the storage
     * @param item - item to be added
     * @param n - amount of items to be added
     * @return - 0 if item was successfully added, 1 if it was successfully passed to the LongTermStorage,
     * and -1 otherwise
     */
    public int addItem(Item item, int n)
    {
        if(n < VALID_VALUE || (item.getVolume() * n > this.getAvailableCapacity()))
        {
            nItemsCantBeInsertedMSG(item, n);
            return INVALID_VALUE;
        }
        if(this.additionalConstraints(item, n) == SPECIAL_INVALID_VALUE)
        {
            System.out.println("”Error: Your request cannot be completed at this time. Problem: the locker" +
                    " cannot contain items of type " + item.getType() + ", as it contains a contradicting " +
                    "item.");
            return SPECIAL_INVALID_VALUE;
        }
        this.inventory.put(item.getType(), n);
        this.setCurOccupiedCapacity(this.getCurOccupiedCapacity() + item.getVolume() * n);
        return this.addItemAdditionalFunctionality(item, n);
    }

    /**
     * @param type - type of item to be counted
     * @return - number of items of type 'type' stored in the storage
     */
    public int getItemCount(String type)
    {
        return this.inventory.getOrDefault(type, VALID_VALUE);
    }

    /**
     * @return - map, representing current inventory of the storage - items and their counters
     */
    public Map<String, Integer> getInventory()
    {
        return this.inventory;
    }

    /**
     * @return - capacity of the storage in volumes
     */
    public int getCapacity()
    {
        return this.capacity;
    }

    /**
     * @return - current available capacity of the storage in volumes
     */
    public int getAvailableCapacity()
    {
        return this.capacity - this.getCurOccupiedCapacity();
    }

    /**
     * Manage additional functionality for adding n items to the storage
     * @param item - item to be added
     * @param n - number of items to be added
     * @return - 0 if additional functionality completed successfully, -1 otherwise
     */
    protected abstract int addItemAdditionalFunctionality(Item item, int n);

    /**
     * Manage additional constraints on the items being added to storage
     * @param item - item to be added
     * @param n - number of items to be added
     * @return - -1 if some constraint will be violated by adding n items to storage, 0 otherwise
     */
    protected abstract int additionalConstraints(Item item, int n);

    /**
     * Prints error message if n items of type item can't be added to storage
     * @param item - item to be added
     * @param n - number of items to be added
     */
    protected void nItemsCantBeInsertedMSG(Item item, int n)
    {
        System.out.println("Error: Your request cannot be completed at this time. Problem: no room for "
                + n + " items of type " + item.getType());
    }

    /**
     * @return - current occupied capacity of the storage
     */
    protected int getCurOccupiedCapacity()
    {
        return this.curOccupiedCapacity;
    }

    /**
     * Set new current occupied capacity of the storage
     * @param newCapacity - new capacity in volumes to be set
     */
    protected void setCurOccupiedCapacity(int newCapacity)
    {
        this.curOccupiedCapacity = newCapacity;
    }
}
