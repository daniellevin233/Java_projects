/*-----=  imports  =-----*/

import org.junit.*;
import static org.junit.Assert.*;

import oop.ex3.searchengine.HotelDataset;
import oop.ex3.searchengine.Hotel;

import java.awt.geom.Point2D;

/**
 * Class for testing BoopingSite
 */
public class BoopingSiteTest {

    /*-----=  static fields  =-----*/

    /**  text file name for retrieving database */
    private static final String dataBaseFile = "hotels_tst1.txt";

    /** object BoopingSite for testing */
    private static BoopingSite boopSite;

    /*-----=  fields  =-----*/

    /** constant for wrong latitude example */
    private final double TEST_WRONG_LATITUDE = -91;

    /** constant for wrong longitude example */
    private final double TEST_WRONG_LONGITUDE = 181;

    /** constant for latitude example */
    private final double TEST_LATITUDE = 0.0;

    /** constant for longitude example */
    private final double TEST_LONGITUDE = 0.0;

    /** constant for wrong city example */
    private final String TEST_WRONG_CITY = "manalinali";

    /** constant for city example */
    private final String TEST_CITY = "manali";

    /** variable number of hotels in the database file */
    private final int numOfHotels = HotelDataset.getHotels(dataBaseFile).length;

    /**
     * Method for creating a tested object - BoopingSite
     */
    @BeforeClass
    public static void createTestObject()
    {
        boopSite = new BoopingSite(dataBaseFile);
    }

    /**
     * Test 1 for getHotelsInCityByRating: check invalid city
     */
    @Test
    public void testGetHotelsInCityByRating1()
    {
        Hotel[] emptyList = new Hotel[0];
        assertArrayEquals("The output list should be empty", emptyList,
                boopSite.getHotelsInCityByRating(TEST_WRONG_CITY));
    }

    /**
     * Test 2 for getHotelsInCityByRating: check correctness of returned sorted array
     */
    @Test
    public void testGetHotelsInCityByRating2()
    {
        Hotel[] actualArr = boopSite.getHotelsInCityByRating(TEST_CITY);
        assertEquals("Returned array is of wrong length", numOfHotels, actualArr.length);
        for(int i = 0; i < numOfHotels - 1; i++)
        {
            if (actualArr[i].getStarRating() == actualArr[i + 1].getStarRating()) {
                assertTrue("Array wasn't sorted right by names in the place " + i,
                        actualArr[i].getPropertyName().compareTo(actualArr[i + 1].getPropertyName()) >= 0);
            } else {
                assertTrue("Array wasn't sorted right by ranking in the place " + i,
                        actualArr[i].getStarRating() > actualArr[i + 1].getStarRating());
            }
        }
    }

    /**
     * Test 1 for getHotelsByProximity: check invalid latitude
     */
    @Test
    public void testGetHotelsByProximity1()
    {
        Hotel[] emptyList = new Hotel[0];
        assertArrayEquals("The output list should be empty", emptyList,
                boopSite.getHotelsByProximity(TEST_WRONG_LATITUDE, TEST_LONGITUDE));
    }

    /**
     * Test 2 for getHotelsByProximity: check invalid longitude
     */
    @Test
    public void testGetHotelsByProximity2()
    {
        Hotel[] emptyList = new Hotel[0];
        assertArrayEquals("The output list should be empty", emptyList,
                boopSite.getHotelsByProximity(TEST_LATITUDE, TEST_WRONG_LONGITUDE));
    }

    /**
     * Test 3 for getHotelsByProximity: check correctness of returned sorted array
     */
    @Test
    public void testGetHotelsByProximity3()
    {
        Hotel[] actualArr = boopSite.getHotelsByProximity(TEST_LATITUDE, TEST_LONGITUDE);
        testHotelsByProximityHelper(actualArr);
    }

    /**
     * Test 1 for getHotelsInCityByProximity: check invalid city
     */
    @Test
    public void testGetHotelsInCityByProximity1()
    {
        Hotel[] emptyList = new Hotel[0];
        assertArrayEquals("The output list should be empty", emptyList,
                boopSite.getHotelsInCityByProximity(TEST_WRONG_CITY, TEST_LATITUDE, TEST_LONGITUDE));
    }

    /**
     * Test 2 for getHotelsInCityByProximity: check invalid latitude
     */
    @Test
    public void testGetHotelsInCityByProximity2()
    {
        Hotel[] emptyList = new Hotel[0];
        assertArrayEquals("The output list should be empty", emptyList,
                boopSite.getHotelsInCityByProximity(TEST_CITY, TEST_WRONG_LATITUDE, TEST_LONGITUDE));
    }

    /**
     * Test 3 for getHotelsInCityByProximity: check invalid longitude
     */
    @Test
    public void testGetHotelsInCityByProximity3()
    {
        Hotel[] emptyList = new Hotel[0];
        assertArrayEquals("The output list should be empty", emptyList,
                boopSite.getHotelsInCityByProximity(TEST_CITY, TEST_LATITUDE, TEST_WRONG_LONGITUDE));
    }

    /**
     * Test 4 for getHotelsInCityByProximity: check that all the cities in returned list are from the
     * correct city
     */
    @Test
    public void testGetHotelsInCityByProximity4()
    {
        Hotel[] actualArr = boopSite.getHotelsInCityByProximity(TEST_CITY, TEST_LATITUDE, TEST_LONGITUDE);
        assertEquals("Returned array is of wrong length", numOfHotels, actualArr.length);
        for(int i = 0; i < numOfHotels; i++)
        {
            assertEquals("WOW", TEST_CITY, actualArr[i].getCity());
        }
    }

    /**
     * Test 5 for getHotelsInCityByProximity: check the correctness of the returned sorted array
     */
    @Test
    public void testGetHotelsInCityByProximity5()
    {
        Hotel[] actualArr = boopSite.getHotelsInCityByProximity(TEST_CITY, TEST_LATITUDE, TEST_LONGITUDE);
        testHotelsByProximityHelper(actualArr);
    }

    /**
     * Helper method for checking correctness of sorting by proximity
     * @param actualArr - returned array to be checked
     */
    private void testHotelsByProximityHelper(Hotel[] actualArr)
    {
        assertEquals("Returned array is of wrong length", numOfHotels, actualArr.length);
        for(int i = 0; i < numOfHotels - 1; i++)
        {
            if (getDistance(actualArr[i], TEST_LATITUDE, TEST_LONGITUDE) == getDistance(actualArr[i + 1],
                    TEST_LATITUDE, TEST_LONGITUDE)) {
                assertTrue("Array wasn't sorted correct by numPOI in the place " + i,
                        actualArr[i].getNumPOI() >= actualArr[i + 1].getNumPOI());
            } else {
                assertTrue("Array wasn't sorted correct by proximity in the place " + i,
                        getDistance(actualArr[i], TEST_LATITUDE, TEST_LONGITUDE) <=
                                getDistance(actualArr[i + 1], TEST_LATITUDE, TEST_LONGITUDE));
            }
        }
    }

    /**
     * Helper method for calculating the distance of the hotel from the given point
     * @param hotel - hotel to calculate distance for
     * @param latitude - latitude of the point
     * @param longitude - longitude of the point
     * @return - double value representing the distance
     */
    private double getDistance(Hotel hotel, double latitude, double longitude)
    {
        return Point2D.distance(hotel.getLatitude(), hotel.getLongitude(), latitude, longitude);
    }
}