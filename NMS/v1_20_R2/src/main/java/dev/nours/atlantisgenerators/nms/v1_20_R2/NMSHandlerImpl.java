package dev.nours.atlantisgenerators.nms.v1_20_R2;

import dev.nours.atlantisgenerators.nms.NMSHandler;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;

public class NMSHandlerImpl implements NMSHandler {
    @Override
    public void isInChunk() {
        Bukkit.getLogger().warning("Chunk 1.20.R2");
    }
}
