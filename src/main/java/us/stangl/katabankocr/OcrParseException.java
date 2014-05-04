package us.stangl.katabankocr;

/**
 * Exception thrown when error occurs during OCR parsing.
 * @author Alex Stangl
 */
public class OcrParseException extends Exception {
	private static final long serialVersionUID = 1L;

	public OcrParseException(String message) {
		super(message);
	}
	
	public OcrParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
