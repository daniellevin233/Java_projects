package filesprocessing.filters;

/*-----=  imports  =-----*/

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Interface representing filter functionality
 */
public interface Filter
{
    /*-----=  static methods  =-----*/

    /**
     * Method define a default filter
     * @return - filter, considered as default
     */
    static Filter defaultFilter()
    {
        return new AllFilter();
    }

    /*-----=  methods  =-----*/

    /**
     * Method ordering the files from filesArr by the this.order
     * @param filesArr - array of files to be filtered
     * @return - array list of filtered files
     */
    default ArrayList<File> filter(ArrayList<File> filesArr)
    {
        return (ArrayList<File>) filesArr.stream().filter(this::accept).collect(Collectors.toList());
    }

    /**
     * Method define if the given file must be accepted by this.filter
     * @param file - file to be filtered
     * @return - true iff file passes this.filter
     */
    boolean accept(File file);
}
