package com.minty.eldertales.skills.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.Main;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minty.eldertales.skills.AwesomeSkills;
import com.minty.eldertales.skills.events.SkillLevelChangeEvent;

public class PlayerSkill
{

	private int level;
	private Skill skill;
	
	private String owner;
	private int price = 0;
	private AwesomeSkills main;
	
	public PlayerSkill(String _owner, AwesomeSkills _main, int _level, Skill _skill)
	{
		owner = _owner;
		main =  _main;
		price = _skill.getBasePrice();
		level = _level;
		skill = _skill;
		checkUpgrade();
	}
	
	public void checkUpgrade()
	{

		price = getSkill().getBasePrice();
		
		for(int i = 0; i <= getLevel(); i++)
		{
			for(int p = 0; p < getSkill().getPaliers().size(); p++)
			{
				Integer palier = getSkill().getPaliers().get(p);
				
				if(i == palier)
				{
					price += getSkill().getUpgradeAmount();
				}
			}
		}
		for(Player player : Bukkit.getOnlinePlayers())
		{
			Bukkit.getPluginManager().callEvent(new SkillLevelChangeEvent(player, this));
		}
	}
	
	public ItemStack getIcon()
	{
		checkUpgrade();
		ItemStack it = new ItemStack(getSkill().getMaterial(), 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.setDisplayName("§6" + getSkill().getType().getName() + " §8- §7(" + getLevel() + "/" + getSkill().getMaxLevel() + ")");
		
		if(getLevel() == getSkill().getMaxLevel())
		{
			meta.addEnchant(Enchantment.OXYGEN, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		
		List<String> lore = new ArrayList<>();
		
		if(getLevel() < getSkill().getMaxLevel())
		{
			lore.add("");
			lore.add("§6» §eClique ici pour améliorer cette caractéristique");
			lore.add("§8- §eCoût : §6" + getPrice() + " point(s) de compétence");
			lore.add("");
		} else {
			lore.add("");
			lore.add("§4» §cCette caractéristique est déjà au niveau maximum !");
			lore.add("");
		}
		
		AwesomeSkills.getInstance().getPlayerDataHandler().getPlayerData(getOwner()).removePoints(1);;
		
		meta.setLore(lore);
		
		it.setItemMeta(meta);
		return it;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public float getTotalUpgradeAmount() {
		return getLevel() * getSkill().getLevelAmount();
	}
	
	public void purchase(Player player, PlayerData data, Inventory inv, int slot)
	{

		if(level < getSkill().getMaxLevel())
		{
			
			if(data.getPoints() < getPrice())
			{
				player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1f, 1f);
				player.sendMessage("§cVous n'avez pas assez de points de caractéristiques !");
			} else
			{
				level++;
				checkUpgrade();
				
				player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
				player.sendMessage("§fVous avez bien amélioré la caractéristique §e" + skill.getType().getName() + " §fau §eniveau " + level + " §f!");
				
				
				
				inv.setItem(slot, getIcon());
				Bukkit.getPluginManager().callEvent(new SkillLevelChangeEvent(player, this));
			}
			
		
		} else {
			player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1f, 1f);
			player.sendMessage("§cCette caractéristique est déjà au niveau maximum !");
		}
	}
	
	public int getPrice()
	{
		return price;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}
	
	public Skill getSkill() {
		return skill;
	}
	
}
