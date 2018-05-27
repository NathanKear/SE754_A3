import acceptance.AcceptanceTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import unit.UnitTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ UnitTestSuite.class, AcceptanceTestSuite.class})
public class FullTestSuite {
}
