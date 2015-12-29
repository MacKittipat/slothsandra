<div class="col s2">
    <#list userByChannelList as userByChannel>
        <div><a href="/slothsandra/channel/${channelName}/user/${userByChannel.userByChannelKey.username}">${userByChannel.userByChannelKey.username}</a></div>
    </#list>
</div>