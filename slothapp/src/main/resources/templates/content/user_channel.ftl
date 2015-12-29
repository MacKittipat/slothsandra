<div class="row">
    <div class="col s12">
        <h1>
            Slothsandra : ${channelName}
        </h1>
    </div>
    <#include "../include/user_list.ftl">
    <div class="col s10">
        <div id="messages-list">
        </div>
    </div>
</div>

<script type="text/javascript" src="/slothsandra/assets/jquery/jquery-2.1.4.min.js"></script>
<script type="text/javascript">

    var latestCreatedTime = 0;

    var displayMessageCallback = function(data) {
        console.log('Retrieve ' + data.length + ' messages');
        for(var i=0; i<data.length; i++) {
            $('#messages-list').append('<div style="word-wrap: break-word;">(' + data[i].readableCreatedTime + ') ' + data[i].username + ' : ' + data[i].escapedMessage + '</div>')
        }
        if(data.length > 0) {
            latestCreatedTime =  data[data.length - 1].createdTime;
        } else {
            latestCreatedTime = 0;
        }
    }

    $(document).ready(function() {
        $.get('http://localhost:9000/slothsandra/api/channels/${channelName}/users/${username}/messages', displayMessageCallback);
    });

    $(window).scroll(function() {
        console.log('Scroll to button');
        if($(window).scrollTop() + $(window).height() == $(document).height() && latestCreatedTime != 0) {
            $.get('http://localhost:9000/slothsandra/api/channels/${channelName}/users/${username}/messages?createdTime=' + latestCreatedTime, displayMessageCallback);
        }
    });

</script>
