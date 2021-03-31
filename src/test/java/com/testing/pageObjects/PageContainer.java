package com.testing.pageObjects;

import java.util.HashMap;
import java.util.Map;

import net.thucydides.core.annotations.DefaultUrl;

import com.testing.pageObjects.pages.*;

import static support.StringEditing.snakify;
import static org.junit.Assert.fail;

public class PageContainer{
  private HashMap<String, BasePage> pages;

  public PageContainer(){
    pages = new HashMap<String, BasePage>();

    pages.put("BASE_PAGE", new BasePage());
    pages.put("HOME_PAGE", new HomePage());
    pages.put("LOGIN_PAGE", new LoginPage());
    pages.put("REGISTER_PAGE", new RegisterPage());
    pages.put("SEARCH_RESULTS_PAGE", new SearchResultsPage());
    pages.put("TOP_MENU_SECTION", new TopMenuSection());
    pages.put("BASKET_PAGE", new BasketPage());
    pages.put("ADDRESS_SELECT_PAGE", new AddressSelectPage());
    pages.put("ADDRESS_CREATE_PAGE", new AddressCreatePage());
    pages.put("DELIVERY_METHOD_PAGE", new DeliveryMethodPage());
    pages.put("PAYMENT_SHOP_PAGE", new PaymentShopPage());
    pages.put("CHANGE_PASSWORD_PAGE", new ChangePasswordPage());
    pages.put("WALLET_PAGE", new WalletPage());
    pages.put("PAYMENT_WALLET_PAGE", new PaymentWalletPage());
  }

  public BasePage getPage(String page){
    BasePage pageObject = pages.get(snakify(page));
    if(pageObject == null) fail("Given page is undefined: " + snakify(page));
    return pageObject;
  }

  public BasePage getCurrentPage(){
    String[] url = getPage("BASE_PAGE").getDriver().getCurrentUrl().split("/#/");
    BasePage page = null;
    String annotationUrl;

    if(url.length == 1){
      page = getPage("HOME_PAGE");
    } else {
      url[1] = "/" + url[1];
      for (Map.Entry<String, BasePage> entry : pages.entrySet()) {
        if(entry.getValue().getClass().getAnnotation(DefaultUrl.class) == null) continue;

        annotationUrl = entry.getValue().getClass().getAnnotation(DefaultUrl.class).value();
        if(annotationUrl.length() <= 1) continue;
        if(url[1].equals(annotationUrl) || (url[1].contains("/search?q=") && url[1].contains(annotationUrl))){
          page = pages.get(entry.getKey());
          break;
        }
      }
    }
    if(page == null) fail("Given page is undefined: " + url[1]); else page.waitForPageToLoad();
    return page;
  }
}
