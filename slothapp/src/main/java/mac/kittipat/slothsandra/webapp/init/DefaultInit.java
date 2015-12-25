package mac.kittipat.slothsandra.webapp.init;

import mac.kittipat.slothsandra.core.dao.ChannelDao;
import mac.kittipat.slothsandra.core.dao.UserDao;
import mac.kittipat.slothsandra.core.model.Channel;
import mac.kittipat.slothsandra.core.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class DefaultInit {

    private static final Logger log = LoggerFactory.getLogger(DefaultInit.class);

    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private UserDao userDao;

    @Resource
    private Map<String, String> channelIdMap;

    @Resource
    private Map<String, String> userIdMap;

    @PostConstruct
    public void init() {

        List<Channel> channelList = channelDao.findAll();
        channelList.forEach((channel) -> {
            channelIdMap.put(channel.getChannelKey().getChannelId(), channel.getChannelKey().getChannelName());
        });
        log.info("Loaded channel info from Cassandra. Found {} channels.", channelList.size());

        List<User> usersList = userDao.findAll();
        usersList.forEach((user) -> {
            userIdMap.put(user.getUserKey().getUserId(), user.getUserKey().getUsername());
        });
        log.info("Loaded user info from Cassandra. Found {} users.", usersList.size());
    }
}
