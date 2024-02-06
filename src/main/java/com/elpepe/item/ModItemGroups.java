package com.elpepe.item;

import com.elpepe.Swordart;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup MAIN_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(Swordart.MOD_ID, "main"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.swordart_main"))
                    .icon(() -> new ItemStack(ModItems.TITAN_SLAYER))
                    .entries(((displayContext, entries) -> {
                        entries.add(ModItems.FIERY_REVENGE);
                        entries.add(ModItems.TITAN_SLAYER);
                    })).build());

    public static void registerModItemGroups() {
        Swordart.LOGGER.info("Registering item groups for " + Swordart.MOD_ID);
    }
}
