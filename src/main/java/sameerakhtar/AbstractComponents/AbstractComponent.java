package sameerakhtar.AbstractComponents;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import javax.imageio.ImageIO;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import io.appium.java_client.windows.WindowsDriver;

//--https://github.com/appium/appium-uiautomator2-driver/blob/master/docs/android-mobile-gestures.md
public class AbstractComponent {
	// ANSI escape code constants
	public static final String RESET = "\u001B[0m"; // Resets to default color
	public static final String BLACK = "\u001B[30m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String PURPLE = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	public static final String WHITE = "\u001B[37m";
	WindowsDriver driver;

	public AbstractComponent(WindowsDriver driver) {
		this.driver = driver;
	}

	public void copyToClipboard(String text) {
		StringSelection stringSelection = new StringSelection(text); // Wrap the text
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); // Get the system clipboard
		clipboard.setContents(stringSelection, null); // Set the clipboard content
	}

	public void pasteClipboardText() throws AWTException {
		Robot action = new Robot();
		action.keyPress(KeyEvent.CTRL_DOWN_MASK);
		action.keyPress(KeyEvent.VK_V);
		action.keyRelease(KeyEvent.VK_V);
		action.keyRelease(KeyEvent.CTRL_DOWN_MASK);
	}

	public Point VerifyScreenPatternAndGetCoordinates(String imageName, int timeInSeconds) throws Exception {
		long endTime = System.currentTimeMillis() + (timeInSeconds * 1000L);

		while (System.currentTimeMillis() < endTime) {
			captureScreenshot(imageName, driver);
			Point coordinates = ImageVerification.getCoordinatesOfItemOnScreen(imageName);
			if (coordinates != null) {
				return coordinates;
			}
//			Thread.sleep(500);
		}
		return null;
	}

	public boolean VerifyScreenPattern(String imageName, int timeInSeconds) throws Exception {
		long endTime = System.currentTimeMillis() + (timeInSeconds * 1000L);

		while (System.currentTimeMillis() < endTime) {
			captureScreenshot(imageName, driver);
			boolean status = ImageVerification.verifyItemOnScreen(imageName);
			if (status) {
				return true;
			}
//			Thread.sleep(500);
		}
		return false;
	}

	public boolean verifyOnScreenText(String expectedText, int x, int y, int width, int height, int timeInSeconds)
			throws Exception { // ------For screen in transition or animated screen
		String imageName = "OnScreenImage";
		long endTime = System.currentTimeMillis() + (timeInSeconds * 1000L);
		while (System.currentTimeMillis() < endTime) {
			captureScreenshot(imageName, driver);
			String extractedText = TesseractOCR.extractText(imageName, x, y, width, height);
			if (extractedText.toLowerCase().equalsIgnoreCase(expectedText.toLowerCase())) {
				System.out.println(GREEN + "extracted text:- " + extractedText.toLowerCase() + "expected text:- "
						+ expectedText.toLowerCase() + RESET);
				System.out.println(
						GREEN + extractedText.toLowerCase().equalsIgnoreCase(expectedText.toLowerCase()) + RESET);
				return true;
			}
//			Thread.sleep(500);
			System.out.println(RED + "extracted text:- " + extractedText.toLowerCase() + "expected text:- "
					+ expectedText.toLowerCase() + RESET);
			System.out.println(RED + extractedText.toLowerCase().equalsIgnoreCase(expectedText.toLowerCase()) + RESET);
		}
		return false;
	}

	public String extractOnScreenText(int x, int y, int width, int height) throws Exception { // -----For static screen
		String imageName = "OnScreenImage";
		captureScreenshot(imageName, driver);
		String extractedText = TesseractOCR.extractText(imageName, x, y, width, height);
		return extractedText;
	}

	public String captureScreenshot(String imageName, WebDriver driver) throws IOException {
//		TakesScreenshot ts = (TakesScreenshot) driver;
//		File src = ts.getScreenshotAs(OutputType.FILE);
//		File filePath = new File(System.getProperty("user.dir") + "/images/capturedImages/" + imageName + ".bmp");
//		FileUtils.copyFile(src, filePath);
//		return System.getProperty("user.dir") + "//reports//" + imageName + ".bmp";
		// Capture the screenshot as a PNG file
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);

		// Read the PNG into a BufferedImage
		BufferedImage pngImage = ImageIO.read(src);

		// Convert the BufferedImage to a 24-bit BMP
		BufferedImage bmpImage = new BufferedImage(pngImage.getWidth(), pngImage.getHeight(),
				BufferedImage.TYPE_INT_RGB); // Ensures 24-bit BMP (no alpha channel)

		// Draw the original PNG image onto the new BufferedImage
		bmpImage.getGraphics().drawImage(pngImage, 0, 0, null);

		// Save the BMP image to the specified location
		String filePath = System.getProperty("user.dir") + "/images/capturedImages/" + imageName + ".bmp";
		File output = new File(filePath);
		ImageIO.write(bmpImage, "bmp", output);

		return filePath;
	}

	public void clickOnScreenWithCoordinates(int x, int y) {

//		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
//		Sequence tap = new Sequence(finger, 1);
//		tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
//		tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
//		tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
//		driver.perform(Collections.singletonList(tap));

		new Actions(driver).moveToLocation(x, y).click().build().perform();

		System.out.println(GREEN + "Clicked at x:" + x + ", y:" + y + RESET);
	}

	public void clickAndHoldOnScreenWithCoordinates(int x, int y) {
		// Default time is 4 seconds
		clickAndHoldOnScreenWithCoordinates(x, y, 4);
	}

	public void clickAndHoldOnScreenWithCoordinates(int x, int y, int time) {
		new Actions(driver).moveToLocation(x, y).clickAndHold().pause(Duration.ofSeconds(time)).release().build()
				.perform();
		System.out.println(GREEN + "Clicked at x:" + x + ", y:" + y + RESET);
	}

	public void sendKeyboardInput(CharSequence... input) {
		new Actions(driver).pause(500).sendKeys(input).build().perform();
	}
}
