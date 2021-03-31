package com.testing.gherkinDefinitions;

import com.testing.stepDefinitions.JuiceShopSteps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;

public class JuiceShopDefinitions {
  JuiceShopSteps juiceShopSteps = new JuiceShopSteps();

  @Before
  public void setUp(){
    juiceShopSteps.setUp();
  }

  @After
  public void tearDown(){
  }
  

  @When("I click {string}")
  public void i_click(String elementName){
    juiceShopSteps.click(elementName);
  }

  @Given("I am on the {string} page")
  public void i_am_on_the_page(String page) {
    juiceShopSteps.validatePage(page);
  }

  @When("I set {string} to {string}")
  public void i_set_to(String elementName, String value) {
    juiceShopSteps.setElementValueTo(elementName, value);
  }

  @Then("I should be on {string} page")
  public void i_should_be_on_page(String page) {
    juiceShopSteps.validatePage(page);
  }

  @When("I search for {string}")
  public void i_search_for(String string) {
    juiceShopSteps.searchFor(string);
  }

  @Then("I see {string} with the following data:")
  public void i_see_with_following_data(String element, DataTable data) {
    juiceShopSteps.validateData(element, data);
  }

  @Then("I {string} with the following data:")
  public void i_with_following_data(String action, DataTable data) {
    juiceShopSteps.doSomethingWithData(action, data);
  }

  @Then("{string} should contain {string}")
  public void should_contain(String elementName, String value) {
    juiceShopSteps.validateElementValue(elementName, value);
  }

  @Then("{string} should be {string}")
  public void should_be_enabled_or_disabled(String elementName, String state) {
    juiceShopSteps.validateElementEnabledOrDisabled(elementName, state);
  }
}
