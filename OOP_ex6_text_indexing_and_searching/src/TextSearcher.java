import processing.parsingRules.SimpleParsingRule;
import processing.textStructure.Block;
import processing.textStructure.Corpus;
import processing.textStructure.Entry;

import java.io.IOException;

/**
 * The main program - A text searching module that indexes and queries large corpuses for strings or word groups
 */
public class TextSearcher {

    /**
     * Main method. Reads and parses a command file and if a query exists, prints the results.
     * @param args
     */
    public static void main(String[] args) {
        try{
            Corpus corpus = new Corpus("Corpuses/Simple", new SimpleParsingRule());
            corpus.populate();
//            for(Entry e: corpus)
//            {
//                for(Block b: e)
//                {
//                    System.out.println(b.toString() + "Printed\n");
//                }
//            }
//            corpus.forEach(e -> e.forEach(b -> System.out.println(b.toString() + "Printed\n")));
        }
        catch (IOException e){
            int x = 0;
        }
    }
}
