package us.stangl.katabankocr;

/**
 * Object which tries to parse series of 4 consecutive
 * lines of "OCR" codes, which look like 7-segment LED
 * type characters made of underscores and vertical bars:
 *     _  _     _  _  _  _  _
 *   | _| _||_||_ |_   ||_||_|
 *   ||_  _|  | _||_|  ||_| _|
 *
 * File is partitioned into blocks of 4 lines of 27 characters.
 * First 3 of the 4 contain 9 digits of these OCR characters,
 * and 4th line is blank.
 * 
 * Checksum calculation
 * account number:  3  4  5  8  8  2  8  6  5
 * position names:  d9 d8 d7 d6 d5 d4 d3 d2 d1
 *
 * checksum calculation:
 * (d1+2*d2+3*d3 +..+9*d9) mod 11 = 0
 * 
 * This simple OCR block parser does simple error checking
 * on the input and returns digit string with illegible digits
 * replaced with ? and an optional ILL or ERR to indicate illegible
 * digits or bad checksum.
 * 
 * @author Alex Stangl
 */
public class SimpleOcrBlockParser extends BaseOcrBlockParser implements OcrBlockParser<String> {
	
	@Override
	public String parse(OcrBlock block) throws OcrParseException {
		String rawDigitsString = parseRawDigitsString(block);
		boolean allLegible = rawDigitsString.indexOf('?') == -1;
		boolean checksumGood = allLegible && checksumGood(rawDigitsString);
		StringBuilder retval = new StringBuilder(rawDigitsString);
		if (! allLegible)
			retval.append(ILLEGIBLE_SUFFIX);
		else if (! checksumGood)
			retval.append(ERROR_SUFFIX);
		return retval.toString();
	}
}
