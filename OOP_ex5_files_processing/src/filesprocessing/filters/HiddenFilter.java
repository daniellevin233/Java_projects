package filesprocessing.filters;

/*-----=  imports  =-----*/

import java.io.File;

/**
 * Filter class accepting files that are hidden
 */
public class HiddenFilter extends BinaryFilter {

    /*-----=  constructor  =-----*/

    /**
     * Constructor for hidden filter
     * @param filterValuesStr - string of binary value of hidden filter
     * @throws FilterException - thrown in case of invalidity of values string format
     */
    HiddenFilter(String filterValuesStr) throws FilterException
    {
        super(filterValuesStr);
    }

    /*-----=  methods  =-----*/

    /**
     * Method defines if the given file must be accepted by hidden filter
     * @param file - file to be filtered
     * @return - true iff file passes hidden filter
     */
    @Override
    public boolean accept(File file)
    {
        return file.isHidden() == value;
    }
}