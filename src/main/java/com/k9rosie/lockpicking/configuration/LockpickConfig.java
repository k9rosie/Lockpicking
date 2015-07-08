package com.k9rosie.lockpicking.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.k9rosie.lockpicking.LockpickingPlugin;

public class LockpickConfig {
	private File configFile;
	private String configName;
	private FileConfiguration config;
	private LockpickingPlugin plugin;
	
	public LockpickConfig(LockpickingPlugin plugin) {
		this.plugin = plugin;
		configName = "config.yml";
		configFile = new File(plugin.getDataFolder(), configName);
	}
	
	public void reloadConfig(){
		config = YamlConfiguration.loadConfiguration(configFile);
		
		//look for defaults in jar
		try {
			Reader defConfigStream = new InputStreamReader(plugin.getResource(configName), "UTF8");
			if (defConfigStream != null) {
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
		        config.setDefaults(defConfig);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getConfig(){
		if(config == null) {
			reloadConfig();
		}
		
		return config;
	}
	
	public void saveConfig() {
	    if (config == null || configFile == null) {
	        return;
	    }
	    try {
	        getConfig().save(configFile);
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	public void saveDefaultConfig() {
	    if (configFile == null) {
	        configFile = new File(plugin.getDataFolder(), configName);
	    }
	    
	    if (!configFile.exists()) {
	    	plugin.saveResource(configName, false);
	    }
	}
	
}
