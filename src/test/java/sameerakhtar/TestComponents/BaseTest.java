package sameerakhtar.TestComponents;

//----https://github.com/appium/appium-uiautomator2-driver/?tab=readme-ov-file
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.options.WindowsOptions;

public class BaseTest {

	public AppiumDriverLocalService service;
	public WindowsDriver driver;

	public void startServiceBuilder() {
		String currentUser = System.getProperty("user.name");
		service = new AppiumServiceBuilder()
				.withAppiumJS(new File("C://Users//" + currentUser
						+ "//AppData//Roaming//npm//node_modules//appium//build//lib//main.js"))
				.withIPAddress("127.0.0.1").usingPort(4723).build();
		service.start();
	}

	public void configureAppiumWindows(String deviceName, String platformName, String appToLaunch)
			throws MalformedURLException, URISyntaxException {
		if (platformName.equalsIgnoreCase("Windows")) {
			// ---Windows code here
			WindowsOptions options = new WindowsOptions();
			options.setCapability("deviceName", deviceName);
			options.setCapability("platformName", platformName);
			// ----CMD---- // Get-StartApps
			options.setCapability("app", appToLaunch);
			driver = new WindowsDriver(new URI("http://127.0.0.1:4723").toURL(), options);
		} else if (platformName.equalsIgnoreCase("Mac")) {
			// ---Mac code here
		}
	}

	public void launchApplicationWindows(String appToLaunch) throws MalformedURLException, URISyntaxException {
		// ---Windows code here
		WindowsOptions options = new WindowsOptions();
		options.setCapability("deviceName", "WindowsPC");
		options.setCapability("platformName", "Windows");
		// ----CMD---- // Get-StartApps
		options.setCapability("app", appToLaunch);
		driver = new WindowsDriver(new URI("http://127.0.0.1:4723").toURL(), options);
	}

	@BeforeMethod
	public void setup() throws URISyntaxException, IOException {
		startServiceBuilder();
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java//sameerakhtar//resources//GlobalData.properties");
		prop.load(fis);
		String deviceName = System.getProperty("deviceName") != null ? System.getProperty("deviceName")
				: prop.getProperty("deviceName");
		String platformName = System.getProperty("platformName") != null ? System.getProperty("platformName")
				: prop.getProperty("platformName");
//		configureAppiumWindows(deviceName, platformName, "Microsoft.GamingApp_8wekyb3d8bbwe!Microsoft.Xbox.App");
		configureAppiumWindows(deviceName, platformName, "Root");
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
