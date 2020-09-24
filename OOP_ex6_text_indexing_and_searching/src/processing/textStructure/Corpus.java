package processing.textStructure;

import processing.parsingRules.IparsingRule;
import utils.MD5;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class represents a body of works - anywhere between one and thousands of documents sharing the same structure and that can be parsed by the same parsing rule.
 */
public class Corpus implements Iterable<Entry>, Serializable {

    public static final long serialVersionUID = 1L;

    /** parsing rule of this corpus **/
    private IparsingRule parsingRule;

    /** list of entries (files) of this corpus **/
    private List<Entry> entryList;

    private String corpusPath;

    /**
     *  check if the path is a folder or file.
     *  if file - single entry corpus.
     *  otherwise, recursively scan the directory for all subdirectories and files.
     *  each entry in a corpus should hold the folder from which the file came.
     * @param path - root path of the corpus
     * @param parsingRule - parsing rule to be activated on this corpus
     * @throws IOException - the case when the file with given path doesn't exist
     */
    public Corpus(String path, IparsingRule parsingRule) throws IOException {
        this.parsingRule = parsingRule;
        this.entryList = new ArrayList<>();
        this.corpusPath = path;
        Stream<Path> walk = Files.walk(Paths.get(path));
        walk.filter(Files::isRegularFile)
                .forEach(x -> this.entryList.add(new Entry(x.toString(), this.parsingRule)));
    }

	/**
	 * Return the parsing rule used for this corpus
	 * @return the parsing rule used for this corpus
	 */
	public IparsingRule getParsingRule() {
        return this.parsingRule;
	}

    /**
     * Iterate over Entry objects in the Corpus
     * @return An Entry iterator
     */
    @Override
    public Iterator<Entry> iterator() {
        return this.entryList.iterator();
    }

    /**
     * Update the RandomAccessFile objects for the Entries in the corpus, if it was loaded from cache.
     */
    public void updateRAFs(){
        //TODO implement me
    }

    /**
     * Return the checksum of the entire corpus. This is a MD5 checksum which represents all the files in
     * the corpus.
     * @return A String representing the checksum of the corpus
     * @throws IOException if any file is invalid
     */
    public String getChecksum() throws IOException{
        StringBuilder res = new StringBuilder();
        for(Entry entry : entryList)
        {
            res.append(fileToString(entry.getEntryPath(), StandardCharsets.UTF_8));
        }
        return MD5.getMd5(res.toString());
    }

    /**
     * The method populates the Block lists for each entry in the corpus
     */
    public void populate()
    {
        this.entryList.forEach(e -> e.forEach(b -> b.setEntry(e)));
    }

    /**
     * The path to the corpus folder
     * @return A String representation of the absolute path to the corpus folder
     */
    public String getPath()
    {
        return this.corpusPath;
    }


    /**
     * Helper static method for reading the entire file to string
     * @param filePath - path of the file to be read
     * @param encoding - type of encoding, to be activated while reading the file
     * @return - String representing entire file
     * @throws IOException - the case when the file wasn't found
     */
    static String fileToString(String filePath, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded, encoding);
    }
}
