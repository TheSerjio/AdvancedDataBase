package advanced;

import arc.scene.ui.layout.Table;
import mindustry.gen.Icon;

public class SpriteContent extends WeirdContent {

    public SpriteContent() {
        super("sprite");
        uiIcon = fullIcon = Icon.zoom.getRegion();
    }

    @Override
    public void checkStats() {
        super.checkStats();
        stats.add(Intelligence.sList, (Table t) -> {
            new SpriteDialog().show();
        });
    }
}