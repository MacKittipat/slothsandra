package mac.kittipat.slothsandra.core.model;

import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.util.UUID;

@PrimaryKeyClass
public class MessageByChannelKey implements Serializable {

    @PrimaryKeyColumn(name = "channel_name", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String channelName;

    @PrimaryKeyColumn(name = "uuid_time", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private UUID uuidTime;

    @PrimaryKeyColumn(name = "created_time", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Long createdTime;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public UUID getUuidTime() {
        return uuidTime;
    }

    public void setUuidTime(UUID uuidTime) {
        this.uuidTime = uuidTime;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }
}
