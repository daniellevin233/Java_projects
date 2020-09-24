package filesprocessing.filters;

/*-----=  imports  =-----*/

import java.io.File;

/**
 * Decorator of order for reversing functionality
 */
class NegateFilter implements Filter
{
    /*-----=  fields  =-----*/

    /** "reference" to a Filter object, for decorating it **/
    private Filter filter;

    /*-----=  constructor  =-----*/

    /**
     * Constructor for negation filter decorator
     * @param filter - "reference" to an Filter object, for decorating it
     */
    NegateFilter(Filter filter)
    {
        this.filter = filter;
    }

    /*-----=  methods  =-----*/

    /**
     * Method overriding filter accept method be simply negating it
     * @param file - file to be filtered
     * @return - true iff file passes this.filter negated
     */
    @Override
    public boolean accept(File file) {
        return !this.filter.accept(file);
    }
}
