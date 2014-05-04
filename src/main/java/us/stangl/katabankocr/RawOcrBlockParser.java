package us.stangl.katabankocr;

/**
 * Object performing most rudimentary parsing of OCR data, showing
 * illegible digits as ? and otherwise performing no further error return/correction.
 * This implements User Story 1
 * 
 * @author Alex Stangl
 */
public class RawOcrBlockParser extends BaseOcrBlockParser implements OcrBlockParser<String> {

	@Override
	public String parse(OcrBlock block) throws OcrParseException {
		return parseRawDigitsString(block);
	}
}
