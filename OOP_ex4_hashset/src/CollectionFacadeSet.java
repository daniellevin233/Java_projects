/*-----=  imports  =-----*/

import java.util.Collection;

/**
 * Wrapper class (facade) for sets implementing SimpleSet interface
 */
public class CollectionFacadeSet implements SimpleSet
{
    /*-----=  fields  =-----*/

    /** Collection object that the facade represents **/
    private Collection<String> collection;

    /*-----=  constructors  =-----*/

    /**
     * Construct facade object
     * @param collection - collection object that the facade represents
     */
    public CollectionFacadeSet(Collection<String> collection)
    {
        this.collection = collection;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue)
    {
        return this.collection.add(newValue);
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal)
    {
        return this.collection.contains(searchVal);
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete)
    {
        return this.collection.remove(toDelete);
    }

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size()
    {
        return this.collection.size();
    }
}
