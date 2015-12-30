package mac.kittipat.slothsandra.core.model;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class Channel {

    @PrimaryKey
    private ChannelKey ChannelKey;

    @Column(value = "count_message")
    private long countMessage;

    public ChannelKey getChannelKey() {
        return ChannelKey;
    }

    public void setChannelKey(ChannelKey channelKey) {
        ChannelKey = channelKey;
    }

    public long getCountMessage() {
        return countMessage;
    }

    public void setCountMessage(long countMessage) {
        this.countMessage = countMessage;
    }
}

