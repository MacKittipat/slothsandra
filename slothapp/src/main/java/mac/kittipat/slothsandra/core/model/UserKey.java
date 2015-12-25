package mac.kittipat.slothsandra.core.model;

import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import java.io.Serializable;

@PrimaryKeyClass
public class UserKey implements Serializable {

    @PrimaryKeyColumn(name = "slack_name", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String slackName;

    @PrimaryKeyColumn(name = "user_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private String userId;

    @PrimaryKeyColumn(name = "username", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private String username;

    public String getSlackName() {
        return slackName;
    }

    public void setSlackName(String slackName) {
        this.slackName = slackName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
