package com.minty.eldertales.skills.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minty.eldertales.skills.AwesomeSkills;
import com.minty.eldertales.skills.core.PlayerData;
import com.minty.eldertales.skills.gui.SkillsMenu;

public class SkillsCommand implements CommandExecutor
{

	private AwesomeSkills main;
	
	public SkillsCommand(AwesomeSkills _main) {
		main = _main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
	{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			
			if(args.length == 0)
			{
				main.getGuiManager().open(player, SkillsMenu.class);
				player.sendMessage("§eOuverture du menu des caractéristiques...");
			} else
			{
				// Check arguments
				
				if(args[0].equalsIgnoreCase("give"))
				{
					if(player.hasPermission("eldertales.skills.give.use"))
					{
						if(args.length < 3)
						{
							player.sendMessage("§cUtilisation: /skills give <Joueur> <Quantité>");
							return false;
						}
						
						Player target = Bukkit.getPlayer(args[1]);
						if(target == null)
						{
							player.sendMessage("§cErreur: Impossible de trouver un joueur avec ce nom !");
							return false;
						}
						
						try {
							int amount = Integer.parseInt(args[2]);
							
							main.getPlayerDataHandler().getPlayerData(target.getUniqueId().toString()).addPoint(amount);
							player.sendMessage("§fVous avez bien ajouté §e" + amount + " §fpoints de caractéristiques à §e" + target.getDisplayName() + " §f!");
							return false;
							
						} catch(NumberFormatException e)
						{
							player.sendMessage("§cErreur: Ceci n'est pas un nombre correct !");
							return false;
						}

					} else {
						player.sendMessage("Erreur: Vous n'avez pas les permissions requises pour exécuter cette commande !");
						return false;
					}
				}
				
				if(args[0].equalsIgnoreCase("take"))
				{
					if(player.hasPermission("eldertales.skills.take.use"))
					{
						if(args.length < 3)
						{
							player.sendMessage("§cUtilisation: /skills take <Joueur> <Quantité>");
							return false;
						}
						
						Player target = Bukkit.getPlayer(args[1]);
						if(target == null)
						{
							player.sendMessage("§cErreur: Impossible de trouver un joueur avec ce nom !");
							return false;
						}
						
						try {
							int amount = Integer.parseInt(args[2]);
							
							main.getPlayerDataHandler().getPlayerData(target.getUniqueId().toString()).removePoints(amount);
							player.sendMessage("§fVous avez bien retiré §e" + amount + " §fpoints de caractéristiques à §e" + target.getDisplayName() + " §f!");
							return false;
							
						} catch(NumberFormatException e)
						{
							player.sendMessage("§cErreur: Ceci n'est pas un nombre correct !");
							return false;
						}

					} else {
						player.sendMessage("Erreur: Vous n'avez pas les permissions requises pour exécuter cette commande !");
						return false;
					}
				}
				
				if(args[0].equalsIgnoreCase("reset"))
				{
					if(player.hasPermission("eldertales.skills.reset.use"))
					{
						if(args.length < 2)
						{
							player.sendMessage("§cUtilisation: /skills reset <Joueur>");
							return false;
						}
						
						Player target = Bukkit.getPlayer(args[1]);
						if(target == null)
						{
							player.sendMessage("§cErreur: Impossible de trouver un joueur avec ce nom !");
							return false;
						}
						
						PlayerData targetData = main.getPlayerDataHandler().getPlayerData(target.getUniqueId().toString());
						
						targetData.removePoints(Integer.MAX_VALUE);
						targetData.getAgilitySkill().setLevel(1);
						targetData.getIntelligenceSkill().setLevel(1);
						targetData.getDefenseSkill().setLevel(1);
						targetData.getEnduranceSkill().setLevel(1);
						targetData.getStrengthSkill().setLevel(1);
						player.sendMessage("§fVous avez bien réinitialisé à 0 les données de §e" + target.getDisplayName());
						targetData.getAgilitySkill().checkUpgrade();
						targetData.getDefenseSkill().checkUpgrade();
						targetData.getIntelligenceSkill().checkUpgrade();
						targetData.getEnduranceSkill().checkUpgrade();
						targetData.getStrengthSkill().checkUpgrade();
						return false;

					} else {
						player.sendMessage("Erreur: Vous n'avez pas les permissions requises pour exécuter cette commande !");
						return false;
					}
				}
				
				if(args[0].equalsIgnoreCase("info"))
				{
					if(player.hasPermission("eldertales.skills.info.use"))
					{
						if(args.length < 2)
						{
							player.sendMessage("§cUtilisation: /skills info <Joueur>");
							return false;
						}
						
						Player target = Bukkit.getPlayer(args[1]);
						if(target == null)
						{
							player.sendMessage("§cErreur: Impossible de trouver un joueur avec ce nom !");
							return false;
						}
						
						PlayerData targetData = main.getPlayerDataHandler().getPlayerData(target.getUniqueId().toString());
						
						player.sendMessage("§eDonnées de §6" + target.getDisplayName() + "§e :");
						player.sendMessage("");
						player.sendMessage("§8- §ePoints de caractéristiques: §6" + targetData.getPoints());
						player.sendMessage("");
						player.sendMessage("§8- §eNiveau d'Agilité: §6" + targetData.getAgilitySkill().getLevel());
						player.sendMessage("§8- §eNiveau de Force: §6" + targetData.getStrengthSkill().getLevel());
						player.sendMessage("§8- §eNiveau d'Intelligence: §6" + targetData.getIntelligenceSkill().getLevel());
						player.sendMessage("§8- §eNiveau d'Endurance: §6" + targetData.getEnduranceSkill().getLevel());
						player.sendMessage("§8- §eNiveau de Défense: §6" + targetData.getDefenseSkill().getLevel());
						player.sendMessage("");
						return false;

					} else {
						player.sendMessage("Erreur: Vous n'avez pas les permissions requises pour exécuter cette commande !");
						return false;
					}
				}
				
				if(args[0].equalsIgnoreCase("help"))
				{
					
					player.sendMessage("§eCommandes disponibles");
					player.sendMessage("");
					player.sendMessage("§6/skills §8- §eOuvrir le menu des caractéristiques");
					player.sendMessage("§6/skills take <Joueur> <Quantité> §8- §ePrendre des points de compétences à un joueur");
					player.sendMessage("§6/skills give <Joueur> <Quantité> §8- §eAjouter des points de compétences à un joueur");
					player.sendMessage("§6/skills reset <Joueur> §8- §eRéinitialiser les points de compétences d'un joueur");
					player.sendMessage("§6/skills info <Joueur> §8- §eAfficher les informations d'un joueur");
					player.sendMessage("");
					return false;
				}
				
				player.sendMessage("§cErreur: Impossible de trouver cette commande !");
				return false;
			}
			
		} else {
			sender.sendMessage("§cErreur: Cette commande peut seulement être exécuté par un joueur !");
		}
		return false;
	}

}
