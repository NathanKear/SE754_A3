package acceptance.AccountManagement;

import Account.*;
import Search.NaturalLanguageProcessor;
import Search.SearchQuery;
import Search.SearchQueryService;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountManagementSteps {
    UserDatabase _userDb;
    AuthenticationService _auth;
    UserManager _userManager;
    SearchDatabase _searchDb;
    User _user;
    boolean _loggedOut;
    int _sessionCount;
    int _userCount;

    public AccountManagementSteps() {
        _userDb = mock(UserDatabase.class);
        _searchDb = mock(SearchDatabase.class);
        _auth = new AuthenticationService(_userDb);
        _userManager = new UserManager(_userDb, _searchDb);
        when(_userDb.get(anyString(), anyString())).thenReturn(null);
        when(_userDb.add("user", "password", new User("user", UserType.BASIC))).thenReturn(true);
        when(_userDb.get("user", "password")).thenReturn(new User("user", UserType.BASIC));
        when(_userDb.add("admin", "password", new User("admin", UserType.ADMIN))).thenReturn(true);
        when(_userDb.get("admin", "password")).thenReturn(new User("admin", UserType.ADMIN));
    }

    @Given("I am in the application")
    public void applicationIsOpen() {
            }

    @When("I signup for a $accountType account with the username $username and password $password")
    public void signup(String accountType, String username, String password) {
        System.out.println(username+ password);
        _auth = new AuthenticationService(_userDb);

        _user = _auth.signUp(username, password, UserType.valueOf(accountType));
    }

    @Then("I have a new $accountType account created for me with username $username")
    public void userCreated(String accountType, String username) {
        assertEquals(username, _user.getUsername());
        assertEquals(UserType.valueOf(accountType), _user.getType());
    }

    //    Scenario: User log in
    //    Given I have an account with username user and password password
    //    When I enter the username user and password password
    //    Then I am logged into my account with username user
    @Given("I have a $accountType account with username $username and password $password")
    public void makeAccount(String accountType, String username, String password) {
        applicationIsOpen();
        _user = _auth.signUp(username, password, UserType.valueOf(accountType));
    }

    @When("I enter the username $username and password $password")
    public void login(String username, String password) {
        _user = _auth.login(username, password);
    }

    @Then("I am logged into my $accountType account with username $username")
    public void loggedIn(String accountType, String username) {
        assertEquals(username, _user.getUsername());
        assertEquals(UserType.valueOf(accountType), _user.getType());
    }

//    Scenario: User log off
//    Given I am logged in with a accountTYpe account
    @Given("I am logged in with a $accountType account")
    public void makeAccountAndLogIn(String accountType) {
        applicationIsOpen();
        _user = _auth.signUp("user", "password", UserType.valueOf(accountType));
    }
//    When I log out of my account
    @When("I log out of my account")
    public void logOut() {
        _loggedOut = _auth.logout(_user);
    }

    //    Then I am logged off
    @Then("I am logged out")
    public void testLogOut() {
        assertTrue(_loggedOut);
    }

//    Scenario: User search session count
//    WHEN I request account information
    @When("I request account information")
    public void accountInformation() {
        _user = _auth.login("user", "password");
        UUID sessionId = _auth.getSessionID(_user);

        List<String> searches = new ArrayList();
        searches.add("Parnell dog walking");
        searches.add("Alaskan bob sledding");
        System.out.println(sessionId);

        when(_searchDb.get(_user, sessionId)).thenReturn(searches);
        _sessionCount = _userManager.getUserSessionSearchCount(_user, sessionId);
    }


//    THEN I want to see number of searches in the current session
    @Then("I want to see number of searches in the current session")
    public void getSessionSearchCount() {
        assertEquals(2, _sessionCount);
    }

    @Then("I want to see total number of searches")
    public void getTotalSearchCount() {
        assertEquals(2, _sessionCount);
    }

//    GIVEN I am logged in as an administrator
    @Given("I am logged in as an administrator")
    public void adminLogin() {
        _user = _auth.signUp("admin", "password", UserType.ADMIN);
    }

//  WHEN I request the information
    @When("I request the information")
    public void getRegisteredUserCount() {
        try {
            _userCount = _userManager.getRegisteredUserCount(_user);
        } catch (Exception e) {}
    }
//    THEN I want to see number of registered users
    @Then("I want to see number of registered users")
    public void checkRegisteredUserCount() {
        assertEquals(2, _userCount);
    }

}
