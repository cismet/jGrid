package de.guigarage.demos.tunes.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class TunesUtilities {

	public static URL load(String path) throws IOException {
        URL url = ClassLoader.getSystemClassLoader().getResource(path);
        if (url == null) {
            // Fuer den Gebrauch mit Jars
            url = TunesUtilities.class.getResource(path);
        }
        if (url == null) {
            url = ClassLoader.getSystemResource(path);
        }
        if (url == null) {
            throw new IOException("URL fŸr " + path + " konnte nicht erstellt werden!");
        }
        return url;
    }
	
    public static BufferedImage createCompatibleImage(int width, int height, boolean alpha) {
        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;

        String bool = System.getProperty("java.awt.headless");
        try {
            if (!GraphicsEnvironment.isHeadless() && !Boolean.parseBoolean(bool)) {
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                try {
                    // Determine the type of transparency of the new buffered image
                    int transparency = Transparency.OPAQUE;
                    if (alpha) {
                        transparency = Transparency.TRANSLUCENT;
                    }

                    // Create the buffered image
                    GraphicsDevice gs = ge.getDefaultScreenDevice();
                    GraphicsConfiguration gc = gs.getDefaultConfiguration();
                    bimage = gc.createCompatibleImage(width, height, transparency);
                } catch (HeadlessException e) {
                    // The system does not have a screen
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            if (alpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(width, height, type);
        }
        return bimage;
    }

    public static BufferedImage createCompatibleImage(BufferedImage image) {
        BufferedImage bimage = createCompatibleImage(image.getWidth(), image.getHeight(), true);
        Graphics g = bimage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }
    
    public static BufferedImage getFasterScaledInstance(BufferedImage img, 
            int targetWidth, int targetHeight, Object hint, 
            boolean progressiveBilinear) { 
        int type = (img.getTransparency() == Transparency.OPAQUE) ? 
            BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB; 
        BufferedImage ret = img; 
        BufferedImage scratchImage = null; 
        Graphics2D g2 = null; 
        int w, h; 
        int prevW = ret.getWidth(); 
        int prevH = ret.getHeight(); 
        if (progressiveBilinear) { 
          // Use multistep technique: start with original size, 
          // then scale down in multiple passes with drawImage() 
          // until the target size is reached 
          w = img.getWidth(); 
          h = img.getHeight(); 
        } else { 
          // Use one-step technique: scale directly from original 
          // size to target size with a single drawImage() call 
          w = targetWidth; 
          h = targetHeight; 
        } 
        do { 
            if (progressiveBilinear && w > targetWidth) { 
              w /= 2; 
              if (w < targetWidth) { 
                w = targetWidth; 
              } 
            } 
            if (progressiveBilinear && h > targetHeight) { 
              h /= 2; 
              if (h < targetHeight) { 
                h = targetHeight; 
              } 
            } 
            if (scratchImage == null) { 
              // Use a single scratch buffer for all iterations 
              // and then copy to the final, correctly sized image 
              // before returning 
              scratchImage = new BufferedImage(w, h, type); 
              g2 = scratchImage.createGraphics(); 
            } 
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                                hint); 
            g2.drawImage(ret, 0, 0, w, h, 0, 0, prevW, prevH, null); 
            prevW = w; 
            prevH = h; 
            ret = scratchImage; 
          } while (w != targetWidth || h != targetHeight); 
          if (g2 != null) { 
            g2.dispose(); 
          } 
          // If we used a scratch buffer that is larger than our 
          // target size, create an image of the right size and copy 
          // the results into it 
          if (targetWidth != ret.getWidth() || 
              targetHeight != ret.getHeight()) { 
            scratchImage = new BufferedImage(targetWidth, 
                                             targetHeight, type); 
            g2 = scratchImage.createGraphics(); 
            g2.drawImage(ret, 0, 0, null); 
            g2.dispose(); 
            ret = scratchImage; 
          } 
          
          return ret; 
        }
}
