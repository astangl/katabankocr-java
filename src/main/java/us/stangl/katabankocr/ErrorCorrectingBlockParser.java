package us.stangl.katabankocr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Error-correcting OCR block parser that does basic error correction.
 * If an input can be unambiguously corrected with a single character
 * change, it will be done automatically. If input has errors, but there
 * is more than one corrected input candidates that passes checksum validation,
 * then status AMB is returned.
 * 
 * Use Story 4 Case analysis (assuming input is basically OK, i.e., well-formed)
 * Scenario                                                          Status
 * =================================                                 ============
 * All digits legible, checksum good                                 blank (good)
 * All digits legible, checksum bad, single good correction          blank (automatically corrected)
 * All digits legible, checksum bad, no good correction              ERR
 * All digits legible, multiple possible valid corrections           AMB
 * All-but-1 digits legible, able to correct to single valid value   blank (automatically corrected)
 * All-but-1 digits legible, not able to correct to any valid value  ILL
 * All-but-1 digits legible, multiple possible valid corrections     AMB
 * Multiple digits illegible                                         ILL
 * 
 * @author Alex Stangl
 */
public class ErrorCorrectingBlockParser extends BaseOcrBlockParser implements OcrBlockParser<String> {
	/**
	 * Parse block of 4 lines of 27 characters of OCR data into string
	 * format described in spec (e.g., digits followed by optional status, etc.)
	 * @param input
	 * @return String containing result of parse, assuming input reasonably well-formed
	 * @throws OcrParseException if input null or not 4 lines, or too short or 3rd line not blank
	 */
	@Override
	public String parse(OcrBlock block) throws OcrParseException {
		String rawDigitsString = parseRawDigitsString(block);
		int nbrOfIllegibles = nbrOfIllegibleCharacters(rawDigitsString);
		boolean checksumGood = nbrOfIllegibles == 0 && checksumGood(rawDigitsString);
		if (checksumGood)
			return rawDigitsString.toString();
		if (nbrOfIllegibles > 1)
			return rawDigitsString + ILLEGIBLE_SUFFIX;
		List<String> replacements = getReplacementCandidates(rawDigitsString, block);
		int nbrReplacements = replacements.size();
		if (nbrReplacements == 1)
			return replacements.get(0);
		if (nbrReplacements > 1) {
			StringBuilder builder = new StringBuilder(rawDigitsString);
			builder.append(" AMB [");
			for (int i = 0; i < nbrReplacements; ++i) {
				if (i > 0)
					builder.append(", ");
				builder.append(replacements.get(i));
			}
			return builder.append(']').toString();
		}
		String suffix = nbrOfIllegibles == 0 ? ERROR_SUFFIX : ILLEGIBLE_SUFFIX;
		return rawDigitsString + suffix;
	}
	
	private int nbrOfIllegibleCharacters(String rawDigitsString) {
		int retval = 0;
		for (char c : rawDigitsString.toCharArray()) {
			if (c == '?')
				++retval;
		}
		return retval;
	}
	
	private List<String> getReplacementCandidates(String rawDigitsString, OcrBlock block) {
		List<String> retval = new ArrayList<String>();

		int nbrOfIllegibles = nbrOfIllegibleCharacters(rawDigitsString);
		if (nbrOfIllegibles == 0) {
			// try alternates in each character position
			for (int i = 0; i < 9; ++i) {
				addCandidatesAtIndex(rawDigitsString, block, i, retval);
			}
		} else if (nbrOfIllegibles == 1) {
			addCandidatesAtIndex(rawDigitsString, block, rawDigitsString.indexOf('?'), retval);
		} else {
			throw new RuntimeException("Programming error - getReplacementCandidates called for " + rawDigitsString + " with more than 1 illegible character.");
		}
		return retval;
	}
	
	private void addCandidatesAtIndex(String rawDigitsString, OcrBlock block, int index, Collection<String> candidates) {
		StringBuilder builder = new StringBuilder(rawDigitsString);
		for (int digit = 0; digit < 10; ++digit) {
			if (differencesFromDigit(block.paddedLines, index * 3, digit) == 1) {
				char saveChar = builder.charAt(index);
				builder.setCharAt(index, (char)('0' + digit));
				String candidate = builder.toString();
				if (checksumGood(candidate))
					candidates.add(candidate);
				builder.setCharAt(index, saveChar);
			}
		}
	}
}

