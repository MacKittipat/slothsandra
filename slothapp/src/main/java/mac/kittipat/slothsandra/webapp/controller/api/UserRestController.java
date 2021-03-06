package mac.kittipat.slothsandra.webapp.controller.api;

import mac.kittipat.slothsandra.core.dao.UserByChannelDao;
import mac.kittipat.slothsandra.core.dao.UserDao;
import mac.kittipat.slothsandra.core.model.User;
import mac.kittipat.slothsandra.core.model.UserByChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserRestController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserByChannelDao userByChannelDao;

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody List<User> userList) {
        userList.forEach(user -> userDao.insert(user));
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/channels/{channelName}/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserByChannel>> findByChannel(@PathVariable String channelName) {
        return new ResponseEntity<>(userByChannelDao.findUserByChannel(channelName), HttpStatus.OK);
    }
}
