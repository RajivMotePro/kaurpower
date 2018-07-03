package com.rajivmote.kaurpower;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageCreator {
	
	public BufferedImage createImage(InputStream rawImage, String[] poemLines) throws IOException {
		BufferedImage image = ImageIO.read(rawImage);
		return image;
	}

}
