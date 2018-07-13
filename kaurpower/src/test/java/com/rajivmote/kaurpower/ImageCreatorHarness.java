package com.rajivmote.kaurpower;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

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
			InputStream imageStream = null;
			Resource imageResource = new ClassPathResource("drawing-1.jpg");
			try {
				imageStream = imageResource.getInputStream();
				BufferedImage image = imageCreator.createImage(imageStream, (String[]) poemLines.toArray(new String[0]));
				ImageIO.write(image, "jpg", new File("C:/Users/Rajiv/Pictures/image.jpg"));
				System.out.println("Wrote: C:/Users/Rajiv/Pictures/image.jpg");
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}

}
