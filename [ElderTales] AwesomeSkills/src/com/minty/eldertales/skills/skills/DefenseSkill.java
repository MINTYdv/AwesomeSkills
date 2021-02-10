package com.minty.eldertales.skills.skills;

import org.bukkit.Material;

import com.minty.eldertales.skills.core.Skill;

public class DefenseSkill extends Skill {

	public DefenseSkill(int _maxLevel, float _levelAmount, int _upgradeAmount, int _upgradeEach)
	{
		super(Material.SHIELD, SkillType.DEFENSE, _maxLevel, _levelAmount, _upgradeAmount, _upgradeEach);
	}

}
