package com.testing.pageObjects.pages;

import static org.junit.Assert.fail;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.cucumber.datatable.DataTable;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("/delivery-method")
public class DeliveryMethodPage extends AddressSelectPage {
  // Locators
  // -------------------------------------------------------------------------------------------------------------------
  public static By DELIVERY_SPEED_TITLE = text("Delivery Address");
  public static By CONTINUE_BUTTON = ariaLabel("Proceed to delivery method selection");
  // Public methods
  // -------------------------------------------------------------------------------------------------------------------
  public void waitForPageToLoad(){
    getElement(DELIVERY_SPEED_TITLE).waitUntilVisible();
    logWeAreOnPage();
  }

  public void performSelectDeliverySpeed(DataTable data){
    String deliverySpeed = null;;
    Boolean found = null;;

    for (Map<String, String> map : dataToMap(data)) {
      deliverySpeed = map.get("Delivery speed");
      found = false;

      for (WebElement element : getElements(ROW_ITEMS)) {
        if(!element.getText().contains(deliverySpeed)) continue;
        element.click();
        found = true;
      }
      if(found) break;
    }
    if(!found) fail("Failed to select given delivery type: " + deliverySpeed);
  }
}
