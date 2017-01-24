package com.programmingwizzard.groupmanager;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.exception.TS3ConnectionFailedException;
import com.github.theholywaffle.teamspeak3.api.reconnect.ReconnectStrategy;

import java.io.File;
import java.util.logging.Level;

/*
 * @author ProgrammingWizzard
 * @date 23.01.2017
 */
public class GroupManager
{
    private Configuration configuration;
    private TS3Config ts3config;
    private TS3Query ts3Query;
    private TS3Api ts3api;

    public static void main(String[] args)
    {
        new GroupManager().start();
    }

    private void start()
    {
        try
        {
            configuration = GsonUtils.readConfiguration(Configuration.class, new File("config.json"));
        } catch (Exception e)
        {
            System.out.println("Blad z ladowaniem konfiguracji!");
            e.printStackTrace();
            return;
        }
        ts3config = new TS3Config();
        ts3config.setDebugLevel(Level.OFF);
        ts3config.setReconnectStrategy(ReconnectStrategy.constantBackoff());
        ts3config.setHost(configuration.getIp());
        ts3config.setQueryPort(configuration.getPort());
        ts3Query = new TS3Query(ts3config);
        try
        {
            ts3Query.connect();
        } catch (TS3ConnectionFailedException e)
        {
            System.out.println("Nastapil blad w polaczeniu sie ze serwerem!");
            e.printStackTrace();
            return;
        }
        ts3api = ts3Query.getApi();
        ts3api.selectVirtualServerById(1);
        ts3api.login(configuration.getLogin(), configuration.getPassword());
        ts3api.setNickname(configuration.getName());
        ts3api.registerAllEvents();

        ts3api.addTS3Listeners(new GroupListener(this));

        System.out.println("Bot zostal pomyslnie zaladowany i dolaczyl na serwer!");
    }

    public Configuration getConfiguration()
    {
        return configuration;
    }

    public TS3Api getTs3api()
    {
        return ts3api;
    }
}
