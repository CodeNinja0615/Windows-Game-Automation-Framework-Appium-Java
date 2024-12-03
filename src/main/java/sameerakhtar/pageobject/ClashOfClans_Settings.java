package sameerakhtar.pageobject;

import java.awt.Point;

import io.appium.java_client.android.AndroidDriver;
import sameerakhtar.AbstractComponents.AbstractComponent;

public class ClashOfClans_Settings extends AbstractComponent{
	AndroidDriver driver;
	public ClashOfClans_Settings(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	public boolean verifySettings() throws Exception {
		return VerifyScreenPattern("COC_SettingsPage", 30);
	}
	
	public boolean gotoMoreSettings() throws Exception {
		Point moreSettings = VerifyScreenPatternAndGetCoordinates("COC_MoreSettings", 30);
		clickOnScreenWithCoordinates(moreSettings.x, moreSettings.y);
		lookForScreenContentAndScrollInAreaWithCoordinates(431, 151, 733, 457, "down", 0.5, "COC_CustomerSupportIcon");
		return VerifyScreenPattern("COC_MoreSettingsPage", 30);
	}
}
