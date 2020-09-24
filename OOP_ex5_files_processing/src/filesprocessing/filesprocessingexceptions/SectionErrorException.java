package filesprocessing.filesprocessingexceptions;

/**
 * Exception class for invalid section catching
 */
public class SectionErrorException extends ErrorException
{
    /*-----=  static fields  =-----*/

    private static final long serialVersionUID = 1L;

    /*-----=  constructor  =-----*/

    /**
     * Constructor for this exception, redirect to ErrorException with appropriate message
     */
    public SectionErrorException()
    {
        super("There is a section of invalid size");
    }
}
