package sameerakhtar.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import sameerakhtar.TestComponents.BaseTest;
import sameerakhtar.pageobject.ClashOfClans_GamePlay;
import sameerakhtar.pageobject.ClashOfClans_MyProfile;
import sameerakhtar.pageobject.ClashOfClans_Settings;

public class ClashOfClansTest extends BaseTest{

	@Test
	public void ClashOfClans_LauchGameplay() throws Exception {
		ClashOfClans_GamePlay gamePlay = launchGameWithPackageName.launchClashOfClans("com.supercell.clashofclans");
		Boolean verifyGamePlay = gamePlay.gamePlay();
		Assert.assertTrue(verifyGamePlay);
		ClashOfClans_MyProfile myProfile = gamePlay.gotoProfile();
		Boolean verifyMyProfile = myProfile.verifyProfile();
		Assert.assertTrue(verifyMyProfile);
		Boolean verifySocial = myProfile.gotoSocialSection();
		Assert.assertTrue(verifySocial);
		Boolean verifySearchedProfile = myProfile.searchPlayers();
		Assert.assertTrue(verifySearchedProfile);
		Boolean cutProfileWindow = myProfile.cutProfileWindow();
		Assert.assertTrue(cutProfileWindow);
		ClashOfClans_Settings gotoSettings = gamePlay.gotoSettings();
		Boolean verifySettingsPage = gotoSettings.verifySettings();
		Assert.assertTrue(verifySettingsPage);
		Boolean gotoMoreSettings = gotoSettings.gotoMoreSettings();
		Assert.assertTrue(gotoMoreSettings);
		myProfile.quitGame("com.supercell.clashofclans");
	}
}
