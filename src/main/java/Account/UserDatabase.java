package Account;

import java.util.List;

public interface UserDatabase {
    boolean add(String username, String password, User user);

    User get(String username, String password);

    List<User> getAll();
}
