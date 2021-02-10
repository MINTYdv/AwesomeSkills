package com.minty.eldertales.skills.skills;

import org.bukkit.Material;

import com.minty.eldertales.skills.core.Skill;

public class IntelligenceSkill extends Skill {

	public IntelligenceSkill(int _maxLevel, float _levelAmount, int _upgradeAmount, int _upgradeEach)
	{
		super(Material.BOOK, SkillType.INTELLIGENCE, _maxLevel, _levelAmount, _upgradeAmount, _upgradeEach);
	}

}
