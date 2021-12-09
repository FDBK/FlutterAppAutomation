package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TrCardTestCase extends TestCase
{

    protected AppiumDriver driver;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        driver = TrCardPlatform.getInstance().getDriver();
    }

    @Override
    protected void tearDown() throws Exception
    {
        takeScreenshot(getName() + "-" + getDateTime());
        driver.quit();
        super.tearDown();
    }

    private void takeScreenshot(String name)
    {
        TakesScreenshot ts = this.driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/screenshots/" + name + ".png";
        try {
            FileUtils.copyFile(source, new File(path));
            System.out.println("Снимок экрана сохранён: " + path + ".");
        } catch (Exception e) {
            System.out.println("Ошибка! Не удалось сохранить снимок экрана (" + e.getMessage() + ").");
        }
    }

    private String getDateTime()
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy-HHmmss");
        return formatter.format(date);
    }

}
