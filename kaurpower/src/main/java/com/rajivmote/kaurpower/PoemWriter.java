package com.rajivmote.kaurpower;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

public class PoemWriter {
	private static final Logger LOG = Logger.getLogger(PoemWriter.class.getName());
	static final int LINE_MAX_LEN = 50;
	static final String EMPTY = "Sometimes silence makes the loudest sound.";
	static final String ATTRIBUTION = "    - not rupi kaur";

	public String[] writePoem(String text) {
		text = cleanText(text);
		if (!StringUtils.isEmpty(text))
		{
			LOG.info("Generating poem from: [" + text + "]");
			List<String> lines = new ArrayList<String>();
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
	
	String cleanText(String text) {
		StringBuilder cleanText = new StringBuilder();
		boolean justWroteWhitespace = false;
		if (text != null) {
			for(char ch : text.toCharArray()) {
				if(Character.isWhitespace(ch)) {
					if (!justWroteWhitespace) {
						cleanText.append(' ');
						justWroteWhitespace = true;
					}
				} else {
					justWroteWhitespace = false;
					cleanText.append(ch);
				}
			}
		}
		return StringUtils.trim(cleanText.toString());
	}
	
	void splitLines(List<String> lines, String text) {
		if (StringUtils.isEmpty(text)) {
			return;
		} else if (text.length() <= LINE_MAX_LEN) {
			lines.add(StringUtils.trim(text));
		} else {
			int i = LINE_MAX_LEN;
			while (i >= 0 && !Character.isWhitespace(text.charAt(i))) {
				i--;
			}
			if (i >= 0) {
				lines.add(StringUtils.trim(text.substring(0, i)));
				splitLines(lines, StringUtils.trim(text.substring(i)));
			} else {
				lines.add(StringUtils.trim(text.substring(0, LINE_MAX_LEN)));
				splitLines(lines, StringUtils.trim(text.substring(LINE_MAX_LEN)));
			}
		}
	}
	
	
}
