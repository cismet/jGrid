/*
 * Created on Jan 22, 2011
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2011 Hendrik Ebbers
 */
package de.jgrid.demo.bookshelf;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Book {

	private String titel;
	
	private BufferedImage cover;
	
	public Book(String titel, String coverUrl) {
		this.titel = titel;
		try {
//			cover = ImageIO.read(new URL("http://covers.openlibrary.org/b/isbn/" + isbn + "-L.jpg"));
			cover = ImageIO.read(new URL(coverUrl));
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
