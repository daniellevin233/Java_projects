/*-----=  imports  =-----*/

import org.junit.*;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Class for testing LongTermStorage class
 */
public class LongTermTest extends StorageTest{

    /*-----=  static fields  =-----*/

    /** capacity of tested storage */
    private static final int TEST_LONG_TERM_STORAGE_CAPACITY = 1000;

    /** storage object to be tested */
    private static LongTermStorage testLongTermStorage;

    /*-----=  methods  =-----*/

    /**
     * Constructor initializing the object being tested
     */
    public LongTermTest()
    {
        super(new LongTermStorage());
    }

    /**
     * Method creating a test object, called once before all tests run
     */
    @BeforeClass
    public static void createTestObject()
    {
        testLongTermStorage = new LongTermStorage();
    }

    /**
     * Method cleaning the test object after each test performance
     */
    @After
    public void cleanTestObject()
    {
        testLongTermStorage.resetInventory();
    }

    /**
     * Test 1 for resetInventory: checking inventory by default
     */
    @Test
    public void testResetInventory1()
    {
        testLongTermStorage.resetInventory();
        assertEquals("Storage should be empty", new HashMap<>(), testLongTermStorage.getInventory());
    }

    /**
     * Test 2 for resetInventory: checking inventory after adding an Item, and then reset
     */
    @Test
    public void testResetInventory2()
    {
        testLongTermStorage.addItem(item2, testLongTermStorage.getCapacity() / item2.getVolume());
        testLongTermStorage.resetInventory();
        assertEquals("Storage should be empty", new HashMap<>(), testLongTermStorage.getInventory());
    }

    /**
     * Test 1 for getItemCount for LongTermStorage: checking counter for empty storage
     */
    @Test
    public void testLongTermStorageGetItemCount1()
    {
        assertEquals("The counter is wrong", BASE_VALUE, testLongTermStorage.getItemCount(item2.getType()));
    }

    /**
     * Test 2 for getItemCount for LongTermStorage: checking counter for empty storage after adding an Item
     */
    @Test
    public void testLongTermStorageGetItemCount2()
    {
        testLongTermStorage.addItem(item2, testLongTermStorage.getCapacity() / item2.getVolume());
        testLongTermStorage.resetInventory();
        assertEquals("The counter is wrong", BASE_VALUE, testLongTermStorage.getItemCount(item2.getType()));
    }

    /**
     * Test 1 for getCapacity for LongTermStorage: checking default capacity
     */
    @Test
    public void testLongTermStorageGetCapacity1()
    {
        assertEquals("Capacity is wrong", TEST_LONG_TERM_STORAGE_CAPACITY,
                testLongTermStorage.getCapacity());
    }

    /**
     * Test 2 for getCapacity for LongTermStorage: checking capacity after adding and reset
     */
    @Test
    public void testLongTermStorageGetCapacity2()
    {
        testLongTermStorage.addItem(item2, testLongTermStorage.getCapacity() / item2.getVolume());
        testLongTermStorage.resetInventory();
        assertEquals("Capacity is wrong", TEST_LONG_TERM_STORAGE_CAPACITY,
                testLongTermStorage.getCapacity());
    }

    /**
     * Test 1 for getAvailableCapacity for LongTermStorage: checking availableCapacity after adding and reset
     */
    @Test
    public void testLongTermStorageGetAvailableCapacity1()
    {
        testLongTermStorage.addItem(item2, testLongTermStorage.getCapacity() / item2.getVolume());
        testLongTermStorage.resetInventory();
        assertEquals("Available capacity is wrong", testLongTermStorage.getCapacity(),
                testLongTermStorage.getAvailableCapacity());
    }
}