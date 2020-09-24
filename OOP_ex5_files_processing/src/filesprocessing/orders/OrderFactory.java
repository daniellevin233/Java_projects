package filesprocessing.orders;

/**
 * Factory of orders
 */
public class OrderFactory {

    /*-----=  static fields  =-----*/

    /** Number of chars for reverse string **/
    private static final int INDEX_OF_REVERSING_POSITION = 8;

    /** String representing format of the reverse string **/
    private static final String REVERSE_STR = "#REVERSE";

    /*-----=  static methods  =-----*/

    /**
     * Static method load, for loading order from given order name
     * @param orderName - string got from the file to be loaded
     * @return - ready object of type Order
     * @throws OrderException - thrown in case of invalid order name
     */
    public static Order load(String orderName) throws OrderException
    {
        boolean reverse = checkReverse(orderName);
        orderName = reverse ? orderName.substring(0, orderName.length() - INDEX_OF_REVERSING_POSITION) :
                orderName; // cutting #REVERSE suffix if exists
        Order order;
        switch (orderName) {
            case "type":
                order = reverse ? new ReverseOrder(new TypeOrder()) : new TypeOrder();
                break;
            case "size":
                order = reverse ? new ReverseOrder(new SizeOrder()) : new SizeOrder();
                break;
            case "abs":
                order = reverse ? new ReverseOrder(new AbsOrder()) : new AbsOrder();
                break;
            default:
                throw new OrderException();
        }
        return order;
    }

    /**
     * Method checks if given order should be reversed afterwards
     * @param orderStr - string of order name to be checked
     * @return - true iff orderStr ends with "#REVERSE"
     */
    static private boolean checkReverse(String orderStr)
    {
        return orderStr.length() >= INDEX_OF_REVERSING_POSITION &&
                orderStr.substring(orderStr.length() - INDEX_OF_REVERSING_POSITION).equals(REVERSE_STR);
    }
}
