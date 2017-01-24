package com.programmingwizzard.groupmanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/*
 * @author ProgrammingWizzard
 * @date 23.01.2017
 */
public class GsonUtils
{
    private final static Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public static <T> T readConfiguration(Class<T> configurationClass, File file) throws Exception
    {
        if (!file.exists())
        {
            file.createNewFile();
            Files.write(file.toPath(), gson.toJson(configurationClass.newInstance()).getBytes(StandardCharsets.UTF_8));
        }
        return gson.fromJson(new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8), configurationClass);
    }

    public static <T> T readObjectFromFile(Class<T> object, File file) throws Exception
    {
        if (!file.exists())
        {
            return null;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        return gson.fromJson(reader, object);
    }

    public static void writeObjectToFile(Object object, File file) throws Exception
    {
        if (!file.exists())
        {
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file);
        writer.write(gson.toJson(object));
        writer.close();
    }
}
