package filesprocessing.filesprocessingexceptions;

/**
 * Exception class for general error of file processing
 */
public class ErrorException extends Exception
{
    /*-----=  static methods  =-----*/

    private static final long serialVersionUID = 1L;

    /*-----=  constructor  =-----*/

    /**
     * Constructor for this exception
     * @param errorStr - String representing error explanation
     */
    ErrorException(String errorStr)
    {
        System.err.println("ERROR: " + errorStr);
    }
}
