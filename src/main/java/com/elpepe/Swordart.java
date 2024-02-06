package com.elpepe;

import com.elpepe.command.ModCommands;
import com.elpepe.item.ModItemGroups;
import com.elpepe.item.ModItems;
import com.elpepe.networking.ModMessages;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Swordart implements ModInitializer {
	public static final String MOD_ID = "swordart";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModItemGroups.registerModItemGroups();

		ModMessages.registerC2SPackets();
		ModCommands.register();
	}
}