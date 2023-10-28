package minestrapp.block;

import java.util.Random;

import minestrapp.MBlocks;
import minestrapp.Minestrapp;
import minestrapp.block.util.BlockStoneBaseMOnly;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRedstoneOre extends BlockStoneBaseMOnly
{
	private final boolean isOn;

	public BlockRedstoneOre(String name, Material material, SoundType soundType, float hardness, String tool, int harvestLevel, boolean isOn)
	{
		super(null, material, soundType, hardness, tool, harvestLevel);
        this.setTranslationKey(name);

        if (isOn == false)
        {
            this.setTranslationKey(name);
            this.setRegistryName(new ResourceLocation(Minestrapp.MODID, this.getTranslationKey().substring(5)));
        }
		else
		{
			this.setRegistryName(new ResourceLocation(Minestrapp.MODID, "ore_redstone_lit"));
			this.setTickRandomly(true);
		}
		
		this.isOn = isOn;
		this.doesDropItem = true;
	}

	public int tickRate(World worldIn)
    {
        return 30;
    }
	
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
    {
        this.activate(worldIn, pos);
        super.onBlockClicked(worldIn, pos, playerIn);
    }
	
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        this.activate(worldIn, pos);
        super.onEntityWalk(worldIn, pos, entityIn);
    }
	
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        this.activate(worldIn, pos);
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
	
	private void activate(World worldIn, BlockPos pos)
    {
        this.spawnParticles(worldIn, pos);

        if (this == MBlocks.ore_redstone)
        {
        	IBlockState state = worldIn.getBlockState(pos);
            worldIn.setBlockState(pos, MBlocks.ore_redstone_lit.getDefaultState().withProperty(VARIANT, ((EnumStoneTypeMOnly)state.getValue(VARIANT))));
        }
    }
	
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (this == MBlocks.ore_redstone_lit)
        {
            worldIn.setBlockState(pos, MBlocks.ore_redstone.getDefaultState().withProperty(VARIANT, ((EnumStoneTypeMOnly)state.getValue(VARIANT))));
        }
    }
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
		if(state.getValue(VARIANT) == EnumStoneTypeMOnly.RED_ROCK || state.getValue(VARIANT) == EnumStoneTypeMOnly.DEEP_RED_ROCK)
			return Item.getItemFromBlock(MBlocks.redstone_sandy);
		else if(state.getValue(VARIANT) == EnumStoneTypeMOnly.COLDSTONE || state.getValue(VARIANT) == EnumStoneTypeMOnly.DEEP_COLDSTONE)
			return Item.getItemFromBlock(MBlocks.redstone_frosted);
		else if(state.getValue(VARIANT) == EnumStoneTypeMOnly.ICESTONE || state.getValue(VARIANT) == EnumStoneTypeMOnly.GLACIERROCK)
			return Item.getItemFromBlock(MBlocks.redstone_icy);
		else if(state.getValue(VARIANT) == EnumStoneTypeMOnly.OCEANSTONE || state.getValue(VARIANT) == EnumStoneTypeMOnly.DEEP_OCEANSTONE)
			return Item.getItemFromBlock(MBlocks.redstone_briny);
		else
			return Items.REDSTONE;
    }
	
	public int quantityDroppedWithBonus(int fortune, Random random)
    {
        return this.quantityDropped(random) + random.nextInt(fortune + 1);
    }
	
	public int quantityDropped(Random random)
    {
        return 4 + random.nextInt(2);
    }
	
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
    }
	
	@Override
    public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        if (this.getItemDropped(state, RANDOM, fortune) != Item.getItemFromBlock(this))
        {
            return 1 + RANDOM.nextInt(5);
        }
        return 0;
    }
	
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (this.isOn)
        {
            this.spawnParticles(worldIn, pos);
        }
    }
	
	private void spawnParticles(World worldIn, BlockPos pos)
    {
        Random random = worldIn.rand;
        double d0 = 0.0625D;

        for (int i = 0; i < 6; ++i)
        {
            double d1 = (double)((float)pos.getX() + random.nextFloat());
            double d2 = (double)((float)pos.getY() + random.nextFloat());
            double d3 = (double)((float)pos.getZ() + random.nextFloat());

            if (i == 0 && !worldIn.getBlockState(pos.up()).isOpaqueCube())
            {
                d2 = (double)pos.getY() + 0.0625D + 1.0D;
            }

            if (i == 1 && !worldIn.getBlockState(pos.down()).isOpaqueCube())
            {
                d2 = (double)pos.getY() - 0.0625D;
            }

            if (i == 2 && !worldIn.getBlockState(pos.south()).isOpaqueCube())
            {
                d3 = (double)pos.getZ() + 0.0625D + 1.0D;
            }

            if (i == 3 && !worldIn.getBlockState(pos.north()).isOpaqueCube())
            {
                d3 = (double)pos.getZ() - 0.0625D;
            }

            if (i == 4 && !worldIn.getBlockState(pos.east()).isOpaqueCube())
            {
                d1 = (double)pos.getX() + 0.0625D + 1.0D;
            }

            if (i == 5 && !worldIn.getBlockState(pos.west()).isOpaqueCube())
            {
                d1 = (double)pos.getX() - 0.0625D;
            }

            if (d1 < (double)pos.getX() || d1 > (double)(pos.getX() + 1) || d2 < 0.0D || d2 > (double)(pos.getY() + 1) || d3 < (double)pos.getZ() || d3 > (double)(pos.getZ() + 1))
            {
                worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d1, d2, d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }
	
	protected ItemStack getSilkTouchDrop(IBlockState state)
    {
        return new ItemStack(MBlocks.ore_redstone, 1, this.damageDropped(state));
    }
	
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(MBlocks.ore_redstone), 1, this.damageDropped(state));
    }
}
