package com.testing.pageObjects.pages;

import java.util.Map;

import org.openqa.selenium.By;

import io.cucumber.datatable.DataTable;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("/")
public class HomePage extends TopMenuSection {
  // Locators ----------------------------------------------------------------------------------------------------------
  public static By DISMISS_BUTTON = ariaLabel("Close Welcome Banner");
  public static By ME_WANT_IT_BUTTON = ariaLabel("dismiss cookie message");
  public static By ALL_PRODUCTS_TITLE = text("All Products");
  public static By SIMPLE_SNACK_BAR_PAGE_RELOAD_BUTTON = css("simple-snack-bar button");
  public static By SIMPLE_SNACK_BAR = css("simple-snack-bar span");
  public static By ITEMS_PER_PAGE = ariaLabel("Items per page:");;
  public static By OPTION_24 = css("mat-option[id='mat-option-1']");
  public static By BLOCK_NAME = css("div[class='mat-tooltip-trigger product']");
  public static By ADD_TO_BASKET_BUTTON;
  public static By PRODUCT_INFO_BLOCK = css("app-product-details[class='ng-star-inserted']");
  public static By PRODUCT_INFO_PRICE = css("p[class='item-price']");
  public static By PRODUCT_INFO_CLOSE = ariaLabel("Close Dialog");
  public static By REVIEWS_EXPAND = css("mat-panel-title");
  public static By COMMENT_SECTION = css("div[class='mat-tooltip-trigger review-text']");
  public static By REVIEW_TEXT_FIELD = css("textarea[id='mat-input-3']");
  public static By SUBMIT_BUTTON = css("button[id='submitButton']");

  // Public methods ----------------------------------------------------------------------------------------------------
  public void waitForPageToLoad() {
    getElement(ALL_PRODUCTS_TITLE).waitUntilPresent();
    logWeAreOnPage();
  }

  public Boolean validateAccountMenu(DataTable data){
    for (Map<String, String> map : dataToMap(data)) {

      for (String key : map.keySet()) {
        if(getElement(snakify(key)).getText().contains(map.get(key))){
          info("Validation for " + snakify(key) + " passed");
        } else {
          info("Validation for " + snakify(key) + " failed");
          info("Expected to contain: \"" + map.get(key) + "\" in \"" + getElement(snakify(key)).getText() + "\"");
          return false;
        }
      }
    }
    return true;
  }

  public void performCreateAnAccount(DataTable data){
    info("Creating an account, while on: " + this.getClass().getSimpleName());
    getElement(ACCOUNT_BUTTON).click();
    getElement(LOGIN_BUTTON).click();
    LoginPage page = new LoginPage();
    page.waitForPageToLoad();
    page.performCreateAnAccount(data);
  }

  public void performLogIn(DataTable data){
    getElement(ACCOUNT_BUTTON).click();
    getElement(LOGIN_BUTTON).click();
    LoginPage page = new LoginPage();
    page.waitForPageToLoad();
    page.performLogIn(data);
  }

  public Boolean validateItemList(DataTable data){
    info("Validating Item list");

    int expectedAmountOfItems = Integer.valueOf(dataToMap(data).get(0).get("Amount"));
    int actualAmountOfItems = getElements(BLOCK_NAME).size();

    info("Found: " + actualAmountOfItems + ", expected: " + expectedAmountOfItems);
    if(expectedAmountOfItems == actualAmountOfItems){
      return true;
    } else {
      return false;
    }
  }

  public void performOpenItem(DataTable data){
    for (Map<String, String> map : dataToMap(data)) {
      String name = map.get("Name");

      for (WebElementFacade element : getElements(BLOCK_NAME)) {
        if(!element.getText().contains(name)) continue;

        element.click();
      }
    }
  }
}
