package de.guigarage.gestures;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;

import com.apple.eawt.event.GestureUtilities;
import com.apple.eawt.event.MagnificationEvent;
import com.apple.eawt.event.MagnificationListener;
import com.apple.eawt.event.RotationEvent;
import com.apple.eawt.event.RotationListener;

import de.jgrid.demo.util.ImageUtilities;
import de.jgrid.demo.util.UrlLoader;

public class GesturesDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	private double rotation = 0.0d;
	
	private double magnification = 0.5d;
	
	private BufferedImage duke;
	
	private BufferedImage background;

	private BufferedImage helpLayer;

	private float alphaValue = 1.0f;
	
	private Animator alphaAnimator;
	
	public GesturesDemo() {
		setTitle("GesturesDemo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		try {
			background = ImageUtilities.createCompatibleImage(ImageIO.read(UrlLoader.getInstance().load("/de/guigarage/gestures/background.png")));
			duke = ImageUtilities.createCompatibleImage(ImageIO.read(UrlLoader.getInstance().load("/de/guigarage/gestures/duke.png")));
			helpLayer = ImageUtilities.createCompatibleImage(ImageIO.read(UrlLoader.getInstance().load("/de/guigarage/gestures/helplayer.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JPanel panel = new JPanel() {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(background, 0, 0, null);
				
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.rotate(rotation * Math.PI / 180, getWidth() / 2, getHeight() / 2);       
				g2.drawImage(duke, (int) (getWidth() / 2 - (duke.getWidth() / 2) * magnification), (int) (getHeight() / 2 - (duke.getHeight() / 2) * magnification), (int) (duke.getWidth() * magnification), (int) (duke.getHeight() * magnification), null);
				g2.dispose();
				
				g2 = (Graphics2D) g.create();
				AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue);
				g2.setComposite(alphaComposite);
				g2.drawImage(helpLayer, 0, 0, null);
				g2.dispose();
			}
		};
		getContentPane().add(panel, BorderLayout.CENTER);
		try {
		GestureUtilities.addGestureListenerTo(panel, new RotationListener() {

			public void rotate(RotationEvent arg0) {
				rotation = rotation + arg0.getRotation();
				repaint();
				animateAlpha();
			}
		});
		
		GestureUtilities.addGestureListenerTo(panel, new MagnificationListener() {

			public void magnify(MagnificationEvent arg0) {
				magnification = magnification + arg0.getMagnification();
				repaint();	
				animateAlpha();
			}
			
		});
		} catch (Exception e) {
			System.out.println("Gestures-API not Supported!");
			e.printStackTrace();
		}
		setSize(800, 600);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		 new GesturesDemo().setVisible(true);
	}

	private void animateAlpha() {
		if(alphaAnimator == null) {
			alphaAnimator = new Animator(1000);
			alphaAnimator.setAcceleration(0.3f);
			alphaAnimator.addTarget(new TimingTarget() {
				
				public void timingEvent(float fraction) {
					alphaValue = Math.max(0.0f, 1.0f - fraction);
					repaint();
				}
				
				public void repeat() {}
				
				public void end() {
					alphaValue = 0.0f;
				}
				
				public void begin() {}
			});
			alphaAnimator.start();
		}
	}
}
