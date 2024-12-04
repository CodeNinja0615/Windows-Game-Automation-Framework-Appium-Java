package sameerakhtar.tests;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.testng.annotations.Test;

import sameerakhtar.TestComponents.BaseTest;

public class XboxAppTest extends BaseTest {

	@Test
	public void xboxAppTest() throws MalformedURLException, URISyntaxException {
		launchApplicationWindows("Microsoft.GamingApp_8wekyb3d8bbwe!Microsoft.Xbox.App");
	}
}
