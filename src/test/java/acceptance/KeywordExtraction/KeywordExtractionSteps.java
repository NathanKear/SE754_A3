package acceptance.KeywordExtraction;

import Search.NaturalLanguageProcessor;
import Search.SearchQuery;
import Search.SearchQueryService;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class KeywordExtractionSteps {

    private final String shortPhrase = "The quick brown fox";
    private final String mediumPhrase = "The quick brown fox jumps";
    private final String longPhrase = "The quick brown fox jumps over the lazy dog";

    private SearchQueryService _searchQueryService;
    private SearchQuery _searchQuery;

    @Given("Application is open")
    public void applicationIsOpen() {
        NaturalLanguageProcessor naturalLanguageProcessor = mock(NaturalLanguageProcessor.class);
        _searchQueryService = new SearchQueryService(naturalLanguageProcessor);

        when(naturalLanguageProcessor.findKeywords(anyString())).thenReturn(new String[] {});
        when(naturalLanguageProcessor.findKeywords(shortPhrase)).thenReturn(new String[] { "fox" });
        when(naturalLanguageProcessor.findKeywords(mediumPhrase)).thenReturn(new String[] { "fox", "jumps" });
        when(naturalLanguageProcessor.findKeywords(longPhrase)).thenReturn(new String[] { "fox", "jumps", "dog" });
    }

    @When("I input the business idea \"$phrase\"")
    public void inputBusinessIdea(String phrase) {
        _searchQuery = _searchQueryService.createSearchQuery(phrase);
    }

    @When("I increase priority of keyword $keyword")
    public void increaseKeywordPriority(String keyword) {
        _searchQuery.adjustKeywordWeighting(keyword, 1);
    }

    @When("I decrease priority of keyword $keyword")
    public void decreaseKeywordPriority(String keyword) {
        _searchQuery.adjustKeywordWeighting(keyword, -1);
    }

    @When("I increase priority of keyword $keyword by $amount")
    public void increaseKeywordPriority(String keyword, double amount) {
        _searchQuery.adjustKeywordWeighting(keyword, amount);
    }

    @When("I decrease priority of keyword $keyword by $amount")
    public void decreaseKeywordPriority(String keyword, double amount) {
        _searchQuery.adjustKeywordWeighting(keyword, -amount);
    }

    @When("I add the keyword $keyword")
    public void addKeyword(String keyword) {
        _searchQuery.addKeyword(keyword);
    }

    @When("I remove the keyword $keyword")
    public void removeKeyword(String keyword) {
        _searchQuery.removeKeyword(keyword);
    }

    @Then("Keywords found $keywords")
    public void keywordFound(List<String> keywords) {
        Assert.assertEquals(keywords.size(), _searchQuery.getKeywords().size());

        for (String keyword : keywords) {
            Assert.assertTrue(_searchQuery.getKeywords().contains(keyword));
        }
    }

    @Then("No keywords found")
    public void noKeywordFound() {
        Assert.assertEquals(0, _searchQuery.getKeywords().size());
    }

    @Then("Keywords in order of priority are $keywords")
    public void keywordsInPriorityOrder(List<String> keywords) {
        Assert.assertEquals(keywords.size(), _searchQuery.getKeywords().size());

        for (int i = 0; i < keywords.size(); i++) {
            Assert.assertEquals(keywords.get(i), _searchQuery.getKeywords().get(i));
        }
    }
}
