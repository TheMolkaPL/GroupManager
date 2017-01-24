package com.programmingwizzard.channelmanager;

import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.event.*;
import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;

import java.util.HashMap;
import java.util.Map;

/*
 * @author ProgrammingWizzard
 * @date 22.01.2017
 */
public class ChannelListener extends TS3EventAdapter
{
    private final Main main;

    protected ChannelListener(Main main)
    {
        this.main = main;
    }

    @Override
    public void onClientJoin(ClientJoinEvent e)
    {
        if (e.getUniqueClientIdentifier().equals("OSOwiLu3Rw3ecx/SXGdq3pngJ1E="))
        {
            main.getTs3Api().sendPrivateMessage(e.getClientId(), "Ten serwer korzysta z ChannelManager'a we wersji 1.0.0!");
        }
    }

    @Override
    public void onClientMoved(ClientMovedEvent e)
    {
        if (e.getTargetChannelId() != main.getConfiguration().getChannelCreator())
        {
            return;
        }
        Channel channel = main.getTs3Api().getChannels().stream().filter(c -> c.getParentChannelId() == main.getConfiguration().getParentChannelId()).filter(c -> c.getTopic().equals(e.getInvokerUniqueId())).findFirst().orElse(null);
        if (channel != null)
        {
            main.getTs3Api().sendPrivateMessage(e.getClientId(), "Posiadasz juz swoj prywatny kanal! Zostaniesz na niego przeniesiony...");
            main.getTs3Api().moveClient(main.getTs3Api().getClients().stream().filter(c -> c.getId() == e.getClientId()).findAny().get(), channel);
            return;
        }
        Map<ChannelProperty, String> channelPropertyStringMap = new HashMap<>();
        channelPropertyStringMap.put(ChannelProperty.CHANNEL_FLAG_PERMANENT, "1");
        channelPropertyStringMap.put(ChannelProperty.CPID, String.valueOf(main.getConfiguration().getParentChannelId()));
        channelPropertyStringMap.put(ChannelProperty.CHANNEL_DESCRIPTION, e.getInvokerUniqueId());
        main.getTs3Api().createChannel(e.getInvokerName(), channelPropertyStringMap);
        main.getTs3Api().sendPrivateMessage(e.getClientId(), "Dostales/as pomysle swoj kanal! Zyczymy milych rozmow :)");
    }
}
