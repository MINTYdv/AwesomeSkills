package com.minty.eldertales.skills.skills;

import org.bukkit.Material;

import com.minty.eldertales.skills.core.Skill;

public class EnduranceSkill extends Skill {

	public EnduranceSkill(int _maxLevel, float _levelAmount, int _upgradeAmount, int _upgradeEach)
	{
		super(Material.CHAINMAIL_CHESTPLATE, SkillType.ENDURANCE, _maxLevel, _levelAmount, _upgradeAmount, _upgradeEach);
	}

}
