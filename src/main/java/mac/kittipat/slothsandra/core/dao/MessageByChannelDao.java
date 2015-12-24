package mac.kittipat.slothsandra.core.dao;

import mac.kittipat.slothsandra.core.model.MessageByChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;

import java.util.List;

public class MessageByChannelDao {

    @Autowired
    private CassandraTemplate cassandraTemplate;

    public MessageByChannel insert(MessageByChannel messageByChannel) {
        return cassandraTemplate.insert(messageByChannel);
    }

    public List<MessageByChannel> findMessageByChannel(String channelName) {
        String cql = "SELECT * FROM message_by_channel WHERE channel_name='" + channelName +  "'";
        return cassandraTemplate.select(cql, MessageByChannel.class);
    }
}
