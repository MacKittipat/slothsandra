package mac.kittipat.slothsandra.core.model;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class User {

    @PrimaryKey
    private UserKey userKey;

    public UserKey getUserKey() {
        return userKey;
    }

    public void setUserKey(UserKey userKey) {
        this.userKey = userKey;
    }
}
