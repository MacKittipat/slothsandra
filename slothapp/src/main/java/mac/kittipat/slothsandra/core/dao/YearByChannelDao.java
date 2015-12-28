package mac.kittipat.slothsandra.core.dao;

import mac.kittipat.slothsandra.core.model.YearByChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class YearByChannelDao {

    @Autowired
    private CassandraTemplate cassandraTemplate;

    public YearByChannel insert(YearByChannel yearByChannel) {
        return cassandraTemplate.insert(yearByChannel);
    }

    public List<YearByChannel> findByChannel(String channelName) {
        String cql = "SELECT * FROM year_by_channel WHERE channel_name='" + channelName + "'";
        return cassandraTemplate.select(cql, YearByChannel.class);
    }

    public List<YearByChannel> findAll() {
        String cql = "SELECT * FROM year_by_channel";
        return cassandraTemplate.select(cql, YearByChannel.class);
    }
}
