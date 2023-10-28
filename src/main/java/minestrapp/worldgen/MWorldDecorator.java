package minestrapp.worldgen;

import java.util.Random;
import java.util.Set;

import minestrapp.MBlocks;
import minestrapp.block.BlockColdSand;
import minestrapp.block.BlockGlowshroom;
import minestrapp.block.BlockMDirt;
import minestrapp.block.BlockMGrass;
import minestrapp.block.BlockTerracreep;
import minestrapp.block.EnumStoneTypeMOnly;
import minestrapp.block.crops.BlockBerryBush;
import minestrapp.block.util.BlockStoneBaseMOnly;
import minestrapp.config.MConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeBeach;
import net.minecraft.world.biome.BiomeDesert;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeHills;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeMesa;
import net.minecraft.world.biome.BiomeMushroomIsland;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeRiver;
import net.minecraft.world.biome.BiomeSavanna;
import net.minecraft.world.biome.BiomeSnow;
import net.minecraft.world.biome.BiomeStoneBeach;
import net.minecraft.world.biome.BiomeSwamp;
import net.minecraft.world.biome.BiomeTaiga;
import net.minecraft.world.biome.Biome.TempCategory;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenBush;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class MWorldDecorator
{
	public static void generate(World world, int chunkX, int chunkZ, Random random)
	{
		if(world.provider.getDimension() == 0)
		{
			BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
			Chunk chunk = world.getChunk(pos);
			BiomeProvider chunkManager = world.getBiomeProvider();
			IBlockState stone = MBlocks.stone.getDefaultState();
			EnumStoneTypeMOnly sType = null;
			EnumStoneTypeMOnly dType = EnumStoneTypeMOnly.DEEPSTONE;
			int deepStoneDepth = 35;
	
			for (int x = 0; x < 16; x++)
			{
				for (int z = 0; z < 16; z++)
				{
					BlockPos subpos = new BlockPos(x, 0, z);
					Biome biome = chunkManager.getBiome(subpos, Biomes.PLAINS);
					
					int i = subpos.getX();
			        int j = subpos.getZ();
			        int k = chunk.getBiomeArray()[j << 4 | i] & 255;
	
			        if (k == 255)
			        {
			            k = Biome.getIdForBiome(biome);
			            chunk.getBiomeArray()[j << 4 | i] = (byte)(k & 255);
			        }
	
			        biome = Biome.getBiome(k);
	
					sType = MStoneGen.getStoneForBiome(biome);
					dType = MStoneGen.getDeepStoneForBiome(biome);
					deepStoneDepth = MStoneGen.getDeepstoneDepthForBiome(biome, random);
	
					for (int y = 256; y >= 0; y--)
					{
						BlockPos subpos2 = new BlockPos((chunkX * 16 + x), y, (chunkZ * 16 + z));
						IBlockState state = world.getBlockState(subpos2);
						Block block = state.getBlock();
	
						if (state.isFullBlock() == true)
						{
							if(state.getBlock() instanceof BlockOre)
							{
								if(state == Blocks.COAL_ORE.getDefaultState())
								{
									if (MConfig.generateDeepstone && y < deepStoneDepth)
									{
										chunk.setBlockState(subpos2, MBlocks.ore_coal.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, dType));
									}
									else if (sType != null)
									{
										chunk.setBlockState(subpos2, MBlocks.ore_coal.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, sType));
									}
								}
								else if(state == Blocks.IRON_ORE.getDefaultState())
								{
									if (MConfig.generateDeepstone && y < deepStoneDepth)
									{
										chunk.setBlockState(subpos2, MBlocks.ore_iron.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, dType));
									}
									else if (sType != null)
									{
										chunk.setBlockState(subpos2, MBlocks.ore_iron.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, sType));
									}
								}
								else if(state == Blocks.GOLD_ORE.getDefaultState())
								{
									if (MConfig.generateDeepstone && y < deepStoneDepth)
									{
										chunk.setBlockState(subpos2, MBlocks.ore_gold.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, dType));
									}
									else if (sType != null)
									{
										chunk.setBlockState(subpos2, MBlocks.ore_gold.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, sType));
									}
								}
								else if(state == Blocks.LAPIS_ORE.getDefaultState())
								{
									if (MConfig.generateDeepstone && y < deepStoneDepth)
									{
										chunk.setBlockState(subpos2, MBlocks.ore_lapis.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, dType));
									}
									else if (sType != null)
									{
										chunk.setBlockState(subpos2, MBlocks.ore_lapis.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, sType));
									}
								}
								else if(state == Blocks.DIAMOND_ORE.getDefaultState())
								{
									if (MConfig.generateDeepstone && y < deepStoneDepth)
									{
										chunk.setBlockState(subpos2, MBlocks.ore_diamond.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, dType));
									}
									else if (sType != null)
									{
										chunk.setBlockState(subpos2, MBlocks.ore_diamond.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, sType));
									}
								}
								else if(state == Blocks.EMERALD_ORE.getDefaultState())
								{
									if (MConfig.generateDeepstone && y < deepStoneDepth)
									{
										chunk.setBlockState(subpos2, MBlocks.ore_emerald.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, dType));
									}
									else if (sType != null)
									{
										chunk.setBlockState(subpos2, MBlocks.ore_emerald.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, sType));
									}
								}
							}
							else if(state.getBlock() instanceof BlockRedstoneOre)
							{
								if (MConfig.generateDeepstone && y < deepStoneDepth)
								{
									chunk.setBlockState(subpos2, MBlocks.ore_redstone.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, dType));
								}
								else if (sType != null)
								{
									chunk.setBlockState(subpos2, MBlocks.ore_redstone.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, sType));
								}
							}
							else if(state.getBlock() == Blocks.COBBLESTONE)
							{
								if (MConfig.generateDeepstone && y < deepStoneDepth)
								{
									chunk.setBlockState(subpos2, MBlocks.cobblestone.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, dType));
								}
								else if (sType != null)
								{
									chunk.setBlockState(subpos2, MBlocks.cobblestone.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, sType));
								}
							}
							else if(state.getBlock() == Blocks.MOSSY_COBBLESTONE)
							{
								if (MConfig.generateDeepstone && y < deepStoneDepth)
								{
									chunk.setBlockState(subpos2, MBlocks.mossy_cobblestone.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, dType));
								}
								else if (sType != null)
								{
									chunk.setBlockState(subpos2, MBlocks.mossy_cobblestone.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, sType));
								}
							}
							else if(state == Blocks.MONSTER_EGG.getDefaultState().withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.STONE))
							{
								if (MConfig.generateDeepstone && y < deepStoneDepth)
								{
									chunk.setBlockState(subpos2, MBlocks.silverfish_stone.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, dType));
								}
								else if (sType != null)
								{
									chunk.setBlockState(subpos2, MBlocks.silverfish_stone.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, sType));
								}
							}
	//						else if(state.getBlock() instanceof BlockStoneBrick)
	//						{
	//							if(state.getValue(BlockStoneBrick.VARIANT) == BlockStoneBrick.EnumType.DEFAULT)
	//							{
	//								if (y < deepStoneDepth)
	//								{
	//									chunk.setBlockState(subpos2, MBlocks.stone_bricks.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, dType));
	//								}
	//								else if (sType != null)
	//								{
	//									chunk.setBlockState(subpos2, MBlocks.stone_bricks.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, sType));
	//								}
	//							}
	//							if(state.getValue(BlockStoneBrick.VARIANT) == BlockStoneBrick.EnumType.MOSSY)
	//							{
	//								if (y < deepStoneDepth)
	//								{
	//									chunk.setBlockState(subpos2, MBlocks.mossy_stone_bricks.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, dType));
	//								}
	//								else if (sType != null)
	//								{
	//									chunk.setBlockState(subpos2, MBlocks.mossy_stone_bricks.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, sType));
	//								}
	//							}
	//							if(state.getValue(BlockStoneBrick.VARIANT) == BlockStoneBrick.EnumType.CRACKED)
	//							{
	//								if (y < deepStoneDepth)
	//								{
	//									chunk.setBlockState(subpos2, MBlocks.cracked_stone_bricks.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, dType));
	//								}
	//								else if (sType != null)
	//								{
	//									chunk.setBlockState(subpos2, MBlocks.cracked_stone_bricks.getDefaultState().withProperty(BlockStoneBaseMOnly.VARIANT, sType));
	//								}
	//							}
	//						}
							else if (MConfig.generateClaySoil && (biome.getDefaultTemperature() >= 1.0F || biome instanceof BiomeJungle || biome instanceof BiomeSwamp))
							{
								if (state.getBlock() == Blocks.DIRT)
									chunk.setBlockState(subpos2, MBlocks.clay_soil.getDefaultState().withProperty(BlockMDirt.VARIANT, BlockMDirt.DirtType.byMetadata(((BlockDirt.DirtType)state.getValue(BlockDirt.VARIANT)).getMetadata())));
								else if (state.getBlock() == Blocks.GRASS)
								{
									if(world.getBlockState(subpos2.up()).getBlock() instanceof BlockLeaves)
										chunk.setBlockState(subpos2, MBlocks.clay_soil.getDefaultState().withProperty(BlockMDirt.VARIANT, BlockMDirt.DirtType.PODZOL));
									else
										chunk.setBlockState(subpos2, MBlocks.clay_grass.getDefaultState());
								}
							}
							else if (MConfig.generatePermafrost && biome.getDefaultTemperature() < 0.2F)
							{
								if (state.getBlock() == Blocks.DIRT)
									chunk.setBlockState(subpos2, MBlocks.permafrost.getDefaultState().withProperty(BlockMDirt.VARIANT, BlockMDirt.DirtType.byMetadata(((BlockDirt.DirtType)state.getValue(BlockDirt.VARIANT)).getMetadata())));
								else if (state.getBlock() == Blocks.SAND)
									chunk.setBlockState(subpos2, MBlocks.cold_sand.getDefaultState().withProperty(BlockColdSand.VARIANT, BlockColdSand.EnumType.byMetadata(((BlockSand.EnumType)state.getValue(BlockSand.VARIANT)).getMetadata())));
								else if (state.getBlock() == Blocks.GRASS)
								{
									boolean leaves = false;
									for (int n = subpos2.getY() ; n < (subpos2.getY() + 5) ; n++)
									{
										if(world.getBlockState(new BlockPos(subpos2.getX(), n + 1, subpos2.getZ())).getBlock() instanceof BlockLeaves)
										{
											leaves = true;
											break;
										}
									}
									if (leaves)
										chunk.setBlockState(subpos2, MBlocks.permafrost.getDefaultState().withProperty(BlockMDirt.VARIANT, BlockMDirt.DirtType.PODZOL));
									else
										chunk.setBlockState(subpos2, MBlocks.lichen.getDefaultState());
								}
							}
						}
						else if (state.getBlock() == Blocks.TALLGRASS)
						{
							if(MConfig.generateTundraGrass && biome.getDefaultTemperature() < 0.2F)
								chunk.setBlockState(subpos2, MBlocks.tundra_grass.getDefaultState());
							else if (MConfig.generateSavannaGrass && biome instanceof BiomeSavanna)
							{
								int o = random.nextInt(3) + 1;
								
								for (int p = 0 ; p < o ; p++)
								{
									BlockPos grassPos = new BlockPos(subpos2.getX(), subpos2.getY() + p, subpos2.getZ());
									if(world.getBlockState(grassPos).getBlock().isReplaceable(world, grassPos))
										chunk.setBlockState(grassPos, MBlocks.savanna_grass.getDefaultState());
								}
							}
						}
						else if (state.getBlock() == Blocks.GRASS_PATH)
						{
							if (MConfig.generateClaySoil && (biome.getDefaultTemperature() >= 1.0F || biome instanceof BiomeJungle || biome instanceof BiomeSwamp))
								chunk.setBlockState(subpos2, MBlocks.clay_grass_path.getDefaultState());
							else if (MConfig.generatePermafrost && biome.getDefaultTemperature() < 0.2F)
								chunk.setBlockState(subpos2, MBlocks.lichen_path.getDefaultState());
						}
						else if (state.getBlock() == Blocks.FARMLAND)
						{
							if (MConfig.generateClaySoil && (biome.getDefaultTemperature() >= 1.0F || biome instanceof BiomeJungle || biome instanceof BiomeSwamp))
								chunk.setBlockState(subpos2, MBlocks.clay_farmland.getDefaultState());
							else if (MConfig.generatePermafrost && biome.getDefaultTemperature() < 0.2F)
								chunk.setBlockState(subpos2, MBlocks.permafrost_farmland.getDefaultState());
						}
					}
				}
			}
			
			//TODO: I think there's still a bug where runaway chunks or partially generated replacement blocks can still happen in the positive x & z directions, but it's unconfirmed. Needs further testing...
			
			Biome biome = world.getBiome(new BlockPos(chunkX * 16, 0, chunkZ * 16));
			
			/*
			if(biome instanceof BiomeSavanna ){
				int chance = random.nextInt(100);
				if(chance<=10){
					int posX = random.nextInt(16)+8;
					int posY = 64 - random.nextInt(6);
					int posZ = random.nextInt(16)+8;
					BlockPos burfalaunt = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
					MGenBurfalaunt burfalauntGen = new MGenBurfalaunt();
					burfalauntGen.generate(world, random, burfalaunt);
				}
				
			}*/
			
			if(biome instanceof BiomeRiver || biome instanceof BiomeSwamp)
			{
				if(MConfig.generateMud)
				{
					int posX = random.nextInt(16)+8;
					int posY = 64 - random.nextInt(6);
					int posZ = random.nextInt(16)+8;
					
					BlockPos mudPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
					MGenMud mudGen = new MGenMud(MBlocks.mud, 7);
					mudGen.generate(world, random, mudPos);
				}
			}

			//Berry Gen
			if(MConfig.generateBerryBushes)
			{
				int posX = random.nextInt(16)+8;
				int posY = 94 - random.nextInt(38);
				int posZ = random.nextInt(16)+8;
					
				BlockPos berryPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
				MGenBushes bushGen = new MGenBushes(biome, 6);
				bushGen.generate(world, random, berryPos);
			}
			
			if(MConfig.generateCarpetGlowMoss)
			{
				int chance = 1;
				int size = 10;
				
				if(biome == Biomes.ROOFED_FOREST || biome == Biomes.MUTATED_ROOFED_FOREST)
				{
					chance = 3;
					size = 20;
				}
				else if(biome == Biomes.MUSHROOM_ISLAND || biome == Biomes.MUSHROOM_ISLAND_SHORE)
				{
					chance = 10;
					size = 80;
				}
				
				for(int i = 0 ; i < chance ; i++)
				{
					int posX = random.nextInt(16)+8;
					int posY = 60 - random.nextInt(58);
					int posZ = random.nextInt(16)+8;
					
					BlockPos mossPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
					if(MBlocks.carpet_glow_moss.canPlaceBlockAt(world, mossPos))
					{
						MGenMoss mossGen = new MGenMoss(size, MBlocks.carpet_glow_moss);
						mossGen.generate(world, random, mossPos);
					}
				}
			}
			
			if(MConfig.generateCreepingGlowMoss)
			{
				int chance = 1;
				int size = 32;
				
				if(biome == Biomes.ROOFED_FOREST || biome == Biomes.MUTATED_ROOFED_FOREST)
				{
					chance = 3;
					size = 64;
				}
				else if(biome == Biomes.MUSHROOM_ISLAND || biome == Biomes.MUSHROOM_ISLAND_SHORE)
				{
					chance = 10;
					size = 128;
				}
				
				for(int i = 0 ; i < chance ; i++)
				{
					int posX = random.nextInt(16)+8;
					int posY = 48 - random.nextInt(46);
					int posZ = random.nextInt(16)+8;
					
					BlockPos mossPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
					if(MBlocks.creeping_glow_moss.canPlaceBlockAt(world, mossPos))
					{
						MGenMoss mossGen = new MGenMoss(size, MBlocks.creeping_glow_moss);
						mossGen.generate(world, random, mossPos);
					}
				}
			}
			
			// Moss Gen
			if(!(biome.getTempCategory() == TempCategory.OCEAN || biome.getDefaultTemperature() < 0.2F))
			{
				if(MConfig.generateMoss)
				{
					int chance = 2;
					if(biome == Biomes.ROOFED_FOREST || biome == Biomes.MUTATED_ROOFED_FOREST || biome == Biomes.MUTATED_REDWOOD_TAIGA || biome == Biomes.REDWOOD_TAIGA)
						chance = 6;
					else if(biome instanceof BiomeForest || biome instanceof BiomeSwamp || biome instanceof BiomeStoneBeach)
						chance = 4;
					else if(biome instanceof BiomeBeach || biome instanceof BiomeDesert || biome instanceof BiomeMesa || biome instanceof BiomePlains || biome instanceof BiomeSavanna)
						chance = 1;
					
					for(int i = 0 ; i < chance ; i++)
					{
						int posX = random.nextInt(16)+8;
						int posY = 78 - random.nextInt(60);
						int posZ = random.nextInt(16)+8;
						
						BlockPos mossPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
						if(MBlocks.moss.canPlaceBlockAt(world, mossPos))
						{
							MGenMoss mossGen = new MGenMoss(32, MBlocks.moss);
							mossGen.generate(world, random, mossPos);
						}
					}
				}
			}
			
			// Icicle Gen
			if(biome.getDefaultTemperature() < 0.3)
			{
				if(MConfig.generateIcicles)
				{
					int boost = 0;
					
					if(biome.getDefaultTemperature() < 0)
						boost = 3;
					else if(biome.getDefaultTemperature() < 0.2)
						boost = 2;
					else if(biome.getDefaultTemperature() < 0.25)
						boost = 1;
					
					for(int i = 0 ; i < boost * 2 ; i++)
					{
						int posX = random.nextInt(16)+8;
						int posY = 60 - random.nextInt(55);
						int posZ = random.nextInt(16)+8;
						
						BlockPos icePos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
						if(!world.canSeeSky(icePos))
						{
							MGenIcicle iceGen = new MGenIcicle(boost);
							iceGen.generate(world, random, icePos);
						}
					}
				}
			}
			
			// IceMound Gen
			if(biome == Biomes.ICE_PLAINS)
			{
				if(MConfig.generateIceMounds)
				{
					int posX = random.nextInt(16)+8;
					int posY = 80 - random.nextInt(20);
					int posZ = random.nextInt(16)+8;
					
					BlockPos icePos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
					MGenIceDeposit iceGen = new MGenIceDeposit();
					iceGen.generate(world, random, icePos);
				}
			}
			
			//Desert Quartz Gen
			if(biome == Biomes.DESERT || biome == Biomes.DESERT_HILLS || biome == Biomes.MUTATED_DESERT || biome == Biomes.MESA || biome == Biomes.MESA_CLEAR_ROCK || biome == Biomes.MESA_ROCK || biome == Biomes.MUTATED_MESA || biome == Biomes.MUTATED_MESA_CLEAR_ROCK || biome == Biomes.MUTATED_MESA_ROCK)
			{
				int chance = 3;
				
				if(biome == Biomes.DESERT || biome == Biomes.DESERT_HILLS || biome == Biomes.MUTATED_DESERT)
					chance = 7;
				
				if(random.nextInt(10) < chance)
				{
					int posX = random.nextInt(16)+8;
					int posY = 80 - random.nextInt(20);
					int posZ = random.nextInt(16)+8;
						
					BlockPos quartzPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
					MGenDesertQuartz quartzGen = new MGenDesertQuartz();
					quartzGen.generate(world, random, quartzPos);
				}
			}
			
			//Mushroom Island Gen
			if(biome instanceof BiomeMushroomIsland)
			{
				if(MConfig.generateGlowshrooms)
				{
					for(int i = 0 ;  i < 3 ; i++)
					{
						int posX = random.nextInt(16)+8;
						int posY = 100 - random.nextInt(40);
						int posZ = random.nextInt(16)+8;
						int centerChance = random.nextInt(10);
	
						Block glowshroom = MBlocks.blue_glowshroom;
						Block bigGlowshroom = MBlocks.blue_glowshroom_block;
							
						BlockPos glowshroomPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
						MGenFairyCircle glowshroomGen = new MGenFairyCircle((BlockGlowshroom) glowshroom, 8);
						glowshroomGen.generate(world, random, glowshroomPos);
						MGenBigGlowshroom bigShroomGen = new MGenBigGlowshroom(bigGlowshroom);
						if(centerChance == 1)
							bigShroomGen.generate(world, random, glowshroomPos);
						
						for(int j = 0 ; j < 4 ; j++)
						{
							posX = random.nextInt(16)+8;
							posY = 129 - random.nextInt(128);
							posZ = random.nextInt(16)+8;
							
							BlockPos bigshroomPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
							bigShroomGen.generate(world, random, bigshroomPos);
						}
					}
				}
				if(MConfig.generateInfectedShrooms)
				{
					for(int i = 0 ; i < 5 ; i++) {
						int posX = random.nextInt(16)+8;
						int posY = 100 - random.nextInt(40);
						int posZ = random.nextInt(16)+8;
						
						BlockPos infectedPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
						if(MBlocks.infected_mushroom.canPlaceBlockAt(world, infectedPos))
						{
							WorldGenBush infectedMushroomGen = new WorldGenBush((BlockBush)MBlocks.infected_mushroom);
							infectedMushroomGen.generate(world, random, infectedPos);
						}
					}
				}
			}
			
			//Palm Tree Gen
			if(MConfig.generatePalmTrees && (biome == Biomes.DESERT || biome == Biomes.DESERT_HILLS || biome == Biomes.MUTATED_DESERT || biome == Biomes.BEACH || biome == Biomes.OCEAN || biome == Biomes.DEEP_OCEAN))
			{
				int tries = 3;
				
				if(biome == Biomes.MUTATED_DESERT)
					tries = 20;
				else if(biome == Biomes.DESERT_HILLS)
					tries = 10;
				else if(biome == Biomes.BEACH)
					tries = 30;
				else if(biome == Biomes.OCEAN || biome == Biomes.DEEP_OCEAN)
					tries = 40;
				
				for(int i = 0 ; i < tries ; i++)
				{
					int posX = random.nextInt(16) + 8;
					int posY = 62 + random.nextInt(40);
					int posZ = random.nextInt(16) + 8;
					
					BlockPos treePos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
					if(world.getBlockState(treePos).getBlock() == Blocks.SAND && world.isAirBlock(treePos.up()))
					{
						MGenPalmTree palmGen = new MGenPalmTree();
						palmGen.generate(world, random, treePos.up());
					}
				}
			}
			
			if(MConfig.generateHeartSpots && random.nextInt(3) == 1)
			{
				int posX = random.nextInt(16)+8;
				int posY = 250 - random.nextInt(240);
				int posZ = random.nextInt(16)+8;
				
				BlockPos heartPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
				Block ground = world.getBlockState(heartPos.down()).getBlock();
				if(world.getBlockState(heartPos).getBlock().isReplaceable(world, heartPos) && !world.containsAnyLiquid(new AxisAlignedBB(heartPos)) && ground == Blocks.DIRT || ground == Blocks.GRASS || ground == Blocks.MYCELIUM || ground == Blocks.SAND || ground instanceof BlockMDirt || ground instanceof BlockMGrass || ground == MBlocks.mud || ground == MBlocks.cold_sand || ground == Blocks.GRAVEL)
				{
					world.setBlockState(heartPos, MBlocks.heart_spot.getDefaultState());
				}
			}
			
			if(MConfig.generateAdamantiumVaults)
			{
				int posX = random.nextInt(16)+8;
				int posY = random.nextInt(20)+7;
				int posZ = random.nextInt(16)+8;
				
				BlockPos adamantiumPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
				if(random.nextInt(500) == 1)
				{
					MGenAdamantiumVault adamantiumGen = new MGenAdamantiumVault();
					adamantiumGen.generate(world, random, adamantiumPos);
				}
			}
		}
		//Nether Gen
		else if(world.provider.getDimension() == -1)
		{
			if(MConfig.generateInvincium)
			{
				BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
				Chunk chunk = world.getChunk(pos);
				for (int x = 0; x < 16; x++)
				{
					for (int z = 0; z < 16; z++)
					{
						BlockPos subpos = pos.add(x, 0, z);
						chunk.setBlockState(subpos, MBlocks.invincium.getDefaultState());
					}
				}
			}
			if(MConfig.generateCreepingGlowMoss)
			{
				for(int i = 0 ; i < 5 ; i++)
				{
					int posX = random.nextInt(16)+8;
					int posY = 128 - random.nextInt(100);
					int posZ = random.nextInt(16)+8;
					
					BlockPos mossPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
					if(MBlocks.creeping_glow_moss.canPlaceBlockAt(world, mossPos))
					{
						MGenMoss mossGen = new MGenMoss(60, MBlocks.creeping_glow_moss);
						mossGen.generate(world, random, mossPos);
					}
				}
			}
			if(MConfig.generateGlowMoss)
			{
				for(int i = 0 ; i < 10 ; i++)
				{
					int posX = random.nextInt(16)+8;
					int posY = 128 - random.nextInt(90);
					int posZ = random.nextInt(16)+8;
					
					BlockPos mossPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
					if(MBlocks.hanging_glow_moss.canPlaceBlockAt(world, mossPos))
					{
						MGenHangingMoss mossGen = new MGenHangingMoss(40, MBlocks.hanging_glow_moss);
						mossGen.generate(world, random, mossPos);
					}
				}
			}
			if(MConfig.generateGlowshrooms)
			{
				for(int i = 0 ;  i < 3 ; i++)
				{
					int posX = random.nextInt(16)+8;
					int posY = 129 - random.nextInt(128);
					int posZ = random.nextInt(16)+8;
					boolean glowType = random.nextBoolean();
					int centerChance = random.nextInt(10);
	
					Block glowshroom = MBlocks.green_glowshroom;
					if(glowType)
						glowshroom = MBlocks.purple_glowshroom;
					Block bigGlowshroom = MBlocks.green_glowshroom_block;
					if(glowType)
						bigGlowshroom = MBlocks.purple_glowshroom_block;
						
					BlockPos glowshroomPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
					MGenFairyCircle glowshroomGen = new MGenFairyCircle((BlockGlowshroom) glowshroom, 8);
					glowshroomGen.generate(world, random, glowshroomPos);
					MGenBigGlowshroom bigShroomGen = new MGenBigGlowshroom(bigGlowshroom);
					if(centerChance == 1)
						bigShroomGen.generate(world, random, glowshroomPos);
					
					for(int j = 0 ; j < 4 ; j++)
					{
						posX = random.nextInt(16)+8;
						posY = 129 - random.nextInt(128);
						posZ = random.nextInt(16)+8;
						
						glowType = random.nextBoolean();
						bigGlowshroom = MBlocks.green_glowshroom_block;
						if(glowType)
							bigGlowshroom = MBlocks.purple_glowshroom_block;
						
						BlockPos bigshroomPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
						bigShroomGen.generate(world, random, bigshroomPos);
					}
				}
			}
			for(int i = 0 ; i < 5 ; i++)
			{
				int posX = random.nextInt(16)+8;
				int posY = 128 - random.nextInt(90);
				int posZ = random.nextInt(16)+8;
				
				BlockPos treePos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
				int dir = random.nextInt(4);
				EnumFacing facing = EnumFacing.NORTH;
				
				if(dir == 0)
					facing = EnumFacing.SOUTH;
				else if(dir == 1)
					facing = EnumFacing.EAST;
				else if(dir == 2)
					facing = EnumFacing.WEST;
				
				MGenHellTree treeGen = new MGenHellTree(facing);
				
				if(treeGen.canGenerateTree(world, treePos) >= 5)
				{
					treeGen.generate(world, random, treePos);
				}
			}
			
			if(MConfig.generateHeartSpots)
			{
				for(int i = 0 ; i < 8 ; i++)
				{
					int posX = random.nextInt(16)+8;
					int posY = 120 - random.nextInt(100);
					int posZ = random.nextInt(16)+8;
					
					BlockPos heartPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
					Block ground = world.getBlockState(heartPos.down()).getBlock();
					if(world.getBlockState(heartPos).getBlock().isReplaceable(world, heartPos) && ground == Blocks.SOUL_SAND || ground == Blocks.GRAVEL || ground == MBlocks.log)
					{
						world.setBlockState(heartPos, MBlocks.heart_spot.getDefaultState());
					}
				}
			}
		}
		else if(world.provider.getDimension() == 1)
		{
			BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
			Chunk chunk = world.getChunk(pos);
	
			if((chunkX * chunkX) + (chunkZ * chunkZ) > 3844)
			{
				if(MConfig.generatePortalDust)
				{
					for (int x = 0; x < 16; x++)
					{
						for (int z = 0; z < 16; z++)
						{
							for (int y = 90; y >= 58; y--)
							{
								BlockPos subpos2 = new BlockPos((chunkX * 16 + x), y, (chunkZ * 16 + z));
								IBlockState state = world.getBlockState(subpos2);
								Block block = state.getBlock();
								
								if (state.isFullBlock() == true && block == Blocks.END_STONE)
								{
									if(!world.getBlockState(subpos2.up()).isFullBlock() && world.getBlockState(subpos2.up()).getBlock() != Blocks.CHORUS_PLANT && world.getBlockState(subpos2.up()).getBlock() != Blocks.CHORUS_FLOWER)
									{
										world.setBlockState(subpos2, MBlocks.fargrowth.getDefaultState());
										
										int depth = random.nextInt(5) + 2;
										
										for(int i = 1 ; i < depth ; i++)
										{
											BlockPos dirtPos = subpos2.offset(EnumFacing.DOWN, i);
											
											if(world.getBlockState(dirtPos).getBlock() == Blocks.END_STONE)
												world.setBlockState(dirtPos, MBlocks.portal_dust.getDefaultState());
										}
										
										if(MConfig.generateClutchthorn && world.getBlockState(subpos2.up()).getBlock().isReplaceable(world, subpos2.up()) && random.nextInt(3) == 1)
											world.setBlockState(subpos2.up(), MBlocks.clutchthorn.getDefaultState());
									}
								}
							}
						}
					}
					for (int x = 0; x < 16; x++)
					{
						for (int z = 0; z < 16; z++)
						{
							for (int y = 90; y >= 58; y--)
							{
								BlockPos subpos2 = new BlockPos((chunkX * 16 + x), y, (chunkZ * 16 + z));
								IBlockState state = world.getBlockState(subpos2);
								Block block = state.getBlock();
								
								if (block == Blocks.CHORUS_PLANT || block == Blocks.CHORUS_FLOWER)
								{
									Block north = world.getBlockState(subpos2.down().north()).getBlock();
									Block south = world.getBlockState(subpos2.down().south()).getBlock();
									Block east = world.getBlockState(subpos2.down().east()).getBlock();
									Block west = world.getBlockState(subpos2.down().west()).getBlock();
									
									MGenChordsolTendril gen = new MGenChordsolTendril(EnumFacing.NORTH);
									
									if(north == MBlocks.fargrowth)
										gen.generate(world, random, subpos2.down().north());
									if(south == MBlocks.fargrowth)
									{
										gen.setFacing(EnumFacing.SOUTH).generate(world, random, subpos2.down().south());
									}
									if(east == MBlocks.fargrowth)
									{
										gen.setFacing(EnumFacing.EAST).generate(world, random, subpos2.down().east());
									}
									if(west == MBlocks.fargrowth)
									{
										gen.setFacing(EnumFacing.WEST).generate(world, random, subpos2.down().west());
									}
								}
							}
						}
					}
					
					if(MConfig.generateMiteHive)
					{
						for(int i = 0 ;  i < 3 ; i++)
						{
							int posX = random.nextInt(16)+8;
							int posY = 90 - random.nextInt(50);
							int posZ = random.nextInt(16)+8;
		
							BlockPos hivePos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
							MGenMiteHive hiveGen = new MGenMiteHive();
							if(world.getBlockState(hivePos).getBlock().isReplaceable(world, hivePos) && world.getBlockState(hivePos.offset(EnumFacing.DOWN)).getBlock() == MBlocks.fargrowth)
							hiveGen.generate(world, random, hivePos);
						}
					}
				}
				if(MConfig.generateCarpetGlowMoss)
				{
					for(int i = 0 ; i < 5 ; i++)
					{
						int posX = random.nextInt(16)+8;
						int posY = 90 - random.nextInt(80);
						int posZ = random.nextInt(16)+8;
						
						BlockPos mossPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
						if(MBlocks.carpet_glow_moss.canPlaceBlockAt(world, mossPos))
						{
							MGenMoss mossGen = new MGenMoss(20, MBlocks.carpet_glow_moss);
							mossGen.generate(world, random, mossPos);
						}
					}
				}
				if(MConfig.generateGlowMoss)
				{
					for(int i = 0 ; i < 15 ; i++)
					{
						int posX = random.nextInt(16)+8;
						int posY = 90 - random.nextInt(80);
						int posZ = random.nextInt(16)+8;
						
						BlockPos mossPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
						if(MBlocks.hanging_glow_moss.canPlaceBlockAt(world, mossPos))
						{
							MGenHangingMoss mossGen = new MGenHangingMoss(36, MBlocks.hanging_glow_moss);
							mossGen.generate(world, random, mossPos);
						}
					}
				}
				if(MConfig.generateTerracreep)
				{
					int posX = random.nextInt(16) + 8;
					int posY = random.nextInt(250);
					int posZ = random.nextInt(16) + 8;
					
					BlockPos creepPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
					
					if(world.isAirBlock(creepPos) && ((world.getBlockState(creepPos.up()).isFullBlock() && world.getBlockState(creepPos.up()).getMaterial() == Material.ROCK) || (world.getBlockState(creepPos.down()).isFullBlock() && world.getBlockState(creepPos.down()).getMaterial() == Material.ROCK) || (world.getBlockState(creepPos.north()).isFullBlock() && world.getBlockState(creepPos.north()).getMaterial() == Material.ROCK) || (world.getBlockState(creepPos.east()).isFullBlock() && world.getBlockState(creepPos.east()).getMaterial() == Material.ROCK) || (world.getBlockState(creepPos.south()).isFullBlock() && world.getBlockState(creepPos.south()).getMaterial() == Material.ROCK) || (world.getBlockState(creepPos.west()).isFullBlock() && world.getBlockState(creepPos.west()).getMaterial() == Material.ROCK)))
					{
						world.setBlockState(creepPos, MBlocks.terracreep.getDefaultState());
					}
				}
				if(MConfig.generateHeartSpots)
				{
					int posX = random.nextInt(16)+8;
					int posY = 90 - random.nextInt(50);
					int posZ = random.nextInt(16)+8;
						
					BlockPos heartPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
					Block ground = world.getBlockState(heartPos.down()).getBlock();
					if(world.getBlockState(heartPos).getBlock().isReplaceable(world, heartPos) && ground == MBlocks.portal_dust || ground == MBlocks.fargrowth)
					{
						world.setBlockState(heartPos, MBlocks.heart_spot.getDefaultState());
					}
				}
			}
		}
	}
}
