package filesprocessing.filters;

/*-----=  imports  =-----*/

import java.io.File;

/**
 * Filter accepting files that their size is smaller than given value
 */
public class SmallerFilter extends SizeFilter {

    /*-----=  constructor  =-----*/

    /**
     * Constructor for smaller filter
     * @param filterValuesStr - string representing values for smaller filter
     * @throws FilterException - thrown in case of invalidity of values string format
     */
    SmallerFilter(String filterValuesStr) throws FilterException
    {
        super(filterValuesStr);
    }

    /*-----=  methods  =-----*/

    /**
     * Method defines if the given file must be accepted by smaller filter
     * @param file - file to be filtered
     * @return - true iff file passes smaller filter
     */
    @Override
    public boolean accept(File file)
    {
        return file.length() < values.get(0);
    }
}
