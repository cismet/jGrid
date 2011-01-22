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

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import de.loganmobile.grid.JGrid;

public class BookshelfDemo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookshelfDemo() {
		setTitle("BookshelfDemo");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		final List<Book> bookList = new ArrayList<Book>();
		bookList.add(new Book("Unseen Academicals", "0552153370"));
		bookList.add(new Book("Thief of time", "0552148407"));
		bookList.add(new Book("The truth", "0552147680"));
		bookList.add(new Book("The fifth elephant", "0552146161"));
		bookList.add(new Book("Carpe Jugulum", "0552146153"));
		bookList.add(new Book("Jingo", "055214598X"));
		bookList.add(new Book("Feet of Clay", "0552142379"));
		bookList.add(new Book("-", "0552142360"));
		bookList.add(new Book("-", "0552138916"));
		bookList.add(new Book("-", "0552146145"));
		bookList.add(new Book("-", "0552140295"));
		bookList.add(new Book("-", "0552134651"));
		bookList.add(new Book("-", "0552134643"));
		bookList.add(new Book("-", "0552134635"));
		bookList.add(new Book("-", "1857989546"));
		bookList.add(new Book("-", "0552134627"));
		bookList.add(new Book("-", "1423101499"));
		bookList.add(new Book("-", "0061477931"));
		
		bookList.add(new Book("Unseen Academicals", "0552153370"));
		bookList.add(new Book("Thief of time", "0552148407"));
		bookList.add(new Book("The truth", "0552147680"));
		bookList.add(new Book("The fifth elephant", "0552146161"));
		bookList.add(new Book("Carpe Jugulum", "0552146153"));
		bookList.add(new Book("Jingo", "055214598X"));
		bookList.add(new Book("Feet of Clay", "0552142379"));
		bookList.add(new Book("-", "0552142360"));
		bookList.add(new Book("-", "0552138916"));
		bookList.add(new Book("-", "0552146145"));
		bookList.add(new Book("-", "0552140295"));
		bookList.add(new Book("-", "0552134651"));
		bookList.add(new Book("-", "0552134643"));
		bookList.add(new Book("-", "0552134635"));
		bookList.add(new Book("-", "1857989546"));
		bookList.add(new Book("-", "0552134627"));
		bookList.add(new Book("-", "1423101499"));
		bookList.add(new Book("-", "0061477931"));
		
		bookList.add(new Book("Unseen Academicals", "0552153370"));
		bookList.add(new Book("Thief of time", "0552148407"));
		bookList.add(new Book("The truth", "0552147680"));
		bookList.add(new Book("The fifth elephant", "0552146161"));
		bookList.add(new Book("Carpe Jugulum", "0552146153"));
		bookList.add(new Book("Jingo", "055214598X"));
		bookList.add(new Book("Feet of Clay", "0552142379"));
		bookList.add(new Book("-", "0552142360"));
		bookList.add(new Book("-", "0552138916"));
		bookList.add(new Book("-", "0552146145"));
		bookList.add(new Book("-", "0552140295"));
		bookList.add(new Book("-", "0552134651"));
		bookList.add(new Book("-", "0552134643"));
		bookList.add(new Book("-", "0552134635"));
		bookList.add(new Book("-", "1857989546"));
		bookList.add(new Book("-", "0552134627"));
		bookList.add(new Book("-", "1423101499"));
		bookList.add(new Book("-", "0061477931"));
		
		JGrid grid = new JGrid(new ListModel() {

			@Override
			public void removeListDataListener(ListDataListener l) {
			}

			@Override
			public int getSize() {
				return bookList.size();
			}

			@Override
			public Object getElementAt(int index) {
				return bookList.get(index);
			}

			@Override
			public void addListDataListener(ListDataListener l) {
			}
		});
		grid.setDefaultCellRenderer(new OpenLibraryGridRenderer());
		grid.setUI(new BookshelfUI());
		getContentPane().add(new JScrollPane(grid));
		setSize(800, 600);
	}
	
	public static void main(String[] args) {
		new BookshelfDemo().setVisible(true);
	}
}
