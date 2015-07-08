package com.k9rosie.lockpicking.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.griefcraft.model.Protection;
import com.k9rosie.lockpicking.Lockpicking;
import com.k9rosie.lockpicking.LockpickingPlugin;
import com.k9rosie.lockpicking.model.Lockpick;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;

public class PlayerListener implements Listener {
	
	private LockpickingPlugin plugin;
	private Lockpicking lockpicking;
	
	public PlayerListener(LockpickingPlugin plugin) {
		this.plugin = plugin;
		lockpicking = plugin.getLockpickingInstance();
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onLockpick(PlayerInteractEvent event) {
		Action action = event.getAction();
		Player player = event.getPlayer();
		
		MPlayer mplayer = MPlayer.get(player);
		Faction faction = mplayer.getFaction();
		
		if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
	
			if (lockpicking.getLockpicks().keySet().contains(event.getItem().getType())) { // if the item in hand was a lockpick
				Lockpick lockpick = lockpicking.getLockpicks().get(event.getItem().getType());
				Block block = event.getClickedBlock();
				Material material = block.getType();
				
				Protection protectionData = lockpicking.getLWCHook().findProtection(block.getLocation());
				Player protectionOwner = protectionData.getBukkitOwner(); // owner of the chest
				
				MPlayer protectionMPlayer = MPlayer.get(protectionOwner);
				Faction protectionFaction = protectionMPlayer.getFaction(); // faction of the owner of the chest
				
				if (lockpicking.getDebug()) {
					lockpicking.log(player.getName() + " tried lockpicking a " + material + " with a " + lockpick.getMaterial());
				}
				
				if (faction.getId().equalsIgnoreCase(protectionFaction.getId())) { // if the lockpicker and the protection owner belong in the same faction
					player.sendMessage("You can't lockpick chests owned by players in your faction.");
					return;
				}
				
				int chance = lockpick.getChance();
				int rolledNumber = lockpicking.chance();
				
				if (lockpicking.getDebug()) {
					lockpicking.log(player.getName() + " rolled a " + rolledNumber + " with a lockpick chance of " + chance + "%");
				}
				
				if (chance >= rolledNumber) { // if we rolled a right number
					
					if (lockpicking.getDebug()) {
						lockpicking.log(player.getName() + " accessed chest at " + block.getLocation().getBlockX() + ", " + block.getLocation().getBlockY() + ", "+ block.getLocation().getBlockZ());
					}
					
					switch (material) {
					case CHEST:
						Chest chest = (Chest) block.getState();
						player.openInventory(chest.getInventory());
					case FURNACE:
						Furnace furnace = (Furnace) block.getState();
						player.openInventory(furnace.getInventory());
					case BURNING_FURNACE:
						Furnace burningFurnace = (Furnace) block.getState();
						player.openInventory(burningFurnace.getInventory());
					case DISPENSER:
						Dispenser dispenser = (Dispenser) block.getState();
						player.openInventory(dispenser.getInventory());
					case DROPPER:
						Dropper dropper = (Dropper) block.getState();
						player.openInventory(dropper.getInventory());
					default:
						break;						
					}
				}
				
			}
		}
	}
	
}
