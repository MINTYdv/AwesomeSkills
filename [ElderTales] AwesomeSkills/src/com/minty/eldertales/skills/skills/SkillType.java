package com.minty.eldertales.skills.skills;

public enum SkillType {

	STRENGTH("Force"),
	AGILITY("Agilité"),
	ENDURANCE("Endurance"),
	INTELLIGENCE("Intelligence"),
	DEFENSE("Defense");
	
	private String name;
	
	SkillType(String _name)
	{
		name = _name;
	}
	
	public String getName() {
		return name;
	}
	
}
