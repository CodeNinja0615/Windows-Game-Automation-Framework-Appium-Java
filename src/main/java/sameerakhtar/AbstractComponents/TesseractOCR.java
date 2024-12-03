package sameerakhtar.AbstractComponents;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TesseractOCR {
	
	public static String extractText(String imageName, int x, int y, int width, int height) throws IOException, TesseractException {
		downloadTessdata();
        // Load the image
        BufferedImage image = ImageIO.read(new File(System.getProperty("user.dir") + "/images/capturedImages/" + imageName + ".bmp"));

        // Crop the desired region
        BufferedImage croppedImage = image.getSubimage(x, y, width, height);

        // Set up Tesseract OCR
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath(System.getProperty("user.dir") + "/tessdata"); // Dynamic tessdata path
        tesseract.setLanguage("eng"); // Specify language

        // Perform OCR
        return tesseract.doOCR(croppedImage);
    }
	
	public static void downloadTessdata() {
	    try {
	        String tessdataDir = System.getProperty("user.dir") + "/tessdata";
	        File directory = new File(tessdataDir);
	        if (!directory.exists()) {
	            directory.mkdirs();
	        }

	        String[] languages = {"eng"}; // Add more languages as needed
	        for (String lang : languages) {
	            String traineddataFile = tessdataDir + "/" + lang + ".traineddata";
	            File traineddata = new File(traineddataFile);

	            if (traineddata.exists()) {
	                System.out.println(lang + ".traineddata already present in tessdata.");
	            } else {
	                String url = "https://github.com/tesseract-ocr/tessdata/raw/main/" + lang + ".traineddata";
	                downloadFile(url, traineddataFile);
	                System.out.println("Downloaded: " + lang + ".traineddata");
	            }
	        }

	        // Set the environment variable for Tesseract
	        System.setProperty("TESSDATA_PREFIX", tessdataDir + "/");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}



	    private static void downloadFile(String urlString, String destination) throws IOException, URISyntaxException {
	        URI uri = new URI(urlString);
	        try (BufferedInputStream in = new BufferedInputStream(uri.toURL().openStream());
	             FileOutputStream fileOutputStream = new FileOutputStream(destination)) {
	             
	            byte[] dataBuffer = new byte[1024];
	            int bytesRead;
	            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
	                fileOutputStream.write(dataBuffer, 0, bytesRead);  // Corrected line
	            }
	        }
	    }
}
