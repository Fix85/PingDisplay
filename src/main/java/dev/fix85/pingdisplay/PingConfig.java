package dev.fix85.pingdisplay;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

@Config(name = "pingdisplay")
public class PingConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean pingBeforeName = false;

    public static void init() {
        AutoConfig.register(PingConfig.class, GsonConfigSerializer::new);
    }

    public static PingConfig get() {
        return AutoConfig.getConfigHolder(PingConfig.class).getConfig();
    }
}
