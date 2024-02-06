package com.elpepe.item;

import com.elpepe.Swordart;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item FIERY_REVENGE = register("fiery_revenge", new BasicSword(new FabricItemSettings().maxCount(1).maxDamage(1280)));
    public static final Item TITAN_SLAYER = register("titan_slayer", new BasicSword(new FabricItemSettings().maxCount(1).maxDamage(4096)));

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Swordart.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Swordart.LOGGER.info("Registering items for " + Swordart.MOD_ID);
    }
}
