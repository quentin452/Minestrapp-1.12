package minestrapp.block.item;

import minestrapp.block.BlockColdSand;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMultistate extends MItemBlock
{

	public ItemBlockMultistate(Block block)
	{
		super(block);
		if(!(block instanceof IMetaBlockName))
		{
			throw new IllegalArgumentException(String.format("The given Block %s is not an instance of IMetaBlockName!", block.getTranslationKey()));
		}
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	public String getTranslationKey(ItemStack stack)
	{
		return super.getTranslationKey() + "_" + ((IMetaBlockName) this.block).getSpecialName(stack);
	}
	
	public int getMetadata(int damage)
	{
		return damage;
	}
}
