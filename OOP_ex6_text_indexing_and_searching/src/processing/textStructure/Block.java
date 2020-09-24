package processing.textStructure;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * This class represents an arbitrary block of text within a file
 */
public class Block implements Serializable {
	public static final long serialVersionUID = 1L;

	private long startIdx;                  //index within the file where the block begins
	private long endIdx;                    //index within the file where the block ends
	private RandomAccessFile inputFile;     //the RAF object pointing to the physical file in the file system
	private Entry entry;					//entry to which the block belongs

	/**
	 * Constructor
	 * @param inputFile     the RAF object backing this block
	 * @param startIdx      start index of the block within the file
	 * @param endIdx        end index of the block within the file
	 */
	public Block(RandomAccessFile inputFile, long startIdx, long endIdx) {
		this.inputFile = inputFile;
		this.startIdx = startIdx;
		this.endIdx = endIdx;
	}

	///////// getters //////////
	/**
	 * @return start index
	 */
	public long getStartIndex() {
		return this.startIdx;
	}

	/**
	 * @return  end index
	 */
	public long getEndIndex() {
		return this.endIdx;
	}

	/**
	 * @return the RAF object for this block
	 */
	public RandomAccessFile getRAF() {
		return this.inputFile;
	}

	/**
	 * Get the metadata of the block, if applicable for the parsing rule used
	 * @return  String of all metadata.
	 */
	public List<String> getMetadata() {
		//TODO implement me!!!
		return null;
	}

	/**
	 * The filename from which this block was extracted
	 * @return  filename
	 */
	public String getEntryName() {
		Path path = Paths.get(this.entry.getEntryPath());
		return path.getFileName().toString();
	}

	/**
	 * Convert an abstract block into a string
	 * @return string representation of the block
	 */
	@Override
	public String toString() {
		try {
			inputFile.seek(this.startIdx);
			byte[] b = new byte[(int)(this.endIdx - this.startIdx) + 1];
			inputFile.readFully(b);
			return new String(b);
		} catch (IOException ignored) {
			return ""; // TODO what are we supposed to do here???
		}
	}

	/**
	 * Sets the entry to which the block belongs
	 * @param entry - entry of this block
	 */
	void setEntry(Entry entry)
	{
		this.entry = entry;
	}

	/**
	 * Adds metadata to the block
	 * @param metaData A list containing metadata entries related to this block
	 */
	public void setMetaData(List<String> metaData)
	{

	}

}
