<div class="collection">
    <#list userByChannelList as userByChannel>
        <a href="/slothsandra/channel/${channelName}/user/${userByChannel.userByChannelKey.username}" class="collection-item">${userByChannel.userByChannelKey.username}</a>
    </#list>
</div>