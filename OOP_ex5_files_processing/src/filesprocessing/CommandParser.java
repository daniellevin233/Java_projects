package filesprocessing;

/*-----=  imports  =-----*/

import filesprocessing.filesprocessingexceptions.*;
import filesprocessing.filters.*;
import filesprocessing.orders.*;

import java.util.*;

/**
 * Class for parsing file with filters and orders
 */
class CommandParser {

    /*-----=  static fields  =-----*/

    /** String representing filter appearance in commands file **/
    private static final String FILTER_SUB_SECTION_NAME = "FILTER";

    /** String representing order appearance in commands file **/
    private static final String ORDER_SUB_SECTION_NAME = "ORDER";

    /** Minimal number of lines for section to be considered as valid **/
    private static final int MIN_SECTION_LINES = 3;

    /** Maximal number of lines for section to be considered as valid **/
    private static final int MAX_SECTION_LINES = 4;

    /*-----=  fields  =-----*/

    /** Scanner for streaming throw the file **/
    private Scanner reader;

    /** Linked list of sections to be parsed **/
    private LinkedList<Section> listOfSections;

    /** Counter of current line in the stream **/
    private int lineCounter = 1;

    /*-----=  constructor  =-----*/

    /**
     * Constructor of the parser
     * @param scanner - opened scanner for streaming throw the file
     */
    CommandParser(Scanner scanner) {
        this.reader = scanner;
        this.listOfSections = new LinkedList<>();
    }

    /*-----=  methods  =-----*/

    /**
     * Main parser method
     * @return - linked list of parsed sections
     * @throws ErrorException - in the case of file invalid format, some section has no mandatory subsection
     */
    LinkedList<Section> parseCommandFile() throws ErrorException
    {
        ArrayList<String> sectionStringArr = new ArrayList<>();
        while (reader.hasNext()) {
            try {
                while (!reader.hasNext(ORDER_SUB_SECTION_NAME)) { // find next order subsection
                    sectionStringArr.add(reader.nextLine());
                }
                while (!reader.hasNext(FILTER_SUB_SECTION_NAME)) { // find next filter subsection
                    sectionStringArr.add(reader.nextLine());
                }
            } catch (NoSuchElementException ignored) {
            } finally { // adding current section
                listOfSections.add(this.parseSection(sectionStringArr));
            }
            sectionStringArr.clear();
        }
        return this.listOfSections;
    }

    /**
     * Parser for one section
     * @param sectionStrings - arrayList of strings contained by the section
     * @return - ready Section object
     * @throws ErrorException - in the case of file invalid format, some section has no mandatory subsection
     */
    private Section parseSection(ArrayList<String> sectionStrings) throws ErrorException
    {
        if(!isValidSectionSize(sectionStrings))
            throw new SectionErrorException();
        int curElementIndex = 0;
        ArrayList<Integer> curWarningLines = new ArrayList<>();
        String curStr, curFilterStr, curOrderStr;
        Filter filter;
        Order order;
        // reading first line in the section
        if (!sectionStrings.get(curElementIndex).equals(FILTER_SUB_SECTION_NAME)) {
            throw new SubSectionLackErrorException(FILTER_SUB_SECTION_NAME);
        }
        // reading the second line in the section
        this.lineCounter++;
        curElementIndex++;
        curFilterStr = sectionStrings.get(curElementIndex);
        try {
            filter = FilterFactory.load(curFilterStr); // adding the current filter
        } catch (FilterException e) {
            filter = Filter.defaultFilter();
            curWarningLines.add(this.lineCounter);
        }
        // reading third line in the section
        this.lineCounter++;
        curElementIndex++;
        curStr = sectionStrings.get(curElementIndex);
        if (!curStr.equals(ORDER_SUB_SECTION_NAME)) {
            throw new SubSectionLackErrorException(ORDER_SUB_SECTION_NAME);
        }
        // reading fourth line in section
        this.lineCounter++;
        curElementIndex++;
        if(sectionStrings.size() == curElementIndex)
            return new Section(filter, Order.defaultOrder(), curWarningLines);
        try {
            curOrderStr = sectionStrings.get(curElementIndex);
            order = OrderFactory.load(curOrderStr); // adding the current order
        } catch (OrderException e) {
            order = Order.defaultOrder();
            curWarningLines.add(this.lineCounter);
        }
        return new Section(filter, order, curWarningLines);
    }

    /*-----=  static methods  =-----*/

    /**
     * Checks if the size of given candidate section (as list of strings) is valid
     * @param sectionToCheck - list of strings representing section to be checked
     * @return - true iff the num of strings is valid for creating such a section
     */
    private static boolean isValidSectionSize(ArrayList<String> sectionToCheck)
    {
        return sectionToCheck.size() >= MIN_SECTION_LINES && sectionToCheck.size() <= MAX_SECTION_LINES;
    }
}