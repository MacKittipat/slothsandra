package mac.kittipat.slothsandra.webapp.controller.api;

import mac.kittipat.slothsandra.core.dao.MessageByChannelDao;
import mac.kittipat.slothsandra.core.dao.MessageByUserChannelDao;
import mac.kittipat.slothsandra.core.dao.MessageDao;
import mac.kittipat.slothsandra.core.model.MessageByChannel;
import mac.kittipat.slothsandra.core.model.MessageByUserChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class MessageRestController {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private MessageByChannelDao messageByChannelDao;

    @Autowired
    private MessageByUserChannelDao messageByUserChannelDao;

    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public ResponseEntity create(@RequestParam String channelName,
                                 @RequestParam String username,
                                 @RequestParam String message) {

        messageDao.insert(channelName, username, message);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/channels/{channelName}/messages", method = RequestMethod.GET)
    public ResponseEntity<List<MessageByChannel>> findByChannel(@PathVariable String channelName) {

        return new ResponseEntity<>(messageByChannelDao.findMessageByChannel(channelName), HttpStatus.OK);
    }

    @RequestMapping(value = "/channels/{channelName}/users/{username}/messages", method = RequestMethod.GET)
    public ResponseEntity<List<MessageByUserChannel>> findByUserChannel(@PathVariable String channelName,
                                                                        @PathVariable String username) {

        return new ResponseEntity<>(messageByUserChannelDao.findMessageByUserChannel(username, channelName), HttpStatus.OK);
    }
}
