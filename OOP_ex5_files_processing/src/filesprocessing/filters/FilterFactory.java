package filesprocessing.filters;

/**
 * Factory of filters
 */
public class FilterFactory {

    /*-----=  static fields  =-----*/

    /** Char for separator symbol **/
    static final char SEPARATOR = '#';

    /** Number of chars for NOT string **/
    private static final int INDEX_OF_NEGATION_POSITION = 4;

    /** String representing the valid format of the "not" string**/
    private static final String NEGATION_STR = "#NOT";

    /*-----=  static methods  =-----*/

    /**
     * Static method load, for loading filter from given filter name
     * @param filterName - string got from the file to be loaded
     * @return - ready object of type Filter
     * @throws FilterException - thrown in case of invalid filter name
     */
    public static Filter load(String filterName) throws FilterException
    {
        Filter filter;
        String filterType, filterValuesStr;
        boolean negate = checkNegate(filterName);
        filterName = negate ? filterName.substring(0, filterName.length() - INDEX_OF_NEGATION_POSITION) :
                filterName; // cutting #NOT suffix if exists
        int endFilterTypeIndex = filterName.indexOf(SEPARATOR);
        if(endFilterTypeIndex == -1)
        {
            filterType = filterName;
            filterValuesStr = "";
        }
        else
        {
            filterType = filterName.substring(0, endFilterTypeIndex);
            try
            {
                filterValuesStr = filterName.substring(endFilterTypeIndex + 1);
            } catch (IndexOutOfBoundsException e) { // filter string contains no values
                throw new FilterException();
            }
        }

        switch (filterType) {
            case "greater_than":
                filter = negate ? new NegateFilter(new GreaterFilter(filterValuesStr)) :
                                    new GreaterFilter(filterValuesStr);
                break;
            case "between":
                filter = negate ? new NegateFilter(new BetweenFilter(filterValuesStr)) :
                        new BetweenFilter(filterValuesStr);
                break;
            case "smaller_than":
                filter = negate ? new NegateFilter(new SmallerFilter(filterValuesStr)) :
                        new SmallerFilter(filterValuesStr);
                break;
            case "file":
                filter = negate ? new NegateFilter(new FileFilter(filterValuesStr)) :
                        new FileFilter(filterValuesStr);
                break;
            case "contains":
                filter = negate ? new NegateFilter(new ContainsFilter(filterValuesStr)) :
                        new ContainsFilter(filterValuesStr);
                break;
            case "prefix":
                filter = negate ? new NegateFilter(new PrefixFilter(filterValuesStr)) :
                        new PrefixFilter(filterValuesStr);
                break;
            case "suffix":
                filter = negate ? new NegateFilter(new SuffixFilter(filterValuesStr)) :
                        new SuffixFilter(filterValuesStr);
                break;
            case "writable":
                filter = negate ? new NegateFilter(new WritableFilter(filterValuesStr)) :
                        new WritableFilter(filterValuesStr);
                break;
            case "executable":
                filter = negate ? new NegateFilter(new ExecutableFilter(filterValuesStr)) :
                        new ExecutableFilter(filterValuesStr);
                break;
            case "hidden":
                filter = negate ? new NegateFilter(new HiddenFilter(filterValuesStr)) :
                        new HiddenFilter(filterValuesStr);
                break;
            case "all":
                filter = negate ? new NegateFilter(new AllFilter()) : new AllFilter();
                break;
            default:
                throw new FilterException();
            }
        return filter;
    }

    /**
     * Method checks if given filter name contains negation suffix
     * @param filterStr - string of filter name to be checked
     * @return - true iff filterStr ends with "#NOT"
     */
    static private boolean checkNegate(String filterStr)
    {
        return filterStr.length() >= INDEX_OF_NEGATION_POSITION &&
                filterStr.substring(filterStr.length() - INDEX_OF_NEGATION_POSITION).equals(NEGATION_STR);
    }
}