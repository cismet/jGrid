package de.guigarage.demos.tunes.model;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.SwingWorker;

import com.amazon.webservices.AWSECommerceService;
import com.amazon.webservices.AWSECommerceServicePortType;
import com.amazon.webservices.AwsHandlerResolver;
import com.amazon.webservices.ItemSearch;
import com.amazon.webservices.ItemSearchRequest;
import com.amazon.webservices.ItemSearchResponse;

import de.guigarage.demos.tunes.util.TunesUtilities;

public class TunesAlbum {

	private String interpreter;
	
	private String name;
	
	private BufferedImage cover;
	
	private int rating;

	private List<TunesTrack> tracks;
	
	private PropertyChangeSupport propertyChangeSupport;
	
	public static String awsAccessKeyId = "INSERT YOUT CODE";
	public static String awsSecretKey = "INSERT YOUT CODE";
	
	private static ExecutorService coverLoader = Executors.newCachedThreadPool();
	
	public TunesAlbum(String name, String interpreter) {
		this.name = name;
		this.interpreter = interpreter;
		this.propertyChangeSupport = new PropertyChangeSupport(this);
		
		coverLoader.execute(new SwingWorker<BufferedImage, Void>() {
			
			@Override
			protected BufferedImage doInBackground() throws Exception {
				return getAlbumArtImageIcon();
			}
			
			@Override
			protected void done() {
				try {
					setCover(get());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		this.rating = (int) (Math.random() * 5.0d);
		tracks = new CopyOnWriteArrayList<TunesTrack>();
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	public void firePropertyChange(String propertyName, 
			Object oldValue, Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
	
	private BufferedImage getAlbumArtImageIcon() throws Exception {
		AWSECommerceService service = new AWSECommerceService();
		service.setHandlerResolver(new AwsHandlerResolver(awsSecretKey));
		AWSECommerceServicePortType port = service.getAWSECommerceServicePort();
		ItemSearchRequest itemSearchRequest = new ItemSearchRequest();
		itemSearchRequest.setSearchIndex("Music");
		itemSearchRequest.setArtist(interpreter);
		itemSearchRequest.setTitle(name);
		itemSearchRequest.getResponseGroup().add("Images");
//		itemSearchRequest.getResponseGroup().add("ItemAttributes");
		
		ItemSearch itemSearch = new ItemSearch();
		itemSearch.setAWSAccessKeyId(awsAccessKeyId);
		itemSearch.getRequest().add(itemSearchRequest);

		// Call the Web service operation and store the response
		// in the response object:
		ItemSearchResponse response = port.itemSearch(itemSearch);
//		for (Item item : response.getItems().get(0).getItem()) {
//			System.out.println(item.getItemAttributes().getTitle() + " " + item.getImageSets().size() + "/" + item.getSmallImage() + "/" + item.getLargeImage());
//		}
		
		return TunesUtilities
		.createCompatibleImage(ImageIO.read(new URL(response.getItems().get(0).getItem().get(0).getLargeImage().getURL())));
	}

	public void addTrack(TunesTrack track) {
		tracks.add(track);
	}
	
	public Iterator<TunesTrack> getTracks() {
		return tracks.iterator();
	}
	
	public String getInterpreter() {
		return interpreter;
	}

	public String getName() {
		return name;
	}

	public BufferedImage getCover() {
		return cover;
	}

	public int getRating() {
		return rating;
	}
	
	public void setCover(BufferedImage cover) {
		this.cover = cover;
		firePropertyChange("cover", null, this.cover);
	}
}
