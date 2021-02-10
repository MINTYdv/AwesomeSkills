package com.minty.eldertales.skills.core;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlayerData
{

	private PlayerSkill strengthSkill;
	private PlayerSkill enduranceSkill;
	private PlayerSkill agilitySkill;
	private PlayerSkill defenseSkill;
	private PlayerSkill intelligenceSkill;
	private int points;
	private String ownerUUID;
	
	public PlayerData(String _owner, Integer _points, PlayerSkill _strength, PlayerSkill _endurance, PlayerSkill _agility, PlayerSkill _defense, PlayerSkill _intelligence)
	{
		ownerUUID = _owner;
		points = _points;
		
		setAgilitySkill(_agility);
		setDefenseSkill(_defense);
		setStrengthSkill(_strength);
		setIntelligenceSkill(_intelligence);
		setEnduranceSkill(_endurance);
	}
	
	public void removePoints(int amount) {
		points -= amount;
		if(points < 0) {
			points = 0;
		}
	}
	
	public void addPoint(int amount) {
		points += amount;
		Player player = Bukkit.getPlayer(UUID.fromString(getOwnerUUID()));
		if(player == null) return;
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
		player.sendMessage("§fVous avez obtenu §e" + amount + " §fpoint(s) de caractéristiques !");
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setAgilitySkill(PlayerSkill agilitySkill) {
		this.agilitySkill = agilitySkill;
	}
	
	public void setDefenseSkill(PlayerSkill defenseSkill) {
		this.defenseSkill = defenseSkill;
	}
	
	public void setEnduranceSkill(PlayerSkill enduranceSkill) {
		this.enduranceSkill = enduranceSkill;
	}
	
	public void setIntelligenceSkill(PlayerSkill intelligenceSkill) {
		this.intelligenceSkill = intelligenceSkill;
	}
	
	public void setStrengthSkill(PlayerSkill strengthSkill) {
		this.strengthSkill = strengthSkill;
	}
	
	public PlayerSkill getAgilitySkill() {
		return agilitySkill;
	}
	
	public PlayerSkill getDefenseSkill()
	{
		return defenseSkill;
	}
	
	public PlayerSkill getEnduranceSkill()
	{
		return enduranceSkill;
	}
	
	public String getOwnerUUID() {
		return ownerUUID;
	}
	
	public PlayerSkill getIntelligenceSkill()
	{
		return intelligenceSkill;
	}
	
	public PlayerSkill getStrengthSkill()
	{
		return strengthSkill;
	}
	
}
