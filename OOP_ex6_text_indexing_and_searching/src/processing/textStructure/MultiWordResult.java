package processing.textStructure;

import java.io.IOException;

/**
 * This class defines a query result for multiple non-consecutive words.
 */
public class MultiWordResult extends WordResult implements Comparable<MultiWordResult> {
	private long[] wordPositions;
	private int confidence;

	/**
	 * Constructor matching Super
	 * @param blk The block in which this word was found
	 * @param query The query for searching
	 * @param idx The index within the block where the word was found.
	 */
	private MultiWordResult(Block blk, String[] query, long idx) {
		super(blk, query, idx);
	}

	public MultiWordResult(String[] query, Block block, long[] locs) {
//		super(block, query, locs[0]); // TODO what does it mean locs[0]???
		this(block, query, locs[0]);
		this.wordPositions = locs;
	}

	/**
	 * Calculate the confidence level of a result, defined by the sum of word distances.
	 * @param locs The locations of the query words in the text
	 * @return  The sum of distances
	 */
	private int calcConfidence(long[] locs) {
		int conf = 0;
		for(int i = 0; i < locs.length - 1; i++)
		{
			conf += (int)locs[i + 1] - locs[i];
		}
		this.confidence = conf;// TODO ???
		return conf;
	}

	/**
	 * Comparator for multi-word results
	 * @param o The other result to compare against
	 * @return int representing comparison result, according to the comparable interface.
	 */
	@Override
	public int compareTo(MultiWordResult o) {
		try{
			return this.resultToString().compareTo(o.resultToString());
		} catch (IOException e) {
			return 0;
		}
	}

	/**
	 * Extract a string that contains all words in the multy-word-result
	 * This should be a sentence starting at the word with the minimal location (index) and ending
	 * at the first line-break after the last word
	 * @return  A piece of text containing all query words
	 */
	@Override
	public String resultToString() throws IOException {
		StringBuilder res = new StringBuilder();
		for(long loc: wordPositions)
		{
			this.getBlock().getRAF().seek(loc);
			byte[] b = new byte[this.content[0].length()]; //TODO ???
			this.getBlock().getRAF().read(b);
			res.append(new String(b));
		}
		return res.toString();
	}
}
