package com.minty.eldertales.skills.skills;

import org.bukkit.Material;

import com.minty.eldertales.skills.core.Skill;

public class StrengthSkill extends Skill {

	public StrengthSkill(int _maxLevel, float _levelAmount, int _upgradeAmount, int _upgradeEach)
	{
		super(Material.DIAMOND_AXE, SkillType.STRENGTH, _maxLevel, _levelAmount, _upgradeAmount, _upgradeEach);
	}

}
