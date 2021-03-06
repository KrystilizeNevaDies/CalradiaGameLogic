package net.narses.game.blockentity;

import net.minestom.server.data.SerializableDataImpl;
import net.minestom.server.utils.BlockPosition;

/**
 * Base class used to represent Block Entities
 */
public class BlockEntity extends SerializableDataImpl {
    private final BlockPosition position;

    public BlockEntity(BlockPosition position) {
        this.position = position;
    }

    public BlockPosition getPosition() {
        return position;
    }
}
