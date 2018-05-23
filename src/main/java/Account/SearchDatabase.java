package Account;

import java.util.List;
import java.util.UUID;

public interface SearchDatabase {
    boolean add(User user, UUID sessionID, String phrase);

    List<String> get(User user);

    List<String> get(User user, UUID sessionID);

}
