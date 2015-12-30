var Slack   = require('slack-client');
var winston  = require('winston');
var rest    = require('restler');
var config = require('config');

var token               = config.get('slothbot.slack.token');
var autoReconnect       = true;
var autoMark            = true;
var slothsandraBaseUrl  = config.get('slothbot.slothsandra.base-url');
var winstonLogLevel     = config.get('slothbot.winston.log.level');

var logger = new (winston.Logger)({
    transports: [
        new (winston.transports.Console)({ level: winstonLogLevel })
    ]
});

var slack = new Slack(token, autoReconnect, autoMark);

// Fetch list of channels from Slack.
var slackChannels = [];
slack._apiCall('channels.list', {'token':token}, function(data) {
    if(data.ok) {
        for(var i=0; i<data.channels.length; i++) {
            var slackChannel = data.channels[i];
            // Check if slothboth is member in channel.
            if(slackChannel.is_member) {
                slackChannels.push({
                    'channelKey': {
                        'channelId': slackChannel.id,
                        'channelName': slackChannel.name
                    }
                });
            }
        }
        // Send list of channels to Slothsandra.
        rest.postJson(slothsandraBaseUrl + '/api/channels', slackChannels)
        .on('success', function(data, response) {
            if (response.statusCode == 201) {
                logger.info('Send channels to Slothsandra success');
            }
        }).on('error', function(err, response) {
            logger.error("Error when send channels to Slothsandra. (" + err + ")");
        });
    }
});

// Fetch list of users from Slack.
var slackUsers = [];
slack._apiCall('users.list', {'token':token}, function(data) {
    if(data.ok) {
        for(var i=0; i<data.members.length; i++) {
            var slackUser = data.members[i];
            slackUsers.push({
                'userKey': {
                    'userId': slackUser.id,
                    'username': slackUser.name
                }
            });
        }
        // Send list of users to Slothsandra.
        rest.postJson(slothsandraBaseUrl + '/api/users', slackUsers)
            .on('success', function(data, response) {
                if (response.statusCode == 201) {
                    logger.info('Send users to Slothsandra success');
                }
            }).on('error', function(err, response) {
            logger.error("Error when send users to Slothsandra. (" + err + ")");
        });
    }
});

logger.info('Logging into Slack');
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
        logger.debug('[' + channel.name + ': ' + message.ts + '] ' + user.name + ' : ' + message.text);
        // Send message to Slothsandra
        rest.post(slothsandraBaseUrl + '/api/messages', {
            data: {
                channelName: channel.name,
                username: user.name,
                message: message.text,
                createdTime: message.ts
            }
        }).on('success', function(data, response) {
            if (response.statusCode == 201) {
                logger.debug('Send message \"[' + channel.name + '] ' + user.name + ' : ' + message.text + '\" to slothsandra success');
            }
        }).on('error', function(err, response) {
            logger.error("Error when send message to Slothsandra. (" + err + ")");
        });
    }
});