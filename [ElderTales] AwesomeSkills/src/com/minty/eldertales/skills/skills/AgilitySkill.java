package com.minty.eldertales.skills.skills;

import org.bukkit.Material;

import com.minty.eldertales.skills.core.Skill;

public class AgilitySkill extends Skill {

	public AgilitySkill(int _maxLevel, float _levelAmount, int _upgradeAmount, int _upgradeEach)
	{
		super(Material.FEATHER, SkillType.AGILITY, _maxLevel, _levelAmount, _upgradeAmount, _upgradeEach);
	}

}
