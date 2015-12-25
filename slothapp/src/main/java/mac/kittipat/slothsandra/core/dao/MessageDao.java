package mac.kittipat.slothsandra.core.dao;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.utils.UUIDs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public class MessageDao {

    @Autowired
    private CassandraTemplate cassandraTemplate;

    public void insert(String channelName, String username, String message) {

        Date date = new Date();
        UUID uuidTime = UUIDs.timeBased();

        Insert insertUserByChannel = QueryBuilder.insertInto("user_by_channel");
        insertUserByChannel.value("channel_name", channelName);
        insertUserByChannel.value("username", username);

        Insert insertMessageByChannel = QueryBuilder.insertInto("message_by_channel");
        insertMessageByChannel.value("channel_name", channelName);
        insertMessageByChannel.value("uuid_time", uuidTime);
        insertMessageByChannel.value("created_time", date.getTime());
        insertMessageByChannel.value("username", username);
        insertMessageByChannel.value("message", message);

        Insert insertMessageByUserChannel = QueryBuilder.insertInto("message_by_user_channel");
        insertMessageByUserChannel.value("channel_name", channelName);
        insertMessageByUserChannel.value("username", username);
        insertMessageByUserChannel.value("uuid_time", uuidTime);
        insertMessageByUserChannel.value("created_time", date.getTime());
        insertMessageByUserChannel.value("message", message);

        // Use batch for atomicity. Keep data in all table consistency.
        BatchStatement batch = new BatchStatement();
        batch.add(insertUserByChannel);
        batch.add(insertMessageByChannel);
        batch.add(insertMessageByUserChannel);

        cassandraTemplate.execute(batch);
    }
}
