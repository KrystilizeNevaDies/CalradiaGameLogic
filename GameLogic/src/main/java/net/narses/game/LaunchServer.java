package net.narses.game;

import net.narses.game.anvil.FileSystemStorage;
import net.narses.game.items.VanillaItems;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.gamedata.loottables.LootTableManager;
import net.minestom.server.instance.block.BlockManager;
import net.minestom.server.network.ConnectionManager;
import net.narses.game.blocks.VanillaBlocks;
import net.narses.game.commands.VanillaCommands;
import net.narses.game.gamedata.loottables.VanillaLootTables;
import net.narses.game.generation.VanillaWorldgen;
import net.narses.game.system.NetherPortal;
import net.narses.game.system.ServerProperties;

import java.io.File;
import java.io.IOException;

public class LaunchServer {

    public static void main(String[] args) throws IOException {
        MinecraftServer minecraftServer = MinecraftServer.init();
        MinecraftServer.setBrandName("Narses");

        BlockManager blockManager = MinecraftServer.getBlockManager();

        CommandManager commandManager = MinecraftServer.getCommandManager();
        VanillaWorldgen.prepareFiles();
        VanillaWorldgen.registerAllBiomes(MinecraftServer.getBiomeManager());
        VanillaCommands.registerAll(commandManager);
        VanillaItems.registerAll(MinecraftServer.getConnectionManager());
        VanillaBlocks.registerAll(MinecraftServer.getConnectionManager(), MinecraftServer.getBlockManager());
        NetherPortal.registerData(MinecraftServer.getDataManager());
        LootTableManager lootTableManager = MinecraftServer.getLootTableManager();
        VanillaLootTables.register(lootTableManager);

        MinecraftServer.getStorageManager().defineDefaultStorageSystem(FileSystemStorage::new);

        ServerProperties properties = new ServerProperties(new File(".", "server.properties"));
        PlayerInit.init(properties);


        MinecraftServer.getSchedulerManager().buildShutdownTask(() -> {
            ConnectionManager connectionManager = MinecraftServer.getConnectionManager();
            connectionManager.getOnlinePlayers().forEach(player -> {
                // TODO: Saving
                player.kick("Server is closing.");
                connectionManager.removePlayer(player.getPlayerConnection());
            });
        });

        minecraftServer.start(properties.get("server-ip"), Integer.parseInt(properties.get("server-port")), (playerConnection, responseData) -> {
            responseData.setName("1.16.2");
            responseData.setMaxPlayer(Integer.parseInt(properties.get("max-players")));
            responseData.setOnline(MinecraftServer.getConnectionManager().getOnlinePlayers().size());
            responseData.setDescription(properties.get("motd"));
            responseData.setFavicon("data:image/png;base64,<data>");
        });
    }

}
