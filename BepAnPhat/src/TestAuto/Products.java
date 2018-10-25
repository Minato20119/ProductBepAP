package TestAuto;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Products {

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver", "C:/Users/nvlong/Downloads/chromedriver.exe");

		WebDriver driver = new ChromeDriver();
		WebDriverWait driverWait = new WebDriverWait(driver, 10);

		// Mở rộng cửa sổ trình duyệt lớn nhất
		driver.manage().window().maximize();

		// Wait 10s cho page được load thành công
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Get url
		driver.get("http://bepanphat.com/wp-admin");

		// Wait 10s cho page được load thành công
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Check open url success
		waitForVisible(driverWait, "//form[@id='loginform']");
		waitForVisible(driverWait, "//a[contains(text(),'Quay lại Bếp An Phát')]");

		// Login
		driver.findElement(By.xpath("//input[@id='user_login']")).sendKeys(Users.ADMIN_ACCOUNT);
		driver.findElement(By.xpath("//input[@id='user_pass']")).sendKeys(Users.ADMIN_PASSWORD);
		driver.findElement(By.xpath("//input[@id='wp-submit']")).click();

		// Check login success
		waitForVisible(driverWait, "//div[@id='wpadminbar']");
		waitForVisible(driverWait, "//a[contains(text(),'Bếp An Phát')]");

		// Click vao san pham
		driver.findElement(By.xpath("//div[contains(text(),'Sản phẩm')]")).click();
		waitForVisible(driverWait, "//h1[text()='Sản phẩm']");

		// Get number pages
		WebElement numberPages = driver.findElement(By.xpath("//*[@class='tablenav bottom']//*[@class='total-pages']"));
		int pages = Integer.parseInt(numberPages.getText());

		int number = 1;
		while (number < pages) {

			// Get list title
			List<WebElement> rowTitle = driver.findElements(By.xpath("//a[@class='row-title']"));

			// Get list id
			List<WebElement> listId = driver.findElements(By.xpath("//div[contains(@id,'inline_')]"));

			//	String[] id_product = new String[rowTitle.size()];

			int count = 0;
			for (WebElement title : rowTitle) {
				
				WebElement inline = listId.get(count);
				
				String id = inline.getAttribute("id");
				id = id.replaceAll("inline_", "");
				
				System.out.println(id + "," + title.getText());
				
				count++;
			}
			
			driver.findElement(By.xpath("//*[@class='tablenav bottom']//*[@class='next-page']")).click();
		
			number++;
			
			// Wait 10s cho page được load thành công
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		
		System.out.println("Done!");
	}

	private static void waitForVisible(WebDriverWait myWait, String xPath) {
		myWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
	}
}
