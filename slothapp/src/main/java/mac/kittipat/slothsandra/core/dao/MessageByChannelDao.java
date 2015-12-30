package mac.kittipat.slothsandra.core.dao;

import com.datastax.driver.core.utils.UUIDs;
import mac.kittipat.slothsandra.core.bean.MessageBean;
import mac.kittipat.slothsandra.core.dao.rowmapper.MessageBeanRowMapper;
import mac.kittipat.slothsandra.core.model.MessageByChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageByChannelDao {

    private static final Logger log = LoggerFactory.getLogger(MessageByChannelDao.class);

    @Autowired
    private CassandraTemplate cassandraTemplate;

    @Autowired
    private MessageBeanRowMapper messageBeanRowMapper;

    public MessageByChannel insert(MessageByChannel messageByChannel) {
        return cassandraTemplate.insert(messageByChannel);
    }

    public List<MessageBean> findMessageByChannel(String channelName, int year, long createdTime, int limit) {
        String cql = "SELECT * FROM message_by_channel " +
                "WHERE channel_name = '" + channelName +  "'" +
                " AND year = " + year +
                " AND uuid_time < " + UUIDs.startOf(createdTime) +
                " LIMIT " + limit;
        log.info("CQL : {}", cql);
        return cassandraTemplate.query(cql, messageBeanRowMapper::mapRow);
    }
}
