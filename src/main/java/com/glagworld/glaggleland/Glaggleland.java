package com.glagworld.glaggleland;

import com.glagworld.glaggleland.entity.ModEntities;
import com.glagworld.glaggleland.entity.client.giggler.GigglerRenderer;
import com.glagworld.glaggleland.entity.client.glagglecannon.GlaggleCannonRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(Glaggleland.MODID)
public class Glaggleland {
    public static final String MODID = "glaggleland";
    public static final Logger LOGGER = LogUtils.getLogger();

    // Registries collect this mod's content before NeoForge adds it to the game.
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Items
    public static final DeferredHolder<Item, Item> THORN_GLOGNUT = 
        ITEMS.register("thorn_glognut", () -> new Item(new Item.Properties()
                .stacksTo(6)
                .food(new FoodProperties.Builder()
                        .nutrition(6)
                        .saturationModifier(0.6F)
                        .build()
            )
        ));

    public static final DeferredHolder<Item, Item> GIGGLER_SPAWN_EGG =
            ITEMS.register("giggler_spawn_egg", () -> new SpawnEggItem(
                    ModEntities.GIGGLER.get(),
                    0xF7E65A,
                    0xF7E65A,
                    new Item.Properties()
            ));

    // Creative tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GLAGGLELAND_TAB = CREATIVE_MODE_TABS.register("glaggleland", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.glaggleland"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> THORN_GLOGNUT.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(THORN_GLOGNUT.get());
                output.accept(GIGGLER_SPAWN_EGG.get());
            })
            .build());



    @SuppressWarnings("unused")
    public Glaggleland(IEventBus modEventBus, ModContainer modContainer) {
        // Register startup hooks and mod content.
        modEventBus.addListener(this::commonSetup);
        ModEntities.register(modEventBus);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Glaggleland common setup complete.");
    }

    // Client-only setup, like entity renderers.
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            //ModItemProperties.addCustomItemProperties();

            EntityRenderers.register(ModEntities.GIGGLER.get(), GigglerRenderer::new);
            EntityRenderers.register(ModEntities.GLAGGLE_CANNON.get(), GlaggleCannonRenderer::new);

        }
    }

}
