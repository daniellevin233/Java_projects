/*-----=  imports  =-----*/

import java.util.LinkedList;

/**
 * Class implementing set by means of open hash table
 */
public class OpenHashSet extends SimpleHashSet
{

    /*-----=  fields  =-----*/

    /** Array containing the set's members - my linked lists of strings **/
    private MyLinkedList[] arr = new MyLinkedList[INITIAL_CAPACITY];

    /*-----=  constructors  =-----*/

    /**
     * Default constructor for closed hashing set
     */
    public OpenHashSet()
    {
        super();
        this.initializeEmptyArr();
    }

    /**
     * Constructor for open hashing set getting upper and lower load factors
     * @param upperLoadFactor - upper load factor for the hash table to be set
     * @param lowerLoadFactor - lower load factor for the hash table to be set
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor)
    {
        super(upperLoadFactor, lowerLoadFactor);
        this.initializeEmptyArr();
    }

    /**
     * Constructor for open hashing set getting array of strings to initialize the set with
     * @param data - list of strings to be added on initialization stage
     */
    public OpenHashSet(String[] data)
    {
        super();
        this.initializeEmptyArr();
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
        if(this.contains(newValue))
            return false;
        if(this.needToIncrease(this.getLoadFactor() + (float) MINIMAL_STRUCTURE_SIZE / this.capacity()))
        {
            this.resize(INCREASE_RESIZING_FACTOR);
        }
        int insertIndex = clamp(newValue.hashCode());
        arr[insertIndex].add(newValue);
        return true;
    }

    /**
     * Resize the HashSet by multiplying its capacity by resizing factor, and rehashing all the elements
     * @param resizingFactor - factor of increasing capacity of the set
     */
    @Override
    protected void resize(float resizingFactor)
    {
        if(this.capacity() * resizingFactor >= MINIMAL_STRUCTURE_SIZE)
        {
            MyLinkedList[] toRehashArr = this.arr;
            this.arr = new MyLinkedList[(int)(resizingFactor * this.capacity())];
            initializeEmptyArr();
            this.rehash(toRehashArr);
        }
    }

    /**
     * Method initializes array of empty my linked lists
     */
    private void initializeEmptyArr()
    {
        for(int i = 0; i < arr.length; i++)
        {
            arr[i] = new MyLinkedList(this);
        }
    }

    /**
     * Method rehashes values from toRehashArr to 'this' object, initialised as empty set with proper size
     * @param toRehashArr - array from which all the objects will be rehashed
     */
    private void rehash(MyLinkedList[] toRehashArr)
    {
        for(MyLinkedList curChain : toRehashArr)
        {
            curChain.rehash();
        }
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal)
    {
        int searchIndex = clamp(searchVal.hashCode());
        return arr[searchIndex] != null && arr[searchIndex].contains(searchVal);
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete)
    {
        if(!contains(toDelete))
            return false;
        int deleteIndex = clamp(toDelete.hashCode());
        arr[deleteIndex].delete(toDelete);
        if(needToDecrease(this.getLoadFactor()))
        {
            this.resize(DECREASE_RESIZING_FACTOR);
        }
        return true;
    }

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size()
    {
        int cnt = 0;
        for (MyLinkedList chain : arr)
        {
            cnt += chain.size();
        }
        return cnt;
    }

    /**
     * @return The current capacity (number of cells) of the table.
     */
    public int capacity()
    {
        return arr.length;
    }

    /*-----=  inner classes  =-----*/

    /**
     * Helper inner class for linked list of Strings
     */
    private class MyLinkedList
    {
        /*-----=  fields  =-----*/

        /** Linked list of strings representing the wrapped data structure **/
        private LinkedList<String> myLinkedList;

        /** OpenHashSet representing the instance of wrapping class **/
        private OpenHashSet outerObject;

        /*-----=  constructor  =-----*/

        /**
         * Constructor for MyLinkedList getting the instance of wrapping class
         * @param outerObject - OpenHashSet representing the instance of wrapping class
         */
        private MyLinkedList(OpenHashSet outerObject)
        {
            myLinkedList = new LinkedList<>();
            this.outerObject = outerObject;
        }

        /*-----=  methods  =-----*/

        /**
         * Add a specified element to the my linked list.
         * @param newValue - new string to be added
         */
        private void add(String newValue)
        {
            myLinkedList.add(newValue);
        }

        /**
         * Remove the input element from the my linked list.
         * @param deleteValue - Value to delete
         */
        private void delete(String deleteValue)
        {
            myLinkedList.remove(deleteValue);
        }

        /**
         * Look for a specified value in the my linked list.
         * @param searchValue - Value to search for
         * @return True iff searchValue is found in the my linked list
         */
        private boolean contains(String searchValue)
        {
            return myLinkedList.contains(searchValue);
        }

        /**
         * @return The number of elements currently in the my linked list
         */
        private int size()
        {
            return myLinkedList.size();
        }

        /**
         * Method sends all the values contained in 'this' my linked list to the wrapping open hash set
         * Used for rehashing only
         */
        private void rehash()
        {
            for(String val : myLinkedList)
            {
                this.outerObject.add(val);
            }
        }
    }
}
