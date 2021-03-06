<div class="row">
    <div class="col s12 breadcrumb-box">
        <a href="/slothsandra/">Home</a> >
        <a href="/slothsandra/channel/${channelName}">#${channelName}</a>
        <#if username??>
            > <a href="/slothsandra/channe/${channelName}/user/${username}">@${username}</a>
        </#if>
    </div>
    <div class="col s2">
        <#include "../include/user_list.ftl">
    </div>
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
            $('#messages-list').append(
                    '<div class="message-box">' +
                        '<div>' +
                            '<span class="message-username">' + data[i].username + '</span> <span class="message-time">' + data[i].readableCreatedTime + '</span>' +
                        '</div>' +
                        '<div class="message-content">' +
                            data[i].escapedMessage +
                        '</div>' +
                    '</div>');
        }
        if(data.length > 0) {
            latestCreatedTime =  data[data.length - 1].createdTime;
        } else {
            latestCreatedTime = 0;
        }
    };

    $(document).ready(function() {
        $.get('${restRetrieveMessagePath}', displayMessageCallback);
    });

    $(window).scroll(function() {
        if($(window).scrollTop() + $(window).height() == $(document).height() && latestCreatedTime != 0) {
            $.get('${restRetrieveMessagePath}?createdTime=' + latestCreatedTime, displayMessageCallback);
        }
    });

</script>
