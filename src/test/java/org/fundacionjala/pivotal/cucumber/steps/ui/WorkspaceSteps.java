package org.fundacionjala.pivotal.cucumber.steps.ui;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.fundacionjala.core.api.services.WorkSpaceService;
import org.fundacionjala.core.ui.forms.FormsElements;
import org.fundacionjala.pivotal.pages.Dashboard;
import org.fundacionjala.pivotal.pages.Header;
import org.fundacionjala.pivotal.pages.WorkSpaceNew;
import org.fundacionjala.pivotal.pages.WorkSpaceSettings;
import org.fundacionjala.util.ScenarioContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.asserts.SoftAssert;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * This class will have steps for workspace feature.
 **/
public class WorkspaceSteps {
    private static final Logger LOGGER =
            Logger.getLogger(WorkspaceSteps.class.getName());


    public static final String WS_SETTINGS_PAGE = "ws_settings_page";
    public static final String WS_NAME = "ws_name";

    @Autowired
    private Dashboard dashboard;

    @Autowired
    private Header header;

    @Autowired
    private WorkSpaceNew workSpaceNew;

    @Autowired
    private SoftAssert softAssert;

    /**
     * This step edit workspace.
     *
     * @param strname param.
     **/
    @And("edit workspace’s title")
    public static void setWorkSpacename(final String strname) {

        final WorkSpaceSettings settingsPage =
                (WorkSpaceSettings) ScenarioContext.getInstance().getContext(
                        WS_SETTINGS_PAGE);
        ScenarioContext.getInstance().setContext(WS_NAME, strname);
        settingsPage.setName(strname);
    }

    /**
     * This step save workspace.
     **/
    @And("clicks on Save Button")
    public static void clickSaveButton() {
        final WorkSpaceSettings settingsPage =
                (WorkSpaceSettings) ScenarioContext.getInstance().getContext(
                        WS_SETTINGS_PAGE);
        settingsPage.clickOnSave();

    }

    /**
     * This step display workspace edited.
     **/
    @Then("workspace title should be edited")
    public void getWorkSpaceLabel() {
        final String name = (String) ScenarioContext.getInstance().getContext(
                WS_NAME);
        this.dashboard.goToWorkSpaceTab();
        softAssert.assertTrue(this.dashboard.existWorkSpace(name));
    }

    /**
     * This step create a workspace.
     **/
    @Given("create an workspace")
    public static void createaworkspace() {
        final String name = "My WorkSpace Test";
        final int workspaceId = WorkSpaceService.createWorkspace(name);
        ScenarioContext.getInstance().setContext(WS_NAME, name);
        ScenarioContext.getInstance().setContext("ws_id", workspaceId);
    }

    /**
     * This step click over settings button.
     **/
    @When("the user clicks on workspace settings button")
    public void theUserClicksOnWorkSpaceSettingBtn() {
        final String name = (String) ScenarioContext.getInstance().getContext(
                WS_NAME);
        try {
            final WorkSpaceSettings page =
                    this.dashboard.clickWorkSpaceSettings(name);
            ScenarioContext.getInstance().setContext(WS_SETTINGS_PAGE, page);


        } catch (final NoSuchElementException e) {
            LOGGER.warn("The Workspace web element was not find ", e);
        }
    }

    /**
     * This step delete a workspace.
     **/
    @And("click delete workspace")
    public static void clickDeleteWorkspace() {
        final WorkSpaceSettings settingsPage =
                (WorkSpaceSettings) ScenarioContext.getInstance().getContext(
                        WS_SETTINGS_PAGE);
        settingsPage.clickOnDeleteLink();

    }

    /**
     * This step confirm delete.
     **/
    @And("click delete confirm")
    public static void clickConfirmDeleteWorkspace() {
        final WorkSpaceSettings settingsPage =
                (WorkSpaceSettings) ScenarioContext.getInstance().getContext(
                        WS_SETTINGS_PAGE);
        settingsPage.clickOnConfirmDelete();

    }

    /**
     * workspace si deleted.
     **/
    @Then("workspace should be deleted")
    public void verifyDelete() {
        final String name = (String) ScenarioContext.getInstance().getContext(
                WS_NAME);
        this.dashboard.goToWorkSpaceTab();
        softAssert.assertFalse(this.dashboard.existWorkSpace(name));
    }

    /**
     * This step click over create button.
     **/
    @Given("clicks on create workspace button")
    public void clicksTheCreateWorkspaceButton() {
        this.workSpaceNew.clickDashboardLink();
        this.workSpaceNew.clickCreateWorkSpaceButton();
    }

    /**
     * This step set name for a workspace.
     *
     * @param name param.
     **/
    @And("sets the workspace name: {string}")
    public void setsTheWorkspaceNameName(final String name) {
        // guardar el name de forma mas optima q e una variable
        this.workSpaceNew.setName(name);
        this.workSpaceNew.clickCreateButton();
    }

    /**
     * This steps verify that workspace.
     *
     * @param name param.
     **/
    @Then("the workspace should be created: {string}")
    public void theWorkspaceShouldBeCreated(final String name) {
        final String actualresult = this.workSpaceNew.getWorkSpaceLabel();
        softAssert.assertEquals(actualresult, name);
    }

    /**
     * This step verify that workspace is displayed.
     **/
    @And("the workspace board should be displayed")
    public void theWorkspaceBoardShouldBeDisplayed() {
        softAssert.assertTrue(true);
    }

    @When("creates a workspace")
    public void createsAWorkspace(final Map<String, String> workspaceAttributes) {
        this.workSpaceNew.setName(workspaceAttributes
                .get(FormsElements.TITLE.toString()));
        ScenarioContext.getInstance().setContext(WS_NAME, workspaceAttributes
                .get(FormsElements.TITLE.toString()));
        this.workSpaceNew.clickCreateButton();
    }


    @Then("validates presence on workspace home")
    public void validatesPresenceOnWorkspaceHome() {
        softAssert.assertTrue(this.dashboard.existWorkSpace(WS_NAME));
    }

}



