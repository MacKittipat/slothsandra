package mac.kittipat.slothsandra.core.dao;

import mac.kittipat.slothsandra.core.model.UserByChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserByChannelDao {

    @Autowired
    private CassandraTemplate cassandraTemplate;

    public UserByChannel insert(UserByChannel userByChannel) {
        return cassandraTemplate.insert(userByChannel);
    }

    public List<UserByChannel> findUserByChannel(String channelName) {
        String cql = "SELECT * FROM user_by_channel WHERE channel_name='" + channelName +  "'";
        return cassandraTemplate.select(cql, UserByChannel.class);
    }
}
