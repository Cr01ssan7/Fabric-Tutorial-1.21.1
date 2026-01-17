package net.xander.tutorialmod.item.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import static net.minecraft.particle.ParticleUtil.spawnParticles;

public class SmiteStick extends Item {

    private static final int COOLDOWN_TICKS = 20 * 5; // 5 seconds
    private static final double RAY_DISTANCE = 200.00;

    public SmiteStick(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }



    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world.isClient()) {
            return TypedActionResult.success(stack);

        }

        ServerWorld serverWorld = (ServerWorld) world;

        boolean willBreak = stack.getDamage() + 2 >= stack.getMaxDamage();

        Vec3d start = user.getCameraPosVec(1.0F);
        Vec3d direction = user.getRotationVec(1.0F);
        Vec3d end = start.add(direction.multiply(RAY_DISTANCE));

        BlockHitResult hitResult = serverWorld.raycast(new RaycastContext(
                start,
                end,
                RaycastContext.ShapeType.OUTLINE,
                RaycastContext.FluidHandling.NONE,
                user
        ));

        Vec3d pos = hitResult.getPos();
        double x = pos.x;
        double y = pos.y;
        double z = pos.z;

        // --- spawn lightning at target ---
        LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(serverWorld);
        if (lightning != null) {
            lightning.refreshPositionAfterTeleport(
                    hitResult.getPos().x,
                    hitResult.getPos().y,
                    hitResult.getPos().z
            );
            serverWorld.spawnEntity(lightning);
            serverWorld.spawnParticles(
                    ParticleTypes.ELECTRIC_SPARK,
                    pos.x +0,
                    pos.y + 1,
                    pos.z +0,
                    40,
                    0.4, 1.0, 0.4,
                    0.0
            );
        }

        stack.damage(2, user, EquipmentSlot.MAINHAND);

        // --- if the stick breaks, smite the player ---
        if (willBreak) {
            LightningEntity selfStrike = EntityType.LIGHTNING_BOLT.create(serverWorld);
            if (selfStrike != null) {
                selfStrike.setCosmetic(true); // Weak, no fire
                selfStrike.refreshPositionAfterTeleport(
                        user.getX(),
                        user.getY(),
                        user.getZ()
                );
                serverWorld.spawnEntity(selfStrike);
            }
        }

        user.getItemCooldownManager().set(this, COOLDOWN_TICKS);

        return TypedActionResult.success(stack);
    }
}

