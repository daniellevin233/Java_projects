package filesprocessing.filters;

/*-----=  imports  =-----*/

import java.io.File;

/**
 * Abstract filter class accepting files that passes some binary condition
 */
public abstract class BinaryFilter implements Filter
{

    /*-----=  static fields  =-----*/

    /** String representing negative binary value **/
    private static final String NEGATIVE_STRING = "NO";

    /** String representing positive binary value **/
    private static final String POSITIVE_STRING = "YES";

    /*-----=  fields  =-----*/

    /** Binary value of this filter **/
    protected boolean value;

    /*-----=  constructor  =-----*/

    /**
     * Constructor for binary filters
     * @param filterValuesStr - string of binary value of this filter
     * @throws FilterException - thrown in case of invalidity of values string format
     */
    BinaryFilter(String filterValuesStr) throws FilterException
    {
        if(!(filterValuesStr.equals(NEGATIVE_STRING) || filterValuesStr.equals(POSITIVE_STRING)))
            throw new FilterException();
        value = filterValuesStr.equals(POSITIVE_STRING);
    }

    /*-----=  method  =-----*/

    /**
     * Method define if the given file must be accepted by binary.filter
     * @param file - file to be filtered
     * @return - true iff file passes binary.filter
     */
    @Override
    public abstract boolean accept(File file);
}
