package filesprocessing.filters;

/*-----=  imports  =-----*/

import java.io.File;

/**
 * Filter class accepting files with names equal to value string
 */
public class FileFilter extends FileNameFilter
{
    /*-----=  constructor  =-----*/

    /**
     * Constructor for file filter, redirecting to father constructor
     * @param filterValuesStr - string representing values for file filter
     */
    FileFilter(String filterValuesStr)
    {
        super(filterValuesStr);
    }

    /*-----=  methods  =-----*/

    /**
     * Method define if the given file must be accepted by file filter
     * @param file - file to be filtered
     * @return - true iff file passes file filter
     */
    @Override
    public boolean accept(File file)
    {
        return file.getName().equals(value);
    }
}
