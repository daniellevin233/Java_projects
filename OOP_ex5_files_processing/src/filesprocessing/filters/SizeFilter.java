package filesprocessing.filters;

/*-----=  static imports  =-----*/

import static filesprocessing.filters.FilterFactory.SEPARATOR;

/*-----=  imports  =-----*/

import java.io.File;
import java.util.ArrayList;

/**
 * Abstract class for filters filtering by means of file size
 */
public abstract class SizeFilter implements Filter
{
    /*-----=  static fields  =-----*/

    /** Conversion from byte to Kbyte factor **/
    private static final int BYTE_CONVERSION_FACTOR = 1024;

    /** String representing empty string**/
    private static final String EMPTY_STR = "";

    /*-----=  fields  =-----*/

    /** Array list of double, representing values for filtering **/
    ArrayList<Double> values;

    /** String representation of values of this filter **/
    String valuesStr;

    /*-----=  constructor  =-----*/

    /**
     * Constructor of size filter
     */
    SizeFilter()
    {
        this.values = new ArrayList<>();
    }

    /*-----=  methods  =-----*/

    /**
     * Constructor of size filter with string representing values
     * @param filterValuesStr - string representing values for this filter
     * @throws FilterException - thrown in the case of invalid string values format
     */
    SizeFilter(String filterValuesStr) throws FilterException
    {
        this.values = new ArrayList<>();
        this.valuesStr = filterValuesStr;
        try {
            values.add(this.getNextValue());
        } catch (NumberFormatException e) {
            throw new FilterException();
        }
        if(this.hasNegative() || !this.valuesStr.equals(EMPTY_STR))
            throw new FilterException();
    }

    /**
     * Searching for the next double value in the value string
     * @return - next double value for size filter
     */
    protected double getNextValue()
    {
        int indexOfSeparator = this.valuesStr.indexOf(SEPARATOR);
        indexOfSeparator = indexOfSeparator == -1 ? this.valuesStr.length() : indexOfSeparator;
        double value = Double.parseDouble(this.valuesStr.substring(0, indexOfSeparator));
        try
        {
            this.valuesStr = this.valuesStr.substring(indexOfSeparator + 1);
        }
        catch (IndexOutOfBoundsException e) {
            this.valuesStr = EMPTY_STR;
        }
        return BYTE_CONVERSION_FACTOR * value;
    }

    /**
     * Method defines if the given file must be accepted by size.filter
     * @param file - file to be filtered
     * @return - true iff file passes size.filter
     */
    @Override
    public abstract boolean accept(File file);

    /**
     * Method checks if given values has negative value
     * @return - true iff at least one negative value exists in given values
     */
    private boolean hasNegative()
    {
        for(double val: this.values)
        {
            if(val < 0)
                return true;
        }
        return false;
    }

}
