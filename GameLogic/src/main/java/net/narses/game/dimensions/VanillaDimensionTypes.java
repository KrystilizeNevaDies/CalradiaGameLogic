package net.narses.game.dimensions;

import net.narses.game.io.MinecraftData;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.DimensionType;

public class VanillaDimensionTypes {

    public static DimensionType OVERWORLD = loadDimension(NamespaceID.from("minecraft:overworld"));
    public static DimensionType NETHER = loadDimension(NamespaceID.from("minecraft:the_nether"));
    public static DimensionType END = loadDimension(NamespaceID.from("minecraft:the_end"));

    private static DimensionType loadDimension(NamespaceID id) {
        return MinecraftData.open(id, "dimension_type", DimensionType.class).orElseThrow();
    }
}
