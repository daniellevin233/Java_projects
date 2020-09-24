package filesprocessing;

/*-----=  imports  =-----*/

import filesprocessing.filesprocessingexceptions.WarningException;
import filesprocessing.filters.Filter;
import filesprocessing.orders.Order;

import java.io.File;
import java.util.ArrayList;

/**
 * Class representing section for files processing
 */
class Section
{
    /*-----=  fields =-----*/

    /** Filter of this section **/
    private Filter filter;

    /** Order of this section **/
    private Order order;

    /** List of lines containing warnings of this section **/
    private ArrayList<Integer> warningLines;

    /*-----=  constructor  =-----*/

    /**
     * Constructor for section
     * @param filter - Filter of this section
     * @param order - Order of this section
     * @param warningLines - List of lines containing warnings of this section
     */
    Section(Filter filter, Order order, ArrayList<Integer> warningLines)
    {
        this.filter = filter;
        this.order = order;
        this.warningLines = warningLines;
    }

    /*-----=  methods  =-----*/

    /**
     * Method aimed at printing this section, first all the warning lines, then filtered and ordered files
     * @param sourceFiles - list of files to be processed
     **/
    void print(ArrayList<File> sourceFiles)
    {
        this.warningLines.forEach((value) -> {
            try {
                throw new WarningException(value);
            } catch (WarningException ignore) {}
        });
        ArrayList<File> filteredFiles = new ArrayList<>(this.filter.filter(sourceFiles));
        ArrayList<File> orderedFiles = this.order.order(filteredFiles);
        orderedFiles.forEach((file) -> System.out.println(file.getName()));
    }
}
