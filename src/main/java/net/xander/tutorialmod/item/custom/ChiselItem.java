package net.xander.tutorialmod.item.custom;

import net.fabricmc.fabric.api.client.particle.v1.ParticleRenderEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

import java.util.Map;

public class ChiselItem extends Item {
    private static final Map<Block, Block> CHISEL_MAP =
            Map.ofEntries(
                    // Stone Stuff
                    Map.entry(Blocks.STONE, Blocks.STONE_BRICKS),
                    Map.entry(Blocks.STONE_BRICKS, Blocks.SMOOTH_STONE),
                    Map.entry(Blocks.SMOOTH_STONE, Blocks.STONE_BRICKS),

                    // Stone Slab Stuff
                    Map.entry(Blocks.STONE_SLAB, Blocks.STONE_BRICK_SLAB),
                    Map.entry(Blocks.STONE_BRICK_SLAB, Blocks.SMOOTH_STONE_SLAB),
                    Map.entry(Blocks.SMOOTH_STONE_SLAB, Blocks.STONE_BRICK_SLAB),

                    // Stone Stair Stuff
                    Map.entry(Blocks.STONE_STAIRS, Blocks.STONE_BRICK_STAIRS),

                    // Deepslate Stuff
                    Map.entry(Blocks.DEEPSLATE, Blocks.DEEPSLATE_BRICKS),

                    // Sandstone Stuff
                    Map.entry(Blocks.SANDSTONE, Blocks.SMOOTH_SANDSTONE),
                    Map.entry(Blocks.SMOOTH_SANDSTONE, Blocks.CUT_SANDSTONE),
                    Map.entry(Blocks.CUT_SANDSTONE, Blocks.CHISELED_SANDSTONE),
                    Map.entry(Blocks.CHISELED_SANDSTONE, Blocks.SMOOTH_SANDSTONE),

                    Map.entry(Blocks.RED_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE),
                    Map.entry(Blocks.SMOOTH_RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE),
                    Map.entry(Blocks.CHISELED_RED_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE),

                    // Infested Stone Stuff
                    Map.entry(Blocks.INFESTED_STONE, Blocks.INFESTED_STONE_BRICKS),

                    // End Stone Stuff
                    Map.entry(Blocks.END_STONE, Blocks.END_STONE_BRICKS)
            );

    public ChiselItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        Block clickedBlock = world.getBlockState(context.getBlockPos()).getBlock();

        if(CHISEL_MAP.containsKey(clickedBlock)) {
            if(!world.isClient()) {
                world.setBlockState(context.getBlockPos(), CHISEL_MAP.get(clickedBlock).getDefaultState());

                context.getStack().damage(1, ((ServerWorld) world), ((ServerPlayerEntity) context.getPlayer()),
                        item -> context.getPlayer().sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));

                world.playSound(null, context.getBlockPos(), SoundEvents.BLOCK_SMITHING_TABLE_USE, SoundCategory.BLOCKS);
            }
        }

        return ActionResult.SUCCESS;
    }
}
