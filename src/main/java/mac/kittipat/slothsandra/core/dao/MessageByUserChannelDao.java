package mac.kittipat.slothsandra.core.dao;

import mac.kittipat.slothsandra.core.model.MessageByUserChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageByUserChannelDao {

    @Autowired
    private CassandraTemplate cassandraTemplate;

    public MessageByUserChannel insert(MessageByUserChannel messageByUserChannel) {
        return cassandraTemplate.insert(messageByUserChannel);
    }

    public List<MessageByUserChannel> findMessageByUserChannel(String username, String channelName) {
        String cql = "SELECT * FROM message_by_user_channel " +
                "WHERE channel_name='" + channelName +  "' " +
                "AND username='" + username + "'";
        return cassandraTemplate.select(cql, MessageByUserChannel.class);
    }
}
