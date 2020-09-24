package processing.searchStrategies;

import processing.textStructure.Block;
import processing.textStructure.Corpus;
import processing.textStructure.Entry;
import processing.textStructure.WordResult;

import java.util.ArrayList;
import java.util.List;

public class NaiveSearch implements IsearchStrategy {

	private Corpus origin;
	public NaiveSearch(Corpus origin) {
		this.origin = origin;
	}


	/**
	 * The main search method to comply with the IsearchStrategy interface
	 * @param query The query string to search for.
	 * @return  A list of wordResults
	 */
	@Override
	public List<WordResult> search(String query) {
		List<WordResult> allResults = new ArrayList<WordResult>();
        for(Entry e: origin) {
            for (Block b : e) {
                String blockString = b.toString();

            }
        }
		return null;
	}


	private long searchQueryInBlock(String query, String block){
//	    int lenOfQuery = query.length();
//	    int indexInQuery = 0;
	    int indexInBlock = 0;
	    long indOfQueryInBlock = -1;
	    while (indexInBlock < block.length()){
//	        char relCharInQuery = query.charAt(indexInQuery);
	        indOfQueryInBlock = block.indexOf(query.charAt(0), indexInBlock);
	        for (int i = 0; i < query.length(); i++){
	            indexInBlock = indexInBlock + i;
	            if (block.charAt(indexInBlock) != query.charAt(i)){
	                return -1;
                }
            }
        }
        return indOfQueryInBlock;
    }

    public static void main(String[] args){
	    String block = "lalalatrtealala";
	    String query = "tea";
        int indexInBlock = 0;
        long indOfQueryInBlock = -1;
        while (indexInBlock < block.length()){
//	        char relCharInQuery = query.charAt(indexInQuery);
            indOfQueryInBlock = block.indexOf(query.charAt(0), indexInBlock);
            for (int i = 0; i < query.length(); i++){
                indexInBlock = indexInBlock + i;
                if (block.charAt(indexInBlock) != query.charAt(i)){
                    System.out.println("index in block is " + indexInBlock);
                }
            }
        }

	    System.out.println("rel index of query in block is " + indOfQueryInBlock);
    }
}
