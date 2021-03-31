package com.testing.pageObjects.pages;

import org.openqa.selenium.By;

import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("/wallet")
public class WalletPage extends HomePage {
  // Locators ----------------------------------------------------------------------------------------------------------
  public static By DISMISS_BUTTON;
  public static By WALLET_TITLE;
  public static By WALLET_BALANCE;
  public static By AMOUNT_FIELD;

  // Public methods ----------------------------------------------------------------------------------------------------
  public void waitForPageToLoad() {
    getElement(WALLET_TITLE).waitUntilPresent();
    logWeAreOnPage();
  }
}
