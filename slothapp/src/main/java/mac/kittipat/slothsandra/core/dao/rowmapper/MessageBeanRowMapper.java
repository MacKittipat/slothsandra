package mac.kittipat.slothsandra.core.dao.rowmapper;

import com.datastax.driver.core.Row;
import mac.kittipat.slothsandra.core.bean.MessageBean;
import org.springframework.web.util.HtmlUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class MessageBeanRowMapper {

    public static MessageBean mapRow(Row row, int rowNum) {
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
    }
}
