package filesprocessing.filters;

/*-----=  imports  =-----*/

import java.io.File;

/**
 * Abstract filter class accepting files that passes some condition about their name
 */
public abstract class FileNameFilter implements Filter
{
    /*-----=  fields  =-----*/

    /** String representing textual value relevant for this filter **/
    String value;

    /*-----=  constructor  =-----*/

    /**
     * Constructor for file name filter fixing the given string value
     * @param filterValuesStr - string representing values for file name filter
     */
    FileNameFilter(String filterValuesStr)
    {
        value = filterValuesStr;
    }

    /*-----=  methods  =-----*/

    /**
     * Method define if the given file must be accepted by file name filter
     * @param file - file to be filtered
     * @return - true iff file passes file name filter
     */
    @Override
    public abstract boolean accept(File file);
}
