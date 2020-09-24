/*-----=  imports  =-----*/

import java.awt.geom.Point2D;
import java.util.Comparator;

import oop.ex3.searchengine.Hotel;

/**
 * Comparator for hotels by proximity
 */
public class ComparatorProximity implements Comparator<Hotel>
{
    /*-----=  fields  =-----*/

    /** latitude of the point for proximity measuring */
    private double latitude;

    /** longitude of the point for proximity measuring */
    private double longitude;

    /**
     * Constructor for Comparator
     * @param latitude - latitude of point
     * @param longitude - longitude of point
     */
    ComparatorProximity(double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Overrided function compare, compares to hotels by their proximity to the point initialized for the
     * comparator
     * @param h1 - first hotel to be compared
     * @param h2 - second hotel to be compared
     * @return - positive integer if h1 bigger then h2, 0 if they are equal, negative otherwise
     */
    @Override
    public int compare(Hotel h1, Hotel h2)
    {
        double h1Distance = Point2D.distance(h1.getLatitude(), h1.getLongitude(), this.latitude,
                this.longitude);
        double h2Distance = Point2D.distance(h2.getLatitude(), h2.getLongitude(), this.latitude,
                this.longitude);
        return h1Distance == h2Distance ? (h2.getNumPOI() - h1.getNumPOI()) :
                (int)Math.signum(h1Distance - h2Distance);
    }
}