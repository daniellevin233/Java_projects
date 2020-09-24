/*-----=  imports  =-----*/

import java.util.Comparator;

import oop.ex3.searchengine.Hotel;

/**
 * Comparator for hotels by star ranking
 */
public class ComparatorRating implements Comparator<Hotel>
{
    /**
     * Overrided function compare, compares to hotels by their star ranking
     * @param h1 - first hotel to be compared
     * @param h2 - second hotel to be compared
     * @return - positive integer if h1 bigger then h2, 0 if they are equal, negative otherwise
     */
    @Override
    public int compare(Hotel h1, Hotel h2)
    {
        return h1.getStarRating() == h2.getStarRating() ?
                h2.getPropertyName().compareTo(h1.getPropertyName()) :
                h2.getStarRating() - h1.getStarRating();
    }
}