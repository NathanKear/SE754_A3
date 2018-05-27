package unit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestAccountManagement.class,
        TestBusinessIdeaValidation.class,
        TestMarketComprehension.class,
        TestSearchQuery.class,
        TestSearchQueryService.class})
public class UnitTestSuite {
}
