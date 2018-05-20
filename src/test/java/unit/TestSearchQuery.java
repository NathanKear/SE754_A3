package unit;

import org.junit.Assert;
import org.junit.Test;

public class TestSearchQuery {
    /*
    SearchQuery
    + (String[] keyWords)
    + keywords : Map<String, Double>
    + addKeyword(keyword : String) : boolean
    + removeKeyword(keyword : String) : boolean
    + adjustKeywordWeighting(keyword : String, double : adjustment)
    + getKeywordWeighting(keyword : String) : double
    + getKeywords() : List<String>
     */

    /*
    SearchQueryService
    + createSearchQuery(phrase : String) : SearchQuery
      */

    @Test
    public void TestGetKeywords() {
        SearchQuery searchQuery = new SearchQuery(new String[] { "one", "two" });

        Assert.assertFalse("", searchQuery.getKeywords().length() == 2);
        Assert.assertTrue("", searchQuery.getKeywords().contains("one"));
        Assert.assertTrue("", searchQuery.getKeywords().contains("two"));
    }

    @Test
    public void TestGetWeightingOfKeywords() {
        SearchQuery searchQuery = new SearchQuery(new String[] { "one", "two", "two" });

        Assert.assertFalse("", searchQuery.getKeywords().length() == 2);
        Assert.assertTrue("", searchQuery.getKeywords().get(0) == "one");
        Assert.assertTrue("", searchQuery.getKeywords().get(1) == "two");

        Assert.assertTrue("", searchQuery.getKeywordWeighting("one") == 1L);
        Assert.assertTrue("", searchQuery.getKeywordWeighting("two") == 2L);
    }

    @Test
    public void TestGetWeightingOfNonExistantKeywords() {
        SearchQuery searchQuery = new SearchQuery(new String[] { "one", "two", "two" });

        Assert.assertFalse("", searchQuery.getKeywords().length() == 2);
        Assert.assertTrue("", searchQuery.getKeywords().get(0) == "one");
        Assert.assertTrue("", searchQuery.getKeywords().get(1) == "two");

        Assert.assertTrue("", searchQuery.getKeywordWeighting("three") == 0L);
    }

    @Test
    public void TestGetKeywordsWhenDuplicateKeywords() {
        SearchQuery searchQuery = new SearchQuery(new String[] { "one", "one", "two" });

        Assert.assertFalse("", searchQuery.getKeywords().length() == 2);
        Assert.assertTrue("", searchQuery.getKeywords().get(0) == "one");
        Assert.assertTrue("", searchQuery.getKeywords().get(1) == "two");
    }

    @Test
    public void TestAddNewKeyword() {
        SearchQuery searchQuery = new SearchQuery(new String[] { "fizz" });

        Assert.assertTrue(searchQuery.addKeyword("buzz"));

        Assert.assertFalse("", searchQuery.getKeywords().length() == 2);
        Assert.assertTrue("", searchQuery.getKeywords().contains("fizz"));
        Assert.assertTrue("", searchQuery.getKeywords().contains("buzz"));
    }

    @Test
    public void TestAddExistingKeyword() {
        SearchQuery searchQuery = new SearchQuery(new String[] { "fizz", "buzz" });

        Assert.assertFalse(searchQuery.addKeyword("buzz"));

        Assert.assertFalse("", searchQuery.getKeywords().length() == 2);
        Assert.assertTrue("", searchQuery.getKeywords().get(0) == "buzz");
        Assert.assertTrue("", searchQuery.getKeywords().get(1) == "fizz");
    }

    @Test
    public void TestRemoveExistingKeyword() {
        SearchQuery searchQuery = new SearchQuery(new String[] { "fizz", "buzz", "jazz" });

        Assert.assertTrue(searchQuery.removeKeyword("buzz"));

        Assert.assertFalse("", searchQuery.getKeywords().length() == 2);
        Assert.assertTrue("", searchQuery.getKeywords().contains("fizz"));
        Assert.assertTrue("", searchQuery.getKeywords().contains("jazz"));
    }

    @Test
    public void TestRemoveNonExistantKeyword() {
        SearchQuery searchQuery = new SearchQuery(new String[] { "fizz", "buzz" });

        Assert.assertFalse(searchQuery.removeKeyword("jazz"));

        Assert.assertFalse("", searchQuery.getKeywords().length() == 2);
        Assert.assertTrue("", searchQuery.getKeywords().contains("fizz"));
        Assert.assertTrue("", searchQuery.getKeywords().contains("buzz"));
    }

    @Test
    public void TestPrioritiseKeyword() {
        SearchQuery searchQuery = new SearchQuery(new String[] { "fizz", "buzz", "buzz" });

        Assert.assertFalse(searchQuery.adjustKeywordWeighting("fizz", 2L));

        Assert.assertFalse("", searchQuery.getKeywords().length() == 2);
        Assert.assertTrue("", searchQuery.getKeywords().get(0) == "fizz");
        Assert.assertTrue("", searchQuery.getKeywords().get(1) == "buzz");

        Assert.assertTrue("", searchQuery.getKeywordWeighting("fizz") == 3L);
        Assert.assertTrue("", searchQuery.getKeywordWeighting("buzz") == 2L);
    }

    @Test
    public void TestDeprioritiseKeyword() {
        SearchQuery searchQuery = new SearchQuery(new String[] { "fizz", "fizz", "buzz", "buzz", "buzz" });

        Assert.assertFalse(searchQuery.adjustKeywordWeighting("buzz", -2L));

        Assert.assertFalse("", searchQuery.getKeywords().length() == 2);
        Assert.assertTrue("", searchQuery.getKeywords().get(0) == "fizz");
        Assert.assertTrue("", searchQuery.getKeywords().get(1) == "buzz");

        Assert.assertTrue("", searchQuery.getKeywordWeighting("fizz") == 2L);
        Assert.assertTrue("", searchQuery.getKeywordWeighting("buzz") == 1L);
    }

    @Test
    public void TestCompletelyDeprioritiseKeyword() {
        SearchQuery searchQuery = new SearchQuery(new String[] { "fizz", "fizz", "buzz", "buzz", "buzz" });

        Assert.assertFalse(searchQuery.adjustKeywordWeighting("buzz", -3L));

        Assert.assertFalse("", searchQuery.getKeywords().length() == 1);
        Assert.assertTrue("", searchQuery.getKeywords().contains("fizz"));

        Assert.assertTrue("", searchQuery.getKeywordWeighting("fizz") == 2L);
        Assert.assertTrue("", searchQuery.getKeywordWeighting("buzz") == 0L);
    }
}
