package minestrapp.item.util;

import minestrapp.MTabs;
import minestrapp.potion.MPotions;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class MItemsFood extends ItemFood
{
	private ItemStack droppedItem;
	private int igniteTime;
	private boolean curesEffects;
	private int burnTime;
	private int burnMeta;
	private boolean isFancyFood = false;
	private float fedDuration = 0;
	private int fedModifier = 0;
	
	public MItemsFood(int amount, float saturation, boolean isWolfFood, String string)
	{
		super(amount, saturation, isWolfFood);
		this.setTranslationKey(string);
        this.setRegistryName(string);
        this.setCreativeTab(MTabs.food);
        this.droppedItem = null;
        this.igniteTime = 0;
        this.curesEffects = false;
        this.burnTime = 0;
		this.burnMeta = OreDictionary.WILDCARD_VALUE;
	}
	
	public MItemsFood(int amount, float saturation, boolean isWolfFood, String string, float wellFed, int modifier)
	{
		this(amount, saturation, isWolfFood, string);
		this.isFancyFood = true;
		this.fedDuration = wellFed;
		this.fedModifier = modifier;
	}
	
	public MItemsFood setDroppedItem(ItemStack stack)
	{
		this.droppedItem = stack;
		return this;
	}
	
	public MItemsFood setIgnitesPlayer(int duration)
	{
		this.igniteTime = duration;
		return this;
	}
	
	public MItemsFood setCuresEffects()
	{
		this.curesEffects = true;
		return this;
	}
	
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
		super.onFoodEaten(stack, worldIn, player);
		ItemStack dropstack = this.droppedItem;
		
		if(this.igniteTime > 0)
		{
			player.setFire(igniteTime);
		}
		if (this.curesEffects && !worldIn.isRemote)
		{
			player.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
		}
		else if(this.igniteTime < 0)
		{
			player.extinguish();
		}
		if (!player.capabilities.isCreativeMode && this.droppedItem != null && !worldIn.isRemote)
        {	
			if (!player.inventory.addItemStackToInventory(new ItemStack(dropstack.getItem(), 1, dropstack.getMetadata())))
            {
                player.dropItem(new ItemStack(dropstack.getItem(), 1, dropstack.getMetadata()), false);
            }
        }
		if (this.isFancyFood && player.getFoodStats().getFoodLevel() == 20)
		{
			player.addPotionEffect(new PotionEffect(MPotions.wellFed, Math.round(this.fedDuration * 1200), this.fedModifier, true, false));
		}
    }
	
	public MItemsFood setBurnTime(int time)
	{
		return setBurnTime(time, 0);
	}
	
	public MItemsFood setBurnTime(int time, int meta)
	{
		this.burnTime = time;
		this.burnMeta = meta;
		return this;
	}
	
	public int getItemBurnTime(ItemStack itemStack)
    {
		if(this.burnTime > 0 && this.burnMeta == itemStack.getMetadata())
			return this.burnTime;
		else
			return 0;
    }
	
	public MItemsFood setFancyFood(float wellFed, int modifier)
	{
		this.isFancyFood = true;
		this.fedDuration = wellFed;
		this.fedModifier = modifier;
		return this;
	}
}