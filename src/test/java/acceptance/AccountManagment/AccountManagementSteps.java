package acceptance.AccountManagment;

import Account.AuthenticationService;
import Account.SearchDatabase;
import Account.UserDatabase;
import Account.UserManager;
import Search.NaturalLanguageProcessor;
import Search.SearchQuery;
import Search.SearchQueryService;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import java.util.List;

import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountManagementSteps {
    UserDatabase _userDb;
    AuthenticationService _auth;
    UserManager _userManager;
    SearchDatabase _searchDb;

    private SearchQueryService _searchQueryService;
    private SearchQuery _searchQuery;

    @Given("Application is open")
    public void applicationIsOpen() {
        NaturalLanguageProcessor naturalLanguageProcessor = mock(NaturalLanguageProcessor.class);
        _searchQueryService = new SearchQueryService(naturalLanguageProcessor);

        when(naturalLanguageProcessor.findKeywords(anyString())).thenReturn(new String[] {});
        when(naturalLanguageProcessor.findKeywords(shortPhrase)).thenReturn(new String[] { "fox" });
        when(naturalLanguageProcessor.findKeywords(longPhrase)).thenReturn(new String[] { "fox", "jumps", "dog" });
    }

    @When("I input the business idea \"$phrase\"")
    public void inputBusinessIdea(String phrase) {
        _searchQuery = _searchQueryService.createSearchQuery(phrase);
    }

    @Then("Keywords found $keywords")
    public void keywordFound(List<String> keywords) {
        fail();
        Assert.assertEquals(_searchQuery.getKeywords().size(), keywords.size());

        for (String keyword : keywords) {
            Assert.assertTrue(keywords.contains(keyword));
        }
    }

    @Then("No keywords found")
    public void noKeywordFound() {
        fail();
    }
}
