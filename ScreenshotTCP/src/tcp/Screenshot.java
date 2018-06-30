package tcp;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Screenshot{

	public static final long serialVersionUID = 1L;

	/**
	 * Takes a screenshot and places it in
	 * default folder
	 */
	public static void takeShot() {
		try {
			Thread.sleep(120);
			Robot r = new Robot();

			// Path to picture folder
			String path = "/Users/Daniel/Documents/Skole/dat104/ws-datanett/Test/Screenshots/" + "Screenshot" + ".png";

			// Used to get ScreenSize and capture image
			Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage image = r.createScreenCapture(capture);

			ImageIO.write(image, "png", new File(path));
			System.out.println("Screenshot saved!");

		} catch (AWTException | IOException | InterruptedException ex) {
			System.out.println(ex);
		}
	}

	/**
	 * 
	 * @return BufferedImage of the whole screen
	 */
	public static BufferedImage captureWholeScreen() {
		try {
			Thread.sleep(120);
			Robot r = new Robot();

			// Used to get ScreenSize and capture image
			Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage image = r.createScreenCapture(capture);

			System.out.println("Screen Captured!");

			return image;
		} catch (AWTException | InterruptedException ex) {

			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates a .png file of the BufferedImage
	 * at the defined path.
	 * 
	 * @param path
	 * @param image
	 */
	public static void saveImage(String path, BufferedImage image) {
		try {
			ImageIO.write(image, "png", new File(path));
			System.out.println("Screenshot saved!");
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	
	/**
	 * 
	 * @param BufferedImage image
	 * @return an int array of rgb colours for each pixel
	 */
	public static int[][] deconstructImage(BufferedImage image) {
		
		int[][] rgb = new int[image.getWidth()][image.getHeight()];
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				rgb[i][j] = image.getRGB(i, j);
			}
		}
		return rgb;
	}
	
	/**
	 * 
	 * @param rgb 2d array of pixel colours
	 * @param type of image default = 1
	 * @return BufferedImage constructed from rgb pixel colour array
	 */
	public static BufferedImage constructImage(int[][] rgb, int type) {
		BufferedImage image = new BufferedImage(rgb.length, rgb[0].length, type);
		for(int i = 0; i < rgb.length; i++) {
			for(int j = 0; j < rgb[0].length; j++) {
				image.setRGB(i, j, rgb[i][j]);
			}
		}
		return image;
	}
}
