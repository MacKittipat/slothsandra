package mac.kittipat.slothsandra.core.dao;

import com.datastax.driver.core.utils.UUIDs;
import mac.kittipat.slothsandra.core.bean.MessageBean;
import mac.kittipat.slothsandra.core.model.MessageByChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.util.HtmlUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class MessageByChannelDao {

    private static final Logger log = LoggerFactory.getLogger(MessageByChannelDao.class);

    @Autowired
    private CassandraTemplate cassandraTemplate;

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
        return cassandraTemplate.query(cql, (row, rowNum) -> {
            MessageBean messageBean = new MessageBean();
            messageBean.setChannelName(row.getString("channel_name"));
            messageBean.setUsername(row.getString("username"));
            messageBean.setMessage(row.getString("message"));
            messageBean.setEscapedMessage(HtmlUtils.htmlEscape(row.getString("message")));
            messageBean.setCreatedTime(row.getDate("created_time"));
            Instant instant = Instant.ofEpochMilli(messageBean.getCreatedTime().getTime());
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
            messageBean.setReadableCreatedTime(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            return messageBean;
        });
    }
}
