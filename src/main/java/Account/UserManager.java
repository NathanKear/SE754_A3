package Account;

import org.mockito.internal.matchers.Not;

import java.util.List;
import java.util.UUID;

public class UserManager {
    private AuthenticationService _auth;
    private UserDatabase _userDb;
    private SearchDatabase _searchDb;

    public UserManager(UserDatabase userDb, SearchDatabase searchDb) {
        _userDb = userDb;
        _searchDb = searchDb;
        _auth = new AuthenticationService(userDb);
    }
    public int getRegisteredUserCount(User requestingUser) throws NotAuthorizedException {
        if (!_auth.isAdmin(requestingUser)) {
            throw new NotAuthorizedException("Action requires admin permissions");
        }

        List<User> users = _userDb.getAll();
        return users.size();
    }

    public void makeUserSearch(User user, String phrase) {
        UUID session = _auth.getSessionID(user);
        _searchDb.add(user, session, phrase);
    }

    public int getUserSessionSearchCount(User user, UUID sessionID) {
        List<String> searches = _searchDb.get(user,sessionID);
        return searches.size();
    }

    public int getUserTotalSearchCount(User user) {
        List<String> searches = _searchDb.get(user);
        return searches.size();
    }
}
