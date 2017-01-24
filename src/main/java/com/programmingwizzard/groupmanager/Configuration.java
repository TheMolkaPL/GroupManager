package com.programmingwizzard.groupmanager;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/*
 * @author ProgrammingWizzard
 * @date 23.01.2017
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
    private String name = "BOT @ GroupManager";

    @SerializedName("channel")
    private int channel = 2;

    @SerializedName("groups")
    private Map<String, Integer> groups = new HashMap<>();
    {
        groups.put("Name 1", 1);
        groups.put("Name 2", 2);
        groups.put("Name 3", 3);
    }

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

    public int getChannel()
    {
        return channel;
    }

    public Map<String, Integer> getGroups()
    {
        return groups;
    }
}
