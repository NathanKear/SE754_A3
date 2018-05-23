package unit;

import Account.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestAccountManagement {
    UserDatabase _userDb;
    AuthenticationService _auth;
    UserManager _userManager;
    SearchDatabase _searchDb;

    @Before
    public void setup() {
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


    @Test
    public void testUserSignUp() {
        User newUser = _auth.signUp("user", "password", UserType.BASIC);
        assertEquals(new User("user", UserType.BASIC), newUser);
    }

    @Test
    public void testUserLogin() {
        User existingUser = _auth.login("user", "password");
        assertEquals(new User("user", UserType.BASIC), existingUser);
    }

    @Test
    public void testNonExistentUserLogin() {
        User nullUser = _auth.login("nouser", "password");
        assertNull(nullUser);
    }

    @Test
    public void testAdminSignUp() {
        User newAdmin = _auth.signUp("admin", "password", UserType.ADMIN);
        assertEquals(new User("admin",  UserType.ADMIN), newAdmin);
    }

    @Test
    public void testAdminLogin() {
        User existingAdmin = _auth.login("admin", "password");
        assertEquals(new User("admin", UserType.ADMIN), existingAdmin);
    }

    @Test
    public void testNonExistentAdminLogin() {
        User nullAdmin = _auth.login("noadmin", "password");
        assertNull(nullAdmin);
    }

    @Test
    public void testLoggedInUserSignOut() {
        User newUser = _auth.signUp("user", "password", UserType.BASIC);
        boolean loggedOut = _auth.logout(newUser);
        assertEquals(true, loggedOut);
    }

    @Test
    public void testLoggedInAdminSignOut() {
        User newAdmin = _auth.signUp("user", "password", UserType.ADMIN);
        boolean loggedOut = _auth.logout(newAdmin);
        assertEquals(true, loggedOut);
    }

    @Test
    public void testAdminGetUserCount() {
        List<User> users = new ArrayList();
        users.add(new User("user", UserType.BASIC));
        users.add(new User("admin", UserType.ADMIN));
        when(_userDb.getAll()).thenReturn(users);

        User admin = _auth.login("admin", "password");
        try {
            int userCount = _userManager.getRegisteredUserCount(admin);
            assertEquals(2, userCount);
        } catch (NotAuthorizedException e) {
            fail();
        }
    }

    @Test
    public void testNonAdminGetUserCount() {
        try {
            User user = _auth.login("user", "password");
            _userManager.getRegisteredUserCount(user);
        } catch (NotAuthorizedException e) {
            assertEquals(NotAuthorizedException.class, e.getClass());
            assertEquals("Action requires admin permissions", e.getMessage());
        }
    }

    @Test
    public void testSessionSearchHistory() {
        User user = _auth.login("user", "password");
        UUID sessionId = _auth.getSessionID(user);

        List<String> searches = new ArrayList();
        searches.add("Parnell dog walking");
        searches.add("Alaskan bob sledding");
        System.out.println(sessionId);

        when(_searchDb.get(user, sessionId)).thenReturn(searches);

        assertEquals(searches.size(), _userManager.getUserSessionSearchCount(user, sessionId));
    }
}
