package cs601;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ IntegrationTest.class ,UnitTest.class , SystemTest.class })
public class AllTests {
 
}
