package minestrapp.block.helper;

import java.util.List;

import com.google.common.collect.Lists;

import minestrapp.block.magnetpiston.BlockMagnetPistonBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.BlockPistonStructureHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMagnetPistonStructureHelper extends BlockPistonStructureHelper
// Credit to crazysnailboy for all code.
{
	private final World world;
	private final BlockPos pistonPos;
	private final BlockPos blockToMove;
	private final boolean extending;
	private final EnumFacing moveDirection;
	private final List<BlockPos> toMove = Lists.<BlockPos>newArrayList();
	private final List<BlockPos> toDestroy = Lists.<BlockPos>newArrayList();
	private final int strength;

	public BlockMagnetPistonStructureHelper(World world, BlockPos pos, EnumFacing pistonFacing, boolean extending, int strength)
	{
		super(world, pos, pistonFacing, extending);
		this.world = world;
		this.pistonPos = pos;
		if(extending)
			this.strength = 12;
		else
			this.strength = strength * 3;
		this.extending = extending;

		if (extending)
		{
			this.moveDirection = pistonFacing;
			this.blockToMove = pos.offset(pistonFacing);
		}
		else
		{
			this.moveDirection = pistonFacing.getOpposite();
			this.blockToMove = pos.offset(pistonFacing, 2);
		}
	}

	@Override
	public boolean canMove()
	{
		this.toMove.clear();
		this.toDestroy.clear();
		IBlockState state = this.world.getBlockState(this.blockToMove);

		if (!BlockMagnetPistonBase.canPush(state, this.world, this.blockToMove, this.moveDirection, false, this.moveDirection))
		{
			if (state.getPushReaction() == EnumPushReaction.DESTROY)
			{
				this.toDestroy.add(this.blockToMove);
				return true;
			}
			else
			{
				return false;
			}

		}
		else if (!this.addBlockLine(this.blockToMove, this.moveDirection))
		{
			return false;
		}
		else
		{
			for (int i = 0; i < this.toMove.size(); ++i)
			{
				BlockPos pos = this.toMove.get(i);
				if (this.world.getBlockState(pos).getBlock() == Blocks.SLIME_BLOCK && !this.addBranchingBlocks(pos))
				{
					return false;
				}
			}
			return true;
		}
	}

	private boolean addBlockLine(BlockPos origin, EnumFacing direction)
	{
		IBlockState state = this.world.getBlockState(origin);
		Block block = state.getBlock();

		if (state.getBlock().isAir(state, this.world, origin))
		{
			return true;
		}
		else if (!BlockMagnetPistonBase.canPush(state, this.world, origin, this.moveDirection, false, direction))
		{
			return true;
		}
		else if (origin.equals(this.pistonPos))
		{
			return true;
		}
		else if (this.toMove.contains(origin))
		{
			return true;
		}
		else
		{
			int i = 1;

			if (i + this.toMove.size() > this.strength && this.extending)
			{
				return false;
			}
			else
			{
				for(int j = 0 ; j < this.strength ; j++) // while (block == Blocks.SLIME_BLOCK)
				{
					BlockPos blockpos = origin.offset(this.moveDirection.getOpposite(), i);
					state = this.world.getBlockState(blockpos);
					block = state.getBlock();

					if ((state.getBlock().isAir(state, this.world, blockpos) && this.extending) || !BlockMagnetPistonBase.canPush(state, this.world, blockpos, this.moveDirection, false, this.moveDirection.getOpposite()) || blockpos.equals(this.pistonPos))
					{
						break;
					}

					++i;

					if (i + this.toMove.size() > this.strength && this.extending)
					{
						return false;
					}
				}

				int i1 = 0;
				
				int pullLength = this.strength - 1;
				if(i - 1 < this.strength - 1)
					pullLength = i - 1;

				for (int j = (this.extending ? i - 1 : pullLength); j >= 0; --j)
				{
					this.toMove.add(origin.offset(this.moveDirection.getOpposite(), j));
					++i1;
				}

				int j1 = 1;

				while (true)
				{
					BlockPos blockpos1 = origin.offset(this.moveDirection, j1);
					int k = this.toMove.indexOf(blockpos1);

					if (k > -1)
					{
						this.reorderListAtCollision(i1, k);

						for (int l = 0; l <= k + i1; ++l)
						{
							BlockPos blockpos2 = this.toMove.get(l);

							if (this.world.getBlockState(blockpos2).getBlock() == Blocks.SLIME_BLOCK && !this.addBranchingBlocks(blockpos2))
							{
								return false;
							}
						}

						return true;
					}

					state = this.world.getBlockState(blockpos1);

					if (state.getBlock().isAir(state, this.world, blockpos1))
					{
						return true;
					}

					if (!BlockMagnetPistonBase.canPush(state, this.world, blockpos1, this.moveDirection, true, this.moveDirection) || blockpos1.equals(this.pistonPos))
					{
						return false;
					}

					if (state.getPushReaction() == EnumPushReaction.DESTROY)
					{
						this.toDestroy.add(blockpos1);
						return true;
					}

					if (this.toMove.size() >= this.strength)
					{
						return false;
					}

					this.toMove.add(blockpos1);
					++i1;
					++j1;
				}
			}
		}
	}

	private void reorderListAtCollision(int p_177255_1_, int p_177255_2_)
	{
		List<BlockPos> list = Lists.<BlockPos>newArrayList();
		List<BlockPos> list1 = Lists.<BlockPos>newArrayList();
		List<BlockPos> list2 = Lists.<BlockPos>newArrayList();
		list.addAll(this.toMove.subList(0, p_177255_2_));
		list1.addAll(this.toMove.subList(this.toMove.size() - p_177255_1_, this.toMove.size()));
		list2.addAll(this.toMove.subList(p_177255_2_, this.toMove.size() - p_177255_1_));
		this.toMove.clear();
		this.toMove.addAll(list);
		this.toMove.addAll(list1);
		this.toMove.addAll(list2);
	}

	private boolean addBranchingBlocks(BlockPos pos)
	{
		for (EnumFacing enumfacing : EnumFacing.values())
		{
			if (enumfacing.getAxis() != this.moveDirection.getAxis() && !this.addBlockLine(pos.offset(enumfacing), this.moveDirection))
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public List<BlockPos> getBlocksToMove()
	{
		return this.toMove;
	}

	@Override
	public List<BlockPos> getBlocksToDestroy()
	{
		return this.toDestroy;
	}
}
