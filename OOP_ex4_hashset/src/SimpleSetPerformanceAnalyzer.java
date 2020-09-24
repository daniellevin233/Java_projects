/*-----=  imports  =-----*/

import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * Class analyzing the performances of standard operations for sets with different implementations
 */
public class SimpleSetPerformanceAnalyzer
{
    /*-----=  static fields  =-----*/

    /** Number of iterations for warming before analyzing "contains" performance **/
    private final static int ITERATIONS_OF_WARMING = 70000;

    /** Factor for converting from nanoseconds to milliseconds **/
    private final static int MILLION_FACTOR = 1000000;

    /** Number of iterations for warming for LinkedList before analyzing "contains" performance **/
    private final static int ITERATIONS_OF_WARMING_LINKED_LIST = 7000;

    /** Index of LinkedList in collections list**/
    private final static int LINKED_LIST_INDEX = 3;

    /*-----=  fields  =-----*/

    /** string stating path of the text file containing data for adding **/
    private String dataPath;

    /** List of strings containing strings for working with **/
    private String[] parsedData;

    /** string stating path of the text file containing data for searching **/
    private String[] wordsForSearch;

    /** List of collections ,implementing SimpleSet interface, for performances analyzing **/
    private SimpleSet[] collectionsList;

    /*-----=  constructor  =-----*/


    /**
     * Constructor for performance analyzer
     * @param dataPath - string stating path of the text file containing data for adding
     * @param wordsForSearchPath - string stating path of the text file containing data for searching
     */
    private SimpleSetPerformanceAnalyzer(String dataPath, String wordsForSearchPath)
    {
        this.dataPath = dataPath;
        this.parsedData = Ex4Utils.file2array(dataPath);
        this.wordsForSearch = Ex4Utils.file2array(wordsForSearchPath);
        this.collectionsList = new SimpleSet[] {
                new OpenHashSet(),
                new ClosedHashSet(),
                new CollectionFacadeSet(new TreeSet<>()),
                new CollectionFacadeSet(new LinkedList<>()),
                new CollectionFacadeSet(new HashSet<>())
        };
    }

    /*-----=  methods  =-----*/

    /**
     * Method adds all the strings from parsedData to appropriate data structure
     * @param set - set to which the strings will be added
     */
    private void addStrings(SimpleSet set)
    {
        for(String string: this.parsedData)
        {
            set.add(string);
        }
    }

    /**
     * Activate the contains method with given set and string numOfIterations number of iterations
     * @param set - set to activate contains on
     * @param string - string to be searched in the set
     * @param numOfIterations - quantity of times that contains should be activated (for warming purposes)
     */
    private void checkContains(SimpleSet set, String string, int numOfIterations)
    {
        for(int i = 0; i < numOfIterations; i++)
        {
            set.contains(string);
        }
    }

    /**
     * Analyze performance of 'contains' given string in given set numOfIter times
     * Method prints time taken for performing such operation
     * @param set - set to be analyzed
     * @param strForSearching - string to be searched
     * @param numOfIter - quantity of times that contains should be activated (for warming purposes)
     */
    private void checkContaining(SimpleSet set, String strForSearching, int numOfIter)
    {
        checkContains(set, strForSearching, numOfIter);
        long timeBefore = System.nanoTime();
        checkContains(set, strForSearching, numOfIter);
        long difference = (System.nanoTime() - timeBefore) / numOfIter;
        System.out.println("Time taken for searching the string '" + strForSearching + "' in the set " +
                set.getClass() + " is: " + difference + " ns");
    }

    /**
     * Analyze performance of 'add' parsedData to given set
     * Method prints time taken for performing such operation
     * @param set - set to be analyzed
     */
    private void checkAdding(SimpleSet set)
    {
        long timeBefore = System.nanoTime();
        addStrings(set);
        long difference = (System.nanoTime() - timeBefore) / MILLION_FACTOR;
        System.out.println("Time taken for adding all the words from " + this.dataPath + " to the set of " +
                "the " + set.getClass() + " is: " + difference + " ms");
    }


    /**
     * Method manages analyzers of performances for all types of set in collectionList
     */
    private void checkPerformancesOnData()
    {
        int numOfIterationsForContains;
        for(int i = 0; i < this.collectionsList.length; i++)
        {
            checkAdding(this.collectionsList[i]);
            numOfIterationsForContains = (i == LINKED_LIST_INDEX) ? ITERATIONS_OF_WARMING_LINKED_LIST :
                    ITERATIONS_OF_WARMING; // LinkedList has specific number of iterations for warming
            for(String strForSearching : this.wordsForSearch)
            {
                checkContaining(this.collectionsList[i], strForSearching, numOfIterationsForContains);
            }
            System.out.println();
        }
    }

    /**
     * Method prints information about data that will be used by analyzer and call the check method
     */
    private void performanceAnalyzer()
    {
        System.out.println(this.dataPath + " ----- " + this.dataPath + " ----- " + this.dataPath + "\n");
        checkPerformancesOnData();
        System.out.println("\n" + this.dataPath + " ----- " + this.dataPath + " ----- " + this.dataPath +
                "\n");
    }

    /**
     * Main method for activating performance analyzer
     * @param args - should contain filePath with data for adding on every even index and filePath with
     *            data for searching on every odd position.
     */
    public static void main(String[] args)
    {
        for(int i = 0; i < args.length; i += 2)
        {
            SimpleSetPerformanceAnalyzer analyzer = new SimpleSetPerformanceAnalyzer(args[i], args[i + 1]);
            analyzer.performanceAnalyzer();
        }
    }
}