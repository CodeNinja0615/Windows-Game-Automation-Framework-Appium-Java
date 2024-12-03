package sameerakhtar.TestComponents;

//----https://github.com/appium/appium-uiautomator2-driver/?tab=readme-ov-file
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.windows.WindowsDriver;
import sameerakhtar.pageobject.LaunchGameWithPackageName;

public class BaseTest {

	public AppiumDriverLocalService service;
	public AndroidDriver driver;
	public WindowsDriver winDriver;
	public LaunchGameWithPackageName launchGameWithPackageName;

	public void configureAppiumMobile(String deviceName, String platformName, boolean setNoReset)
			throws MalformedURLException, URISyntaxException {

		if (platformName.equalsIgnoreCase("Android")) {
			// ---Android code here
			String currentUser = System.getProperty("user.name");
			service = new AppiumServiceBuilder()
					.withAppiumJS(new File("C://Users//" + currentUser
							+ "//AppData//Roaming//npm//node_modules//appium//build//lib//main.js"))
					.withIPAddress("127.0.0.1").usingPort(4723).build();
			service.start();
			UiAutomator2Options options = new UiAutomator2Options();
			options.setDeviceName(deviceName);
			options.setPlatformName(platformName);
			// -----------------------------------------------------------------------------------------------------------------------//
//			options.setApp("C:/Users/HP/Downloads/General-Store.apk");
//			adb shell dumpsys window | findstr "mCurrentFocus"
//			options.setAppActivity("com.instagram.barcelona.mainactivity.BarcelonaActivity");
//			options.setAppPackage("com.instagram.barcelona");
			// -----------------------------------------------------------------------------------------------------------------------//
			options.setNoReset(setNoReset); // ----- set true else app will be reset on start
			options.setAppWaitForLaunch(true);
			options.setGpsEnabled(true);
			options.autoGrantPermissions();
			driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			driver.unlockDevice();
		} else if (platformName.equalsIgnoreCase("iOS")) {
			// ---iOS code here
		}
	}

	@BeforeMethod
	public void setup() throws URISyntaxException, IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java//sameerakhtar//resources//GlobalData.properties");
		prop.load(fis);
		String deviceName = System.getProperty("deviceName") != null ? System.getProperty("deviceName")
				: prop.getProperty("deviceName");
		String platformName = System.getProperty("platformName") != null ? System.getProperty("platformName")
				: prop.getProperty("platformName");

		configureAppiumMobile(deviceName, platformName, true);
		launchGameWithPackageName = new LaunchGameWithPackageName(driver); // ----Providing driver to the first page
																			// object model class

	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
		service.stop();
	}

	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {// ----Goes to extent report
		// in Listeners
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File filePath = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(src, filePath);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}
}
