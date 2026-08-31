package com.glagworld.glaggleland.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

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


    private boolean facingUp = false;

    private boolean waitingToLaunch = false;
    private boolean waitingToCheck = false;

    private float turning_progress = 0.0f;


    private Vec3 launch_direction;

    public GlaggleCannonEntity(EntityType<?> entityType, Level level) {
        super((EntityType<? extends Mob>) entityType, level);
        setPersistenceRequired();
    }

    private static final EntityDataAccessor<Boolean> FACING_UP =
            SynchedEntityData.defineId(GlaggleCannonEntity.class, EntityDataSerializers.BOOLEAN);

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FACING_UP, false);
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

        if (waitingToCheck == true && sittingPlayer != null) {

            Vec3 appliedForce = new Vec3(launch_direction.x * 2.0, launch_direction.y * 10000.0, launch_direction.z * 2.0);
            sittingPlayer.setDeltaMovement(sittingPlayer.getDeltaMovement().add(appliedForce));
            sittingPlayer.hurtMarked = true;
            sittingPlayer.hasImpulse = true;

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
            sittingPlayer = null;
            return;
        }

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

            launch_direction = this.getLookAngle().normalize().yRot((float) Math.toRadians(90)); //offsets facing direction because I messed it up lol

            Vec3 currentMotion = passenger.getDeltaMovement();
            Vec3 appliedForce = new Vec3(launch_direction.x * 2.0, 10000.0, launch_direction.z * 2.0);

            passenger.stopRiding();

            passenger.setDeltaMovement(currentMotion.add(appliedForce));
            passenger.hurtMarked = true;
            passenger.hasImpulse = true;

            sittingPlayer.displayClientMessage(Component.literal("Ohhhh I'm launching it"), true);

            waitingToCheck = true;
            timer = 60;

        }
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

                setFacingUp(true);


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


    public boolean getFacingUp() { return this.entityData.get(FACING_UP); }

    public void setFacingUp(boolean value) { this.entityData.set(FACING_UP, value); }

    public float getTurningProgress() { return turning_progress; }
}
