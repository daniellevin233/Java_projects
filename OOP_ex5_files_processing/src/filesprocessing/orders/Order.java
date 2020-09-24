package filesprocessing.orders;

/*-----=  imports  =-----*/

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Interface representing order functionality
 */
public interface Order
{

    /*-----=  static methods  =-----*/

    /**
     * Method define a default order
     * @return - order, considered as default
     */
    static Order defaultOrder()
    {
        return new AbsOrder();
    }

    /*-----=  methods  =-----*/

    /**
     * Method ordering the files from filesArr by the this.order
     * @param filesArr - array of files to be ordered
     * @return - array list of sorted files
     */
    default ArrayList<File> order(ArrayList<File> filesArr)
    {
        QuickSort<File> quickSort = new QuickSort<>(this.getComparator());
        quickSort.sort(filesArr, 0, filesArr.size() - 1);
        return filesArr;
    }

    /**
     * Getter for comparator based on order
     * @return - comparator of files based on order
     */
    Comparator<File> getComparator();
}
