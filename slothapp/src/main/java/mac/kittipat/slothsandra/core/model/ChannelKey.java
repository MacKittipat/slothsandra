package mac.kittipat.slothsandra.core.model;

import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import java.io.Serializable;

@PrimaryKeyClass
public class ChannelKey implements Serializable {

    @PrimaryKeyColumn(name = "slack_name", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String slackName;

    @PrimaryKeyColumn(name = "channel_name", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private String channelName;

    public String getSlackName() {
        return slackName;
    }

    public void setSlackName(String slackName) {
        this.slackName = slackName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
