package com.minty.eldertales.skills.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.minty.eldertales.skills.core.PlayerSkill;

public class SkillLevelChangeEvent extends Event {

	 private static final HandlerList handlers = new HandlerList();

	 private Player player;
	 private PlayerSkill playerSkill;
	 
	 public SkillLevelChangeEvent(Player _player, PlayerSkill _PlayerSkill)
	 {
		 player = _player;
		 playerSkill = _PlayerSkill;
	 }
	 
	 public PlayerSkill getPlayerSkill() {
		return playerSkill;
	}
	 
	 public Player getPlayer() {
		return player;
	}
	 
	 public HandlerList getHandlers()
	 {
	    return handlers; 
	 } 
	 
	 public static HandlerList getHandlerList()
	 { 
		return handlers;
	 }
	
}
