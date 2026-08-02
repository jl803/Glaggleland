package com.glagworld.glaggleland.entity;

import com.glagworld.glaggleland.Glaggleland;
import com.glagworld.glaggleland.entity.custom.GigglerEntity;
import com.glagworld.glaggleland.entity.custom.GlaggleCannonEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Glaggleland.MODID);

    public static final Supplier<EntityType<GigglerEntity>> GIGGLER =
            ENTITY_TYPES.register("giggler", () -> EntityType.Builder.of(GigglerEntity::new, MobCategory.CREATURE)
                    .sized(2f,2f).build("giggler"));

    public static final Supplier<EntityType<GlaggleCannonEntity>> GLAGGLE_CANNON =
            ENTITY_TYPES.register("glagglecannon", () -> EntityType.Builder.of(GlaggleCannonEntity::new, MobCategory.CREATURE)
                    .sized(1f,1f).build("glagglecannon"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}

