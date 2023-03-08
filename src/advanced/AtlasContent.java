package advanced;

import arc.graphics.g2d.TextureRegion;
import arc.graphics.g2d.TextureAtlas.AtlasRegion;
import arc.scene.ui.Tooltip;
import arc.scene.ui.layout.Table;
import mindustry.gen.Tex;
import mindustry.ui.dialogs.BaseDialog;

public class AtlasContent extends WeirdContent {

    public AtlasContent() {
        super("atlas");
    }

    private int i;

    private static class ImageDialog extends BaseDialog{

        public ImageDialog(String title, TextureRegion region) {
            super(title);
            shown(() -> {
                cont.image(region);
            });
            addCloseButton();
        }
    }

    @Override
    public void checkStats() {
        super.checkStats();
        stats.add(Intelligence.sImages, (Table table) -> {
            table.row();
            i = 0;
            arc.Core.atlas.getRegionMap().each((String name, AtlasRegion region) -> {
                var cell = table.image(region).size(64);
                var img = cell.get();
                img.addListener(new Tooltip(i -> i.background(Tex.button).add(name)));
                img.clicked(() -> new ImageDialog(name, region).show());

                if(i++ % 16==0)
                    table.row();
            });
            i = 0;
        });
    }
}