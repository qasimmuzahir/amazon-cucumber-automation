package amazon;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import static org.hamcrest.Matchers.containsString;

import java.util.concurrent.TimeUnit;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Stepdefs {
	
	WebDriver driver = null;

	@Given("^user opens amazon homepage \"([^\"]*)\"$")
	public void user_opens_amazon_homepage(String url) {
		System.setProperty("webdriver.gecko.driver", "..\\amazon-cucumber-automation\\src\\test\\resources\\geckodriver.exe");
		try {
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
			driver.get("https://" + url);
			Assert.assertThat(driver.getCurrentUrl(), containsString(url));
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			driver.quit();
		}
	}

	@Given("^user search text \"([^\"]*)\" in search field$")
	public void user_search_text_in_search_field(String search_text) {
		try {
			driver.findElement(By.id("twotabsearchtextbox")).sendKeys(search_text);
			driver.findElement(By.xpath("//div[@id='nav-search']/form/div[@class='nav-right']/div/input[@class='nav-input']")).click();			
			driver.findElement(By.xpath("//a[@id='bcKwText']/span")).getText().equals(search_text);
		} catch (NoSuchElementException e) {
			driver.quit();
			e.printStackTrace();
		}
	}

	@Given("^user sorts displayed products from highest to lowest price$")
	public void user_sorts_displayed_products_from_highest_to_lowest_price() {
		try {
			Select select = new Select(driver.findElement(By.xpath("//select[@id='sort']")));
			select.selectByVisibleText("Price: High to Low");
			driver.findElement(By.xpath("//div[@id='centerPlus']/h3/span[1]")).getText().equals("Price: High to Low");
		} catch (NoSuchElementException e) {
			driver.quit();
			e.printStackTrace();
		}
	}

	@When("^user clicks on the second product from the displayed products list$")
	public void user_clicks_on_the_second_product_from_the_displayed_products_list() {
		try {
			driver.findElement(By.xpath("//li[@id='result_1']//a[contains(@class, 's-access-detail-page')]")).click();
		} catch (Exception e) {
			driver.quit();
			e.printStackTrace();
		}
	}

	@Then("^user verifies the product name contains text \"([^\"]*)\"$")
	public void user_verifies_the_product_name_contains_text(String text) {
		try {
			String title_text = driver.findElement(By.id("productTitle")).getText();
			Assert.assertThat(title_text, containsString(text));
		} catch (Exception e) {
			driver.quit();
			e.printStackTrace();
		} catch (AssertionError e) {
			System.out.println("Text \""+text+"\" not found in product title");
			driver.quit();
		}
	}

	@Then("^user closes the browser$")
	public void user_closes_the_browser() {
		driver.quit();
	}
}
