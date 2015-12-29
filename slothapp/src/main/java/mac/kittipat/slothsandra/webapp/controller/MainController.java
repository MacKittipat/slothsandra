package mac.kittipat.slothsandra.webapp.controller;

import mac.kittipat.slothsandra.core.dao.ChannelDao;
import mac.kittipat.slothsandra.core.dao.UserByChannelDao;
import mac.kittipat.slothsandra.core.model.Channel;
import mac.kittipat.slothsandra.core.model.UserByChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private UserByChannelDao userByChannelDao;

    @RequestMapping(value = "/")
    public String index(Model model) {
        List<Channel> channelList = channelDao.findAll();
        model.addAttribute("channelList", channelList);
        model.addAttribute("contentPath", "index.ftl");
        return "layout";
    }

    @RequestMapping(value = "/channel/{channelName}")
    public String channel(Model model,
                          @PathVariable String channelName) {
        List<UserByChannel> userByChannelList = userByChannelDao.findUserByChannel(channelName);
        model.addAttribute("userByChannelList", userByChannelList);
        model.addAttribute("contentPath", "channel.ftl");
        return "layout";
    }

    @RequestMapping(value = "/channel/{channelName}/user/{username}")
    public String userChannel(Model model,
                              @PathVariable String channelName,
                              @PathVariable String username) {
        List<UserByChannel> userByChannelList = userByChannelDao.findUserByChannel(channelName);
        model.addAttribute("userByChannelList", userByChannelList);
        model.addAttribute("contentPath", "user_channel.ftl");
        return "layout";
    }
}
