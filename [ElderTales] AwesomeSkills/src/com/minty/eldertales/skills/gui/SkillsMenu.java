package com.minty.eldertales.skills.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minty.eldertales.skills.AwesomeSkills;
import com.minty.eldertales.skills.core.PlayerData;
import com.minty.eldertales.util.GuiBuilder;

public class SkillsMenu implements GuiBuilder {

	private AwesomeSkills main;
	
	public SkillsMenu(AwesomeSkills _main)
	{
		main = _main;
	}
	
	@Override
	public String name() {
		return "§eCaractéristiques";
	}

	@Override
	public int getSize() {
		return 27;
	}

	@Override
	public void contents(Player player, Inventory inv)
	{
		PlayerData playerData = main.getPlayerDataHandler().getPlayerData(player.getUniqueId().toString());
		
		player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1f, 1f);
		List<String> lore = new ArrayList<>();
		
		lore.add("");
		lore.add("§7Tes points de caractéristiques te permettent");
		lore.add("§7d'améliorer tes différentes caractéristiques et");
		lore.add("§7de devenir meilleur dans chaque domaine !");
		lore.add("");
		lore.add("§8- §ePoints de caractéristiques : §6" + playerData.getPoints());
		lore.add("");
		
		inv.setItem(4, createItem(Material.DOUBLE_PLANT, "§6Mes points de caractéristiques", (byte) 0, lore));
		
		inv.setItem(11, playerData.getDefenseSkill().getIcon());
		inv.setItem(12, playerData.getEnduranceSkill().getIcon());
		inv.setItem(13, playerData.getStrengthSkill().getIcon());
		inv.setItem(14, playerData.getAgilitySkill().getIcon());
		inv.setItem(15, playerData.getIntelligenceSkill().getIcon());
		
		
		inv.setItem(22, createItem(Material.BARRIER, "§cFermer", (byte) 0));
		
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack it, int slot)
	{
		if(it == null) return;
		PlayerData playerData = main.getPlayerDataHandler().getPlayerData(player.getUniqueId().toString());
		
		switch(it.getType())
		{
			case BARRIER:
				player.closeInventory();
				break;
			case BOOK:
				playerData.getIntelligenceSkill().purchase(player, playerData, inv, slot);
				break;
			case FEATHER:
				playerData.getAgilitySkill().purchase(player, playerData, inv, slot);
				break;
			case DIAMOND_AXE:
				playerData.getStrengthSkill().purchase(player, playerData, inv, slot);
				break;
			case CHAINMAIL_CHESTPLATE:
				playerData.getEnduranceSkill().purchase(player, playerData, inv, slot);
				break;
			case SHIELD:
				playerData.getDefenseSkill().purchase(player, playerData, inv, slot);
				break;
			default:
				break;
		}
	}
	
	private ItemStack createItem(Material mat, String name, byte data, List<String> lore)
	{
		ItemStack it = createItem(mat, name, data);
		ItemMeta meta = it.getItemMeta();
		
		meta.setLore(lore);
		
		it.setItemMeta(meta);
		
		return it;
	}

	
	private ItemStack createItem(Material mat, String name, byte data)
	{
		ItemStack it = new ItemStack(mat, 1, data);
		ItemMeta meta = it.getItemMeta();
		
		meta.setDisplayName(name);
		
		it.setItemMeta(meta);
		
		return it;
	}

}
