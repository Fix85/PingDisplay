package dev.fix85.pinghud;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingHUD implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("pinghud");

    @Override
    public void onInitializeClient() {
        LOGGER.info("PingHUD initialized!");
    }
}
