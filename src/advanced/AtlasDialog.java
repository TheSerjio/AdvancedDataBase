package advanced;

import arc.graphics.Texture;
import arc.graphics.g2d.TextureAtlas.AtlasRegion;
import arc.scene.ui.Image;
import arc.scene.ui.Label;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import mindustry.ui.dialogs.BaseDialog;

public class AtlasDialog extends BaseDialog {
    public AtlasDialog(){
        super("atlas");
        closeOnBack();
        var all = new Table();
        for(var texture : arc.Core.atlas.getTextures()){
            all.add(new Atlas(texture));
            all.row();
        }
        cont.pane(all);
    }

    public static class Atlas extends Image{
        private final Seq<AtlasRegion> regions;
        private final Label label;

        public Atlas(Texture texture){
            super(texture);
            label = new Label("");
            regions = arc.Core.atlas.getRegions().copy().filter((AtlasRegion r) -> r.texture == texture);
        }

        @Override
        public void draw() {
            super.draw();
            var mouse = arc.Core.input.mouse();
            label.x = mouse.x;
            label.y = mouse.y;
            var rx = label.x - x;
            var ry = label.y - y;
            label.setText("");
            for(var r : regions)
            {
                var fy = r.texture.height - r.getY() - r.packedHeight;//they use different coordinate system
                if(rx > r.getX() && ry > fy && rx < r.getX() + r.packedWidth && ry < fy + r.packedHeight)
                {
                    label.setText(r.name);
                    break;
                }
            }
            label.draw();
        }
    }
}