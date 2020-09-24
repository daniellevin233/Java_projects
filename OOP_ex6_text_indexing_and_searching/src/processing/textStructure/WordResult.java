package processing.textStructure;

import java.io.IOException;

/**
 * This class represents a result containing a single string (single word or multiple words treated as one)
 */
public class WordResult {
	/** The offset of the word within the block **/
	private long idxInBlk;

	/** The block in which this word was found **/
	Block location;

	/** The word queried, represented as an array of size 1. **/
	protected String[] content;


	/**
	 * Simple constructor without index
	 * @param loc The block in which this word was found
	 * @param word The word queried, represented as an array of size 1.
	 */
	private WordResult(Block loc, String[] word) {
		this.location = loc;
		this.content = word;
	}

	/**
	 * Constructor containing index of word in block
	 * @param blk The block in which this word was found
	 * @param word The word queried, represented as an array of size 1.
	 * @param idx The index within the block where the word was found.
	 */
	public WordResult(Block blk, String[] word, long idx) {
		this(blk, word);
		this.idxInBlk = idx;
	}

	/**
	 * @return the block in which this word was found
	 */
	public Block getBlock() {
		return this.location;
	}

	/**
	 * @return The query word that generated this result
	 */
	public String[] getWord() {
		return this.content;
	}

	/**
	 * Method for printing the result
	 * @return The result representation as defined by the "printing results" requirement in the exercise
	 * instructions
	 * @throws IOException
	 */
	public String resultToString() throws IOException {
//		System.out.println("The top 10 results for query " + this.content[0] + " are:");
		this.location.getRAF().seek(this.idxInBlk);
		byte[] b = new byte[this.content[0].length()];
		this.location.getRAF().read(b);
		return new String(b);
	}

//	/**
//	 * @return The filename from which this word result was extracted
//	 */
//	public String getSourceEntry() {
//		return this.location.getEntryName();
//	}
//
//	/**
//	 * Overriding of compareTo function
//	 * @param wordResult other wordResult to be compared to
//	 * @return positive integer if this word result bigger than given lexicographically,
//	 * 0 if they are equal, negative integer otherwise
//	 */
//	@Override
//	public int compareTo(WordResult wordResult) {
//		return getSourceEntry().compareTo(wordResult.getSourceEntry());
//	}
}
