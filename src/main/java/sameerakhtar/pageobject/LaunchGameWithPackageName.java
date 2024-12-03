package sameerakhtar.pageobject;

import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.android.AndroidDriver;
import sameerakhtar.AbstractComponents.AbstractComponent;

public class LaunchGameWithPackageName extends AbstractComponent {

	AndroidDriver driver;
	public LaunchGameWithPackageName(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public HillClimbRacing_MainMenu launchHillClimbRacing(String packageName) {
		launchGameWithPackageName(packageName);
		return new HillClimbRacing_MainMenu(driver);
	}
	public ClashOfClans_GamePlay launchClashOfClans(String packageName) {
		launchGameWithPackageName(packageName);
		return new ClashOfClans_GamePlay(driver);
	}
}
