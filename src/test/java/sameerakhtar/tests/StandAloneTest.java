package sameerakhtar.tests;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import sameerakhtar.TestComponents.BaseTest;

public class StandAloneTest extends BaseTest{
	@Test(enabled=false)
	public void appiumTest() throws MalformedURLException, URISyntaxException {
		driver.activateApp("com.instagram.barcelona");
		driver.findElement(AppiumBy.xpath("//android.view.View[@resource-id=\"barcelona_tab_profile\"]/android.view.View[2]")).click();
		driver.terminateApp("com.instagram.barcelona");
	}
}
