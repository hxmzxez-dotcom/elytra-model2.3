package com.test.elytramodel;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class ElytraModelClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        UseBlockCallback.EVENT.register((player, level, hand, hit) -> {
            if (!level.isClientSide()) return InteractionResult.PASS;

            // Elde fisek, sirtta elytra, suzuluyor olmali
            if (!player.getItemInHand(hand).is(Items.FIREWORK_ROCKET)) return InteractionResult.PASS;
            if (!player.getItemBySlot(EquipmentSlot.CHEST).is(Items.ELYTRA)) return InteractionResult.PASS;
            if (!player.isFallFlying()) return InteractionResult.PASS;

            // Goz ag blogunun icindeyse calismasin
            BlockPos eyePos = BlockPos.containing(player.getEyePosition());
            if (level.getBlockState(eyePos).is(Blocks.COBWEB)) return InteractionResult.PASS;

            // Blok etkilesimini iptal et, fisegi havada kullanilmis gibi gonder
            Minecraft mc = Minecraft.getInstance();
            if (mc.gameMode != null) {
                mc.gameMode.useItem(player, hand);
            }
            return InteractionResult.FAIL;
        });
    }
}
