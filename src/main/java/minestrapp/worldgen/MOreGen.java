package minestrapp.worldgen;

import java.util.Random;

import com.google.common.base.Predicate;

import minestrapp.MBlocks;
import minestrapp.block.BlockIrradiumOre;
import minestrapp.block.EnumStoneType;
import minestrapp.block.crops.BlockBerryBush;
import minestrapp.block.util.BlockStoneBase;
import minestrapp.block.util.BlockStoneBaseMOnly;
import minestrapp.config.MConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.TempCategory;
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
import net.minecraft.world.biome.BiomeStoneBeach;
import net.minecraft.world.biome.BiomeSwamp;
import net.minecraft.world.biome.BiomeTaiga;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class MOreGen implements IWorldGenerator
{
	private WorldGenerator salt;
	private WorldGenerator copper;
	private WorldGenerator tin;
	private WorldGenerator meurodite;
	private WorldGenerator irradium;
	private WorldGenerator torite;
	private WorldGenerator titanium;
	private WorldGenerator blazium;
	private WorldGenerator soul;
	private WorldGenerator dimensium;
	
	public MOreGen()
	{
		salt = new MGenMinable(MBlocks.ore_salt.getDefaultState().withProperty(BlockStoneBase.VARIANT,  EnumStoneType.STONE), 20);
		copper = new MGenMinable(MBlocks.ore_copper.getDefaultState().withProperty(BlockStoneBase.VARIANT, EnumStoneType.STONE), 10);
		tin = new MGenMinable(MBlocks.ore_tin.getDefaultState().withProperty(BlockStoneBase.VARIANT, EnumStoneType.STONE), 10);
		meurodite = new MGenMinable(MBlocks.ore_meurodite.getDefaultState().withProperty(BlockStoneBase.VARIANT, EnumStoneType.STONE), 5);
		irradium = new MGenMinable(MBlocks.ore_irradium.getDefaultState().withProperty(BlockIrradiumOre.VARIANT, EnumStoneType.STONE), 3);
		torite = new MGenMinable(MBlocks.ore_torite.getDefaultState().withProperty(BlockStoneBase.VARIANT, EnumStoneType.STONE), 4);
		titanium = new MGenMinable(MBlocks.ore_titanium.getDefaultState().withProperty(BlockStoneBase.VARIANT, EnumStoneType.STONE), 3);
		blazium = new MGenMinable(MBlocks.ore_blazium.getDefaultState(), 5, new MMinablePredicate(Blocks.NETHERRACK.getDefaultState()));
		soul = new MGenMinable(MBlocks.ore_soul.getDefaultState(), 3, new MMinablePredicate(Blocks.SOUL_SAND.getDefaultState()));
		dimensium = new MGenMinable(MBlocks.ore_dimensium.getDefaultState(), 4, new MMinablePredicate(Blocks.END_STONE.getDefaultState()));
	}
	
	private void runGenerator(WorldGenerator generator, World world, Random rand, int chunkX, int chunkZ, int spawnChance, int minHeight, int maxHeight, boolean biomeSpecific)
	{
		if(minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
		{
			throw new IllegalArgumentException("The Ore Height Params are Fucked, God I'm Bad at Math.");
		}
		int heightDiff = maxHeight - minHeight + 1;
		if(biomeSpecific == true && generator instanceof MGenMinable)
		{
			Chunk chunk = world.getChunk(chunkX, chunkZ);
			BiomeProvider chunkManager = world.getBiomeProvider();
			IBlockState state = ((MGenMinable) generator).getOreState();
			int count = ((MGenMinable)generator).getBlockCount();
			Predicate<IBlockState> predicate = ((MGenMinable)generator).getPredicate();
			for(int i = 0 ; i < spawnChance ; i++)
			{
				int xRand = rand.nextInt(16);
				int zRand = rand.nextInt(16);
				BlockPos pos = new BlockPos(xRand, 0, zRand);
				int x = chunkX * 16 + xRand;
				int y = minHeight + rand.nextInt(heightDiff);
				int z = chunkZ * 16 + zRand;
				Biome biome = chunkManager.getBiome(pos, Biomes.PLAINS);
				int k = chunk.getBiomeArray()[zRand << 4 | xRand] & 255;
				
				if (k == 255)
		        {
		            k = Biome.getIdForBiome(biome);
		            chunk.getBiomeArray()[zRand << 4 | xRand] = (byte)(k & 255);
		        }

		        biome = Biome.getBiome(k);
		        
		        if(MConfig.generateDeepstone && y < MStoneGen.getDeepstoneDepthForBiome(biome, rand))
		        {
		        	if(state.getBlock() instanceof BlockIrradiumOre)
		        		generator = new MGenMinable(state.withProperty(BlockIrradiumOre.VARIANT, EnumStoneType.byMetadata(MStoneGen.getDeepStoneForBiome(biome).getMetadata())), count, predicate);
		        	else
		        		generator = new MGenMinable(state.withProperty(BlockStoneBase.VARIANT, EnumStoneType.byMetadata(MStoneGen.getDeepStoneForBiome(biome).getMetadata())), count, predicate);
		        }
		        else
		        {
		        	if(MStoneGen.getStoneForBiome(biome) != null)
		        	{
		        		if(state.getBlock() instanceof BlockIrradiumOre)
		        			generator = new MGenMinable(state.withProperty(BlockIrradiumOre.VARIANT, EnumStoneType.byMetadata(MStoneGen.getStoneForBiome(biome).getMetadata())), count, predicate);
			        	else
			        		generator = new MGenMinable(state.withProperty(BlockStoneBase.VARIANT, EnumStoneType.byMetadata(MStoneGen.getStoneForBiome(biome).getMetadata())), count, predicate);
		        	}
		        	else
		        	{
		        		if(state.getBlock() instanceof BlockIrradiumOre)
		        			generator = new MGenMinable(state.withProperty(BlockIrradiumOre.VARIANT, EnumStoneType.STONE), count, predicate);
			        	else
			        		generator = new MGenMinable(state.withProperty(BlockStoneBase.VARIANT, EnumStoneType.STONE), count, predicate);
		        	}
		        }
		        
				generator.generate(world, rand, new BlockPos(x, y, z));
			}
		}
		else
		{
			for(int i = 0 ; i < spawnChance ; i++)
			{
				int x = chunkX * 16 + rand.nextInt(16);
				int y = minHeight + rand.nextInt(heightDiff);
				int z = chunkZ * 16 + rand.nextInt(16);
				generator.generate(world, rand, new BlockPos(x, y, z));
			}
		}
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		if(world.provider.getDimension() == 0)
		{
			if(MConfig.generateCopper)
				this.runGenerator(copper, world, random, chunkX, chunkZ, 10, 30, 110, true);
			if(MConfig.generateTin)
				this.runGenerator(tin, world, random, chunkX, chunkZ, 10, 30, 110, true);
			if(MConfig.generateMeurodite)
				this.runGenerator(meurodite, world, random, chunkX, chunkZ, 5, 0, 36, true);
			if(MConfig.generateIrradium)
				this.runGenerator(irradium, world, random, chunkX, chunkZ, 5, 0, 32, true);
			if(MConfig.generateTitanium)
				this.runGenerator(titanium, world, random, chunkX, chunkZ, 3, 0, 10, true);
			
			Biome biome = world.getBiome(new BlockPos(chunkX * 16, 0, chunkZ * 16));
			if(biome.getTempCategory() == TempCategory.OCEAN || biome instanceof BiomeMushroomIsland)
			{
				if(MConfig.generateSalt)
					this.runGenerator(salt, world, random, chunkX, chunkZ, 3, 12, 60, true);
			}
			else if(biome instanceof BiomeJungle || biome instanceof BiomeSwamp || biome == Biome.getBiome(29))
			{
				if(MConfig.generateTorite)
					this.runGenerator(torite, world, random, chunkX, chunkZ, 4, 0, 36, true);
			}
			
			if(MConfig.generateSunstone)
			{
				int posX = random.nextInt(16)+8;
				int posY = 128 - random.nextInt(120);
				int posZ = random.nextInt(16)+8;
				
				BlockPos sunstonePos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
				MGenSunstone sunstoneGen = new MGenSunstone(5);
				sunstoneGen.generate(world, random, sunstonePos);
			}
			
			MStoneGen.generate(world, chunkX, chunkZ, random);
		}
		
		else if(world.provider.getDimension() == -1)
		{
			if(MConfig.generateBlazium)
				this.runGenerator(blazium, world, random, chunkX, chunkZ, 12, 0, 128, false);
			if(MConfig.generateShimmeringOre)
			{
				for(int i = 0 ; i < 30 ; i++)
				{
					int posX = random.nextInt(16)+8;
					int posY = 60 - random.nextInt(58);
					int posZ = random.nextInt(16)+8;
					
					BlockPos shimmeringPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
					MGenShimmeringOre shimmeringGen = new MGenShimmeringOre();
					shimmeringGen.generate(world, random, shimmeringPos);
				}
			}
			if(MConfig.generateSoulOre)
				this.runGenerator(soul, world, random, chunkX, chunkZ, 40, 20, 100, false);
		}
		
		else if(world.provider.getDimension() == 1)
		{
			if(MConfig.generateDimensium)
				this.runGenerator(dimensium, world, random, chunkX, chunkZ, 8, 20, 128, false);
			
			if((chunkX * chunkX) + (chunkZ * chunkZ) > 3844)
			{
				int posX = random.nextInt(16);
				int posY = 100 - random.nextInt(70);
				int posZ = random.nextInt(16);
				
				if(MConfig.generateBerryBushes)
				{
					BlockPos berryPos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
					MGenVoidberry bushGen = new MGenVoidberry(12);
					bushGen.generate(world, random, berryPos);
				}
				if(MConfig.generateSunstone)
				{
					posX = random.nextInt(16);
					posY = 100 - random.nextInt(70);
					posZ = random.nextInt(16);
					
					BlockPos sunstonePos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
					MGenSunstone sunstoneGen = new MGenSunstone(8);
					sunstoneGen.generate(world, random, sunstonePos);
				}
				if(MConfig.generateLavaSponge)
				{
					posX = random.nextInt(16);
					posY = random.nextInt(26) + 10;
					posZ = random.nextInt(16);
					
					BlockPos spongePos = new BlockPos(chunkX * 16 + posX, posY, chunkZ * 16 + posZ);
					MGenLavaSponge spongeGen = new MGenLavaSponge();
					spongeGen.generate(world, random, spongePos);
				}
			}
		}
	}
}