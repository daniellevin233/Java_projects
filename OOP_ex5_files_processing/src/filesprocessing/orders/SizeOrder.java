package filesprocessing.orders;

/*-----=  imports  =-----*/

import java.io.File;
import java.util.Comparator;

/**
 * Class representing order by size of the files
 */
class SizeOrder implements Order
{

    /*-----=  methods  =-----*/

    /**
     * Getter for comparator based on size order
     * @return - comparator of files based on size order
     */
    public Comparator<File> getComparator()
    {
        return new SizeOrderComparator();
    }

    /*-----=  static inner class  =-----*/

    /**
     * Static class representing size comparator
     */
    private static class SizeOrderComparator implements Comparator<File>
    {
        /**
         * Overrided function compare
         * @param f1 - first file to be compared
         * @param f2 - second file to be compared
         * @return - positive int iff size of f1 bigger than of f2, 0 iff equals, negative integer
         * otherwise
         */
        @Override
        public int compare(File f1, File f2)
        {
            int result = (int)(f1.length() - f2.length());
            return result == 0 ? Order.defaultOrder().getComparator().compare(f1, f2) : result;
        }
    }
}
