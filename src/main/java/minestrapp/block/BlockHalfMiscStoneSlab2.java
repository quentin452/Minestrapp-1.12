package minestrapp.block;

import minestrapp.Minestrapp;
import net.minecraft.util.ResourceLocation;

public class BlockHalfMiscStoneSlab2 extends BlockMiscStoneSlab2
{
	public BlockHalfMiscStoneSlab2(String name)
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
