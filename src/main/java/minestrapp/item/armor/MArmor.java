package minestrapp.item.armor;

import minestrapp.MItems;
import minestrapp.MTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

public class MArmor extends ItemArmor
{	
	public MArmor(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String name){
		
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setRegistryName(name);
		this.setCreativeTab(MTabs.combat);
		
	}
}
