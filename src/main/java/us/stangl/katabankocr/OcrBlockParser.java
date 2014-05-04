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
 * @author Alex Stangl
 */
public interface OcrBlockParser<T> {
	/**
	 * Parse OCR input block, returning result if able to parse,
	 * or throwing OcrParseException if problems occur (other than "expected"
	 * errors, like illegible digits and bad checksum.)
	 * 
	 * @param block input block to parse
	 * @return parse results
	 * @throws OcrParseException if problems prevent successful parsing
	 */
	T parse(OcrBlock block) throws OcrParseException;
}
