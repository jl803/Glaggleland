package com.glagworld.glaggleland.entity.client.glagglecannon;

import com.glagworld.glaggleland.Glaggleland;
import com.glagworld.glaggleland.entity.client.giggler.GigglerModel;
import com.glagworld.glaggleland.entity.custom.GigglerEntity;
import com.glagworld.glaggleland.entity.custom.GlaggleCannonEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class GlaggleCannonRenderer extends MobRenderer<GlaggleCannonEntity, GlaggleCannonModel<GlaggleCannonEntity>> {
    public GlaggleCannonRenderer(EntityRendererProvider.Context context) {
        super(context, new GlaggleCannonModel<>(context.bakeLayer(GlaggleCannonModel.LAYER_LOCATION)), .25f);
    }


    @Override
    public ResourceLocation getTextureLocation(GlaggleCannonEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(Glaggleland.MODID, "textures/entity/glagglecannon/glagglecannon.png");
    }

    @Override
    public void render(GlaggleCannonEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.45f,0.45f,0.45f);
        } else {
            poseStack.scale(4f,4f,4f);
        }

        super.render(entity,entityYaw,partialTicks,poseStack,buffer,packedLight);
    }


}
