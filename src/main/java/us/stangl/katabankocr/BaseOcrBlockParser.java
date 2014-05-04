package us.stangl.katabankocr;

/**
 * Abstract base class for OCR block parsers.
 * Contains common data/functionality.
 * @author Alex Stangl
 */
public abstract class BaseOcrBlockParser {
	protected static final String[] DIGITS_LINES = {
		" _     _  _     _  _  _  _  _ ",
		"| |  | _| _||_||_ |_   ||_||_|",
		"|_|  ||_  _|  | _||_|  ||_| _|"
	};

	protected static final String ILLEGIBLE_SUFFIX = " ILL";
	protected static final String ERROR_SUFFIX = " ERR";
	
	protected String parseRawDigitsString(OcrBlock block) {
		StringBuilder rawDigitBuilder = new StringBuilder(9);
		for (int startIndex = 0; startIndex < 27; startIndex += 3) {
			int digit = parseDigit(block.paddedLines, startIndex);
			rawDigitBuilder.append(digit == -1 ? "?" : Integer.toString(digit));
		}
		return rawDigitBuilder.toString();
	}
	
	// return raw digit parsed from input strings starting at specified index, or -1 if none match
	protected int parseDigit(String[] input, int startIndex) {
		for (int retval = 0; retval <= 9; ++retval) {
			if (isDigitMatch(input, startIndex, retval)) {
				return retval;
			}
		}
		return -1;
	}
	
	protected boolean isDigitMatch(String[] input, int startIndex, int digit) {
		return differencesFromDigit(input, startIndex, digit) == 0;
	}
	
	protected int differencesFromDigit(String[] input, int startIndex, int digit) {
		int retval = 0;
		for (int row = 0; row < 3; ++row) {
			for (int index = 0; index < 3; ++index) {
				if (input[row].charAt(startIndex + index) 
						!= DIGITS_LINES[row].charAt(3 * digit + index))
				{
					++retval;
				}
			}
		}
		return retval;
	}
	
	/**
	 * @param digitString digit string to check for validity
	 * @return whether specified digit string has correct length, contents, and checksum
	 */
	protected boolean checksumGood(String digitString) {
		if (digitString == null || digitString.length() != 9) {
			return false;
		}
		int sum = 0;
		for (int i = 0; i < 9; ++i) {
			char c = digitString.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
			int value = c - '0';
			sum += value * (9 - i);
		}
		return sum % 11 == 0;
	}
}
