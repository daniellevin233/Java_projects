package filesprocessing.filters;

/*-----=  imports  =-----*/

import java.io.File;

/**
 * Filter accepting files that their size is greater than given value
 */
public class GreaterFilter extends SizeFilter {

    /*-----=  constructor  =-----*/

    /**
     * Constructor for greater filter
     * @param filterValuesStr - string representing values for greater filter
     * @throws FilterException - thrown in case of invalidity of values string format
     */
    GreaterFilter(String filterValuesStr) throws FilterException
    {
        super(filterValuesStr);
    }

    /*-----=  methods  =-----*/

    /**
     * Method defines if the given file must be accepted by greater filter
     * @param file - file to be filtered
     * @return - true iff file passes greater filter
     */
    @Override
    public boolean accept(File file)
    {
        return file.length() > values.get(0);
    }
}
