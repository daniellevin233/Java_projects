package filesprocessing.filesprocessingexceptions;

/**
 * Exception class for sub sections of section errors
 */
public class SubSectionLackErrorException extends ErrorException
{
    /*-----=  static fields  =-----*/

    private static final long serialVersionUID = 1L;

    /*-----=  constructor  =-----*/

    /**
     * Constructor if this exception
     * @param subsection - string of subsection with some warning
     */
    public SubSectionLackErrorException(String subsection)
    {
        super("Some section doesn't have '" + subsection + "' block");
    }
}
