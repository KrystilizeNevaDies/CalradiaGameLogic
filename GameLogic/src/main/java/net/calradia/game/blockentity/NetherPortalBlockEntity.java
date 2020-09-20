package net.calradia.game.blockentity;

import net.minestom.server.utils.BlockPosition;
import net.calradia.game.system.NetherPortal;

public class NetherPortalBlockEntity extends BlockEntity {
    public NetherPortalBlockEntity(BlockPosition position) {
        super(position);
    }

    public void setRelatedPortal(NetherPortal portal) {
        set("portal", portal, NetherPortal.class);
    }

    public NetherPortal getRelatedPortal() {
        return get("portal");
    }
}
