package com.testing.pageObjects.pages;

import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("/payment/wallet")
public class PaymentWalletPage extends PaymentShopPage{
  // Public methods
  // -------------------------------------------------------------------------------------------------------------------
  public void click(String elementName){
    super.click(elementName);
    if(elementName.equals("CONTINUE_BUTTON")) getElement(CONTINUE_BUTTON).waitUntilNotVisible();
  }
}
