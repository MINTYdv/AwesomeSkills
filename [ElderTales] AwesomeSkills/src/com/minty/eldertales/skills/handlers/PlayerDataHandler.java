package com.minty.eldertales.skills.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.minty.eldertales.skills.AwesomeSkills;
import com.minty.eldertales.skills.core.PlayerData;
import com.minty.eldertales.skills.core.PlayerSkill;
import com.minty.eldertales.skills.core.Skill;
import com.minty.eldertales.skills.skills.SkillType;

import net.md_5.bungee.api.ChatColor;

public class PlayerDataHandler implements Listener
{

	private AwesomeSkills main;
	
	private Map<String, PlayerData> playersData = new HashMap<>();
	
	public PlayerDataHandler(AwesomeSkills _main)
	{
		main = _main;
		setup();
	}
	
	private void setup()
	{
		checkSection();
		loadAllData();
	}
	
	public void sendMsg(String m) {
		System.out.println("[AwesomeSkills] " + m);
	}
	
	private void checkSection()
	{
		ConfigurationSection sec = this.main.getConfigurationHandler().getCustomConfig().getConfigurationSection("data");
		if (sec == null)
		{
			this.main.getConfigurationHandler().getCustomConfig().createSection("data");
			main.getConfigurationHandler().getCustomConfig().set("data.default.points", 0);
		}
		System.out.println("Vérification du contenu de la configuration...");
	}
	
	private void loadAllData()
	{
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Loading data from config...");
		ConfigurationSection section = main.getConfigurationHandler().getCustomConfig().getConfigurationSection("data");
		for(String uuid : section.getKeys(false))
		{
			Bukkit.broadcastMessage("Loading UUID from config : " + uuid);
			if(uuid == "default") return;
			int points = main.getConfigurationHandler().getCustomConfig().getInt("data." + uuid + ".points");
			int strengthLevel = getSkillLevelFromConfig("strength", uuid);
			int agilityLevel = getSkillLevelFromConfig("agility", uuid);
			int defenseLevel = getSkillLevelFromConfig("defense", uuid);
			int intelligenceLevel = getSkillLevelFromConfig("intelligence", uuid);
			int enduranceLevel = getSkillLevelFromConfig("endurance", uuid);
			
			PlayerSkill strength = new PlayerSkill(uuid, main, strengthLevel, skillForType(SkillType.STRENGTH));
			PlayerSkill agility = new PlayerSkill(uuid, main, agilityLevel, skillForType(SkillType.AGILITY));
			PlayerSkill defense = new PlayerSkill(uuid, main, defenseLevel, skillForType(SkillType.DEFENSE));
			PlayerSkill intelligence = new PlayerSkill(uuid, main, intelligenceLevel, skillForType(SkillType.INTELLIGENCE));
			PlayerSkill endurance = new PlayerSkill(uuid, main, enduranceLevel, skillForType(SkillType.ENDURANCE));
			
			PlayerData newData = new PlayerData(uuid, points, strength, endurance, agility, defense, intelligence);
			getPlayersData().put(uuid, newData);
			
		}
		
	}
	
	public void saveAllData()
	{
		System.out.println("Sauvegarde de " + getPlayersData().entrySet().size() + " entrées en cours ...");
		for(Entry<String, PlayerData> entry : getPlayersData().entrySet())
		{
			String uuid = entry.getKey();
			PlayerData data = entry.getValue();
			
			main.getConfigurationHandler().getCustomConfig().set("data." + uuid + ".points", data.getPoints());
			
			main.getConfigurationHandler().getCustomConfig().set("data." + uuid + ".strength", data.getStrengthSkill().getLevel());
			main.getConfigurationHandler().getCustomConfig().set("data." + uuid + ".endurance", data.getEnduranceSkill().getLevel());
			main.getConfigurationHandler().getCustomConfig().set("data." + uuid + ".agility", data.getAgilitySkill().getLevel());
			main.getConfigurationHandler().getCustomConfig().set("data." + uuid + ".intelligence", data.getIntelligenceSkill().getLevel());
			main.getConfigurationHandler().getCustomConfig().set("data." + uuid + ".defense", data.getDefenseSkill().getLevel());
		}
	}
	
	private void createAccount(String uuid)
	{
		if(!hasAccount(uuid))
		{
			System.out.println("Création d'un compte pour le joueur " + uuid + "...");
			int points = 0;
			
			if(skillForType(SkillType.STRENGTH) == null) System.out.println("skillForType is null");
			if(main == null) System.out.println("main is null");
			
			PlayerSkill strength = new PlayerSkill(uuid, main, 1, skillForType(SkillType.STRENGTH));
			PlayerSkill agility = new PlayerSkill(uuid, main, 1, skillForType(SkillType.AGILITY));
			PlayerSkill defense = new PlayerSkill(uuid, main, 1, skillForType(SkillType.DEFENSE));
			PlayerSkill intelligence = new PlayerSkill(uuid, main, 1, skillForType(SkillType.INTELLIGENCE));
			PlayerSkill endurance = new PlayerSkill(uuid, main, 1, skillForType(SkillType.ENDURANCE));
			
			PlayerData newData = new PlayerData(uuid, points, strength, endurance, agility, defense, intelligence);
			getPlayersData().put(uuid, newData);
		}
	}
	
	private boolean hasAccount(String uuid)
	{
		if(getPlayersData().get(uuid) == null) {
			return false;
		} else {
			return true;
		}
	}
	
	private void loadData(String uuid)
	{
		if(!hasAccount(uuid))
		{
			Bukkit.broadcastMessage("§cLoading data for : " + uuid + " but he doesn't have an account yet !...");
			createAccount(uuid);
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		
		loadData(player.getUniqueId().toString());
	}
	
	public PlayerData getPlayerData(String uuid)
	{
		return getPlayersData().get(uuid);
	}
	
	public int getSkillLevelFromConfig(String skill, String uuid)
	{
		return main.getConfigurationHandler().getCustomConfig().getInt("data." + uuid + "." + skill.toLowerCase());
	}
	
	public Skill skillForType(SkillType type)
	{
		if(type == SkillType.AGILITY) {
			return main.getAgilitySkill();
		}
		if(type == SkillType.DEFENSE) {
			return main.getDefenseSkill();
		}
		if(type == SkillType.ENDURANCE) {
			return main.getEnduranceSkill();
		}
		if(type == SkillType.INTELLIGENCE) {
			return main.getIntelligenceSkill();
		}
		if(type == SkillType.STRENGTH) {
			return main.getStrengthSkill();
		}
		return null;
		
	}
	
	public Map<String, PlayerData> getPlayersData() {
		return playersData;
	}
	
}
