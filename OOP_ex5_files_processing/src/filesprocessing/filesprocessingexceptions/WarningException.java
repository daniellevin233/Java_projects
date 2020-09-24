package filesprocessing.filesprocessingexceptions;

/**
 * Exception Class for warning exceptions
 */
public class WarningException extends Exception
{
    /*-----=  static fields  =-----*/

    private static final long serialVersionUID = 1L;

    /*-----=  constructor  =-----*/

    /**
     * Constructor of this warning exception, prints message with line of warning
     * @param line - integer, line of warning for this exception
     */
    public WarningException(int line)
    {
        System.err.println("Warning in line " + line);
    }
}
