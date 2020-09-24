package filesprocessing.orders;

/*-----=  imports  =-----*/

import java.io.File;
import java.util.Comparator;

/**
 * Class representing order by type of the files in lexicografic order
 */
class TypeOrder implements Order
{

    /*-----=  static fields  =-----*/

    /** Char representing point **/
    private static final char POINT_CHAR = '.';

    /*-----=  methods  =-----*/

    /**
     * Getter for comparator based on type order
     * @return - comparator of files based on type order
     */
    public Comparator<File> getComparator()
    {
        return new TypeOrderComparator();
    }

    /**
     * Method for extracting file
     * @param f - file to figure out type of
     * @return - String of the format type
     */
    private static String getType(File f)
    {
        String filePath = f.getAbsolutePath();
        int lastPointIndex = filePath.lastIndexOf(POINT_CHAR);
        return filePath.substring(lastPointIndex + 1);
    }

    /*-----=  static inner class  =-----*/

    /**
     * Static class representing type comparator
     */
    private static class TypeOrderComparator implements Comparator<File>
    {
        /**
         * Overrided function compare
         * @param f1 - first file to be compared
         * @param f2 - second file to be compared
         * @return - positive int iff type of f1 bigger than of f2, 0 iff equals, negative integer
         * otherwise
         */
        @Override
        public int compare(File f1, File f2)
        {
            int result = getType(f1).compareTo(getType(f2));
            return result == 0 ? Order.defaultOrder().getComparator().compare(f1, f2) : result;
        }
    }
}
