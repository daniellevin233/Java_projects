package Join;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ExternalMemoryImpl extends IExternalMemory {

	private static final long WINDOWS_NEW_LINE = 2;
	private static final long LINUX_NEW_LINE = 1;

	private static final long NEW_LINE_CHAR_SIZE = WINDOWS_NEW_LINE;

	private static final long SIZE_OF_TUPLE_IN_BYTES = 50; // := (10 + 20 + 20)
	private static final long SIZE_OF_TUPLE_LINE_IN_BYTES = 52 + WINDOWS_NEW_LINE;// (10 + 1 + 20 + 1 + 20 + \n)
	private static final long SIZE_OF_BLOCK_IN_BYTES = 4000; // := 4KB
	private static final long INTERNAL_MEMORY_BLOCKS = 1000; // := 4 MB of main memory out of 50MB
	private static final long NUM_OF_TUPLES_PER_BLOCK = 
			(long) Math.floor((double) (SIZE_OF_BLOCK_IN_BYTES / SIZE_OF_TUPLE_IN_BYTES));
	
	private static final String TMP_FILE_NAME = "chunk";

	static private long getNumOfTuples(String fileName){
		File f = new File(fileName);
		long fileSizeInBytes = 0;
		if (f.exists() && f.isFile()) fileSizeInBytes = f.length() + NEW_LINE_CHAR_SIZE; // to handle last
		// line which lacks chars '[\r]\n'
		return fileSizeInBytes / (SIZE_OF_TUPLE_LINE_IN_BYTES - 1); // number of tuples in the table
	}

	static private long getNumOfBlocks(String in){
		return (long) Math.ceil((double)
							getNumOfTuples(in) * SIZE_OF_TUPLE_IN_BYTES // memory needed for entire table
							/ SIZE_OF_BLOCK_IN_BYTES);
	}

	static private void writeSortedTuplesInRange(long left, long right, BufferedReader br, BufferedWriter bw)
			throws IOException {
		List<String> listOfLines = new ArrayList<>();
		String line;
		while (left <= right) {
			int i = 0;
			while(i < NUM_OF_TUPLES_PER_BLOCK && (line = br.readLine()) != null){
				listOfLines.add(line);
				i++;
			}
			left++;
		}
		Collections.sort(listOfLines);
		for (int i = 0; i < listOfLines.size() - 1; i++){
			bw.write(listOfLines.get(i));
			bw.newLine();
		}
		if(listOfLines.size() != 0)
			bw.write(listOfLines.get(listOfLines.size() - 1));
	}

	static private void sortStep1(String in, long numOfBlocks, String tmp) throws IOException {
		int sizeOfChunk =
				(int) (INTERNAL_MEMORY_BLOCKS * NUM_OF_TUPLES_PER_BLOCK * SIZE_OF_TUPLE_LINE_IN_BYTES);
		try (BufferedReader br = new BufferedReader(new FileReader(in), sizeOfChunk)) {
			int i = 0;
			long left = 0, right;
			while (left < numOfBlocks) {
				right = Math.min(left + INTERNAL_MEMORY_BLOCKS, numOfBlocks) - 1;
				BufferedWriter bw = new BufferedWriter(new FileWriter(getChunkFileName(tmp, i)), sizeOfChunk);
				writeSortedTuplesInRange(left, right, br, bw);
				bw.close();
				left += INTERNAL_MEMORY_BLOCKS;
				i++;
			}
		}
	}

	static private void writeTuplesFromList(BufferedWriter bw, List<String> listToWrite) throws IOException {
		for (String str: listToWrite) {
			bw.write(str);
			bw.newLine();
		}
	}

	static private String getChunkFileName(String dir, int idxOfChunk){
		return dir + "/" + TMP_FILE_NAME + idxOfChunk;
	}

	static private void sortStep2(String in, String out, String tmp, long numOfBlocks) throws IOException {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(out), (int) SIZE_OF_BLOCK_IN_BYTES)){
			long N = getNumOfTuples(in);
			long numOfChunks = (long) Math.ceil((double) numOfBlocks / INTERNAL_MEMORY_BLOCKS);
			Map<BufferedReader, String> chunkToLineMap = new HashMap<>();
			for(int i = 0; i < numOfChunks; i++){
				BufferedReader br = new BufferedReader(new FileReader(getChunkFileName(tmp, i)),
						(int) SIZE_OF_BLOCK_IN_BYTES);
				chunkToLineMap.put(br, br.readLine());
			}
			long k = 0;
			List<String> output = new ArrayList<>();
			while (k < N && !chunkToLineMap.isEmpty()){
				BufferedReader curMinReader = Collections.min(chunkToLineMap.entrySet(),
						Comparator.comparing(Map.Entry::getValue)).getKey();
				output.add(chunkToLineMap.get(curMinReader)); // write cur minimal string among all pointers
				String nextLineInMinChunk; // move pointer
				if ((nextLineInMinChunk = curMinReader.readLine()) == null){ // current chunk has been read
					chunkToLineMap.remove(curMinReader);
					curMinReader.close();
				} else {
					chunkToLineMap.put(curMinReader, nextLineInMinChunk);
				}
				k++;
				if (k % NUM_OF_TUPLES_PER_BLOCK == 0 || chunkToLineMap.isEmpty()){
					writeTuplesFromList(bw, output);
					output.clear();
				}
			}
		}
	}

	static private void deleteTmpFiles(String tmp){
		for(File file: Objects.requireNonNull(new File(tmp).listFiles()))
			if (!file.isDirectory() && file.getName().split("\\d")[0].equals(TMP_FILE_NAME))
				file.delete();
	}

	@Override
	public void sort(String in, String out, String tmpPath) {
		long numOfBlocks = getNumOfBlocks(in);
		try{
			sortStep1(in, numOfBlocks, tmpPath);
			sortStep2(in, out, tmpPath, numOfBlocks);
		} catch (IOException e) {
			e.printStackTrace();
		}
		deleteTmpFiles(tmpPath);
	}

	static private int compareTuples(String t1, String t2){
		return t1.split("\\s")[0].compareTo(t2.split("\\s")[0]);
	}

	@Override
	protected void join(String in1, String in2, String out, String tmpPath) {
		try (BufferedReader br1 = new BufferedReader(new FileReader(in1), (int) SIZE_OF_BLOCK_IN_BYTES);
			 BufferedReader br2 = new BufferedReader(new FileReader(in2), (int) SIZE_OF_BLOCK_IN_BYTES);
			 BufferedWriter bw = new BufferedWriter(new FileWriter(out), (int) SIZE_OF_BLOCK_IN_BYTES)){
			String tuple, curRelation, firstRelevantRelation;
			List<String> output = new ArrayList<>();
			tuple = br1.readLine();
			curRelation = br2.readLine();
			firstRelevantRelation = curRelation;
			while(tuple != null && curRelation != null){
				while(compareTuples(tuple, firstRelevantRelation) < 0){
					if ((tuple = br1.readLine()) == null){
						writeTuplesFromList(bw, output);
						return;
					}
				}

				while(compareTuples(tuple, firstRelevantRelation) > 0){
					if ((firstRelevantRelation = br2.readLine()) == null){
						writeTuplesFromList(bw, output);
						return;
					}
				}

				while(compareTuples(tuple, firstRelevantRelation) == 0){
					curRelation = firstRelevantRelation;
					while(compareTuples(curRelation, tuple) == 0){
						output.add(tuple + " " + curRelation.split("\\s")[1] + " " + curRelation.split("\\s")[2]);
						if(output.size() == NUM_OF_TUPLES_PER_BLOCK){
							writeTuplesFromList(bw, output);
							output.clear();
						}
						if ((curRelation = br2.readLine()) == null){
							writeTuplesFromList(bw, output);
							return;
						}
					}
					if((tuple = br1.readLine()) == null){
						writeTuplesFromList(bw, output);
						return;
					}
				}
				firstRelevantRelation = curRelation;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void select(String in, String out, String substrSelect, String tmpPath) {
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(out)))
		{
			Files
			.lines(Paths.get(in))
			.filter(line -> line.split("\\s")[0].contains(substrSelect))
			.forEach(line -> {
				try {
					bw.write(line);
					bw.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void joinAndSelectEfficiently(String in1, String in2, String out, String substrSelect,
										 String tmpPath) {
		String outFileName = new File(out).getName();
		String tmpFileName1 = outFileName.substring(0, outFileName.lastIndexOf('.'))
				+ "_filtered1" + outFileName.substring(outFileName.lastIndexOf('.'));
		String tmpOut1 = Paths.get(tmpPath, tmpFileName1).toString();
		String tmpFileName2 = outFileName.substring(0, outFileName.lastIndexOf('.'))
				+ "_filtered2" + outFileName.substring(outFileName.lastIndexOf('.'));
		String tmpOut2 = Paths.get(tmpPath, tmpFileName2).toString();

		this.select(in1, tmpOut1, substrSelect, tmpPath);
		this.select(in2, tmpOut2, substrSelect, tmpPath);
		this.sortAndJoin(tmpOut1, tmpOut2, out, tmpPath);

		try {
			Files.deleteIfExists(Paths.get(tmpPath, tmpFileName1));
			Files.deleteIfExists(Paths.get(tmpPath, tmpFileName2));
			} catch (IOException e) {
			e.printStackTrace();
		}
	}
}