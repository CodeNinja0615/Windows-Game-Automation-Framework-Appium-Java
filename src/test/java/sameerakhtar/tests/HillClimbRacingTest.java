package sameerakhtar.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import sameerakhtar.TestComponents.BaseTest;
import sameerakhtar.pageobject.HillClimbRacing_GamePlay;
import sameerakhtar.pageobject.HillClimbRacing_MainMenu;

public class HillClimbRacingTest extends BaseTest {
 
	@Test
	public void HillClimbRacing_LauchGameplay() throws Exception {
		HillClimbRacing_MainMenu mainMenu = launchGameWithPackageName.launchHillClimbRacing("com.fingersoft.hillclimb");
		Boolean verifyMainMenu = mainMenu.verifyManiMenu();
		Assert.assertTrue(verifyMainMenu);
		Boolean verifySettings = mainMenu.naviagteToGameSettings();
		Assert.assertTrue(verifySettings);
		Boolean verifyMainMenuAgain = mainMenu.gotoMainMenu();
		Assert.assertTrue(verifyMainMenuAgain);
		HillClimbRacing_GamePlay gamePlay = mainMenu.navigateToGamePLay();
		Boolean verifyGamePlay = gamePlay.verifyGamePlay();
		Assert.assertTrue(verifyGamePlay);
		gamePlay.playTheGame(25);
		Boolean gamePlayPaused = gamePlay.pauseGamePlay();
		Assert.assertTrue(gamePlayPaused);
		Boolean verifyGamePlayQuit = gamePlay.quitGamePlay();
		Assert.assertTrue(verifyGamePlayQuit);
		gamePlay.quitGame("com.fingersoft.hillclimb");
	}
}
