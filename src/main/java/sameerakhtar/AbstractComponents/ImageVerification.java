package sameerakhtar.AbstractComponents;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ImageVerification {
	public static final String RESET = "\u001B[0m"; // Resets to default color
	public static final String BLACK = "\u001B[30m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String PURPLE = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	public static final String WHITE = "\u001B[37m";
	public static int smallWidth;
	public static int smallHeight;

	public static Point getCoordinatesOfItemOnScreen(String imageName) throws Exception {
		File largeImageFile = new File(System.getProperty("user.dir") + "/images/capturedImages/" + imageName + ".bmp");
		File smallImageFile = new File(System.getProperty("user.dir") + "/images/iconImages/" + imageName + ".bmp");

		BufferedImage largeImage = ImageIO.read(largeImageFile);
		BufferedImage smallImage = ImageIO.read(smallImageFile);

		// Convert both images to grayscale
		BufferedImage grayLargeImage = convertToGrayscale(largeImage);
		BufferedImage graySmallImage = convertToGrayscale(smallImage);

		Point coordinates = findImageCoordinates(grayLargeImage, graySmallImage);
		if (coordinates != null) {
//			System.out.println("Small image found in the large image.");
			System.out.println(GREEN + imageName +" Match found at coordinates:- x=" + coordinates.x + ", y=" + coordinates.y + RESET);
			return coordinates;
		} else {
			System.out.println(RED + imageName + " Image Match Not Found, retruning null Co-ordinates" + RESET);
			return null;
		}
	}

	public static boolean verifyItemOnScreen(String imageName) throws Exception {
		File largeImageFile = new File(System.getProperty("user.dir") + "/images/capturedImages/" + imageName + ".bmp");
		File smallImageFile = new File(System.getProperty("user.dir") + "/images/iconImages/" + imageName + ".bmp");

		BufferedImage largeImage = ImageIO.read(largeImageFile);
		BufferedImage smallImage = ImageIO.read(smallImageFile);

		// Convert both images to grayscale
		BufferedImage grayLargeImage = convertToGrayscale(largeImage);
		BufferedImage graySmallImage = convertToGrayscale(smallImage);

		Point coordinates = findImageCoordinates(grayLargeImage, graySmallImage);
		if (coordinates != null) {
			System.out.println(GREEN + imageName + " Image Match Found." + RESET);
			return true;
		} else {
			System.out.println(RED + imageName + " Image Match Not Found." + RESET);
			return false;
		}
	}

	// Method to convert image to grayscale
	public static BufferedImage convertToGrayscale(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g2d = grayImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		return grayImage;
	}

	// Method to find coordinates of the small image in the large image
	public static Point findImageCoordinates(BufferedImage largeImage, BufferedImage smallImage)
			throws InterruptedException, ExecutionException {
		int width = largeImage.getWidth();
		int height = largeImage.getHeight();
		smallWidth = smallImage.getWidth();
		smallHeight = smallImage.getHeight();

		ExecutorService executor = Executors.newFixedThreadPool(7); // Create a thread pool for optimization
		List<Callable<Point>> tasks = new ArrayList<>();

		// Loop through all possible positions where the small image can fit inside the
		// large image
		for (int y = 0; y <= height - smallHeight; y++) {
			for (int x = 0; x <= width - smallWidth; x++) {
				int finalX = x;
				int finalY = y;
				tasks.add(() -> compareRegion(largeImage, smallImage, finalX, finalY));
			}
		}

		List<Future<Point>> results = executor.invokeAll(tasks);
		executor.shutdown();

		// Check if any task returned coordinates
		for (Future<Point> result : results) {
			Point coordinates = result.get();
			if (coordinates != null) {
				return coordinates; // Found a match
			}
		}

		return null; // No match found
	}

	// Method to compare one region of the large image with the small image and
	// return coordinates if matched
	public static Point compareRegion(BufferedImage largeImage, BufferedImage smallImage, int startX, int startY) {
		int smallWidth = smallImage.getWidth();
		int smallHeight = smallImage.getHeight();
		int tolerance = 30; // Tolerance for pixel value variations

		// Compare each pixel in the small image with the corresponding pixel in the
		// large image
		for (int i = 0; i < smallWidth; i++) {
			for (int j = 0; j < smallHeight; j++) {
				int largePixel = largeImage.getRGB(startX + i, startY + j);
				int smallPixel = smallImage.getRGB(i, j);

				// Extract grayscale values
				int largeGray = (largePixel >> 16) & 0xff; // R, G, B are the same in grayscale
				int smallGray = (smallPixel >> 16) & 0xff;

				// Compare grayscale values with a tolerance level
				if (Math.abs(largeGray - smallGray) > tolerance) {
					return null; // Mismatch found
				}
			}
		}

		return new Point(startX + smallWidth / 2, startY + smallHeight / 2); // Return the top-left corner coordinates
																				// where the match was found
	}
}
