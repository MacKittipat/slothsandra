package mac.kittipat.slothsandra.core.dao;

import mac.kittipat.slothsandra.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao {

    @Autowired
    private CassandraTemplate cassandraTemplate;

    @Resource
    private Map<String, String> userIdMap;

    @Value("${slothsandra.slack-name}")
    private String slackName;

    public User insert(User user) {
        user.getUserKey().setSlackName(slackName);
        cassandraTemplate.insert(user);

        // Add new user to userIdMap
        userIdMap.put(user.getUserKey().getUserId(), user.getUserKey().getUsername());

        return user;
    }

    public List<User> findAll() {
        String cql = "SELECT * FROM user";
        return cassandraTemplate.select(cql, User.class);
    }
}
