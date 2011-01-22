package de.loganmobile.grid.bookshelf;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Book {

	private String titel;
	
	private BufferedImage cover;
	
	public Book(String titel, String isbn) {
		this.titel = titel;
		try {
			cover = ImageIO.read(new URL("http://covers.openlibrary.org/b/isbn/" + isbn + "-L.jpg"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getTitel() {
		return titel;
	}
	
	public BufferedImage getCover() {
		return cover;
	}
}
