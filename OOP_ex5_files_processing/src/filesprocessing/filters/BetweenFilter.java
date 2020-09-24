package filesprocessing.filters;

/*-----=  imports  =-----*/

import java.io.File;

/**
 * Filter accepting files that their size is between two given values
 */
public class BetweenFilter extends SizeFilter
{
    /*-----=  constructor  =-----*/

    /**
     * Constructor for between filter
     * @param filterValuesStr - string representing values for between filter
     * @throws FilterException - thrown in case of invalidity of values string format
     */
    BetweenFilter(String filterValuesStr) throws FilterException
    {
        super();
        this.valuesStr = filterValuesStr;
        try{
            this.values.add(getNextValue());
            this.values.add(getNextValue());
        } catch (NumberFormatException e) {
            throw new FilterException();
        }
        if(values.get(0) > values.get(1))
            throw new FilterException();
    }

    /*-----=  methods  =-----*/

    /**
     * Method define if the given file must be accepted by between filter
     * @param file - file to be filtered
     * @return - true iff file passes between filter
     */
    @Override
    public boolean accept(File file)
    {
        return (values.get(0) <= file.length()) && (file.length() <= values.get(1));
    }
}
