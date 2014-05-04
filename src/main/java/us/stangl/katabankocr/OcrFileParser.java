package us.stangl.katabankocr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Object that applies a block parser across an entire input file,
 * returning list of results.
 * @author Alex Stangl
 */
public class OcrFileParser {
	/**
	 * Parse OCR data from input file
	 * @param inputFile file to parse
	 * @return parsed data
	 * @throws OcrParseException if error occurs during parsing
	 */
	public <T> List<T> parseFile(File inputFile, OcrBlockParser<T> parser) throws OcrParseException {
		BufferedReader reader = null;
		List<T> responseList = new ArrayList<T>();
		boolean normalTermination = false;
		try {
			String[] stringBlock = new String[4];
			reader = new BufferedReader(new FileReader(inputFile));
			for (int i = 0; ; i = (i + 1) % 4) {
				String line = reader.readLine();
				if (line == null) {
					break;
				}
				stringBlock[i] = line;
				if (i == 3) {
					responseList.add(parser.parse(OcrBlock.newInstance(stringBlock)));
				}
			}
			normalTermination = true;
		} catch (FileNotFoundException e) {
			throw new OcrParseException("FileNotFoundException unexpectedly caught trying to read " + inputFile, e);
		} catch (IOException e) {
			throw new OcrParseException("IOException unexpectedly caught trying to read " + inputFile, e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					if (normalTermination) {
						throw new OcrParseException("IOException unexpectedly caught trying to close " + inputFile, e);
					}
					// Not throwing OcrParseException here because we are terminating abnormally and don't want to lose the primary exception
					System.err.println("Suppressing IOException caught during abnormal termination of reading " + inputFile + ": " + e); 
				}
			}
		}
		return responseList;
	}
}
