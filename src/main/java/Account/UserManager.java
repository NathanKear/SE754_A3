package Account;

import org.mockito.internal.matchers.Not;

import java.util.List;

public class UserManager {
    private AuthenticationService _auth;
    private UserDatabase _db;

    public UserManager(UserDatabase db) {
        _db = db;
        _auth = new AuthenticationService(db);
    }
    public int getRegisteredUserCount(User requestingUser) throws NotAuthorizedException {
        if (!_auth.isAdmin(requestingUser)) {
            throw new NotAuthorizedException("Action requires admin permissions");
        }

        List<User> users = _db.getAll();
        return users.size();
    }
}
