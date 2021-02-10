package com.minty.eldertales.skills.handlers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.minty.eldertales.skills.AwesomeSkills;

public class ConfiguratitonHandler
{

	private AwesomeSkills main;
	
	private String fileName = "data.yml";
	
    private File customConfigFile;
    private FileConfiguration customConfig;
	
	public ConfiguratitonHandler(AwesomeSkills _main)
	{
		main = _main;
		setup();
	}
	
	private void setup()
	{
        createCustomConfig();
	}
	
	public void saveConfigFile()
	{
		try {
			getCustomConfig().save(new File(main.getDataFolder(), fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    private void createCustomConfig() {
        customConfigFile = new File(main.getDataFolder(), fileName);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            main.saveResource(fileName, false);
         }

        customConfig= new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        
        customConfig.set("data.default.points", 0);
    }
	
    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }
	
}
