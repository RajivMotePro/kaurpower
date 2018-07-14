package com.rajivmote.kaurpower;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageCreatorHarness {

	public static void main(String[] args) {
		if (args == null || args.length < 2) {
			System.out.println("Usage: <image filename> <string to paint>");
		} else {
			ImageCreator imageCreator = new ImageCreator();
			List<String> poemLines = new ArrayList<String>();
			for (int i = 1; i < args.length; i++) {
				poemLines.add(args[i]);
			}
			try {
				BufferedImage image = imageCreator.createImage((String[]) poemLines.toArray(new String[0]));
				ImageIO.write(image, "jpg", new File("C:/Users/Rajiv/Pictures/image.jpg"));
				System.out.println("Wrote: C:/Users/Rajiv/Pictures/image.jpg");
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}

}
