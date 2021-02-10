package com.minty.eldertales.skills.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;

import com.minty.eldertales.skills.AwesomeSkills;
import com.minty.eldertales.skills.core.PlayerData;
import com.minty.eldertales.skills.events.SkillLevelChangeEvent;
import com.nisovin.magicspells.events.SpellApplyDamageEvent;

public class SkillsListeners implements Listener {
	
	private AwesomeSkills main;
	
	public SkillsListeners(AwesomeSkills _main)
	{
		main = _main;
	}
	
	@EventHandler
	public void onSpellDamage(SpellApplyDamageEvent e) {
		PlayerData data = main.getPlayerDataHandler().getPlayerData(e.getCaster().getUniqueId().toString());
		
		e.applyDamageModifier(data.getIntelligenceSkill().getTotalUpgradeAmount());
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e)
	{
		if(!(e.getDamager() instanceof Player)) return;
		
		PlayerData data = main.getPlayerDataHandler().getPlayerData(e.getDamager().getUniqueId().toString());
		
		double finalDamage = (e.getDamage() + data.getStrengthSkill().getTotalUpgradeAmount());
		if(e.getEntity() instanceof Player)
		{
			Player player = (Player) e.getEntity();
			PlayerData victimData  = main.getPlayerDataHandler().getPlayerData(player.getUniqueId().toString());
			
			finalDamage -= victimData.getDefenseSkill().getTotalUpgradeAmount();
		}
		e.setDamage(finalDamage);
	}
	
	@SuppressWarnings("deprecation")
	public void changeStats(Player player, PlayerData data)
	{
		player.setWalkSpeed(0.2f + data.getAgilitySkill().getTotalUpgradeAmount());
		player.setMaxHealth(20 + data.getEnduranceSkill().getTotalUpgradeAmount());
	}
	
	@EventHandler
	public void onSkillLevelUp(SkillLevelChangeEvent e)
	{
		Player player = e.getPlayer();
		changeStats(player, main.getPlayerDataHandler().getPlayerData(player.getUniqueId().toString()));
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		
		String UUID = player.getUniqueId().toString();
		
		if(main.getPlayerDataHandler() != null && main.getPlayerDataHandler().getPlayerData(UUID) != null)
		{
			changeStats(player, main.getPlayerDataHandler().getPlayerData(player.getUniqueId().toString()));
		}
	}
	
	@EventHandler
	public void onLevelUp(PlayerLevelChangeEvent e)
	{
		String uuid = e.getPlayer().getUniqueId().toString();
		
		if(e.getOldLevel() < e.getNewLevel())
		{
			main.getPlayerDataHandler().getPlayerData(uuid).addPoint(1);
		}
	}

}
