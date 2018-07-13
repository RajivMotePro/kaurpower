package com.rajivmote.kaurpower;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageCreator {
	public InputStream chooseImageBackground() {
		// TODO Randomly choose from available background images
		return null;
	}
	
	public BufferedImage createImage(InputStream rawImage, String[] poemLines) throws IOException {
		BufferedImage image = ImageIO.read(rawImage);
		Graphics graphics = image.getGraphics();
	    graphics.setColor(Color.BLACK);
	    graphics.setFont(new Font("TimesRoman", Font.ITALIC, 60));
	    int x = 100;
	    int y = 250;
	    for (String line : poemLines) {
	    	graphics.drawString(line, x, y);
	    	y = y + 100;
	    }
		return image;
	}

}
