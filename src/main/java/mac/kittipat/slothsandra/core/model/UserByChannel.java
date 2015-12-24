package mac.kittipat.slothsandra.core.model;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class UserByChannel {

    @PrimaryKey
    private UserByChannelKey userByChannelKey;

    public UserByChannelKey getUserByChannelKey() {
        return userByChannelKey;
    }

    public void setUserByChannelKey(UserByChannelKey userByChannelKey) {
        this.userByChannelKey = userByChannelKey;
    }
}
