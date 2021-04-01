package com.testing.pageObjects.pages;

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;

import io.cucumber.datatable.DataTable;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("/register")
public class RegisterPage extends TopMenuSection {
  // Locators
  // -------------------------------------------------------------------------------------------------------------------
  public static By USER_REGISTRATION_TITLE = text("User Registration");
  public static By EMAIL_FIELD = css("input[id=emailControl");
  public static By PASSWORD_FIELD = css("input[id=passwordControl");
  public static By REPEAT_PASSWORD_FIELD = css("input[id=repeatPasswordControl");
  public static By SECURITY_QUESTION = css("mat-select[role=combobox]");
  public static By NAME_OF_FAVORITE_PET_OPTION = By.xpath("//span[contains(text(), 'Name of your favorite pet?')]");
  public static By SECURITY_QUESTION_LIST = css("div[id^=mat-select][role=listbox]");
  public static By SECURITY_ANSWER_FIELD = css("input[id=securityAnswerControl]");
  public static By REGISTRATION_BUTTON = css("button[id=registerButton]");

  // Public methods
  // -------------------------------------------------------------------------------------------------------------------
  public void waitForPageToLoad() {
    getElement(USER_REGISTRATION_TITLE).waitUntilVisible();
    logWeAreOnPage();
  }

  public void setElementValueTo(String elementName, String value) {
    if (elementName.contains("EMAIL_FIELD") && value.contains("RANDOM_EMAIL")) {
      value = RandomStringUtils.randomAlphanumeric(10) + "@testdevlab.com";
      Serenity.setSessionVariable("RANDOM_EMAIL").to(value);
    }
    super.setElementValueTo(elementName, value);
  }

  public void performCreateAnAccount(DataTable data) {
    info("Creating an account, while on: " + this.getClass().getSimpleName());
    for (Map<String, String> map : dataToMap(data)) {
      for (String key : map.keySet()) {
        if(snakify(key).contains("FIELD")) {
          setElementValueTo(snakify(key), map.get(key));
        } else {
          if(snakify(key).contains("QUESTION")){
            getElement(SECURITY_QUESTION).click();
            for (WebElementFacade element : getElements(SECURITY_QUESTION_LIST)) {
              if(!element.getText().contains(map.get(key))) continue;
              scrollIntoView(element);
              element.click();
              break;
            }
          }
        }
      }
    }
    getElement(REGISTRATION_BUTTON).click();
    new LoginPage().waitForPageToLoad();
  }
}
