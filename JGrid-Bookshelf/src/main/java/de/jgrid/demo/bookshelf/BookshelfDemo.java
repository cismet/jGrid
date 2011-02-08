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

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import de.jgrid.JGrid;
import de.jgrid.demo.util.CoolProgressBarUI;

public class BookshelfDemo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookshelfDemo() {
		setTitle("BookshelfDemo");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		final JFrame loadFrame = new JFrame("Loading Demo");
		loadFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JProgressBar bar = new JProgressBar();
		bar.setForeground(Color.ORANGE);
		bar.setUI(new CoolProgressBarUI());
		loadFrame.getContentPane().setLayout(
				new FlowLayout(SwingUtilities.CENTER, 20, 20));
		loadFrame.getContentPane().add(bar);
		loadFrame.setResizable(false);
		loadFrame.pack();
		loadFrame.setLocationRelativeTo(null);
		loadFrame.setBackground(Color.DARK_GRAY);
		loadFrame.setVisible(true);
		
		final DefaultListModel model = new DefaultListModel();
		
		SwingWorker<Void, Book> worker = new SwingWorker<Void, Book>() {

			@Override
			protected Void doInBackground() throws Exception {
				int count = 18;
				int i = 0;
				setProgress((100 / count) * i++);
				publish(new Book("Unseen Academicals", "0552153370"));
				setProgress((100 / count) * i++);
				publish(new Book("Thief of time", "0552148407"));
				setProgress((100 / count) * i++);
				publish(new Book("The truth", "0552147680"));
				setProgress((100 / count) * i++);
				publish(new Book("The fifth elephant", "0552146161"));
				setProgress((100 / count) * i++);
				publish(new Book("Carpe Jugulum", "0552146153"));
				setProgress((100 / count) * i++);
				publish(new Book("Jingo", "055214598X"));
				setProgress((100 / count) * i++);
				publish(new Book("Feet of Clay", "0552142379"));
				setProgress((100 / count) * i++);
				publish(new Book("-", "0552142360"));
				setProgress((100 / count) * i++);
				publish(new Book("-", "0552138916"));
				setProgress((100 / count) * i++);
				publish(new Book("-", "0552146145"));
				setProgress((100 / count) * i++);
				publish(new Book("-", "0552140295"));
				setProgress((100 / count) * i++);
				publish(new Book("-", "0552134651"));
				setProgress((100 / count) * i++);
				publish(new Book("-", "0552134643"));
				setProgress((100 / count) * i++);
				publish(new Book("-", "0552134635"));
				setProgress((100 / count) * i++);
				publish(new Book("-", "1857989546"));
				setProgress((100 / count) * i++);
				publish(new Book("-", "0552134627"));
				setProgress((100 / count) * i++);
				publish(new Book("-", "1423101499"));
				setProgress((100 / count) * i++);
				publish(new Book("-", "0061477931"));
				return null;
			}

			@Override
			protected void process(List<Book> chunks) {
				bar.setValue(getProgress());
				for (Iterator<Book> iterator = chunks.iterator(); iterator
						.hasNext();) {
					model.addElement(iterator.next());
				}
			}

			@Override
			protected void done() {
				loadFrame.setVisible(false);
				setVisible(true);
			}
		};
		worker.execute();
		
		JGrid grid = new JGrid(model);
		grid.setDefaultCellRenderer(new OpenLibraryGridRenderer());
		grid.setUI(new BookshelfUI());
		JScrollPane scrollPane = new JScrollPane(grid);
		scrollPane.setBorder(null);
		getContentPane().add(scrollPane);
		setSize(800, 600);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		new BookshelfDemo();
	}
}
