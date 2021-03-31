package com.testing.pageObjects.pages;

import org.openqa.selenium.By;

import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("/address/create")
public class AddressCreatePage extends AddressSelectPage {
  // Locators
  // -------------------------------------------------------------------------------------------------------------------
  public static By CREATE_AN_ADDRESS_TITLE;
  public static By COUNTRY_FIELD;
  public static By NAME_FIELD;
  public static By MOBILE_NUMBER_FIELD;
  public static By ZIP_CODE_FIELD;
  public static By ADDRESS_FIELD;
  public static By CITY_FIELD;
  public static By STATE_FIELD;
  // Public methods
  // -------------------------------------------------------------------------------------------------------------------
  public void waitForPageToLoad(){
    getElement(CREATE_AN_ADDRESS_TITLE).waitUntilVisible();
    logWeAreOnPage();
  }

  public void click(String elementName){
    super.click(snakify(elementName));
    if(snakify(elementName).equals("SUBMIT_BUTTON")) getElement(SELECT_AN_ADDRESS_TITLE).waitUntilVisible();
  }
}
