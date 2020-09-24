/*-----=  imports  =-----*/

import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;

import java.util.*;

/**
 * Booping site class, for managing hotels list, and different sorting methods
 */
public class BoopingSite {

    /*-----=  static fields  =-----*/

    /** Success value */
    private static final int VALID_VALUE = 0;

    /** double border for latitude validity */
    private static final double LATITUDE_BORDER = 90;

    /** double border for longitude validity */
    private static final double LONGITUDE_BORDER = 180;

    /*-----=  fields  =-----*/

    /** ArrayList of hotels contained at the booping site */
    private final ArrayList<Hotel> hotelsList;

    /*-----=  methods  =-----*/

    /**
     * Constructor for booping site
     * @param dataFile - file from which hotels list should be retrieved
     */
    public BoopingSite(String dataFile)
    {
        this.hotelsList = new ArrayList<>(Arrays.asList(HotelDataset.getHotels(dataFile)));
    }

    /**
     * Sorts hotels by its star ranking in the given city
     * @param city - city, from which hotels for sorting should be taken
     * @return - array of Hotels, sorted by star ranking
     */
    public Hotel[] getHotelsInCityByRating(String city)
    {
        ArrayList<Hotel> hotelsListInCity = filterByCity(this.hotelsList, city);
        hotelsListInCity.sort(new ComparatorRating());
        return hotelsListInCity.toArray(new Hotel[VALID_VALUE]);
    }

    /**
     * Sorts hotels by its proximity from the point with given latitude and longitude
     * @param latitude - latitude of the point
     * @param longitude - longitude of the point
     * @return - array of Hotels, sorted by proximity to the given point
     */
    public Hotel[] getHotelsByProximity(double latitude, double longitude)
    {
        return sortByProximity(this.hotelsList, latitude, longitude);
    }

    /**
     * Sorts hotels by its proximity from the point with given latitude and longitude in the given city
     * @param city - city, from which hotels for sorting should be taken
     * @param latitude - latitude of the point
     * @param longitude - longitude of the point
     * @return - array of Hotels, sorted by proximity to the given point
     */
    public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude)
    {
        return sortByProximity(filterByCity(this.hotelsList, city), latitude, longitude);
    }

    /**
     * Helper method, which sorts hotels in hotelsArr by proximity to given point
     * @param hotelsArr - array of hotels to be sorted
     * @param latitude - latitude of the point
     * @param longitude - longitude of the point
     * @return - sorted array of hotels
     */
    private Hotel[] sortByProximity(ArrayList<Hotel> hotelsArr, double latitude, double longitude)
    {
        if(!isCoordinateValid(latitude, longitude))
        {
            return new Hotel[VALID_VALUE];
        }
        hotelsArr.sort(new ComparatorProximity(latitude, longitude));
        return hotelsArr.toArray(new Hotel[VALID_VALUE]);
    }

    /**
     * Helper method, which filters list of hotels to hotels which are located in the given city
     * @param hotelsList - list of hotels to be filtered
     * @param city - city, for which hotels should be filtered
     * @return - filtered ArrayList of cities
     */
    private ArrayList<Hotel> filterByCity(ArrayList<Hotel> hotelsList, String city)
    {
        ArrayList<Hotel> filtered = new ArrayList<>();
        for(Hotel curH : hotelsList)
        {
            if(curH.getCity().equals(city))
            {
                filtered.add(curH);
            }
        }
        return filtered;
    }

    /**
     * Method checks if the given coordinates are valid
     * @param latitude - latitude to be checked
     * @param longitude - longitude to be checked
     * @return - true if point values are valid, false otherwise
     */
    private boolean isCoordinateValid(double latitude, double longitude)
    {
        return latitude <= LATITUDE_BORDER && latitude >= -LATITUDE_BORDER &&
                longitude <= LONGITUDE_BORDER && longitude >= -LONGITUDE_BORDER;
    }
}