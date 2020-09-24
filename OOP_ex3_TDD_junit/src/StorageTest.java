/*-----=  imports  =-----*/

import oop.ex3.spaceship.*;
import org.junit.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Abstract class for Storage testing
 */
public abstract class StorageTest
{
    /*-----=  static fields  =-----*/

    /** Default value */
    protected static final int BASE_VALUE = 0;

    /** Success value*/
    protected static final int NEG_VALUE = -1;

    /** Failure value*/
    protected static final int POS_VALUE = 1;

    /*-----=  fields  =-----*/

    /** Item for testing */
    protected final Item item2 = oop.ex3.spaceship.ItemFactory.createSingleItem("baseball bat");//volume=2

    /** Item for testing */
    protected final Item item3 = oop.ex3.spaceship.ItemFactory.createSingleItem("helmet, size 1");//volume=3

    /** Item for testing */
    protected final Item item5 = oop.ex3.spaceship.ItemFactory.createSingleItem("helmet, size 3");//volume=5

    /** Item for testing */
    protected final Item item10 = oop.ex3.spaceship.ItemFactory.createSingleItem("spores engine");//volume=10

    /** Item for testing */
    protected final Item item4 = oop.ex3.spaceship.ItemFactory.createSingleItem("football");//volume=4

    /** Storage object to be tested */
    private Storage testStorage;

    /*-----=  methods  =-----*/

    /**
     * Constructor of the storage test, initializes object for testing
     * @param testStorage - Storage to initialize with
     */
    public StorageTest(Storage testStorage)
    {
        this.testStorage = testStorage;
    }

    /*-----=  tests  =-----*/

    /**
     * Test 1 for addItem: checking invalid input - negative
     */
    @Test
    public void testAddItem1()
    {
        int res = testStorage.addItem(item2, NEG_VALUE); // n = -1
        assertEquals("Add should fail for negative input", NEG_VALUE, res);
    }

    /**
     * Test 2 for addItem: checking invalid input - zero
     */
    @Test
    public void testAddItem2()
    {
        int res = testStorage.addItem(item2, BASE_VALUE); // n = 0
        assertEquals("Add should success for 0 items", BASE_VALUE, res);
    }

    /**
     * Test 3 for addItem: checking regular adding
     */
    @Test
    public void testAddItem3()
    {
        int res = testStorage.addItem(item3, POS_VALUE); // n = 1
        assertEquals("Add should success", BASE_VALUE, res);
    }

    /**
     * Test 4 for addItem: checking adding too much items
     */
    @Test
    public void testAddItem4()
    {
        int res = testStorage.addItem(item10, testStorage.getCapacity()); // n = capacity
        assertEquals("Add should fail", NEG_VALUE, res);
    }

    /**
     * Test 1 for getItemCount: checking empty Storage
     */
    @Test
    public void testGetItemCount1()
    {
        assertEquals("The storage should be empty", BASE_VALUE, testStorage.getItemCount(item2.getType()));
    }

    /**
     * Test 2 for getItemCount: checking Storage with added Item
     */
    @Test
    public void testGetItemCount2()
    {
        testStorage.addItem(item2, POS_VALUE);
        assertEquals("The counter is wrong", POS_VALUE, testStorage.getItemCount(item2.getType()));
    }

    /**
     * Test 1 for getInventory: checking empty Storage
     */
    @Test
    public void testGetInventory1()
    {
        assertEquals("Inventory should be empty", new HashMap<>(), testStorage.getInventory());
    }

    /**
     * Test 2 for getInventory: checking Storage with added Item
     */
    @Test
    public void testGetInventory2()
    {
        Map<String, Integer> res = new HashMap<>();
        testStorage.addItem(item2, POS_VALUE);
        res.put(item2.getType(), POS_VALUE);
        assertEquals("Inventory wasn't updated right", res, testStorage.getInventory());
    }

    /**
     * Test 1 for getAvailableCapacity: checking empty Storage
     */
    @Test
    public void testGetAvailableCapacity()
    {
        assertEquals("Available capacity is wrong", testStorage.getCapacity(),
                testStorage.getAvailableCapacity());
    }
}