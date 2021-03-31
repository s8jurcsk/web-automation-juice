package com.testing.pageObjects.pages;

import static org.junit.Assert.fail;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.cucumber.datatable.DataTable;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("/address/select")
public class AddressSelectPage extends BasketPage {
  // Locators
  // -------------------------------------------------------------------------------------------------------------------
  public static By SELECT_AN_ADDRESS_TITLE;
  public static By CONTINUE_BUTTON;
  public static By ADD_NEW_ADDRESS_BUTTON;
  // Public methods
  // -------------------------------------------------------------------------------------------------------------------
  public void waitForPageToLoad(){
    getElement(SELECT_AN_ADDRESS_TITLE).waitUntilVisible();
    logWeAreOnPage();
  }

  public void performSelectAddress(DataTable data){
    String address = null;
    Boolean found = null;;

    for (Map<String, String> map : dataToMap(data)) {
      address = map.get("Address");
      found = false;

      for (WebElement element : getElements(ROW_ITEMS)) {
        if(!element.getText().contains(address)) continue;
        element.click();
        found = true;
        break;
      }
      if(found) break;
    }
    if(!found) fail("Failed to select given address: " + address);
  }

  public Boolean validateAddress(DataTable data){
    String name;
    String address;
    String country;
    Boolean found;

    for (Map<String, String> map : dataToMap(data)) {
      name = map.get("Name");
      address = map.get("Address");
      country = map.get("Country");
      found = false;

      for (WebElementFacade element : getElements(ROW_ITEMS)) {
        String text = element.getText();

        if(text.contains(name) && text.contains(address) && text.contains(country)) found = true;
      }
      if(!found) return false;
    }
    return true;
  }
}
