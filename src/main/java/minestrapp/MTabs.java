package minestrapp;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MTabs
{
	public static CreativeTabs plant = new CreativeTabs("Plants")
	{
		@Override
		public String getTabLabel()
		{
			return "Plants";
		}
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIcon()
		{
			return new ItemStack(MBlocks.blueberry_bush);
		}

		@Override
		public ItemStack createIcon() {
			ItemStack icon = new ItemStack(MBlocks.blueberry_bush);
			icon.setCount(1);
			return icon;
		}
	};
	
	public static CreativeTabs environment = new CreativeTabs("EnvironmentalBlocks")
	{
		@Override
		public String getTabLabel()
		{
			return "EnvironmentalBlocks";
		}
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIcon()
		{
			return new ItemStack(Item.getItemFromBlock(MBlocks.clay_grass));
		}

		@Override
		public ItemStack createIcon() {
			ItemStack icon = new ItemStack(MBlocks.clay_grass);
			icon.setCount(1);
			return icon;
		}
	};
	
	public static CreativeTabs wood = new CreativeTabs("Wood")
	{
		@Override
		public String getTabLabel()
		{
			return "Wood";
		}
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIcon()
		{
			return new ItemStack(Item.getItemFromBlock(MBlocks.planks));
		}

		@Override
		public ItemStack createIcon() {
			ItemStack icon = new ItemStack(MBlocks.planks);
			icon.setCount(1);
			return icon;
		}
	};
	
	public static CreativeTabs stone = new CreativeTabs("Stone")
	{
		@Override
		public String getTabLabel()
		{
			return "Stone";
		}
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIcon()
		{
			return new ItemStack(Item.getItemFromBlock(MBlocks.stone), 1, 2);
		}

		@Override
		public ItemStack createIcon() {
			ItemStack icon = new ItemStack(MBlocks.stone);
			icon.setCount(1);
			return icon;
		}
	};
	
	public static CreativeTabs ore = new CreativeTabs("Ores")
	{
		@Override
		public String getTabLabel()
		{
			return "Ores";
		}
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIcon()
		{
			return new ItemStack(Item.getItemFromBlock(MBlocks.ore_copper), 1, 9);
		}

		@Override
		public ItemStack createIcon() {
			ItemStack icon = new ItemStack(MBlocks.ore_copper);
			icon.setCount(1);
			return icon;
		}
	};
	
	public static CreativeTabs resource = new CreativeTabs("ResourceBlocks")
	{
		@Override
		public String getTabLabel()
		{
			return "ResourceBlocks";
		}
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIcon()
		{
			return new ItemStack(Item.getItemFromBlock(MBlocks.block_copper), 1, 0);
		}

		@Override
		public ItemStack createIcon() {
			ItemStack icon = new ItemStack(MBlocks.block_copper);
			icon.setCount(1);
			return icon;
		}
	};
	
	public static CreativeTabs dye = new CreativeTabs("Dyeables")
	{
		@Override
		public String getTabLabel()
		{
			return "Dyeables";
		}
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIcon()
		{
			return new ItemStack(MItems.dye, 1, 3);
		}

		@Override
		public ItemStack createIcon() {
			ItemStack icon = new ItemStack(MItems.dye);
			icon.setCount(1);
			return icon;
		}
	};
	
	public static CreativeTabs decor = new CreativeTabs("DecorBlocks")
	{
		@Override
		public String getTabLabel()
		{
			return "DecorBlocks";
		}
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIcon()
		{
			return new ItemStack(Item.getItemFromBlock(MBlocks.wooden_window), 1, 0);
		}

		@Override
		public ItemStack createIcon() {
			ItemStack icon = new ItemStack(MBlocks.wooden_window);
			icon.setCount(1);
			return icon;
		}
	};
	
	public static CreativeTabs utility = new CreativeTabs("UtilityBlocks")
	{
		@Override
		public String getTabLabel()
		{
			return "UtilityBlocks";
		}
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIcon()
		{
			return new ItemStack(Item.getItemFromBlock(MBlocks.alloy));
		}

		@Override
		public ItemStack createIcon() {
			ItemStack icon = new ItemStack(MBlocks.alloy);
			icon.setCount(1);
			return icon;
		}
	};
	
	public static CreativeTabs minerals = new CreativeTabs("Minerals")
	{
		@Override
		public String getTabLabel()
		{
			return "Minerals";
		}
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIcon()
		{
			return new ItemStack(MItems.ingots, 1, 0);
		}

		@Override
		public ItemStack createIcon() {
			ItemStack icon = new ItemStack(MItems.ingots);
			icon.setCount(1);
			return icon;
		}
	};
	
	public static CreativeTabs food = new CreativeTabs("Food")
	{
		@Override
		public String getTabLabel()
		{
			return "Food";
		}
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIcon()
		{
			return new ItemStack(MItems.spaghetti);
		}

		@Override
		public ItemStack createIcon() {
			ItemStack icon = new ItemStack(MItems.spaghetti);
			icon.setCount(1);
			return icon;
		}
	};
	
	public static CreativeTabs ingredients = new CreativeTabs("Ingredients")
	{
		@Override
		public String getTabLabel()
		{
			return "Ingredients";
		}
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIcon()
		{
			return new ItemStack(MItems.tech_components, 1, 4);
		}

		@Override
		public ItemStack createIcon() {
			ItemStack icon = new ItemStack(MItems.tech_components);
			icon.setCount(1);
			return icon;
		}
	};
	
	public static CreativeTabs tools = new CreativeTabs("Tools")
	{
		@Override
		public String getTabLabel()
		{
			return "Tools";
		}
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIcon()
		{
			return new ItemStack(MItems.titanium_pickaxe);
		}

		@Override
		public ItemStack createIcon() {
			ItemStack icon = new ItemStack(MItems.titanium_pickaxe);
			icon.setCount(1);
			return icon;
		}
	};
	
	public static CreativeTabs combat = new CreativeTabs("Combat")
	{
		@Override
		public String getTabLabel()
		{
			return "Combat";
		}
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIcon()
		{
			return new ItemStack(MItems.titanium_sword);
		}

		@Override
		public ItemStack createIcon() {
			ItemStack icon = new ItemStack(MItems.titanium_sword);
			icon.setCount(1);
			return icon;
		}
	};	
}
