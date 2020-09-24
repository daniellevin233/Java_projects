import java.lang.reflect.Method;
import java.io.StringWriter;
import java.io.PrintWriter;
/**
 * This class is used to test the proper implementation of the Customer,
 * ProductType and Store classes. To activate the tests it encompasses, simply run
 * its main method.
 */
public class SimpleTests {

    protected static boolean bookObjectsCreated = false;
    protected static boolean patronObjectsCreated = false;
    protected static boolean libraryObjectsCreated = false;

    protected static int counter = 0;
    protected static int failedCounter = 0;
    protected static int combinedFailedCounter = 0;
    /*----=  Constructors  =-----*/

    protected SimpleTests() {}

    /*----=  Class Methods  =-----*/

    /**
     * @param args
     */
    public static void main(String[] args){
        double sumScore = 0;
        try{
            sumScore += testNoMainMethodsInAllClasses(); //Max=0, Min=-6
            sumScore += bookTests(); //Max=10
            sumScore += patronTests(); //Max=20
            sumScore += libraryTests(); //Max=60
        }catch(Exception e){
            System.out.println("EXCEPTION");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            System.out.println(sStackTrace);
        }


        if (sumScore < 0){
            sumScore = 0;
        }

//		System.out.println(sumScore);
        // TODO - amirkr: the "double-safety" checks (see bookTests, patronTests, libraryTests)
        // try o catch an unhandled exception from a bunch of operations. These are called
        // "combined" tests, and if one of them did fail, I want to give a representation for it.
        System.err.print("SUMMARY: " + Integer.toString(failedCounter) +
                " tests passed.\n");
    }

    /*----=  Assertion Methods  =-----*/

    /*
     * Prints the given error message to the standard error stream.
     */
    protected static void printError(String errorMessage) {
        System.out.println("general_error " + errorMessage);
    }

    private static void printOutputDiff(String actual, String expected){
        System.err.print("Expected: " + expected + "\n");
        System.err.print("Actual: " + actual + "\n");
    }

    /*
     * Prints the error code to the standard output stream.
     */
    protected static void printErrorCode(String code){
        System.out.println(code);
    }

    /*
     * Evaluates the given boolean expression, and prints the given error
     * message if it is false. Then, the boolean value is returned.
     */
    protected static boolean assertThis(boolean expression, String errorMessage, String errorCode) {
        if (!expression) {
            failedCounter++;
            printError(errorMessage);
            //printErrorCode(errorCode);
        }
        counter++;
        return expression;
    }

//	private static String getBadFieldInitErrMsg(String fieldName, Object obj){
//	   return "Bad initialization of "+fieldName+" field in "+obj.getClass()+" class.";
//	}

    /*----=  ProductType Tests  =-----*/

    protected static String bookTitle1 = "Let Me Off at the Top!: My Classy Life and Other Musings";
    protected static String bookTitle2 = "Paddle Your Own Canoe: One Man's Fundamentals for Delicious Living";
    protected static String bookTitle3 = "Pawnee: The Greatest Town in America";
    protected static String bookAuthor1 = "Ron Burgundy";
    protected static String bookAuthor2 = "Ron Swanson";
    protected static String bookAuthor3 = "Leslie Knope";
    protected static int bookPublication1 = 1975;
    protected static int bookPublication2 = 20302030;
    protected static int bookPublication3 = 1;
    protected static int comicValue1 = 9;
    protected static int comicValue2 = 3;
    protected static int comicValue3 = 5;
    protected static int dramaticValue1 = 2;
    protected static int dramaticValue2 = 4;
    protected static int dramaticValue3 = 2;
    protected static int educationalValue1 = 1;
    protected static int educationalValue2 = 8;
    protected static int educationalValue3 = 3;
    protected static Book book1;
    protected static Book book2;
    protected static Book book3;
    protected static Book book4;
    protected static Book book5;
    protected static Book book6;

    protected static Book book21;
    protected static Book book22;
    protected static Book book23;
    protected static Book book24;
    protected static Book book25;
    protected static Book book26;

    protected static Book[] books;
    protected static String book1ToString = bookToString(bookTitle1,bookAuthor1,bookPublication1,comicValue1,
            dramaticValue1, educationalValue1);
    protected static String book2ToString = bookToString(bookTitle2,bookAuthor2,bookPublication2,comicValue2,
            dramaticValue2, educationalValue2);
    protected static String book3ToString = bookToString(bookTitle3,bookAuthor3,bookPublication3,comicValue3,
            dramaticValue3, educationalValue3);

    protected static String bookToString(String title, String author, int publication, int comicValue,
                                         int dramaricValue, int educationalValue){
        return "["+title+","+author+","+publication+","+(comicValue+dramaricValue+educationalValue)+"]";
    }

    protected static double testNoMainMethod(Class<?> c){
        double sumScore = 0;
        Method methodToFind = null;
        try {
            methodToFind = c.getMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            //do nothing
        } catch (SecurityException e) {
            //do nothing
        }

        if (methodToFind != null){
            printError("The class "+c.getName()+" should not have a main method!");
            printErrorCode("MAIN_METHOD_" + c.getName());
            sumScore -= 2;
        }

        return sumScore;
    }

    protected static double testNoMainMethodsInAllClasses(){
        double sumScore = 0;
        sumScore += testNoMainMethod(Book.class);
        sumScore += testNoMainMethod(Patron.class);
        sumScore += testNoMainMethod(Library.class);
        return sumScore;
    }

    // Max score: 1
    private static double testBookInstatiation(){
        try {
            book1 = new Book(bookTitle1,bookAuthor1,bookPublication1,comicValue1,
                    dramaticValue1, educationalValue1);
            books = new Book[]{book1};
            bookObjectsCreated = true;
        } catch (Exception e) {
            printError("Problem creating objects of type Book.\n"+
                    "Most tests will not be run.");
            printErrorCode("BAD_BOOK_INSTANTIATION");
            return 0;
        }
        return 1;
    }


    // Max score: 5
    // TODO - amirkr: literary value tests considered all-or-nothing, exceptions will be
    // caught by the invoking method.
    private static double testBookLiteraryValue(){
        double sumScore = 0;
        double scorePerAssert = 1.66666667;
        String msg = "Method getLiteraryValue() in Book returns an unexpected value.";
        if (assertThis(book1.getLiteraryValue() == (comicValue1+dramaticValue1+educationalValue1), msg, "BLV_F1")){
            sumScore += scorePerAssert;
        }
        if (sumScore > 4.9){
            return 5;
        }
        return sumScore;
    }


    /*
     * Maximum score is 10.
     */
    // TODO - amirkr: double-safety - if one method threw some exception we did not anticipate,
    // continue to the next test.
    protected static double bookTests(){
        double sumScore = 0;
        sumScore += testBookInstatiation(); //Max=1
        if (bookObjectsCreated){
//			sumScore += testBookInitialization(); //Max=1
//			sumScore += testBookStringRepresentation(); //Max=3
            try
            {
                sumScore += testBookLiteraryValue(); //Max=5
            }
            catch(Exception e)
            {
                printError("Could not run all testBookLiteraryValue tests. Unhandled " +
                        "exception: " + e.getMessage() + ".");
                combinedFailedCounter++;
            }
        }
//		System.out.println("book tests returned "+sumScore);
        return sumScore;
    }



    /*----=  Patron Tests  =-----*/

    protected static String patronFName1 = "SirDance";
    protected static String patronFName2 = "Marc";
    protected static String patronFName3 = "Ir";
    protected static String[] patronFNames = {patronFName1,patronFName2,patronFName3};
    protected static String patronLName1 ="Alot";
    protected static String patronLName2 ="DeMarco";
    protected static String patronLName3 ="Babbon";
    protected static String[] patronLNames = {patronLName1,patronLName2,patronLName3};
    protected static int comicW1 = 4;
    protected static int comicW2 = 5;
    protected static int comicW3 = 6;
    protected static int[] comicWeights = {comicW1,comicW2,comicW3};
    protected static int dramaticW1 = 5;
    protected static int dramaticW2 = 4;
    protected static int dramaticW3 = 8;
    protected static int[] dramaticWeights = {dramaticW1,dramaticW2,dramaticW3};
    protected static int eduW1 = 9;
    protected static int eduW2 = 3;
    protected static int eduW3 = 3;
    protected static int[] eduWeights = {eduW1,eduW2,eduW3};
    protected static int thresh1 = 48;
    protected static int thresh2 = 54;
    protected static int thresh3 = 84;
    protected static int[] thresholds = {thresh1,thresh2,thresh3};
    protected static Patron patron1;
    protected static Patron patron2;
    protected static Patron patron3;
    protected static Patron[] patrons;



    // Max score: 2
    private static double testPatronInstatiation(){
        try {
            patron1 = new Patron(patronFName1,patronLName1,comicW1,dramaticW1,eduW1,thresh1);
            patron2 = new Patron(patronFName2,patronLName2,comicW2,dramaticW2,eduW2,thresh2);
            patron3 = new Patron(patronFName3,patronLName3,comicW3,dramaticW3,eduW3,thresh3);
            patrons = new Patron[]{patron1};
            patronObjectsCreated = true;
        } catch (Exception e) {
            printError("Problem creating objects of type Patron.\n"+
                    "Most further tests will not be run.");
            printErrorCode("BAD_PATRON_INSTANTIATION");
            return 0;
        }
        return 2;
    }




    private static boolean[][] correctBookEnjoyment = {
            {true}
    };

    // Max score: 8
    private static double testPatronWillEnjoyBook(){
        double sumScore = 0;
        double scorePerAssert = 0.44444444444;
        String errMsg = "willEnjoyBook() in Patron class returns the wrong value.";
        int pCount = 0, bCount = 0;
        for (Patron patron : patrons){
            bCount = 0;
            for (Book book : books){

                // TODO - amirkr: if willEnjoyBook threw an exception, contain it, no score.
                try
                {
                    if (assertThis(patron.willEnjoyBook(book)== correctBookEnjoyment[pCount][bCount++], errMsg, "WEB_F1")){
                        sumScore += scorePerAssert;
                    }
                }
                catch(Exception e)
                {
                    printError("willEnjoyBook threw an exception: " + e.getMessage() + ".");
                }
//          System.out.print(","+patron.willEnjoyBook(book));
//          System.out.println("Patron="+pCount+", Book="+(bCount++)+", getBookScore = "+patron.getBookScore(book));
            }
//       System.out.println();
            pCount++;
        }
        if (sumScore > 7.8){
            return 8;
        }
        return sumScore;
    }

    /**
     * Maximum score: 20
     */
    // TODO - amirkr: double-safety - if any method threw some exception we don't know about,
    // continue handling other tests.
    protected static double patronTests(){
        double sumScore = 0;
        try
        {
            sumScore += testPatronInstatiation(); //max=2
        }
        catch(Exception e)
        {
            // TODO - amirkr: do nothing.
        }

        if (patronObjectsCreated){
//			sumScore += testPatronsInitialization(); //max=1.5
            if (bookObjectsCreated){
                try
                {
                    sumScore += testPatronWillEnjoyBook(); //max=8
                }
                catch(Exception e)
                {
                    printError("Could not run all testPatronWillEnjoyBook tests. Unhandled " +
                            "exception: " + e.getMessage() + ".");
                    combinedFailedCounter++;
                }
            }
        }
//		System.out.println("patron tests returned "+sumScore);
        return sumScore;
    }

    /*----=  Library Tests  =-----*/
    protected static Library lib1;
    protected static Library lib2;

    protected static int maxBooks1 = 7;
    protected static int maxBooks2 = 15;
    protected static int maxBorrow1 = 3;
    protected static int maxBorrow2 = 1;
    protected static int maxPatrons1 = 2;
    protected static int maxPatrons2 = 4;

    protected static int[] bookIds1 = new int[maxBooks1];
    protected static int[] bookIds2 = new int[maxBooks2];

    protected static int[] patronIds1 = new int[maxPatrons1];
    protected static int[] patronIds2 = new int[maxPatrons2];

    protected static Book book7 = new Book("I love lamp","Brick", 2013,1,1,1);
    protected static Book book8 = new Book("I'm the only gay in the village","Dafit",1921,1,1,1);
    protected static Book book27 = new Book("I love lamp","Brick", 2013,1,1,1);
    protected static Book book28 = new Book("I'm the only gay in the village","Dafit",1921,1,1,1);
    protected static Book[] moreBooks;
    protected static Book[] moreBooks2;

    // Max score: 0. Min score: -4.
    // They get negative points if the messed up the instatiation,
    // since we gave them the constructor.
    private static double testLibraryInstatiation(){
        try {
            moreBooks = new Book[]{book1,book2,book3,book4,book5,book6,book7,book8};
            moreBooks2 = new Book[]{book21,book22,book23,book24,book25,book26,book27,book28};
            lib1 = new Library(maxBooks1, maxBorrow1, maxPatrons1);
            lib2 = new Library(maxBooks2, maxBorrow2, maxPatrons2);
            libraryObjectsCreated = true;
        } catch (Exception e) {
            printError("Problem creating objects of type Library.\n"+
                    "All further Library tests will not be run.");
            printErrorCode("BAD_LIBRARY_INSTANTIATION");
            return 0;
        }
        return 0;
    }

    // Max score: 5
    private static double testLibraryAddBookToLibrary(){
        double sumScore = 0;
        double scorePerAssert = 0.29411;
        int result1 = -1;
        int result2 = -1;
        int result3 = -1;
        String errorMsg = "Unexpected result of addBookToLibrary() in Library."
                +" Book should have been added successfully, as space should"
                + " be available.";
        String fullLibraryErrorMsg = "Unexpected result of addBookToLibrary() in "
                + "Library. Library should be full after filling it to capacity, "
                + "and additional books should not be added successfully.";
        String bookAlreadyInLibraryErrorMsg = "Unexpected result of addBookToLibrary() in"
                + "Library. A book's id number should be returned if it's already in "
                + "the library.";

        try
        {
            result1 = lib1.addBookToLibrary(moreBooks[0]);
            if (assertThis(result1 >= 0, errorMsg, "ABL_F1")){
                bookIds1[0] = result1;
                sumScore += scorePerAssert;
            }
        }
        catch(Exception e)
        {
            printError("Failed to add book to library:");
            e.printStackTrace(System.err);
            failedCounter++;
        }

        if (sumScore > 4.9){
            return 5;
        }
        return sumScore;
    }



// private static double testLibraryBorrowBook(){
//    double sumScore = 0;
//    double scorePerAssert = 1.5;
//    boolean result;
//
//    String errorMsg = "Unexpected result of addBookToLibrary() in Library."
//          +" Book should have been added successfully, as space should"
//          + " be available.";
//
//    return sumScore;
// }

    /**
     * Maximum score: 60
     */
    // TODO - amirkr: double-safety - if one "combined tests" method threw an unhandled
    // exception, contain it, no score added, continue on.
    protected static double libraryTests(){
        double sumScore = 0;
        sumScore += testLibraryInstatiation(); //max=0
        if (libraryObjectsCreated){
            if (bookObjectsCreated){
                try
                {
                    sumScore += testLibraryAddBookToLibrary(); //max=5
                }
                catch(Exception e)
                {
                    printError("An unhandled exception (" + e.getMessage() + " caught in " +
                            "the testLibraryAddBookToLibrary test. No points given.");
                    combinedFailedCounter++;
                }
            }
        }
        //		System.out.println("lib tests returned "+sumScore);
        return sumScore;
    }

}

