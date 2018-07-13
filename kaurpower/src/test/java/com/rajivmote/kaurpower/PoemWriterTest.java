package com.rajivmote.kaurpower;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PoemWriterTest {
	private PoemWriter poemWriter;

	@Before
	public void setUp() throws Exception {
		poemWriter = new PoemWriter();
	}

	@After
	public void tearDown() throws Exception {
		poemWriter = null;
	}

	@Test
	public void testWritePoemNull() {
		String[] result = poemWriter.writePoem(null);
		assertNotNull(result);
		assertEquals(2, result.length);
		assertEquals(PoemWriter.EMPTY, result[0]);
		assertEquals(PoemWriter.ATTRIBUTION, result[1]);
	}

	@Test
	public void testWritePoemEmpty() {
		String[] result = poemWriter.writePoem("");
		assertNotNull(result);
		assertEquals(2, result.length);
		assertEquals(PoemWriter.EMPTY, result[0]);
		assertEquals(PoemWriter.ATTRIBUTION, result[1]);
	}

	@Test
	public void testWritePoemBlank() {
		String[] result = poemWriter.writePoem("    ");
		assertNotNull(result);
		assertEquals(2, result.length);
		assertEquals(PoemWriter.EMPTY, result[0]);
		assertEquals(PoemWriter.ATTRIBUTION, result[1]);
	}
	
	@Test
	public void testWritePoemSimple() {
		final String TEST_LINE = "This is a test.";
		String[] result = poemWriter.writePoem(TEST_LINE);
		assertNotNull(result);
		assertEquals(2, result.length);
		assertEquals(TEST_LINE, result[0]);
		assertEquals(PoemWriter.ATTRIBUTION, result[1]);
	}
	
	@Test
	public void testWritePoemUnderThreshold() {
		final String TEST_LINE = "01234567890123456789012345678901234567890123456789";
		String[] result = poemWriter.writePoem(TEST_LINE);
		assertNotNull(result);
		assertEquals(2, result.length);
		assertEquals(TEST_LINE, result[0]);
		assertEquals(PoemWriter.ATTRIBUTION, result[1]);
	}
	
	@Test
	public void testWritePoemOverThreshold() {
		final String TEST_LINE = "012345678901234567890123456789012345678901234567890123456789";
		String[] result = poemWriter.writePoem(TEST_LINE);
		assertNotNull(result);
		assertEquals(3, result.length);
		assertEquals(TEST_LINE.substring(0, PoemWriter.LINE_MAX_LEN), result[0]);
		assertEquals(TEST_LINE.substring(PoemWriter.LINE_MAX_LEN), result[1]);
		assertEquals(PoemWriter.ATTRIBUTION, result[2]);
	}
	
	@Test
	public void testSplitLinesNoSpace() {
		final String CHARS_10 = "0123456789";
		final String CHARS_50 = CHARS_10 + CHARS_10 + CHARS_10 + CHARS_10 + CHARS_10;
		final String CHARS_70 = CHARS_50 + CHARS_10 + CHARS_10;
		
		List<String> lines = new ArrayList<String>();
		poemWriter.splitLines(lines, CHARS_50);
		assertEquals(1, lines.size());
		assertEquals(CHARS_50, lines.get(0));
		
		lines.clear();
		poemWriter.splitLines(lines, CHARS_70);
		assertEquals(2, lines.size());
		assertEquals(CHARS_50, lines.get(0));
		assertEquals(CHARS_10 + CHARS_10, lines.get(1));
	}
	
	@Test
	public void testSplitLinesWithSpace() {
		final String CHARS_11 = "0123456 789";
		final String CHARS_55 = CHARS_11 + CHARS_11 + CHARS_11 + CHARS_11 + CHARS_11;
		final String CHARS_77 = CHARS_55 + CHARS_11 + CHARS_11;
		
		List<String> lines = new ArrayList<String>();
		poemWriter.splitLines(lines, CHARS_55);
		assertEquals(2, lines.size());
		assertEquals(50, lines.get(0).length());
		assertEquals(5, lines.get(1).length());
		
		lines.clear();
		poemWriter.splitLines(lines, CHARS_77);
		assertEquals(2, lines.size());
		assertEquals(50, lines.get(0).length());
		assertEquals(27, lines.get(1).length());
	}
	
	@Test
	public void testSplitLinesStripInternalNewline() {
		final String CHARS = "01234\n56789";
		List<String> lines = new ArrayList<String>();
		poemWriter.splitLines(lines, CHARS);
		assertEquals(1, lines.size());
		assertFalse(lines.get(0).contains("\n"));
		assertTrue(lines.get(0).contains(" "));
	}
	
}
