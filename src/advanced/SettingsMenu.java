package advanced;

import arc.struct.ObjectMap;
import arc.util.I18NBundle;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.gen.Icon;
import mindustry.world.meta.BuildVisibility;

import static mindustry.Vars.*;

public class SettingsMenu {

    private static class WeirdVisibility extends BuildVisibility {

        public final String name;

        public WeirdVisibility(String name) {
            super(() -> {
                var i = arc.Core.settings.getInt("advanced." + name);
                if (i == 0)
                    return false;
                if (i == 2)
                    return true;
                if (!Vars.net.active())
                    return true;
                return Vars.net.server();
            });
            this.name = name;
        }
    }

    public static final WeirdVisibility boulder = new WeirdVisibility("boulder");
    public static final WeirdVisibility debug = new WeirdVisibility("debug");
    public static final BuildVisibility wall = new BuildVisibility(() -> BuildVisibility.sandboxOnly.visible() && (Vars.net.server() || !Vars.net.active()));

    public static void init() {

        ui.settings.addCategory("@advanced.settings", Icon.bookOpen, table -> {
            table.label(() -> arc.Core.bundle.get("setting.uiscale.description"));
            try {
                var f = I18NBundle.class.getDeclaredField("properties");
                f.setAccessible(true);
                var props = (ObjectMap<String, String>) f.get(arc.Core.bundle);
                props.put("setting.advanced.boulder.name", Blocks.boulder.emoji());
                props.put("setting.advanced.debug.name", Liquids.gallium.emoji());
            } catch (Throwable e) {
                arc.util.Log.err(e);
            }
            for (var wv : new WeirdVisibility[] { boulder, debug })
                table.sliderPref("advanced." + wv.name, 0, 0, 2,
                        (int i) -> arc.Core.bundle.get("settings.a.visibility." + i));
        });
    }
}