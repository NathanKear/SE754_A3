package unit;

import Account.AuthenticationService;
import Account.User;
import Account.UserDatabase;
import Account.UserType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestAccountManagement {
    UserDatabase _db;
    AuthenticationService _auth;

    @Before
    public void setup() {
        _db = mock(UserDatabase.class);
        _auth = new AuthenticationService(_db);

        when(_db.get(anyString(), anyString())).thenReturn(null);

        when(_db.add("user", "password", new User("user", UserType.BASIC))).thenReturn(true);
        when(_db.get("user", "password")).thenReturn(new User("user", UserType.BASIC));

        when(_db.add("admin", "password", new User("admin", UserType.ADMIN))).thenReturn(true);
        when(_db.get("admin", "password")).thenReturn(new User("admin", UserType.ADMIN));
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
        User existingAdmin = _auth.login("username", "password");
        assertEquals(new User("admin", UserType.ADMIN), existingAdmin);
    }

    @Test
    public void testNonExistentAdminLogin() {
        User nullAdmin = _auth.login("noadmin", "password");
        assertNull(nullAdmin);
    }

    @Test
    public void testNotLoggedInUserSignOut() {
        User newUser = _auth.signUp("user", "password", UserType.BASIC);
        boolean loggedOut = _auth.logout(newUser);
        assertEquals(true, loggedOut);
    }

    @Test
    public void testNotLoggedInAdminSignOut() {
        User newAdmin = _auth.signUp("user", "password", UserType.ADMIN);
        boolean loggedOut = _auth.logout(newAdmin);
        assertEquals(true, loggedOut);
    }
}
