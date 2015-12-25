package mac.kittipat.slothsandra.core.dao;

import mac.kittipat.slothsandra.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private CassandraTemplate cassandraTemplate;

    public User insert(User user) {
        return cassandraTemplate.insert(user);
    }

    public List<User> findAll() {
        String cql = "SELECT * FROM user";
        return cassandraTemplate.select(cql, User.class);
    }
}
