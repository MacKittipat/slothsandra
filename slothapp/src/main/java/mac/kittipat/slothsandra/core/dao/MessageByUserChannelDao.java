package mac.kittipat.slothsandra.core.dao;

import com.datastax.driver.core.utils.UUIDs;
import mac.kittipat.slothsandra.core.bean.MessageBean;
import mac.kittipat.slothsandra.core.dao.rowmapper.MessageBeanRowMapper;
import mac.kittipat.slothsandra.core.model.MessageByUserChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageByUserChannelDao {

    private static final Logger log = LoggerFactory.getLogger(MessageByChannelDao.class);

    @Autowired
    private CassandraTemplate cassandraTemplate;

    @Autowired
    private MessageBeanRowMapper messageBeanRowMapper;

    public MessageByUserChannel insert(MessageByUserChannel messageByUserChannel) {
        return cassandraTemplate.insert(messageByUserChannel);
    }

    public List<MessageBean> findMessageByUserChannel(String username, String channelName, int year, long createdTime, int limit) {
        String cql = "SELECT * FROM message_by_user_channel " +
                "WHERE channel_name = '" + channelName +  "' " +
                "AND username = '" + username + "'" +
                " AND year = " + year +
                " AND uuid_time < " + UUIDs.startOf(createdTime) +
                " LIMIT " + limit;
        log.info("CQL : {}", cql);
        return cassandraTemplate.query(cql, messageBeanRowMapper::mapRow);
    }
}
