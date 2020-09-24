package filesprocessing.filters;

/*-----=  imports  =-----*/

import java.io.File;

/**
 * Filter class accepting files that are writable
 */
public class WritableFilter extends BinaryFilter {

    /*-----=  constructor  =-----*/

    /**
     * Constructor for writable filter
     * @param filterValuesStr - string of binary value of writable filter
     * @throws FilterException - thrown in case of invalidity of values string format
     */
    WritableFilter(String filterValuesStr) throws FilterException
    {
        super(filterValuesStr);
    }

    /*-----=  methods  =-----*/

    /**
     * Method defines if the given file must be accepted by writable filter
     * @param file - file to be filtered
     * @return - true iff file passes writable filter
     */
    @Override
    public boolean accept(File file)
    {
        return file.canWrite() == value;
    }
}
