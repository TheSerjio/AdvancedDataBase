package advanced;

import arc.graphics.g2d.TextureAtlas.AtlasRegion;
import arc.scene.ui.layout.Table;

public class AtlasContent extends WeirdContent {

    public AtlasContent() {
        super("atlas");
    }

    @Override
    public void checkStats() {
        super.checkStats();
        stats.add(Intelligence.sImages, (Table t) -> {
            t.row();
            arc.Core.atlas.getRegionMap().each((String name, AtlasRegion region) -> {
                t.label(() -> name);
                t.image(region);
                t.row();
            });
        });
    }
}