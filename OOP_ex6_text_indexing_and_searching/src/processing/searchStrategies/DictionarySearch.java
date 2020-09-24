package processing.searchStrategies;

import processing.textStructure.Word;
import processing.textStructure.WordResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DictionarySearch implements IsearchStrategy {

	private HashMap<?, List<Word>> dict;

	public DictionarySearch(HashMap<Integer, List<Word>> dict) {
		this.dict = dict;
	}

	/**
	 * Search the dictionary generated by the indexer for query results
	 * @param query The query string to search for.
	 * @return  A list of WordResults or any extension of them.
	 */
	@Override
	public List<? extends WordResult> search(String query) {
		ArrayList<WordResult> resultsLst = new ArrayList<>();
		String[] words = query.split("\\s");
		for(String word: words)
		{
			String result = stemmer.stem(word);
			for(Word res: dict.get(result.hashCode()))
			{
				resultsLst.add(null);//new MultiWordResult()); //TODO ???
			}
		}
		return null;
	}
}
