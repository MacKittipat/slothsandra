package mac.kittipat.slothsandra.webapp.controller.api;

import mac.kittipat.slothsandra.core.dao.ChannelDao;
import mac.kittipat.slothsandra.core.model.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ChannelRestController {

    @Autowired
    private ChannelDao channelDao;

    @RequestMapping(value = "/channels", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody List<Channel> channelList) {
        channelList.forEach(channel -> channelDao.insert(channel));
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/channels", method = RequestMethod.GET)
    public ResponseEntity<List<Channel>> findAll() {
        return new ResponseEntity<>(channelDao.findAll(), HttpStatus.OK);
    }
}
