package org.fundacionjala.pivotal.pages;

import org.fundacionjala.core.ui.AbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;

/**
 * this is a modal that is used to confirm a story delete.
 */
@Component
public class DeleteModal extends AbstractPage {
    /**
     * This is the delete confirm story button.
     */
    @FindBy(css = "button[data-aid='DeleteButton']")
    private WebElement deleteStoryConfirmButton;

    /**
     * Click delete button.
     **/
    public void clickConfirmDeleteButton() {
        this.action.click(this.deleteStoryConfirmButton);
    }


}
