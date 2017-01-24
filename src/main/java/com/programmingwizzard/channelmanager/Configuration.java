package com.programmingwizzard.channelmanager;

import com.google.gson.annotations.SerializedName;

/*
 * @author ProgrammingWizzard
 * @date 22.01.2017
 */
@SuppressWarnings("FieldCanBeLocal")
public class Configuration
{
    @SerializedName("ip")
    private String ip = "localhost";

    @SerializedName("port")
    private int port = 10011;

    @SerializedName("login")
    private String login = "serveradmin";

    @SerializedName("password")
    private String password = "";

    @SerializedName("name")
    private String name = "BOT @ ChannelManager";

    @SerializedName("parent-channel-id")
    private int parentChannelId = 3;

    @SerializedName("channel-creator")
    private int channelCreator = 2;

    @SerializedName("channel-admin-id")
    private int channelAdminId = 5;

    @SerializedName("child-channels")
    private int childChannels = 3;

    public String getIp()
    {
        return ip;
    }

    public int getPort()
    {
        return port;
    }

    public String getLogin()
    {
        return login;
    }

    public String getPassword()
    {
        return password;
    }

    public String getName()
    {
        return name;
    }

    public int getParentChannelId()
    {
        return parentChannelId;
    }

    public int getChannelCreator()
    {
        return channelCreator;
    }

    public int getChildChannels()
    {
        return childChannels;
    }

    public int getChannelAdminId()
    {
        return channelAdminId;
    }
}
