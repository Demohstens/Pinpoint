package net.demo.pinpoint;

import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import javax.annotation.Nonnull;

public class CoordinateHUD extends CustomUIHud {
    String coords;

    public CoordinateHUD(@Nonnull PlayerRef playerRef, @Nonnull String coords) {
        super(playerRef);
        this.coords = coords;
    }

    @Override
    protected void build(UICommandBuilder builder) {
        builder.append("Pages/Demo_SimpleCoordinatesOverlay.ui");
        updateCoordinates(coords, builder);
    }

    void updateCoordinates(String coords, UICommandBuilder builder) {
        this.coords = coords;
        builder.set("#ButtonTest.Text", coords);
    }

}
