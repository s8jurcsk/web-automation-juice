package com.testing.pageObjects.pages;

import static org.junit.Assert.fail;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.cucumber.datatable.DataTable;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("/payment/shop")
public class PaymentShopPage extends DeliveryMethodPage{
  // Locators
  // -------------------------------------------------------------------------------------------------------------------
  public static By MY_PAYMENT_OPTIONS_TITLE = text("My Payment Options");
  public static By CONTINUE_BUTTON = css("button[id=submitButton]"); //???
  public static By ADD_NEW_CARD_OPTION = css("mat-expansion-header[id=mat-expansion-panel-header-0]");
  public static By CARD_NAME_FIELD = css("input[id=mat-input-9]");
  public static By CARD_NUMBER_FIELD = css("input[id=mat-input-10]");
  public static By EXPIRY_MONTH_FIELD = css("select[id=mat-input-11]");
  public static By EXPIRY_YEAR_FIELD = css("select[id=mat-input-12]");
  // Public methods
  // -------------------------------------------------------------------------------------------------------------------
  public void waitForPageToLoad(){
    getElement(MY_PAYMENT_OPTIONS_TITLE).waitUntilVisible();
    logWeAreOnPage();
  }

  public void performSelectCreditCard(DataTable data){
    String cardNumber = null;
    Boolean found = null;

    for (Map<String, String> map : dataToMap(data)) {
      found = false;
      cardNumber = map.get("Card Number");
      for (WebElement element : getElements(ROW_ITEMS)) {
        if(!element.getText().contains(cardNumber)) continue;
        element.findElement(css("mat-radio-button")).click();
        found = true;
      }
      if(found) break;
    }
    if(!found) fail("Failed to select given card number: " + cardNumber);
  }

  public void setElementValueTo(String elementName, String value) {
    switch(elementName){
      case "EXPIRY_MONTH":
        getElement(EXPIRY_MONTH_FIELD).click();
        getElement(exactText(value)).click();
        break;
      case "EXPIRY_YEAR":
        getElement(EXPIRY_MONTH_FIELD).click();
        getElement(exactText(value)).click();
        break;
      default:
        getElement(elementName).click();
        getElement(elementName).sendKeys(value);
    }
  }
}
