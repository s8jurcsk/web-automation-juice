package com.testing.stepDefinitions;

import io.cucumber.datatable.DataTable;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.WebElementFacade;

import com.testing.pageObjects.PageContainer;
import com.testing.pageObjects.pages.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.fail;
import static support.StringEditing.snakify;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;

import org.apache.commons.text.WordUtils;

public class JuiceShopSteps {
  private final PageContainer PAGE_CONTAINER;
  private final Logger logger;
  
  public JuiceShopSteps(){
    PAGE_CONTAINER = new PageContainer();
    logger = LoggerFactory.getLogger(this.getClass());
    Serenity.setSessionVariable("PAGE_CONTAINER").to(PAGE_CONTAINER);
  }

  public void info(String string) {
    logger.info(string);
  }

  public void setUp() {
    PAGE_CONTAINER.getPage("HOME_PAGE").open();
  }

  public void validateElementEnabledOrDisabled(String elementName, String state){
    info("Validating that \"" + snakify(elementName) + "\" is \"" + state +"\"");
    Boolean stateTrue = false;

    switch(state){
      case "enabled":
      stateTrue = !isDisabled(snakify(elementName));
      break;
      case "disabled":
        stateTrue = isDisabled(snakify(elementName));
        break;
      case "visible":
        stateTrue = getCurrentPage().getElement(snakify(elementName)).isVisible();
        break;
      case "not visible":
        stateTrue = !getCurrentPage().getElement(snakify(elementName)).isVisible();
        break;
      default: fail("The given state is undefined: " + state);
    }
    if(!stateTrue) fail("Expected " + snakify(elementName) + " to be " + state);
  }

  public BasePage getCurrentPage() {
    return PAGE_CONTAINER.getCurrentPage();
  }

  public void click(String elementName) {
    getCurrentPage().click(snakify(elementName));
  }

  public void validatePage(String page) {
    String formattedPageName = page.toUpperCase() + "_PAGE";
    try{
      PAGE_CONTAINER.getPage(formattedPageName).waitForPageToLoad();
    }catch(NoSuchElementException e){
      fail("Failed to validate: " + formattedPageName);
    }
  }

  public void setElementValueTo(String elementName, String value) {
    info("Setting value of " + snakify(elementName) + " to " + value);
    getCurrentPage().setElementValueTo(snakify(elementName), value);
  }

  public void validateElement(String elementName) {
    info("Validating element: " + snakify(elementName));
    getCurrentPage().getElement(snakify(elementName)).waitUntilVisible();
  }

  public void searchFor(String string) {
    ((TopMenuSection) PAGE_CONTAINER.getPage("TOP_MENU_SECTION")).searchFor(string);
    PAGE_CONTAINER.getPage("SEARCH_RESULTS_PAGE").getElement("SEARCH_RESULTS_TITLE").waitUntilVisible();
  }

  public void validateData(String elementName, DataTable data) {
    info("Validating: " + elementName);

    String methodName = "validate" + WordUtils.capitalizeFully(elementName).replaceAll("\\s+", "");
    Boolean passed = false;
    Boolean found = false;
    BasePage currentPage = getCurrentPage();
    Method[] methods = currentPage.getClass().getMethods();

    for (Method method : methods) {
      if (!method.toString().contains(methodName)) continue;

      found = true;

      try {
        info(currentPage.getClass().getSimpleName());
        passed = (Boolean) method.invoke(currentPage, data);
      } catch (IllegalAccessException | IllegalArgumentException e) {
        undefinedMethodFailure(methodName);
      } catch (InvocationTargetException e){
        fail("Failed to execute: " + methodName);
      }
    }

    if(!found) undefinedMethodFailure(methodName);
    if(!passed) fail("Validation failed for " + elementName);
  }

  public void doSomethingWithData(String action, DataTable data) {
    info("Performing: " + action);

    String methodName = "perform" + WordUtils.capitalizeFully(action).replaceAll("\\s+", "");
    BasePage currentPage = getCurrentPage();
    Method[] methods = currentPage.getClass().getMethods();
    Boolean found = false;

    for (Method method : methods) {
      if (!method.toString().contains(methodName)) continue;

      found = true;
      try {
        method.invoke(currentPage, data);
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        fail("Failed to execute: " + methodName);
      }
    }

    if(!found) undefinedMethodFailure(methodName);
  }

  public void validateElementValue(String elementName, String value) {
    info("Validating element: " + snakify(elementName));

    Integer last = 5;
    BasePage currentPage = getCurrentPage();
    WebElementFacade element;
    String elementValue = null;

    for (int i = 1; i <= last; i++) {
      try {
        element = currentPage.getElement(snakify(elementName));
        elementValue = element.getText();
      } catch (org.openqa.selenium.StaleElementReferenceException e) {
        info("Failed retrieve value of element, retrying...");
        if (i == last) fail("Failed to retrieve value");
      }
    }

    if(elementValue.contains(value)){
      info("Validation for element " + snakify(elementName) + " Passed");
    } else {
      info("Expected to have value: \"" + value + "\" instead of \"" + elementValue + "\"");
      fail("Validation for element: " + snakify(elementName) + " Failed");
    }
  }

  // Auxillary
  // -------------------------------------------------------------------------------------------------------------------
  public void undefinedMethodFailure(String methodName){
    fail("Given method is undefined: " + methodName);
  }

  public Boolean isDisabled(String elementName){
    WebElementFacade element = getCurrentPage().getElement(elementName);
    String ariaDisabled = element.getAttribute("aria-disabled");

    if(ariaDisabled != null && ariaDisabled.equals("true")) return true;
    return element.isDisabled();
  }
}
