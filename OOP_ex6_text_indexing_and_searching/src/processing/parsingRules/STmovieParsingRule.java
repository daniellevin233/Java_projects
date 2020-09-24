package processing.parsingRules;

import processing.textStructure.Block;
import processing.textStructure.WordResult;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.List;


public class STmovieParsingRule implements IparsingRule, Serializable {
	public static final long serialVersionUID = 1L;

	public STmovieParsingRule() {

	}

	@Override
	public Block parseRawBlock(RandomAccessFile inputFile, long startPos, long endPos) {
		//TODO implement me!!!
		return null;
	}

	@Override
	public List<Block> parseFile(RandomAccessFile inputFile) {
		//TODO implement me!!!
		return null;
	}

	@Override
	public void printResult(WordResult wordResult) {
		//TODO implement me!!!
	}
}