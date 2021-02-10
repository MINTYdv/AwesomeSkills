package com.minty.eldertales.skills;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.minty.eldertales.skills.cmd.SkillsCommand;
import com.minty.eldertales.skills.gui.SkillsMenu;
import com.minty.eldertales.skills.handlers.ConfiguratitonHandler;
import com.minty.eldertales.skills.handlers.PlayerDataHandler;
import com.minty.eldertales.skills.listeners.SkillsListeners;
import com.minty.eldertales.skills.skills.AgilitySkill;
import com.minty.eldertales.skills.skills.DefenseSkill;
import com.minty.eldertales.skills.skills.EnduranceSkill;
import com.minty.eldertales.skills.skills.IntelligenceSkill;
import com.minty.eldertales.skills.skills.StrengthSkill;
import com.minty.eldertales.util.GuiBuilder;
import com.minty.eldertales.util.GuiManager;

public class AwesomeSkills extends JavaPlugin {

	private PlayerDataHandler playerDataHandler;
	private ConfiguratitonHandler configurationHandler;
	private GuiManager guiManager;
	
	private Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus = new HashMap<>();
	
	private AgilitySkill agilitySkill;
	private DefenseSkill defenseSkill;
	private StrengthSkill strengthSkill;
	private EnduranceSkill enduranceSkill;
	private IntelligenceSkill intelligenceSkill;
	
	private SkillsListeners skillsListeners;
	private static AwesomeSkills instance;
	
	@Override
	public void onEnable()
	{
		System.out.println("[" + this.getName() + "] Plugin actif !");
		System.out.println("Plugin par : MINTY#8171 - Commandes : ON");
		
		instance = this;
		
		saveDefaultConfig();
		
		registerSkills();
		registerReferences();
		registerListeners();
		registerCommands();
		registerMenus();
		
		for(Player player : Bukkit.getOnlinePlayers())
		{
			getSkillsListeners().changeStats(player, getPlayerDataHandler().getPlayerData(player.getUniqueId().toString()));
		}
	}

	private void registerSkills()
	{

		int agilityMax = getConfig().getInt("skills.Agility.maxLevel");
		int strengthMax = getConfig().getInt("skills.Strength.maxLevel");
		int defenseMax = getConfig().getInt("skills.Defense.maxLevel");
		int enduranceMax = getConfig().getInt("skills.Endurance.maxLevel");
		int intelligenceMax = getConfig().getInt("skills.Intelligence.maxLevel");
		
		float agilityLevelAmount = (float) getConfig().getDouble("skills.Agility.levelAmount");
		float defenseLevelAmount = (float) getConfig().getDouble("skills.Defense.levelAmount");
		float strengthLevelAmount = (float) getConfig().getDouble("skills.Strength.levelAmount");
		float intelligenceLevelAmount = (float) getConfig().getDouble("skills.Intelligence.levelAmount");
		float enduranceLevelAmount = (float) getConfig().getDouble("skills.Endurance.levelAmount");
		
		int enduranceEach = getConfig().getInt("skills.Endurance.upgradeEach");
		int strengthEach = getConfig().getInt("skills.Strength.upgradeEach");
		int intelligenceEach = getConfig().getInt("skills.Intelligence.upgradeEach");
		int agilityEach = getConfig().getInt("skills.Agility.upgradeEach");
		int defenseEach = getConfig().getInt("skills.Defense.upgradeEach");
		
		int defenseEachAmount = getConfig().getInt("skills.Defense.upgradeAmount");
		int agilityEachAmount = getConfig().getInt("skills.Agility.upgradeAmount");
		int strengthEachAmount = getConfig().getInt("skills.Strength.upgradeAmount");
		int intelligenceEachAmount = getConfig().getInt("skills.Intelligence.upgradeAmount");
		int enduranceEachAmount = getConfig().getInt("skills.Endurance.upgradeAmount");
		
		agilitySkill = new AgilitySkill(agilityMax, agilityLevelAmount, agilityEachAmount, agilityEach);
		strengthSkill = new StrengthSkill(strengthMax, strengthLevelAmount, strengthEachAmount, strengthEach);
		enduranceSkill = new EnduranceSkill(enduranceMax, enduranceLevelAmount, enduranceEachAmount, enduranceEach);
		intelligenceSkill = new IntelligenceSkill(intelligenceMax, intelligenceLevelAmount, intelligenceEachAmount, intelligenceEach); defenseSkill = new DefenseSkill(defenseMax, defenseLevelAmount, defenseEachAmount, defenseEach);
	}
	
	private void registerReferences()
	{
		configurationHandler = new ConfiguratitonHandler(this);
		guiManager = new GuiManager(this);
		playerDataHandler = new PlayerDataHandler(this);
	}

	private void registerListeners()
	{
		getServer().getPluginManager().registerEvents((Listener)getGuiManager(), (Plugin)this);
		getServer().getPluginManager().registerEvents((Listener)getPlayerDataHandler(), (Plugin)this);
		skillsListeners = new SkillsListeners(this);
		getServer().getPluginManager().registerEvents(skillsListeners, (Plugin)this);
	}
	
	public SkillsListeners getSkillsListeners() {
		return skillsListeners;
	}
	
	private void registerMenus()
	{
		getGuiManager().addMenu(new SkillsMenu(this));
	}
	
	private void registerCommands()
	{
		getCommand("skills").setExecutor((CommandExecutor)new SkillsCommand(this));
	}
	
	@Override
	public void onDisable()
	{
		System.out.println("[" + this.getName() + "] Plugin inactif !");
		getPlayerDataHandler().saveAllData();
		getConfigurationHandler().saveConfigFile();
	}
	
	public PlayerDataHandler getPlayerDataHandler() {
		return playerDataHandler;
	}
	
	public ConfiguratitonHandler getConfigurationHandler() {
		return configurationHandler;
	}
	
	public DefenseSkill getDefenseSkill() {
		return defenseSkill;
	}
	
	public AgilitySkill getAgilitySkill() {
		return agilitySkill;
	}
	
	public StrengthSkill getStrengthSkill() {
		return strengthSkill;
	}
	
	public EnduranceSkill getEnduranceSkill() {
		return enduranceSkill;
	}
	
	public static AwesomeSkills getInstance() {
		return instance;
	}
	
	public IntelligenceSkill getIntelligenceSkill() {
		return intelligenceSkill;
	}
	
	public GuiManager getGuiManager() {
		return guiManager;
	}
	
	public Map<Class<? extends GuiBuilder>, GuiBuilder> getRegisteredMenus() {
		return registeredMenus;
	}
	
}
