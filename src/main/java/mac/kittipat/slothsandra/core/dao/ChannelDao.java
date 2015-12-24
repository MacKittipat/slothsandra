package mac.kittipat.slothsandra.core.dao;

import mac.kittipat.slothsandra.core.model.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChannelDao {

    @Autowired
    private CassandraTemplate cassandraTemplate;

    public Channel insert(Channel channel) {
        return cassandraTemplate.insert(channel);
    }

    public List<Channel> findAll() {
        String cql = "SELECT * FROM channel";
        return cassandraTemplate.select(cql, Channel.class);
    }
}
