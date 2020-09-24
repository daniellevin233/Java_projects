package filesprocessing.filters;

/*-----=  imports  =-----*/

import java.io.File;

/**
 * Filter class accepting files with names suffixed by value string
 */
public class SuffixFilter extends FileNameFilter
{

    /*-----=  constructor  =-----*/

    /**
     * Constructor for suffix filter, redirecting to father constructor
     * @param filterValuesStr - string representing values for suffix filter
     */
    SuffixFilter(String filterValuesStr)
    {
        super(filterValuesStr);
    }

    /*-----=  methods  =-----*/

    /**
     * Method defines if the given file must be accepted by suffix filter
     * @param file - file to be filtered
     * @return - true iff file passes suffix filter
     */
    @Override
    public boolean accept(File file)
    {
        int fileNameLen = file.getName().length();
        return file.getName().substring(Math.max(fileNameLen - value.length(), 0)).equals(value);
    }
}
