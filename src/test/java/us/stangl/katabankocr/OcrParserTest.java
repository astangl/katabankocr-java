package us.stangl.katabankocr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class OcrParserTest {
	// parser for testing user story 1
	private OcrBlockParser<String> rawParser = new RawOcrBlockParser();
	
	// parser for testing user story 3
	private OcrBlockParser<String> simpleParser = new SimpleOcrBlockParser();
	
	// parser for testing user story 4
	private OcrBlockParser<String> errorCorrectingParser = new ErrorCorrectingBlockParser();
 
	@Test
	public void testRaw000000000Case() throws OcrParseException  {
		String input[] = {
				" _  _  _  _  _  _  _  _  _ ",
				"| || || || || || || || || |",
				"|_||_||_||_||_||_||_||_||_|",
				"                           "
		};

		testSimpleRawCase(input, "000000000");
	}
	
	@Test
	public void testRaw111111111Case() throws OcrParseException  {
		String input[] = {
				"                           ",
				"  |  |  |  |  |  |  |  |  |",
				"  |  |  |  |  |  |  |  |  |",
				"                           "
		};
		testSimpleRawCase(input, "111111111");
	}
	
	@Test
	public void testRaw222222222Case() throws OcrParseException  {
		String input[] = {
				" _  _  _  _  _  _  _  _  _ ",
				" _| _| _| _| _| _| _| _| _|",
				"|_ |_ |_ |_ |_ |_ |_ |_ |_ ",
				"                           "
		};
		testSimpleRawCase(input, "222222222");
	}
	
	@Test
	public void testRaw333333333Case() throws OcrParseException  {
		String input[] = {
				" _  _  _  _  _  _  _  _  _ ",
				" _| _| _| _| _| _| _| _| _|",
				" _| _| _| _| _| _| _| _| _|",
				"                           "
		};
		testSimpleRawCase(input, "333333333");
	}
	
	@Test
	public void testRaw444444444Case() throws OcrParseException  {
		String input[] = {
				"                           ",
				"|_||_||_||_||_||_||_||_||_|",
				"  |  |  |  |  |  |  |  |  |",
				"                           "
		};
		testSimpleRawCase(input, "444444444");
	}
	
	@Test
	public void testRaw555555555Case() throws OcrParseException  {
		String input[] = {
				" _  _  _  _  _  _  _  _  _ ",
				"|_ |_ |_ |_ |_ |_ |_ |_ |_ ",
				" _| _| _| _| _| _| _| _| _|",
				"                           "
		};
		testSimpleRawCase(input, "555555555");
	}
	
	@Test
	public void testRaw666666666Case() throws OcrParseException  {
		String input[] = {
				" _  _  _  _  _  _  _  _  _ ",
				"|_ |_ |_ |_ |_ |_ |_ |_ |_ ",
				"|_||_||_||_||_||_||_||_||_|",
				"                           "
		};
		testSimpleRawCase(input, "666666666");
	}
	
	@Test
	public void testRaw777777777Case() throws OcrParseException  {
		String input[] = {
				" _  _  _  _  _  _  _  _  _ ",
				"  |  |  |  |  |  |  |  |  |",
				"  |  |  |  |  |  |  |  |  |",
				"                           "
		};
		testSimpleRawCase(input, "777777777");
	}
	
	@Test
	public void testRaw888888888Case() throws OcrParseException  {
		String input[] = {
				" _  _  _  _  _  _  _  _  _ ",
				"|_||_||_||_||_||_||_||_||_|",
				"|_||_||_||_||_||_||_||_||_|",
				"                           "
		};
		testSimpleRawCase(input, "888888888");
	}
	
	@Test
	public void testRaw999999999Case() throws OcrParseException  {
		String input[] = {
				" _  _  _  _  _  _  _  _  _ ",
				"|_||_||_||_||_||_||_||_||_|",
				" _| _| _| _| _| _| _| _| _|",
				"                           "
		};
		testSimpleRawCase(input, "999999999");
	}
	
	@Test
	public void testRaw123456789Case() throws OcrParseException  {
		String input[] = {
				"    _  _     _  _  _  _  _",
				"  | _| _||_||_ |_   ||_||_|",
				"  ||_  _|  | _||_|  ||_| _|",
				"                           "
		};
		testSimpleRawCase(input, "123456789");
	}
	
	@Test
	public void testFileReadGoodAndBadNumbers() throws OcrParseException {
		File inputFile = new File("UserStory2Data.txt");
		List<String> output = new OcrFileParser().parseFile(inputFile, simpleParser);
		assertEquals(6, output.size());
		assertEquals("711111111", output.get(0));
		assertEquals("123456789", output.get(1));
		assertEquals("490867715", output.get(2));
		assertEquals("888888888 ERR", output.get(3));
		assertEquals("490067715 ERR", output.get(4));
		assertEquals("012345678 ERR", output.get(5));
	}
	
	@Test
	public void testString000000051Case() throws OcrParseException {
		String input[] = {
				" _  _  _  _  _  _  _  _    ", 
				"| || || || || || || ||_   |",
				"|_||_||_||_||_||_||_| _|  |",
                "                           " 
		};
		testUserStory3Case(input, "000000051");
	}
	
	@Test
	public void testIllegalStringCase1() throws OcrParseException {
		String input[] = {
				"    _  _  _  _  _  _     _ ",
				"|_||_|| || ||_   |  |  | _ ",
				"  | _||_||_||_|  |  |  | _|",
				"                           "
		};
		testUserStory3Case(input, "49006771? ILL");
	}
	
	@Test
	public void testIllegalStringCase2() throws OcrParseException {
		String input[] = {
				"    _  _     _  _  _  _  _ ",
				"  | _| _||_| _ |_   ||_||_|",
				"  ||_  _|  | _||_|  ||_| _ ",
				"                           "
		};
		testUserStory3Case(input, "1234?678? ILL");
	}
	
	@Test
	public void testCorrectedCase1() throws OcrParseException {
		String input[] = {
				"                           ",
				"  |  |  |  |  |  |  |  |  |",
				"  |  |  |  |  |  |  |  |  |",
				"                           "
		};
		testUserStory4Case(input, "711111111");
	}
	
	@Test
	public void testCorrectedCase2() throws OcrParseException {
		String input[] = {
				" _  _  _  _  _  _  _  _  _ ",
				"  |  |  |  |  |  |  |  |  |",
				"  |  |  |  |  |  |  |  |  |",
				"                           "
		};
		testUserStory4Case(input, "777777177");
	}
	
	@Test
	public void testCorrectedCase3() throws OcrParseException {
		String input[] = {
				" _  _  _  _  _  _  _  _  _ ",
				" _|| || || || || || || || |",
				"|_ |_||_||_||_||_||_||_||_|",
				"                           "
		};
		testUserStory4Case(input, "200800000");
	}
	
	@Test
	public void testCorrectedCase4() throws OcrParseException {
		String input[] = {
				" _  _  _  _  _  _  _  _  _ ",
				" _| _| _| _| _| _| _| _| _|",
				" _| _| _| _| _| _| _| _| _|",
				"                           "
		};
		testUserStory4Case(input, "333393333");
	}
	
	@Test
	public void test888888888Ambiguity() throws OcrParseException  {
		String input[] = {
				" _  _  _  _  _  _  _  _  _ ",
				"|_||_||_||_||_||_||_||_||_|",
				"|_||_||_||_||_||_||_||_||_|",
				"                           "
		};
		testForExpectedAmbiguity(input, "888888888", "888886888", "888888880", "888888988");
	}
	
	@Test
	public void test555555555Ambiguity() throws OcrParseException  {
		String input[] = {
				" _  _  _  _  _  _  _  _  _ ",
				"|_ |_ |_ |_ |_ |_ |_ |_ |_ ",
				" _| _| _| _| _| _| _| _| _|",
				"                           "
		};
		testForExpectedAmbiguity(input, "555555555", "555655555", "559555555");
	}
	
	@Test
	public void test666666666Ambiguity() throws OcrParseException  {
		String input[] = {
				" _  _  _  _  _  _  _  _  _ ",
				"|_ |_ |_ |_ |_ |_ |_ |_ |_ ",
				"|_||_||_||_||_||_||_||_||_|",
				"                           "
		};
		testForExpectedAmbiguity(input, "666666666", "666566666", "686666666");
	}
	
	@Test
	public void test999999999Ambiguity() throws OcrParseException  {
		String input[] = {
				" _  _  _  _  _  _  _  _  _ ",
				"|_||_||_||_||_||_||_||_||_|",
				" _| _| _| _| _| _| _| _| _|",
				"                           "
		};
		testForExpectedAmbiguity(input, "999999999", "899999999", "993999999", "999959999");
	}
	
	@Test
	public void test490067715Ambiguity() throws OcrParseException {
		String input[] = {
				"    _  _  _  _  _  _     _ ",
				"|_||_|| || ||_   |  |  ||_ ",
				"  | _||_||_||_|  |  |  | _|",
				"                           "
		};
		testForExpectedAmbiguity(input, "490067715", "490067115", "490067719", "490867715");
	}
	
	@Test
	public void test123456789Correction() throws OcrParseException  {
		String input[] = {
				"    _  _     _  _  _  _  _",
				" _| _| _||_||_ |_   ||_||_|",
				"  ||_  _|  | _||_|  ||_| _|",
				"                           "
		};
		testUserStory4Case(input, "123456789");
	}
	
	@Test
	public void test000000051Correction() throws OcrParseException {
		String input[] = {
				" _     _  _  _  _  _  _    ", 
				"| || || || || || || ||_   |",
				"|_||_||_||_||_||_||_| _|  |",
                "                           " 
		};
		testUserStory4Case(input, "000000051");
	}
	
	@Test
	public void test490067715Correction() throws OcrParseException {
		String input[] = {
				"    _  _  _  _  _  _     _ ",
				"|_||_|| ||_||_   |  |  | _ ",
				"  | _||_||_||_|  |  |  | _|",
				"                           "
		};
		testUserStory4Case(input, "490867715");
	}
	
	private void testSimpleRawCase(String[] input, String expectedRawValue) throws OcrParseException {
		assertEquals(expectedRawValue, rawParser.parse(OcrBlock.newInstance(input)));
	}
	
	private void testUserStory3Case(String[] input, String expectedStringOutput) throws OcrParseException {
		assertEquals(expectedStringOutput, simpleParser.parse(OcrBlock.newInstance(input)));
	}
	
	private void testUserStory4Case(String[] input, String expectedStringOutput) throws OcrParseException {
		assertEquals(expectedStringOutput, errorCorrectingParser.parse(OcrBlock.newInstance(input)));
	}
	
	private void testForExpectedAmbiguity(String[] input, String expectedUncorrectedOutput, String... replacementCandidates) throws OcrParseException {
		String output = errorCorrectingParser.parse(OcrBlock.newInstance(input));
		String expectedPrefix = expectedUncorrectedOutput + " AMB [";
		String realPrefix = output.substring(0, expectedPrefix.length());
		assertEquals(expectedPrefix, realPrefix);
		assertTrue(output.endsWith("]"));
		String[] actualCandidates = output.substring(expectedPrefix.length(), output.length() - 1).split(", *");
		
		Set<String> actualCandidatesSet = new HashSet<String>(Arrays.asList(actualCandidates));
		Set<String> expectedCandidatesSet = new HashSet<String>(Arrays.asList(replacementCandidates));
		assertEquals(expectedCandidatesSet, actualCandidatesSet);
	}
}
