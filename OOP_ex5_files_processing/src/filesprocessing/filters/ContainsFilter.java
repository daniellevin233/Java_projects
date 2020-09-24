package filesprocessing.filters;

/*-----=  imports  =-----*/

import java.io.File;

/**
 * Filter class accepting files that contains value string in its name
 */
public class ContainsFilter extends FileNameFilter {

    /*-----=  constructor  =-----*/

    /**
     * Constructor for contains filter, redirecting to father constructor
     * @param filterValuesStr - string representing values for contains filter
     */
    ContainsFilter(String filterValuesStr)
    {
        super(filterValuesStr);
    }

    /*-----=  methods  =-----*/

    /**
     * Method define if the given file must be accepted by contains filter
     * @param file - file to be filtered
     * @return - true iff file passes contains filter
     */
    @Override
    public boolean accept(File file)
    {
        return file.getName().contains(value);
    }
}
