// Import message from slack


// [
// {
// id: "C09N52FRP",
// name: "abctech-english-only"
// },
// {
// id: "C048ED3FG",
// name: "game"
// },
// {
// id: "C0476BKVB",
// name: "general"
// },
// {
// id: "C0476BL03",
// name: "random"
// },
// {
// id: "C0H9YRPRT",
// name: "test"
// }
// ]

var rest    = require('restler');
var Slack   = require('slack-client');

var token = 'xoxb-17364172837-W77ecUAFTsQZJ3tJYUTMGQhK';
var slack = new Slack(token, true, true);

var channelNameId = "C0476BL03";
var channelName = 'random'

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


        rest.get('https://slack.com/api/channels.history?token=' + token + '&channel=' + channelNameId + '&oldest=1455280243.000269')
        .on('complete', function(results) {
          for(var i=0; i<=results.messages.length; i++) {
            var result = results.messages[i];
            //console.log(results.messages[i]);
            //console.log(result);
            if(result == undefined) {
              console.log('skip');
              continue;
            }
            // console.log(result);
            rest.post('http://192.168.50.177:9000/slothsandra/api/messages', {
                data: {
                    channelName: channelName,
                    username: findUserById(result.user),
                    message: result.text,
                    createdTime: result.ts
                }
            }).on('success', function(data, response) {
                if (response.statusCode == 201) {
                    console.log('success');
                }
            }).on('error', function(err, response) {
                // logger.error("Error when send message to Slothsandra. (" + err + ")");
            });
          }
        });

    }
});


function findUserById(userId) {
  for(var i=0; i<slackUsers.length; i++) {
    if(slackUsers[i].userKey.userId == userId) {
      return slackUsers[i].userKey.username;
    }
  }
}
