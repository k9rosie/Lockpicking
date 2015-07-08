package com.k9rosie.lockpicking.model;

import org.bukkit.Material;

public class Lockpick {
	
	private int chance;
	private Material material;
	
	public Lockpick(Material material, int chance) {
		this.material = material;
		this.chance = chance;
	}
	
	public void setChance(int chance) {
		this.chance = chance;
	}
	
	public int getChance() {
		return chance;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public Material getMaterial() {
		return material;
	}
	
}
