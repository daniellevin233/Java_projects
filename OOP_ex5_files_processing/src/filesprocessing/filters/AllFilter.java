package filesprocessing.filters;

/*-----=  imports  =-----*/

import java.io.File;

/**
 * Filter accepting all files
 */
class AllFilter implements Filter
{

    /*-----=  constructor  =-----*/

    /**
     * Default constructor for all filter
     */
    AllFilter() {}

    /*-----=  methods  =-----*/

    /**
     * Method define if the given file must be accepted by all filter
     * @param file - file to be filtered
     * @return - true iff file passes all filter
     */
    @Override
    public boolean accept(File file)
    {
        return true;
    }
}
