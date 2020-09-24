/*-----=  imports  =-----*/

import java.util.Arrays;

/**
 * Class implementing set by means of closed hash table
 */
public class ClosedHashSet extends SimpleHashSet
{
    private static final int INITIAL_CLAMPING_INDEX = 0;
    private static final int FAILURE_VALUE = -1;

    /*-----=  fields  =-----*/

    /** Array containing the set's members **/
    private String[] arr = new String[INITIAL_CAPACITY];

    /** Array representing states of each cell of the table, whether it have already contained some element
     or not **/
    private boolean[] stateArr = new boolean[INITIAL_CAPACITY];

    /*-----=  constructors  =-----*/

    /**
     * Default constructor for closed hashing set
     */
    public ClosedHashSet()
    {
        super();
    }

    /**
     * Constructor for closed hashing set getting upper and lower load factors
     * @param upperLoadFactor - upper load factor for the hash table to be set
     * @param lowerLoadFactor - lower load factor for the hash table to be set
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor)
    {
        super(upperLoadFactor, lowerLoadFactor);
    }

    /**
     * Constructor for closed hashing set getting array of strings to initialize the set with
     * @param data - list of strings to be added on initialization stage
     */
    public ClosedHashSet(String[] data)
    {
        super();
        this.addStrings(data);
    }

    /*-----=  methods  =-----*/

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue)
    {
        if(this.needToIncrease(this.getLoadFactor() + (float)MINIMAL_STRUCTURE_SIZE / this.capacity()))
        {
            this.resize(INCREASE_RESIZING_FACTOR);
        }
        if(this.contains(newValue))
            return false;
        int insertIndex = findFreeIndex(newValue);
        arr[insertIndex] = newValue;
        return true;
    }

    /**
     * Methods search for a free index for insert new value
     * @param value - value to be inserted
     * @return - index of the hash table for given value
     */
    private int findFreeIndex(String value)
    {
        int res, i = INITIAL_CLAMPING_INDEX;
        do
        {
            res = quadraticProbing(value, i);
            i++;
        }while(arr[res] != null);
        return res;
    }

    /**
     * Method implements quadratic probing formula
     * @param value - string value to be clamped
     * @param i - current index of clamping
     * @return - clamp code for value and index i
     */
    private int quadraticProbing(String value, int i)
    {
        return clamp(value.hashCode() + (i + i * i) / 2);
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal)
    {
        return Arrays.asList(arr).contains(searchVal);
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete)
    {
        int index = findValueIndex(toDelete);
        if(index == FAILURE_VALUE)
            return false;
        arr[index] = null;
        stateArr[index] = true; // stating previous existence
        if(this.needToDecrease(this.getLoadFactor()))
        {
            this.resize(DECREASE_RESIZING_FACTOR);
        }
        return true;
    }

    /**
     * Method finds the index of the hash table for given value, uses quadratic probing formula
     * @param value - string to find correct index for
     * @return integer representing the index of the hash table for the given value, -1 if value isn't found
     */
    private int findValueIndex(String value)
    {
        return findValueIndexHelper(value, INITIAL_CLAMPING_INDEX);
    }

    /**
     * Helper method for finding the index of the hash table for given value, uses quadratic probing formula
     * @param value - string to find correct index for
     * @return integer representing the index of the hash table for the given value, -1 if value isn't found
     */
    private int findValueIndexHelper(String value, int i)
    {
        int res = quadraticProbing(value, i);
        if((i >= this.capacity()) || (arr[res] == null && !stateArr[res])) // the problematic case
            return FAILURE_VALUE;
        if(arr[res] != null && arr[res].equals(value)) // got it
            return res;
        return findValueIndexHelper(value, i + 1);
    }

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size()
    {
        int cnt = 0;
        for (String value : arr)
        {
            if (value != null)
                cnt++;
        }
        return cnt;
    }

    /**
     * @return The current capacity of the table.
     */
    public int capacity()
    {
        return arr.length;
    }

    /**
     * Resize the HashSet by multiplying its capacity by resizing factor, and rehashing all the elements
     * @param resizingFactor - factor of increasing capacity of the set
     */
    @Override
    protected void resize(float resizingFactor)
    {
        if(this.capacity() * resizingFactor >= 1)
        {
            String[] toRehashArr = this.arr;
            stateArr = new boolean[(int)(resizingFactor * this.capacity())];
            this.arr = new String[(int)(resizingFactor * this.capacity())];
            this.rehash(toRehashArr);
        }
    }

    /**
     * Method rehashes values from toRehashArr to 'this' object, initialised as empty set with proper size
     * @param toRehashArr - array from which all the objects will be rehashed
     */
    private void rehash(String[] toRehashArr)
    {
        for (String curVal : toRehashArr)
        {
            if (curVal != null)
            {
                this.add(curVal);
            }
        }
    }
}
