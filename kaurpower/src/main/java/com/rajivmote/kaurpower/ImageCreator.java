package com.rajivmote.kaurpower;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ImageCreator {
	private static final Logger LOG = Logger.getLogger(ImageCreator.class.getName());
	private static final String IMAGE_FILENAME_TEMPLATE = "drawing-%d.jpg";
	
	private Random random;
	private int maxImageTemplates;
	
	public ImageCreator(int maxImageTemplates) {
		random = new Random();
		this.maxImageTemplates = maxImageTemplates;
	}
	
	public ImageCreator() {
		this(1);
	}
	
	protected InputStream chooseImageBackground() throws IOException {
		Resource imageResource = 
				new ClassPathResource(String.format(IMAGE_FILENAME_TEMPLATE, 
						random.nextInt(maxImageTemplates) + 1));
		LOG.info("Using resource: " + imageResource.getFilename());
		return imageResource.getInputStream();
	}
	
	public BufferedImage createImage(String[] poemLines) throws IOException {
		BufferedImage image = ImageIO.read(chooseImageBackground());
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
