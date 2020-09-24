/**
 * Abstract class representing set data structure, implements interface SimpleSet
 */
public abstract class SimpleHashSet implements SimpleSet
{
    /*-----=  static fields  =-----*/

    /** Minimal possible size of set **/
    protected static final int MINIMAL_STRUCTURE_SIZE = 1;

    /** Default initial capacity of set **/
    protected static final int INITIAL_CAPACITY = 16;

    /** Default initial higher load factor of set **/
    protected static final float DEFAULT_HIGHER_LOAD_FACTOR = 0.75f;

    /** Default initial lower load factor of set **/
    protected static final float DEFAULT_LOWER_LOAD_FACTOR = 0.25f;

    /** Default resizing increase factor of set **/
    protected static final float INCREASE_RESIZING_FACTOR = 2f;

    /** Default resizing decrease factor of set **/
    protected static final float DECREASE_RESIZING_FACTOR = 0.5f;

    /*-----=  fields  =-----*/

    /** upper load factor for the hash table to be set **/
    protected float upperLoadFactor;

    /** lower load factor for the hash table to be set **/
    protected float lowerLoadFactor;

    /*-----=  constructors  =-----*/

    /**
     * Default constructor for simple hashing set
     */
    protected SimpleHashSet()
    {
        this(DEFAULT_HIGHER_LOAD_FACTOR, DEFAULT_LOWER_LOAD_FACTOR);
    }

    /**
     * Constructor for simple hashing set getting upper and lower load factors
     * @param upperLoadFactor - upper load factor for the hash table to be set
     * @param lowerLoadFactor - lower load factor for the hash table to be set
     */
    protected SimpleHashSet(float upperLoadFactor, float lowerLoadFactor)
    {
        this.upperLoadFactor = upperLoadFactor;
        this.lowerLoadFactor = lowerLoadFactor;
    }

    /*-----=  methods  =-----*/

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public abstract boolean add(String newValue);

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public abstract boolean contains(String searchVal);

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public abstract boolean delete(String toDelete);

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public abstract int size();

    /**
     * Clamps hashing indices to fit within the current table capacity
     * @param index - the index before clamping
     * @return - an index properly clamped
     */
    protected int clamp(int index)
    {
        return index & (this.capacity() - 1);
    }

    /**
     * Method adds the string from stringsArray to the set
     * @param stringsArray - strings to be added
     */
    protected void addStrings(String[] stringsArray)
    {
        for(String str: stringsArray)
        {
            this.add(str);
        }
    }

    /**
     * @return The lower load factor of the table.
     */
    protected float getLowerLoadFactor()
    {
        return this.lowerLoadFactor;
    }

    /**
     * @return The higher load factor of the table.
     */
    protected float getUpperLoadFactor()
    {
        return this.upperLoadFactor;
    }

    /**
     * @return The load factor of the table.
     */
    protected float getLoadFactor()
    {
        return (float)this.size() / this.capacity();
    }

    /**
     * @return The current capacity of the table.
     */
    protected abstract int capacity();

    /**
     * Checks whether the table should be increased or not in addition to wanted load factor
     * @param curLoadFactor - load factor of the table for which the check should be performed
     * @return - true iff the table should be increased
     */
    protected boolean needToIncrease(float curLoadFactor)
    {
        return curLoadFactor > this.getUpperLoadFactor();
    }

    /**
     * Checks whether the table should be resized or not in addition to wanted load factor
     * @param curLoadFactor - load factor of the table for which the check should be performed
     * @return - true iff the table should be resized
     */
    protected boolean needToDecrease(float curLoadFactor)
    {
        return curLoadFactor < this.getLowerLoadFactor();
    }

    /**
     * Resize the HashSet by multiplying its capacity by resizing factor, and rehashing all the elements
     * @param resizingFactor - factor of increasing capacity of the set
     */
    protected abstract void resize(float resizingFactor);
}
