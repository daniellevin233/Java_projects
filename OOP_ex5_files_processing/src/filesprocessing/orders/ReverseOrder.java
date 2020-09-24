package filesprocessing.orders;

/*-----=  imports  =-----*/

import java.io.File;
import java.util.Comparator;

/**
 * Decorator of order for reversing functionality
 */
public class ReverseOrder implements Order{

    /*-----=  fields  =-----*/

    /** "reference" to an Order object, for decorating it **/
    private Order order;

    /*-----=  constructor  =-----*/

    /**
     * Constructor for reverse order decorator
     * @param order - "reference" to an Order object, for decorating it
     */
    ReverseOrder(Order order)
    {
        this.order = order;
    }

    /*-----=  methods  =-----*/

    /**
     * Getter for comparator based on reversed order of the Order object
     * @return - comparator of files based on reversed order of the Order object
     */
    @Override
    public Comparator<File> getComparator() {
        return order.getComparator().reversed();
    }
}
