package filesprocessing.orders;

/*-----=  static imports  =-----*/

import static java.util.Collections.swap;

/*-----=  imports  =-----*/

import java.util.Comparator;
import java.util.List;

/**
 * Class representing quick sort method for generic type
 * @param <T> - type of objects to be sorted
 */
class QuickSort <T> {

    /*-----=  fields  =-----*/

    /** comparator of given type of objects **/
    private Comparator<T> comp;

    /*-----=  constructor  =-----*/

    /**
     * Constructor for QuickSort
     * @param comp - comparator of given type of objects
     */
    QuickSort(Comparator<T> comp)
    {
        this.comp = comp;
    }

    /*-----=  methods  =-----*/

    /**
     * Helper method of quick sorting, searching for partition of the given sublist
     * @param arr - list to search a partition for
     * @param low - lowest index of the sublist
     * @param high - highest index of the sublist
     * @return - index of partition place in the list
     */
    private int partition(List<T> arr, int low, int high)
    {
        T pivot = arr.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++)
        {
            if (comp.compare(arr.get(j), pivot) <= 0)
            {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    /**
     * Recursive sort function, implementing the quick sort
     * @param arr - array to be sorted
     * @param low - lowest index of subarray to be sorted
     * @param high - highest index of subarray to be sorted
     */
    void sort(List<T> arr, int low, int high)
    {
        if(low < high)
        {
            int part = partition(arr, low, high);
            sort(arr, low, part - 1);
            sort(arr, part + 1, high);
        }
    }
}
