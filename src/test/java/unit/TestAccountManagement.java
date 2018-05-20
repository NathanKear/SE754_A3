package unit;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestAccountManagement {
    Database _db;
    AuthenticationService _auth;

    @Before
    public void setup() {
        _db = mock(Database.class);
        _auth = new AuthenticationServer(_db);

        when(_db.get(anyString(), anyString()).thenReturn(null));

        when(_db.add("user", "password", UserType.USER)).thenReturn(new User("user", UserType.USER));
        when(_db.get("user", "password")).thenReturn(new User("user", UserType.USER));

        when(_db.add("admin", "password", UserType.ADMIN).thenReturn(new User("admin", UserType.ADMIN));
        when(_db.get("admin", "password")).thenReturn(new User("admin", UserType.ADMIN));
    }

    @Test
    public void testUserSignUp() {
        User newUser = auth.signUp("user", "password", UserType.USER);
        assertEquals(new User("user", UserType.USER), newUser);
    }

    @Test
    public void testUserLogin() {
        User existingUser = auth.login("user", "password");
        assertEquals(new User("user", UserType.USER), existingUser);
    }

    @Test
    public void testNonExistentUserLogin() {
        fail();
    }

    @Test
    public void testAdminSignUp() {
        User newAdmin = auth.signUp("admin", "password", UserType.ADMIN);
        assertEquals(new User("admin", UserType.ADMIN), newAdmin);
    }

    @Test
    public void testAdminLogin() {
        User existingAdmin = auth.login("username", "password");
        assertEquals(new User("admin", UserType.ADMIN), existingAdmin);
    }

    @Test
    public void testNonExistentAdminLogin() {
        fail();
    }

    @Test
    public void testLoggedInUserSignOut() {
        fail();
    }

    @Test
    public void testLoggedInAdminSignOut() {
        fail();
    }
}
