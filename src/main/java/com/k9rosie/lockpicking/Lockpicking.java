package com.k9rosie.lockpicking;

import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import com.k9rosie.lockpicking.configuration.LockpickConfig;
import com.k9rosie.lockpicking.model.Lockpick;

public class Lockpicking {
	
	private final LockpickingPlugin plugin;
	private LockpickConfig config;
	private HashMap<Material, Lockpick> lockpicks;
	
	public Lockpicking(LockpickingPlugin plugin) {
		this.plugin = plugin;
		config = new LockpickConfig(plugin);
	}
	
	public void init() {
		getLockpickData();
	}
	
	public Lockpicking getInstance() {
		return this;
	}
	
	public LockpickingPlugin getPlugin() {
		return plugin;
	}
	
	public LockpickConfig getConfig() {
		return config;
	}
	
	public void getLockpickData() {
		ConfigurationSection definedLockpicks = config.getConfig().getConfigurationSection("lockpicking.lockpicks");
		Set<String> keys = definedLockpicks.getKeys(false);
		
		for (String key : keys) {
			Material material = Material.getMaterial(key);
			int chance = config.getConfig().getInt("lockpicking.lockpicks."+key+".chance");
			Lockpick lockpick = new Lockpick(material, chance);
			lockpicks.put(material, lockpick);
		}
		
		
	}
	
	public void log(String message) {
		plugin.getLogger().log(Level.INFO, message);
	}
	
	public void log(Level level, String message) {
		plugin.getLogger().log(level, message);
	}
}
