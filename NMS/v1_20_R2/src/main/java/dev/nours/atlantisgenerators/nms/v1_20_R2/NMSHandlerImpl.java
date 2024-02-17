package dev.nours.atlantisgenerators.nms.v1_20_R2;

import dev.nours.atlantisgenerators.nms.NMSHandler;
import io.papermc.paper.math.BlockPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.*;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R2.CraftChunk;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftItemFrame;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

public class NMSHandlerImpl implements NMSHandler {
//    @Override
//    public boolean countBlockInChunk(Chunk chunk) {
//        LevelChunk nmsChunk = (LevelChunk) ((CraftChunk) chunk).getHandle(ChunkStatus.FULL);
//        LevelChunkSection[] chunkSections = nmsChunk.getSections();
//        for(LevelChunkSection chunkSection : chunkSections) {
//            if(chunkSection != null && !chunkSection.hasOnlyAir()) {
//                for (int y = 0; y < 16; y++) {
//                    for (int x = 0; x < 16; x++) {
//                        for (int z = 0; z < 16; z++) {
//                            BlockState blockState = chunkSection.getBlockState(y, x, z);
//
//                            if (blockState.getBlock() == Blocks.BIRCH_LOG) {
//                                return true;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        return false;
//    }

    @Override
    public boolean countBlockInChunk(Chunk chunk) {
        for (Entity entity : chunk.getEntities()) {
            if(entity instanceof ItemFrame) {
                ItemFrame itemframe = (ItemFrame) entity;
                Bukkit.getLogger().info("Entity Type: " + itemframe.getItem() +
                        ", Location: " + entity.getX() + ", " + entity.getY() + ", " + entity.getZ());
            }
        }

        return false;
    }
}
