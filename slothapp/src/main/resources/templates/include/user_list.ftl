<#if userByChannelList?has_content>
    <div class="collection">
        <#list userByChannelList as userByChannel>
            <a href="/slothsandra/channel/${channelName}/user/${userByChannel.userByChannelKey.username}" class="collection-item">
                ${userByChannel.userByChannelKey.username}
                <span class="badge">${userByChannel.countMessage}</span>
            </a>
        </#list>
    </div>
</#if>