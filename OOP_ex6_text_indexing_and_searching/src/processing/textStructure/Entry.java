package processing.textStructure;

import processing.parsingRules.IparsingRule;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.Iterator;

/**
 * This class represents a single file within a Corpus
 */
public class Entry implements Iterable<Block>, Serializable {
    public static final long serialVersionUID = 1L;

    /** path of the file representing the entry **/
    private String entryPath;

    /** Parsing rule for this entry **/
    private IparsingRule parsingRule;

	/**
	 * Main constructor
	 * @param filePath  The path to the file this entry represents
	 * @param parseRule The parsing rule to be used for this entry
	 */
    public Entry(String filePath, IparsingRule parseRule) {
        this.entryPath = filePath;
        this.parsingRule = parseRule;
    }

    /**
     * Iterate over Block objects in the Entry
     * @return  A Block object iterator
     */
    @Override
    public Iterator<Block> iterator() {
        RandomAccessFile raf;
        try { // TODO check where this exception handled
            raf = new RandomAccessFile(entryPath, "r");
        } catch (IOException e) {
            raf = null; //TODO check if null works with parseFile function
        }
        return this.parsingRule.parseFile(raf).iterator();
    }

    /**
     * @return path of the file representing entry
     */
    String getEntryPath()
    {
        return this.entryPath;
    }
}
