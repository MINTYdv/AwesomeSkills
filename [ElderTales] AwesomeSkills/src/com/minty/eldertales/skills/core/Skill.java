package com.minty.eldertales.skills.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import com.minty.eldertales.skills.skills.SkillType;

public class Skill
{

	private int maxLevel;
	private float levelAmount;
	private SkillType type;
	private int upgradeEach;
	private int upgradeAmount;
	private int basePrice = 1;
	private Material mat;
	
	private List<Integer> paliers = new ArrayList<>();
	
	public Skill(Material _mat, SkillType _type, Integer _maxLevel, float _levelAmount, int _upgradeAmount, int _upgradeEach)
	{
		upgradeEach = _upgradeEach;
		upgradeAmount = _upgradeAmount;
		type = _type;
		maxLevel = _maxLevel;
		levelAmount = _levelAmount;
		mat = _mat;
		paliersSetup();
	}
	
	private void paliersSetup()
	{
		for(int i = 0; i < getMaxLevel(); i++)
		{
			if(i % getUpgradeEach() == 0 && i != 0 && !paliers.contains(i))
			{
				paliers.add(i);
				System.out.println(getType().getName() + " - Palier : " + i);
			}
		}
	}
	
	public int getBasePrice() {
		return basePrice;
	}
	
	public List<Integer> getPaliers() {
		return paliers;
	}
	
	public int getMaxLevel() {
		return maxLevel;
	}
	
	public int getUpgradeEach() {
		return upgradeEach;
	}
	
	public int getUpgradeAmount() {
		return upgradeAmount;
	}
	
	public Material getMaterial() {
		return mat;
	}
	
	public float getLevelAmount()
	{
		return levelAmount;
	}
	
	public SkillType getType() {
		return type;
	}
	
}
