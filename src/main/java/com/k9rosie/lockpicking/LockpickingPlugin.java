package com.k9rosie.lockpicking;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.k9rosie.lockpicking.listener.PlayerListener;

public class LockpickingPlugin extends JavaPlugin {
	
	private Lockpicking lockpicking;
	
	public void onEnable() {
		lockpicking = new Lockpicking(this);
		
		lockpicking.init();
	}
	
	public void onDisable() {
		
	}
	
	public Lockpicking getLockpickingInstance() {
		return lockpicking;
	}
	
	public void registerEvents() {
		PluginManager pluginManager = this.getServer().getPluginManager();
		pluginManager.registerEvents(new PlayerListener(this), this);
	}

}
