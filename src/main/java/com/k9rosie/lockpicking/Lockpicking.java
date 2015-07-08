package com.k9rosie.lockpicking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import com.griefcraft.lwc.LWC;
import com.k9rosie.lockpicking.configuration.LockpickConfig;
import com.k9rosie.lockpicking.model.Lockpick;
import com.massivecraft.factions.Factions;
import com.massivecraft.massivecore.MassiveCore;

public class Lockpicking {
	
	private final LockpickingPlugin plugin;
	private LockpickConfig config;
	private HashMap<Material, Lockpick> lockpicks;
	
	private boolean debug;
	
	private MassiveCore mcore;
	private Factions factions;
	private LWC lwc;
	
	public Lockpicking(LockpickingPlugin plugin) {
		this.plugin = plugin;
		config = new LockpickConfig(plugin);
		lockpicks = new HashMap<Material, Lockpick>();
		
		// hook our dependencies
		mcore = MassiveCore.get();
		factions = Factions.get();
		lwc = LWC.getInstance();
	}
	
	public MassiveCore getMassiveCoreHook() {
		return mcore;
	}
	
	public Factions getFactionsHook() {
		return factions;
	}
	
	public LWC getLWCHook() {
		return lwc;
	}
	
	public void init() {
		debug = config.getConfig().getBoolean("lockpicking.debug"); // set the debug boolean
		
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
	
	public HashMap<Material, Lockpick> getLockpicks() {
		return lockpicks;
	}
	
	public void getLockpickData() {
		ConfigurationSection definedLockpicks = config.getConfig().getConfigurationSection("lockpicking.lockpicks");
		Set<String> keys = definedLockpicks.getKeys(false); // the list of lockpicks defined in lockpicking.lockpicks
		
		for (String key : keys) {
			Material material = Material.getMaterial(key); // get the material from the lockpick material
			
			if (material == null) {
				log(Level.SEVERE, key + " doesn't exist as a material");
			} else {
				int chance = config.getConfig().getInt("lockpicking.lockpicks."+key+".chance");
				
				if (debug) {
					log("Caching lockpick with materal of " + material.toString() + "and chance of " + chance);
				}
				
				Lockpick lockpick = new Lockpick(material, chance);
				lockpicks.put(material, lockpick); // store lockpick data in the cache
			}
		}
		
		
	}
	
	public int chance() {
		Random generator = new Random(System.currentTimeMillis());
		return generator.nextInt(99) + 1;
	}
	
	public boolean getDebug() {
		return debug;
	}
	
	public void log(String message) {
		plugin.getLogger().log(Level.INFO, message);
	}
	
	public void log(Level level, String message) {
		plugin.getLogger().log(level, message);
	}
}
