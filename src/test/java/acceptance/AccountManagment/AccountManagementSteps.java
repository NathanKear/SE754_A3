package acceptance.AccountManagment;

import Account.*;
import Search.NaturalLanguageProcessor;
import Search.SearchQuery;
import Search.SearchQueryService;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountManagementSteps {
    UserDatabase _userDb;
    AuthenticationService _auth;
    UserManager _userManager;
    SearchDatabase _searchDb;
    User _user;

    @Given("Application is open")
    public void applicationIsOpen() {
        _auth = new AuthenticationService(_userDb);
        _userDb = mock(UserDatabase.class);
        when(_userDb.get(anyString(), anyString())).thenReturn(null);
        when(_userDb.add("user", "password", new User("user", UserType.BASIC))).thenReturn(true);
        when(_userDb.get("user", "password")).thenReturn(new User("user", UserType.BASIC));
        when(_userDb.add("admin", "password", new User("admin", UserType.ADMIN))).thenReturn(true);
        when(_userDb.get("admin", "password")).thenReturn(new User("admin", UserType.ADMIN));
    }

    @Given("I have an account with username $username and password $password")
    public void makeAccount(String username, String password) {
        applicationIsOpen();
        _user = _auth.signUp(username, password, UserType.BASIC);
    }

    @When("I enter the username $username and password $password")
    public void login(String username, String password) {
        _user = _auth.login(username, password);
    }

    @When("I enter the username $username and password $password")
    public void signup(String username, String password) {
        _user = _auth.signUp(username, password, UserType.BASIC);
    }

    @Then("I have a new account created for me with username $username")
    public void userCreated(String username) {
        assertEquals(username, _user.getUsername());
    }

    @Then("I am logged into my account with username $username")
    public void loggedIn(String username) {
        assertEquals(username, _user.getUsername());
    }
}
