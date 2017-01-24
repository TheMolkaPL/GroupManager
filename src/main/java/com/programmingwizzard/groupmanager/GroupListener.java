package com.programmingwizzard.groupmanager;

import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;

import java.util.Set;

/*
 * @author ProgrammingWizzard
 * @date 23.01.2017
 */
public class GroupListener extends TS3EventAdapter
{
    private final GroupManager groupManager;

    public GroupListener(GroupManager groupManager)
    {
        this.groupManager = groupManager;
    }

    @Override
    public void onClientMoved(ClientMovedEvent e)
    {
        if (e.getTargetChannelId() != groupManager.getConfiguration().getChannel())
        {
            return;
        }
        groupManager.getTs3api().sendPrivateMessage(e.getClientId(), "\n" +
                                                                             "Witaj w zautomatyzowanym systemie nadawania rang na naszym teamspeak'u!" +
                                                                             "\n" +
                                                                             "\nDostepne komendy:" +
                                                                             "\n  [b]!rangi[/b] - pokazuje liste dostepnych rang do nadania" +
                                                                             "\n  [b]+<nazwa rangi>[/b] - nadaje jedna z dostepnych rang, o podanej nazwie" +
                                                                             "\n  [b]-<nazwa rangi>[/b] - zabiera jedna z dostepnych rang, o podanej nazwie" +
                                                                             "\n" +
                                                                             "\nPrzykladowe uzycie:" +
                                                                             "\n  [b]+Normal[/b] - nadaje grupe o nazwie Normal" +
                                                                             "\n  [b]-Normal[/b] - zabiera grupe o nazwie Normal");
    }

    @Override
    public void onTextMessage(TextMessageEvent e)
    {
        if (e.getTargetMode() != TextMessageTargetMode.CLIENT)
        {
            return;
        }
        if (e.getMessage().startsWith("!rangi"))
        {
            Set<String> keys = groupManager.getConfiguration().getGroups().keySet();
            if (keys.size() == 0)
            {
                groupManager.getTs3api().sendPrivateMessage(e.getInvokerId(), "Aktualnie nie ma [b]zadnych[/b] dostepnych rang! Sprobuj pozniej :)");
                return;
            }
            String message = "";
            for (String s : keys)
            {
                message = message + "[b]" + s + "[/b], ";
            }
            groupManager.getTs3api().sendPrivateMessage(e.getInvokerId(), "Dostepne rangi: " + message);
            return;
        }
        if (e.getMessage().startsWith("+"))
        {
            String message = e.getMessage().substring(1);
            if (message.equals("") || message.equals(" "))
            {
                groupManager.getTs3api().sendPrivateMessage(e.getInvokerId(), "Zly argument komendy!");
                return;
            }
            if (groupManager.getConfiguration().getGroups().get(message) == null)
            {
                groupManager.getTs3api().sendPrivateMessage(e.getInvokerId(), "Taka ranga nie istnieje!");
                return;
            }
            int rankId = groupManager.getConfiguration().getGroups().get(message);
            ClientInfo info = groupManager.getTs3api().getClientByUId(e.getInvokerUniqueId());
            if (info.isInServerGroup(rankId))
            {
                groupManager.getTs3api().sendPrivateMessage(e.getInvokerId(), "Juz nalezysz do grupy o nazwie [b]" + message + "[/b]!");
                return;
            }
            groupManager.getTs3api().addClientToServerGroup(rankId, info.getDatabaseId());
            groupManager.getTs3api().sendPrivateMessage(e.getInvokerId(), "Pomyslnie nadano range [b]" + message + "[/b]!");
        }
        if (e.getMessage().startsWith("-"))
        {
            String message = e.getMessage().substring(1);
            if (message.equals("") || message.equals(" "))
            {
                groupManager.getTs3api().sendPrivateMessage(e.getInvokerId(), "Zly argument komendy!");
                return;
            }
            if (groupManager.getConfiguration().getGroups().get(message) == null)
            {
                groupManager.getTs3api().sendPrivateMessage(e.getInvokerId(), "Taka ranga nie istnieje!");
                return;
            }
            int rankId = groupManager.getConfiguration().getGroups().get(message);
            ClientInfo info = groupManager.getTs3api().getClientByUId(e.getInvokerUniqueId());
            if (!info.isInServerGroup(rankId))
            {
                groupManager.getTs3api().sendPrivateMessage(e.getInvokerId(), "Nie nalezysz do grupy o nazwie [b]" + message + "[/b]!");
                return;
            }
            groupManager.getTs3api().removeClientFromServerGroup(rankId, info.getDatabaseId());
            groupManager.getTs3api().sendPrivateMessage(e.getInvokerId(), "Pomyslnie odebrano range [b]" + message + "[/b]!");
        }
    }
}
