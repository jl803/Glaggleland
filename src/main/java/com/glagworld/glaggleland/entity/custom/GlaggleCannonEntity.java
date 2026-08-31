package com.glagworld.glaggleland.entity.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;

import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;


import net.minecraft.network.chat.Component;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;



public class GlaggleCannonEntity extends Mob {

    private int glaggleCount = 0;
    private int glaggleThreshold = 10;

    private int timer = -1;

    private Player sittingPlayer = null;

    private boolean waitingToLaunch = false;
    private boolean waitingToCheck = false;
    private ArmorStand launchCarrier = null;
    private int carrierLaunchTicks = 0;

    public GlaggleCannonEntity(EntityType<?> entityType, Level level) {
        super((EntityType<? extends Mob>) entityType, level);
        setPersistenceRequired();
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        if (sittingPlayer == null) super.removeWhenFarAway(distance);
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            return;
        }

        if (timer > 0) {
            timer--;
        }

        if (waitingToCheck && launchCarrier != null && carrierLaunchTicks > 0) {
            applyLaunchVelocity(launchCarrier);
            carrierLaunchTicks--;
        }

        if (timer == 0 && waitingToLaunch) {
            timer = -1;
            waitingToLaunch = false;
            launchPlayer();
        } else if (timer == 0 && waitingToCheck) {
            sittingPlayer.displayClientMessage(Component.literal("HELLO???"), false);
            timer = -1;
            waitingToCheck = false;
            sendPlayer();
        }
    }

    private void sendPlayer() {
        Vec3 playerPos = sittingPlayer.getPosition(1);


        sittingPlayer.displayClientMessage(Component.literal("Your position is:" + playerPos), false);
        if (playerPos.y < 100) {
            clearLaunchCarrier();
            sittingPlayer = null;
            return;
        }

        clearLaunchCarrier();

        DimensionTransition transition = new DimensionTransition(
                getServer().getLevel(Level.NETHER),
                self().position(),
                Vec3.ZERO,
                sittingPlayer.getYRot(),
                sittingPlayer.getXRot(),
                DimensionTransition.PLAY_PORTAL_SOUND
        );

        sittingPlayer.changeDimension(transition);
        sittingPlayer = null;
    }

    private void launchPlayer() {
        if (isVehicle()) {
            Entity passenger = getFirstPassenger();

            passenger.stopRiding();
            startCarrierLaunch(passenger);

            sittingPlayer.displayClientMessage(Component.literal("Ohhhh I'm launching it"), true);

            waitingToCheck = true;
            timer = 60;

        }
    }

    private void startCarrierLaunch(Entity passenger) {
        ArmorStand carrier = new ArmorStand(EntityType.ARMOR_STAND, this.level());

        carrier.moveTo(passenger.getX(), passenger.getY(), passenger.getZ(), passenger.getYRot(), passenger.getXRot());
        carrier.setInvisible(true);
        carrier.setNoGravity(true);
        carrier.setInvulnerable(true);
        carrier.setSilent(true);

        this.level().addFreshEntity(carrier);
        boolean startedRiding = passenger.startRiding(carrier, true);

        if (!startedRiding) {
            carrier.discard();
            return;
        }

        launchCarrier = carrier;
        carrierLaunchTicks = 120;
        applyLaunchVelocity(carrier);
    }

    private void applyLaunchVelocity(Entity entity) {
        Vec3 forward = Vec3.directionFromRotation(0.0F, this.getYRot()).normalize().scale(4.0);
        Vec3 launchVelocity = new Vec3(forward.x, 10.0, forward.z);

        entity.setDeltaMovement(launchVelocity);
        entity.move(MoverType.SELF, launchVelocity);
        entity.fallDistance = 0.0F;
        entity.hurtMarked = true;
        entity.hasImpulse = true;
    }

    private void clearLaunchCarrier() {
        if (launchCarrier != null) {
            launchCarrier.discard();
            launchCarrier = null;
        }

        carrierLaunchTicks = 0;
    }

    @Override
    public void onRemovedFromLevel() {
        System.out.print("Removal reason: ");
        System.out.println(getRemovalReason());
        if(!this.level().isClientSide() && getRemovalReason() == RemovalReason.UNLOADED_TO_CHUNK && sittingPlayer != null) {
            sendPlayer();
            sittingPlayer = null;
            timer = -1;
        }
        super.onRemovedFromLevel();
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {

        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        if (!this.level().isClientSide) {

            if (glaggleCount >= glaggleThreshold) {
                player.displayClientMessage(Component.literal("Welcome to Glaggleland!"), false);


                player.startRiding(this);
                sittingPlayer = player;

                waitingToLaunch = true;
                timer = 60;


                return InteractionResult.SUCCESS;
            }

            glaggleCount++;

            player.displayClientMessage(Component.literal("Glaggle power charged: " + (float) glaggleCount * 100 / glaggleThreshold+ "%!!"), false);

            return InteractionResult.SUCCESS;

        }

        return super.mobInteract(player, hand);
    }
}
