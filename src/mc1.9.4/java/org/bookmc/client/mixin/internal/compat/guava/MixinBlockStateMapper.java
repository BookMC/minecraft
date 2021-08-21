package org.bookmc.client.mixin.internal.compat.guava;

import com.google.common.base.MoreObjects;
import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockStateMapper.class)
public class MixinBlockStateMapper {
    @Redirect(
        method = "getVariants",
        at = @At(
            value = "INVOKE",
            target = "Lcom/google/common/base/Objects;firstNonNull(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
            ordinal = 0
        )
    )
    private <T> T firstNonNull(@Nullable T first, @Nullable T second) {
        return MoreObjects.firstNonNull(first, second);
    }
}
