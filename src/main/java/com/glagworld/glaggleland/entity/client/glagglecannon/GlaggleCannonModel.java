package com.glagworld.glaggleland.entity.client.glagglecannon;// Made with Blockbench 5.1.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.glagworld.glaggleland.Glaggleland;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import static net.neoforged.neoforge.internal.versions.neoforge.NeoForgeVersion.MOD_ID;

public class GlaggleCannonModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("modid", "glagglecannon"), "main");
    private final ModelPart WheelLeft;
    private final ModelPart WheelRight;
    private final ModelPart Barrel;

    public GlaggleCannonModel(ModelPart root) {
        this.WheelLeft = root.getChild("WheelLeft");
        this.WheelRight = root.getChild("WheelRight");
        this.Barrel = root.getChild("Barrel");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition WheelLeft = partdefinition.addOrReplaceChild("WheelLeft", CubeListBuilder.create().texOffs(38, 24).addBox(-3.5F, -3.5F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(38, 26).addBox(-3.5F, 2.5F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(38, 33).addBox(2.5F, 2.5F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(38, 35).addBox(2.5F, -3.5F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 36).addBox(-4.5F, -2.5F, -0.75F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 4).addBox(-2.5F, -4.5F, -0.75F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 36).addBox(3.5F, -2.5F, -0.75F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 6).addBox(-2.5F, 3.5F, -0.75F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 33).addBox(-0.5F, -3.5F, -0.75F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 28).addBox(-3.5F, -0.5F, -0.75F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 19.5F, 3.75F));

        PartDefinition cube_r1 = WheelLeft.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(26, 16).addBox(-3.0F, -2.0F, 0.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, 0.25F, -0.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r2 = WheelLeft.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(26, 14).addBox(-3.0F, -2.0F, 0.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 1.75F, -0.75F, 0.0F, 0.0F, -0.7854F));

        PartDefinition WheelRight = partdefinition.addOrReplaceChild("WheelRight", CubeListBuilder.create().texOffs(32, 8).addBox(-3.5F, -3.5F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(38, 20).addBox(-3.5F, 2.5F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(38, 22).addBox(2.5F, 2.5F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 8).addBox(2.5F, -3.5F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 33).addBox(-4.5F, -2.5F, -0.25F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(-2.5F, -4.5F, -0.25F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(34, 33).addBox(3.5F, -2.5F, -0.25F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 2).addBox(-2.5F, 3.5F, -0.25F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 33).addBox(-0.5F, -3.5F, -0.25F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 18).addBox(-3.5F, -0.5F, -0.25F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 19.5F, -3.75F));

        PartDefinition cube_r3 = WheelRight.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(26, 12).addBox(-3.0F, -2.0F, 0.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, 0.25F, -0.25F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r4 = WheelRight.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(26, 10).addBox(-3.0F, -2.0F, 0.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 1.75F, -0.25F, 0.0F, 0.0F, -0.7854F));

        PartDefinition Barrel = partdefinition.addOrReplaceChild("Barrel", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -2.0F, -3.0F, 10.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(8, 38).addBox(-7.0F, 1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 38).addBox(-7.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 30).addBox(-7.0F, -1.0F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 28).addBox(8.0F, -3.0F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(28, 28).addBox(8.0F, 2.0F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(32, 20).addBox(8.0F, -3.0F, 2.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 33).addBox(8.0F, -3.0F, -3.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-5.0F, -3.0F, -2.0F, 9.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 20).addBox(4.0F, -2.0F, -2.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(10, 30).addBox(4.0F, -3.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 20).addBox(4.0F, -1.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 18.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }



    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
        WheelLeft.render(poseStack, vertexConsumer, i, i1, i2);
        WheelRight.render(poseStack, vertexConsumer, i, i1, i2);
        Barrel.render(poseStack, vertexConsumer, i, i1, i2);
    }
}