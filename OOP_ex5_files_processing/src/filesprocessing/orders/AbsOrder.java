package filesprocessing.orders;

/*-----=  imports  =-----*/

import java.io.File;
import java.util.Comparator;

/**
 * Class representing default order by absolute path of the files in lexicografic order
 */
class AbsOrder implements Order
{

    /*-----=  methods  =-----*/

    /**
     * Getter for comparator based on abs order
     * @return - comparator of files based on abs order
     */
    public Comparator<File> getComparator()
    {
        return new AbsOrderComparator();
    }


    /*-----=  static inner class  =-----*/

    /**
     * Static class representing abs comparator
     */
    private static class AbsOrderComparator implements Comparator<File>
    {
        /**
         * Overrided function compare
         * @param f1 - first file to be compared
         * @param f2 - second file to be compared
         * @return - positive int iff absolute path of f1 bigger than of f2, 0 iff equals, negative integer
         * otherwise
         */
        @Override
        public int compare(File f1, File f2)
        {
            return f1.getAbsolutePath().compareTo(f2.getAbsolutePath());
        }
    }
}
