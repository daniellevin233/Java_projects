package filesprocessing;

/*-----=  imports  =-----*/

import filesprocessing.filesprocessingexceptions.ErrorException;

import java.io.*;
import java.util.*;

/**
 * Class for processing directory, filtering and sorting files from folder given as first program arguments
 * in accordance to filters and orders from the file given as second program argument,
 * represents facade design
 */
public class DirectoryProcessor
{

    /*-----=  static methods  =-----*/

    /**
     * Main static method representing facade of the program
     * @param args - folder with files, file with filters and orders sections
     */
    public static void main(String[] args)
    {
        if(args.length != 2)
        {
            System.err.println("Wrong number of arguments given");
            return;
        }
        ArrayList<File> sourceFiles = new ArrayList<> // list of files for filtering
                (Arrays.asList(Objects.requireNonNull(new File(args[0]).listFiles(File::isFile))));
        LinkedList<Section> sections; // list of sections
        try(Scanner sc = new Scanner(new FileReader(args[1])))
        {
            sections = new CommandParser(sc).parseCommandFile();
        } catch(IOException e) { // file doesn't exist
            System.err.println("Accessing the commands file failed");
            return;
        } catch(ErrorException e) { // type 2 exception catched
            return;
        }
        sections.forEach(section -> section.print(sourceFiles));
    }
}