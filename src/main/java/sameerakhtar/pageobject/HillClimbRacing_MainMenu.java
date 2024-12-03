package sameerakhtar.pageobject;

import java.awt.Point;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import sameerakhtar.AbstractComponents.AbstractComponent;

public class HillClimbRacing_MainMenu extends AbstractComponent {

	AndroidDriver driver;
	public HillClimbRacing_MainMenu(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

//	@AndroidFindBy (xpath="")
//	WebElement name;
	
	public boolean verifyManiMenu() throws Exception {
		return VerifyScreenPattern("HCR_MainMenu", 20);
	}
	
	public boolean naviagteToGameSettings() throws Exception {
		Point settings = VerifyScreenPatternAndGetCoordinates("HCR_SettingIcon", 20);
		clickOnScreenWithCoordinates(settings.x, settings.y);
		Boolean verifySettings = VerifyScreenPattern("HCR_Settings", 20);
		return verifySettings;
	}
	
	public boolean gotoMainMenu() throws Exception {
		Point cross = VerifyScreenPatternAndGetCoordinates("HCR_SettingCross", 20);
		clickOnScreenWithCoordinates(cross.x, cross.y);
		return VerifyScreenPattern("HCR_MainMenu", 20);
	}
	
	public HillClimbRacing_GamePlay navigateToGamePLay() throws Exception {
		Point startBtn = VerifyScreenPatternAndGetCoordinates("HCR_StartBtn", 10);
		clickOnScreenWithCoordinates(startBtn.x, startBtn.y);
		return new HillClimbRacing_GamePlay(driver);
	}
}