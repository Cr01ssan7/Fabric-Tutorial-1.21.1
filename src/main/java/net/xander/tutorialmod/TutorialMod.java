package net.xander.tutorialmod;

import net.fabricmc.api.ModInitializer;

import net.xander.tutorialmod.block.ModBlocks;
import net.xander.tutorialmod.item.ModItemGroups;
import net.xander.tutorialmod.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Start Init Mod Components

public class TutorialMod implements ModInitializer {
	public static final String MOD_ID = "tutorialmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
	}
}

// End Init Mod Components