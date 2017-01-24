package com.programmingwizzard.channelmanager;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.exception.TS3ConnectionFailedException;
import com.github.theholywaffle.teamspeak3.api.reconnect.ReconnectStrategy;

import java.io.File;
import java.util.logging.Level;

/*
 * @author ProgrammingWizzard
 * @date 22.01.2017
 */
public class Main
{
    private Configuration configuration;
    private TS3Config ts3Config;
    private TS3Query ts3Query;
    private TS3Api ts3Api;

    public static void main(String[] args)
    {
        new Main().start();
    }

    private void start()
    {
        try
        {
            configuration = GsonUtils.readConfiguration(Configuration.class, new File("config.json"));
        } catch (Exception e)
        {
            System.out.println("Nastapil blad w generowaniu konfiguracji! Sprawdz, czy sa nadane odpowiednie uprawnienia!");
            e.printStackTrace();
            return;
        }
        ts3Config = new TS3Config();
        ts3Config.setHost(configuration.getIp());
        ts3Config.setQueryPort(configuration.getPort());
        ts3Config.setReconnectStrategy(ReconnectStrategy.constantBackoff());
        ts3Config.setDebugLevel(Level.ALL);

        ts3Query = new TS3Query(ts3Config);
        try
        {
            ts3Query.connect();
        } catch (TS3ConnectionFailedException ex)
        {
            System.out.println("Nastapil blad w polaczeniu ze serwerem TS3! Sprawdz poprawnosc danych!");
            return;
        }
        ts3Api = ts3Query.getApi();
        ts3Api.selectVirtualServerById(1);
        ts3Api.login(configuration.getLogin(), configuration.getPassword());
        ts3Api.setNickname(configuration.getName());
        ts3Api.registerAllEvents();

        ts3Api.addTS3Listeners(new ChannelListener(this));

        new ChannelTask(this).start();

        System.out.println("Poprawnie polaczono ze serwerem :)");
    }

    public Configuration getConfiguration()
    {
        return configuration;
    }

    public TS3Api getTs3Api()
    {
        return ts3Api;
    }

    public TS3Query getTs3Query()
    {
        return ts3Query;
    }

    public TS3Config getTs3Config()
    {
        return ts3Config;
    }
}
