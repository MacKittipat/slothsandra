package mac.kittipat.slothsandra.core.dao;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Update;
import com.datastax.driver.core.utils.UUIDs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Repository
public class MessageDao {

    @Autowired
    private CassandraTemplate cassandraTemplate;

    @Resource
    private Map<String, String> channelIdMap;

    public void insert(String channelName, String username, String message, Long createdTime) {

        int year = LocalDate.now().getYear();
        // Use startOf because compare uuid_time<minTimeuuid()
        UUID uuidTime = UUIDs.startOf(createdTime);

        // Use 2 batch because counter operation must be UNLOGGED but other operation are LOGGED by default.

        // Cannot include counter in batch because other operation are not update counter
        // and counter does not work with LOGGED batch.
        Update updateCounterChannel = QueryBuilder.update("channel");
        updateCounterChannel.with(QueryBuilder.incr("count_message", 1));
        updateCounterChannel.where(QueryBuilder.eq("slack_name", "abctech"));
        updateCounterChannel.where(QueryBuilder.eq("channel_name", channelName));
        updateCounterChannel.where(QueryBuilder.eq("channel_id", findChannelIdByChannelName(channelName)));

        Update updateCounterUserByChannel = QueryBuilder.update("user_by_channel");
        updateCounterUserByChannel.with(QueryBuilder.incr("count_message", 1));
        updateCounterUserByChannel.where(QueryBuilder.eq("channel_name", channelName));
        updateCounterUserByChannel.where(QueryBuilder.eq("username", username));

        BatchStatement batchCounter = new BatchStatement(BatchStatement.Type.UNLOGGED);
        batchCounter.add(updateCounterChannel);
        batchCounter.add(updateCounterUserByChannel);
        cassandraTemplate.execute(batchCounter);

        Insert insertMessageByChannel = QueryBuilder.insertInto("message_by_channel");
        insertMessageByChannel.value("channel_name", channelName);
        insertMessageByChannel.value("year", year);
        insertMessageByChannel.value("uuid_time", uuidTime);
        insertMessageByChannel.value("created_time", createdTime);
        insertMessageByChannel.value("username", username);
        insertMessageByChannel.value("message", message);

        Insert insertMessageByUserChannel = QueryBuilder.insertInto("message_by_user_channel");
        insertMessageByUserChannel.value("channel_name", channelName);
        insertMessageByUserChannel.value("username", username);
        insertMessageByUserChannel.value("year", year);
        insertMessageByUserChannel.value("uuid_time", uuidTime);
        insertMessageByUserChannel.value("created_time", createdTime);
        insertMessageByUserChannel.value("message", message);

        Insert insertYearByChannel = QueryBuilder.insertInto("year_by_channel");
        insertYearByChannel.value("channel_name", channelName);
        insertYearByChannel.value("year", year);

        // Use batch for atomicity. Keep data in all table consistency.
        BatchStatement batch = new BatchStatement();
        batch.add(insertMessageByChannel);
        batch.add(insertMessageByUserChannel);
        batch.add(insertYearByChannel);
        cassandraTemplate.execute(batch);
    }

    private String findChannelIdByChannelName(String channelName) {
        return channelIdMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(channelName))
                .map(Map.Entry::getKey)
                .findFirst()
                .get();
    }
}
