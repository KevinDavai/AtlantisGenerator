package dev.nours.atlantisgenerators.nms;

import org.bukkit.Bukkit;

public class NMSAdapter {
    private static NMSHandler nmsHandler;

    static {
        try {
            String name = Bukkit.getServer().getClass().getPackage().getName();
            String version = name.substring(name.lastIndexOf('.') + 1);
            nmsHandler = (NMSHandler) Class.forName("dev.nours.atlantisgenerators.nms." + version + ".NMSHandlerImpl").getConstructor().newInstance();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    /**
     * @return true if this server version is supported, false otherwise
     */
    public static boolean isValidVersion() {
        return nmsHandler != null;
    }

    /**
     * @return the instance of the NMSHandler, or null if this server version is not supported
     */
    public static NMSHandler getHandler() {
        return nmsHandler;
    }
}
