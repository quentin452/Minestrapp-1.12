package minestrapp.block;

import minestrapp.Minestrapp;
import net.minecraft.util.ResourceLocation;

public class BlockHalfMiscStoneSlab3 extends BlockMiscStoneSlab3
{
	public BlockHalfMiscStoneSlab3(String name)
	{
		super(name);
		this.setTranslationKey(name);
		this.setRegistryName(new ResourceLocation(Minestrapp.MODID, this.getTranslationKey().substring(5)));
	}

	@Override
	public boolean isDouble()
	{
		return false;
	}
}
