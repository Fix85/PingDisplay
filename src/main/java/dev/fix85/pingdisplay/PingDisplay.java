package dev.fix85.pingdisplay;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingDisplay implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("pingdisplay");

    @Override
    public void onInitializeClient() {
        PingConfig.init();
        LOGGER.info("PingDisplay initialized!");
    }
}
