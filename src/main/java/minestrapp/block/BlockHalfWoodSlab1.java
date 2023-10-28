package minestrapp.block;

import minestrapp.Minestrapp;
import net.minecraft.util.ResourceLocation;

public class BlockHalfWoodSlab1 extends BlockWoodSlab1
{
	public BlockHalfWoodSlab1(String name)
	{
		super(name);
		this.setRegistryName(new ResourceLocation(Minestrapp.MODID, this.getTranslationKey().substring(5)));
	}

	@Override
	public boolean isDouble()
	{
		return false;
	}
}
