package mac.kittipat.slothsandra.core.dao;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Update;
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
        // Use update because this table have counter column. insert does not work if table have counter column.
        Update update = QueryBuilder.update("channel");
        update.with(QueryBuilder.incr("count_message", 0)); // Count start with zero.
        update.where(QueryBuilder.eq("slack_name", "abctech"));
        update.where(QueryBuilder.eq("channel_name", channel.getChannelKey().getChannelName()));
        update.where(QueryBuilder.eq("channel_id", channel.getChannelKey().getChannelId()));
        cassandraTemplate.execute(update);
        return channel;
    }

    public List<Channel> findAll() {
        String cql = "SELECT * FROM channel";
        return cassandraTemplate.select(cql, Channel.class);
    }
}
