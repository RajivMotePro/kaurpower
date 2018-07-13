package com.rajivmote.kaurpower;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

public class PoemWriter {
	private static final Logger LOG = Logger.getLogger(PoemWriter.class.getName());
	static final int LINE_MAX_LEN = 50;
	static final String EMPTY = "Sometimes silence makes the loudest sound.";
	static final String ATTRIBUTION = "- (not) rupi kaur";

	public String[] writePoem(String text) {
		LOG.fine("Generating poem from: [" + text + "]");
		if (!StringUtils.isEmpty(StringUtils.trim(text)))
		{
			List<String> lines = new ArrayList<String>();
			text = StringUtils.trim(text);
			if (text.length() > LINE_MAX_LEN) {
				splitLines(lines, text);
			} else {
				lines.add(text);
			}
			lines.add(ATTRIBUTION);
			return (String[]) lines.toArray(new String[0]);
		}
		return new String[] { EMPTY, ATTRIBUTION };
	}
	
	void splitLines(List<String> lines, String text) {
		if (StringUtils.isEmpty(StringUtils.trim(text))) {
			return;
		} else if (text.length() <= LINE_MAX_LEN) {
			lines.add(StringUtils.trim(text));
		} else {
			int i = LINE_MAX_LEN;
			while (i >= 0 && !StringUtils.isWhitespace((text.substring(i, i)))) {
				i--;
			}
			if (i >= 0) {
				lines.add(StringUtils.trim(text.substring(0, i)));
				splitLines(lines, text.substring(i));
			} else {
				lines.add(StringUtils.trim(text.substring(0, LINE_MAX_LEN)));
				splitLines(lines, text.substring(LINE_MAX_LEN));
			}
		}
	}
	
	
}
