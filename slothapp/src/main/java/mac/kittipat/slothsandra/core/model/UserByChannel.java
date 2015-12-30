package mac.kittipat.slothsandra.core.model;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "user_by_channel")
public class UserByChannel {

    @PrimaryKey
    private UserByChannelKey userByChannelKey;

    @Column(value = "count_message")
    private long countMessage;

    public UserByChannelKey getUserByChannelKey() {
        return userByChannelKey;
    }

    public void setUserByChannelKey(UserByChannelKey userByChannelKey) {
        this.userByChannelKey = userByChannelKey;
    }

    public long getCountMessage() {
        return countMessage;
    }

    public void setCountMessage(long countMessage) {
        this.countMessage = countMessage;
    }
}
