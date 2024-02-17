package dev.nours.atlantisgenerators.nms.v1_20_R3;

import dev.nours.atlantisgenerators.nms.NMSHandler;
import io.papermc.paper.math.BlockPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.*;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;

import java.lang.reflect.Field;
import java.util.Map;

import java.lang.reflect.Field;

public class NMSHandlerImpl implements NMSHandler {
    private static Field nonEmptyBlockCountField;

    static {
        try {
            // Initialize the Field instance statically
            nonEmptyBlockCountField = LevelChunkSection.class.getDeclaredField("e");
            nonEmptyBlockCountField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean countBlockInChunk(Chunk chunk) {
        LevelChunk nmsChunk = (LevelChunk) ((CraftChunk) chunk).getHandle(ChunkStatus.FULL);
        LevelChunkSection[] chunkSections = nmsChunk.getSections();
        for(LevelChunkSection chunkSection : chunkSections) {
            if(chunkSection != null && !chunkSection.hasOnlyAir()) {
                try {
                    // Iterate through the entire chunk section
                    for (int y = 0; y < 16; y++) {
                        for (int x = 0; x < 16; x++) {
                            for (int z = 0; z < 16; z++) {
                                BlockState blockState = chunkSection.getBlockState(y, x, z);

                                // Check if the block is dirt
                                if (blockState.getBlock() == Blocks.BIRCH_LOG) {
                                    return true;  // Dirt block found
                                }
                            }
                        }
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }
}
