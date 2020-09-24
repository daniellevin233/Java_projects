/*-----=  imports  =-----*/

import org.junit.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Class for testing LockerClass
 */
public class LockerTest extends StorageTest{

    /*-----=  static fields  =-----*/

    /** 50 Percent constant */
    private static final double PERCENT_50 = 0.5;

    /** Capacity for tested locker */
    private static final int TEST_LOCKER_CAPACITY = 10;

    /** Type of failure integer constant */
    private static final int NEG_CONSTRAINT_VALUE = -2;

    /** Object Locker to be tested */
    private static Locker testLocker;

    /*-----=  methods  =-----*/

    /**
     * Constructor initializing the object being tested
     */
    public LockerTest()
    {
        super(new Locker(TEST_LOCKER_CAPACITY));
    }

    // ----- tests ----- //

    /**
     * Initial method to create tested object
     */
    @BeforeClass
    public static void createTestObject()
    {
        testLocker = new Locker(TEST_LOCKER_CAPACITY);
    }

    /**
     * Method being called after each test, clears the tested object
     */
    @After
    public void resetTestObject() // this method added just for better logic of testing
    {
        testLocker = new Locker(TEST_LOCKER_CAPACITY);
    }

    /**
     * Test 1 for addItem to Locker: check adding of more then 50% volume
     */
    @Test
    public void testLockerAddItem1()
    {
        int res = testLocker.addItem(item2, testLocker.getCapacity() / item2.getVolume());
        assertEquals("Add should pass items to long term storage", POS_VALUE, res);
    }

    /**
     * Test 2 for addItem to Locker: checking 20-50% volume keeping
     */
    @Test
    public void testLockerAddItem2()
    {
        testLocker.addItem(item2, testLocker.getCapacity() / item2.getVolume());
        assertEquals("20 Percent border wasn't complied", POS_VALUE,
                testLocker.getItemCount(item2.getType()));
    }

    /**
     * Test 3 for addItem to Locker: checking adding exactly 100% volume, and then passing all the items
     * away to the long term storage
     */
    @Test
    public void testLockerAddItem3()
    {
        testLocker.addItem(item5, testLocker.getCapacity() / item5.getVolume());
        assertEquals("20 Percent border wasn't complied", BASE_VALUE,
                testLocker.getItemCount(item5.getType()));
    }

    /**
     * Test 4 for addItem to Locker: checking 50% volume border keeping
     */
    @Test
    public void testLockerAddItem4()
    {
        testLocker.addItem(item5, (int)(testLocker.getCapacity() * PERCENT_50) / item5.getVolume());
        assertEquals("50 Percent border was complied by fall", POS_VALUE,
                testLocker.getItemCount(item5.getType()));
    }

    /**
     * Test 5 for addItem to Locker: checking range 20-50% keeping as is
     */
    @Test
    public void testLockerAddItem5()
    {
        testLocker.addItem(item4, POS_VALUE);
        assertEquals("50 Percent border was complied by fall", POS_VALUE,
                testLocker.getItemCount(item4.getType()));
    }

    /**
     * Test 1 for removeItem: checking invalid negative input
     */
    @Test
    public void testRemoveItem1()
    {
        int res = testLocker.removeItem(item2, NEG_VALUE); // n = -1
        assertEquals("Remove should fail for negative input", NEG_VALUE, res);
    }

    /**
     * Test 2 for removeItem: checking invalid zero input
     */
    @Test
    public void testRemoveItem2()
    {
        int res = testLocker.removeItem(item2, BASE_VALUE); // n = 0
        assertEquals("Remove should success for 0 items", BASE_VALUE, res);
    }

    /**
     * Test 3 for removeItem: checking regular remove operation
     */
    @Test
    public void testRemoveItem3()
    {
        testLocker.addItem(item2, POS_VALUE);
        int res = testLocker.removeItem(item2, POS_VALUE);
        assertEquals("RemoveItem should success", BASE_VALUE, res);
    }

    /**
     * Test 4 for removeItem: checking remove of too much items
     */
    @Test
    public void testRemoveItem4()
    {
        testLocker.addItem(item2, (int)(testLocker.getCapacity() * PERCENT_50));
        int res = testLocker.removeItem(item2, testLocker.getCapacity());
        assertEquals("RemoveItem should fall", NEG_VALUE, res);
    }

    /**
     * Test 5 for removeItem: checking removing not existing item
     */
    @Test
    public void testRemoveItem5()
    {
        testLocker.addItem(item2, POS_VALUE);
        int res = testLocker.removeItem(item3, POS_VALUE);
        assertEquals("RemoveItem should fall", NEG_VALUE, res);
    }

    /**
     * Test 1 for getItemCount for Locker: checking count after adding and removing Item
     */
    @Test
    public void testLockerGetItemCount1()
    {
        testLocker.addItem(item2, (int)(testLocker.getCapacity() * PERCENT_50) / item2.getVolume());
        testLocker.removeItem(item2, POS_VALUE);
        assertEquals("The counter is wrong", POS_VALUE, testLocker.getItemCount(item2.getType()));
    }

    /**
     * Test 1 for getInventory for Locker: checking inventory after adding and removing Item
     */
    @Test
    public void testLockerGetInventory1()
    {
        Map<String, Integer> res = new HashMap<>();
        testLocker.addItem(item2, POS_VALUE);
        testLocker.removeItem(item2, POS_VALUE);
        res.put(item2.getType(), BASE_VALUE);
        assertEquals("Inventory wasn't updated right", res, testLocker.getInventory());
    }

    /**
     * Test 2 for getInventory for Locker: checking inventory values field updates
     */
    @Test
    public void testLockerGetInventory2()
    {
        Map<String, Integer> res = new HashMap<>();
        testLocker.addItem(item2, (int)(testLocker.getCapacity() * PERCENT_50) / item2.getVolume());
        testLocker.removeItem(item2, POS_VALUE);
        res.put(item2.getType(), POS_VALUE);
        assertEquals("Inventory wasn't updated right", res, testLocker.getInventory());
    }

    /**
     * Test 1 for getCapacity for Locker: checking Locker default capacity
     */
    @Test
    public void testLockerGetCapacity1()
    {
        assertEquals("Capacity is wrong", TEST_LOCKER_CAPACITY, testLocker.getCapacity());
    }

    /**
     * Test 2 for getCapacity for Locker: checking Locker capacity after adding and removing Item
     */
    @Test
    public void testLockerGetCapacity2()
    {
        testLocker.addItem(item2, POS_VALUE);
        testLocker.removeItem(item2, POS_VALUE);
        assertEquals("Capacity is wrong", TEST_LOCKER_CAPACITY, testLocker.getCapacity());
    }

    /**
     * Test 1 for getAvailableCapacity for Locker: checking availableCapacity after regular adding
     */
    @Test
    public void testLockerGetAvailableCapacity1()
    {
        testLocker.addItem(item2, POS_VALUE);
        assertEquals("Available capacity is wrong", testLocker.getCapacity() - item2.getVolume(),
                testLocker.getAvailableCapacity());
    }

    /**
     * Test 2 for getAvailableCapacity for Locker: checking availableCapacity after adding and removing Item
     */
    @Test
    public void testLockerGetAvailableCapacity2()
    {
        testLocker.addItem(item2, POS_VALUE);
        testLocker.removeItem(item2, POS_VALUE);
        assertEquals("Available capacity is wrong", testLocker.getCapacity(),
                testLocker.getAvailableCapacity());
    }

    /**
     * Test 3 for getAvailableCapacity for Locker: checking availableCapacity after adding some Items, and
     * than removing one unit only
     */
    @Test
    public void testLockerGetAvailableCapacity3()
    {
        testLocker.addItem(item2, (int)(testLocker.getCapacity() * PERCENT_50) / item2.getVolume());
        testLocker.removeItem(item2, POS_VALUE);
        assertEquals("Available capacity is wrong",
                testLocker.getCapacity() - POS_VALUE * item2.getVolume(), testLocker.getAvailableCapacity());
    }

    /**
     * Test 1 for constraintItems for Locker: checking keeping constraint condition when adding footballer
     * to baseball bat
     */
    @Test
    public void testFootballBaseballConstraint1()
    {
        testLocker.addItem(item2, POS_VALUE);
        int res = testLocker.addItem(item4, POS_VALUE);
        assertEquals("Football item can't be stored with baseball bat simultaneously", NEG_CONSTRAINT_VALUE,
                res);
    }

    /**
     * Test 2 for constraintItems for Locker: checking keeping constraint condition when adding baseball
     * bat to footballer
     */
    @Test
    public void testFootballBaseballConstraint2()
    {
        testLocker.addItem(item4, BASE_VALUE);
        int res = testLocker.addItem(item2, POS_VALUE);
        assertEquals("Baseball bat item can't be stored with football simultaneously",
                NEG_CONSTRAINT_VALUE, res);
    }
}