package json.jayson.playerblood.object.itemgroup;

import json.jayson.playerblood.registry.zItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class igPlayerBlood extends ItemGroup {
    
	public igPlayerBlood() { 
		super("playerbloodtab");
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(zItem.BLOOD_ORB.get());
	}

	@Override
	public boolean hasSearchBar() {
		return false;
	}

}
