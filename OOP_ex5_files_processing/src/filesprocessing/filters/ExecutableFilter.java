package filesprocessing.filters;

/*-----=  imports  =-----*/

import java.io.File;

/**
 * Executable filter class accepting files that are executable
 */
public class ExecutableFilter extends BinaryFilter {

    /*-----=  constructor  =-----*/

    /**
     * Constructor for executable filter
     * @param filterValuesStr - string of binary value of this filter
     * @throws FilterException - thrown in case of invalidity of values string format
     */
    ExecutableFilter(String filterValuesStr) throws FilterException
    {
        super(filterValuesStr);
    }

    /*-----=  methods  =-----*/

    /**
     * Method define if the given file must be accepted by executable filter
     * @param file - file to be filtered
     * @return - true iff file passes executable filter
     */
    @Override
    public boolean accept(File file)
    {
        return file.canExecute() == value;
    }
}