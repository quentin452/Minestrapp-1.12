package minestrapp.item.tools;

import com.google.common.collect.Multimap;

import minestrapp.MItems;
import minestrapp.MTabs;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MMace extends Item
{
    private final float attackDamage;
    private final Item.ToolMaterial material;
    private int burnTime;

    public MMace(Item.ToolMaterial material, String unlocalizedName)
    {
        this.material = material;
        this.maxStackSize = 1;
        this.setMaxDamage(material.getMaxUses());
        this.attackDamage = 4F + Math.round((material.getAttackDamage() * 2.5F));
        this.burnTime = 0;
        this.setRegistryName(unlocalizedName);
        this.setCreativeTab(MTabs.combat);
    }
    
    public float getAttackDamage()
    {
        return this.material.getAttackDamage() * 2.5F;
    }

    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        Block block = state.getBlock();

        if (state.getMaterial() == Material.CACTUS || state.getMaterial() == Material.CAKE || state.getMaterial() == Material.CORAL || state.getMaterial() == Material.CRAFTED_SNOW || state.getMaterial() == Material.GLASS || state.getMaterial() == Material.GOURD || state.getMaterial() == Material.ICE || state.getMaterial() == Material.LEAVES || state.getMaterial() == Material.PACKED_ICE || state.getMaterial() == Material.REDSTONE_LIGHT || state.getMaterial() == Material.SNOW || (state.getMaterial() == Material.WOOD && state.isFullBlock() == false))
            return 30F;
        else
            return 1.3F;
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
    	float knockback = 0.8F + this.getAttackDamage() / 10;
    	
    	if (target instanceof EntityLivingBase)
        {
            ((EntityLivingBase)target).knockBack(attacker, knockback, (double)MathHelper.sin(attacker.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(attacker.rotationYaw * 0.017453292F)));
        }
        else
        {
            target.addVelocity((double)(-MathHelper.sin(attacker.rotationYaw * 0.017453292F) * knockback), 0.1D, (double)(MathHelper.cos(attacker.rotationYaw * 0.017453292F) * knockback));
        }
    	
        if(this.material == MItems.BLAZIUM)
        {
        	target.setFire(4);
        }
        else if(this.material == MItems.GLACIERITE)
        {
        	target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1));
        }
        
        stack.damageItem(1, attacker);
        return true;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        if ((double)state.getBlockHardness(worldIn, pos) != 0.0D)
        {
            stack.damageItem(3, entityLiving);
        }

        return true;
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return this.material.getEnchantability();
    }

    /**
     * Return the name for this tool's material.
     */
    public String getToolMaterialName()
    {
        return this.material.toString();
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        ItemStack mat = this.material.getRepairItemStack();
        if (!mat.isEmpty() && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) return true;
        return super.getIsRepairable(toRepair, repair);
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -3.4D, 0));
        }

        return multimap;
    }
    
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	if(this.material == MItems.BLAZIUM && super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ) == EnumActionResult.PASS)
    	{
	        pos = pos.offset(facing);
	        ItemStack itemstack = player.getHeldItem(hand);
	
	        if (!player.canPlayerEdit(pos, facing, itemstack))
	        {
	            return EnumActionResult.FAIL;
	        }
	        else
	        {
	            if (worldIn.isAirBlock(pos))
	            {
	                worldIn.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
	                worldIn.setBlockState(pos, Blocks.FIRE.getDefaultState(), 11);
	            }
	
	            if (player instanceof EntityPlayerMP)
	            {
	                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, itemstack);
	            }
	
	            itemstack.damageItem(4, player);
	            return EnumActionResult.SUCCESS;
	        }
    	}
    	return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
    
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return true;
    }
    
    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
        return (enchantment == Enchantments.EFFICIENCY || enchantment == Enchantments.BANE_OF_ARTHROPODS || enchantment == Enchantments.FIRE_ASPECT || enchantment == Enchantments.KNOCKBACK || enchantment == Enchantments.LOOTING || enchantment == Enchantments.SHARPNESS || enchantment == Enchantments.SMITE || enchantment == Enchantments.UNBREAKING || enchantment == Enchantments.MENDING || enchantment == Enchantments.VANISHING_CURSE);
    }
    
    public MMace setBurnTime(int time)
	{
		this.burnTime = time;
		return this;
	}
    
    public int getItemBurnTime(ItemStack itemStack)
    {
		return this.burnTime;
    }
}