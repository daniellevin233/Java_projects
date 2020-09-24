package filesprocessing.filters;

/*-----=  imports  =-----*/

import java.io.File;

/**
 * Filter class accepting files with names prefixed by value string
 */
public class PrefixFilter extends FileNameFilter
{

    /*-----=  constructor  =-----*/

    /**
     * Constructor for prefix filter, redirecting to father constructor
     * @param filterValuesStr - string representing values for prefix filter
     */
    PrefixFilter(String filterValuesStr)
    {
        super(filterValuesStr);
    }

    /*-----=  methods  =-----*/

    /**
     * Method defines if the given file must be accepted by prefix filter
     * @param file - file to be filtered
     * @return - true iff file passes prefix filter
     */
    @Override
    public boolean accept(File file)
    {
        return file.getName().substring(0, Math.min(value.length(), file.getName().length())).equals(value);
    }
}
