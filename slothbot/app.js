var Slack   = require('slack-client');
var winston  = require('winston');
var rest    = require('restler');

var logger = new (winston.Logger)({
    transports: [
        new (winston.transports.Console)({ level: 'debug' })
    ]
});

var token           = 'xoxb-17364172837-T218G26lM8k6cAuzQRvsr7do';
var autoReconnect   = true;
var autoMark        = true;

var slack = new Slack(token, autoReconnect, autoMark);

logger.info('Logging into Slack')
slack.login();

slack.on('open', function() {
    logger.info('Slack on open');
});

slack.on('error', function(error) {
    logger.error(error);
});

slack.on('message', function(message) {
    var user = slack.getUserByID(message.user);
    var channel = slack.getChannelGroupOrDMByID(message.channel);
    if(user && channel && message) {
        logger.debug('[' + channel.name + '] ' + user.name + ' : ' + message.text);
        // Send message to slothsandra
        rest.post('http://localhost:9000/slothsandra/api/messages', {
            data: {
                channelName: channel.name,
                username: user.name,
                message: message.text
            },
        }).on('success', function(data, response) {
            if (response.statusCode == 201) {
                logger.debug('Send message \"[' + channel.name + '] ' + user.name + ' : ' + message.text + '\" to slothsandra success');
            }
        }).on('error', function(err, response) {
            logger.error(err);
        });
    }
});