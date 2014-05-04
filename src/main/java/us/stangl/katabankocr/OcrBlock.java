package us.stangl.katabankocr;

/**
 * Object which represents a block of 4 input lines for OCR scanning.
 * @author Alex Stangl
 */
public class OcrBlock {
	final String[] lines;
	final String[] paddedLines;

	/**
	 * Factory method to return new OCR block based upon a block of 4 input lines.
	 * @param lines input lines
	 * @throws IllegalArgumentException if input null or of wrong length
	 */
	public static OcrBlock newInstance(String[] lines) {
		return new OcrBlock(lines);
	}
	
	/**
	 * Create new OCR block based upon a block of 4 input lines.
	 * @param lines input lines
	 * @throws IllegalArgumentException if input null or of wrong length
	 */
	private OcrBlock(String[] lines) {
		if (lines == null)
			throw new IllegalArgumentException("Null passed to OcrBlockParser");
		if (lines.length != 4)
			throw new IllegalArgumentException("Array of length " + lines.length + " passed to OcrBlockParser. Expected length 4");
		if (! lines[3].trim().isEmpty())
			throw new IllegalArgumentException("Third line '" + lines[3] + "' not empty.");
		this.lines = lines;
		this.paddedLines = new String[4];
		for (int i = 0; i < 4; ++i) {
			this.paddedLines[i] = rightPadTo(lines[i], 27);
		}
	}
	
	private String rightPadTo(String inputString, int desiredLength) {
		StringBuilder builder = new StringBuilder(desiredLength);
		builder.append(inputString);
		while (builder.length() < desiredLength) {
			builder.append(' ');
		}
		return builder.toString();
	}
}
