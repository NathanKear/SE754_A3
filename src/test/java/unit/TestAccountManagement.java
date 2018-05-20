package unit;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestAccountManagement {
    Database _db;

    @Before
    public void setup() {
        _db = mock(Database.class);
        when(_db.get(anyString(), anyString()).thenReturn(null));

        when(_db.add("user", "password", UserType.USER)).thenReturn(new User("user", UserType.USER));
        when(_db.get("user", "password")).thenReturn(new User("user", UserType.USER));

        when(_db.add("admin", "password", UserType.ADMIN).thenReturn(new User("admin", UserType.ADMIN));
        when(_db.get("admin", "password")).thenReturn(new User("admin", UserType.ADMIN));
    }

    @Test
    public void testUserSignUp() {
       fail();
    }

    @Test
    public void testUserLogin() {
        fail();
    }

    @Test
    public void testNonExistentUserLogin() {
        fail();
    }

    @Test
    public void testAdminSignUp() {
        fail();
    }

    @Test
    public void testAdminLogin() {
        fail();
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
