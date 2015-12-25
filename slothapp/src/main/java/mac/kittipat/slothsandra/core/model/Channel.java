package mac.kittipat.slothsandra.core.model;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class Channel {

    @PrimaryKey
    private ChannelKey ChannelKey;

    public ChannelKey getChannelKey() {
        return ChannelKey;
    }

    public void setChannelKey(ChannelKey channelKey) {
        ChannelKey = channelKey;
    }
}

