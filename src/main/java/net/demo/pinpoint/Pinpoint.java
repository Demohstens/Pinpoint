package net.demo.pinpoint;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.event.events.entity.EntityEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;


import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

public class Pinpoint extends JavaPlugin {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public Pinpoint(@Nonnull JavaPluginInit init) {
        super(init);
    }


    @Override
    protected void setup() {
        super.setup();
        HytaleServer.SCHEDULED_EXECUTOR.scheduleAtFixedRate(() -> {
            Universe.get().getWorlds().forEach((key, world) -> {
                world.getPlayers().forEach(player -> {
                    world.execute(() -> {
                        Ref<EntityStore> ref = player.getReference();
                        if (ref != null && ref.isValid()) {
                            Store<EntityStore> store = ref.getStore();
                            PlayerRef playerRefComponent = store.getComponent(ref, PlayerRef.getComponentType());
                            Vector3d pos = playerRefComponent.getTransform().getPosition();
                            String coords = String.format("X: %.1f Y: %.1f Z: %.1f", pos.x, pos.y, pos.z);

                            var ui = new CoordinateHUD(playerRefComponent, coords);
                            // Get the player from the manager
                            player.getHudManager().setCustomHud(playerRefComponent, ui);
                        }
                    });
                });
            });

        }, 0, 100, TimeUnit.MICROSECONDS);
    }

}

