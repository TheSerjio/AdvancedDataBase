package advanced;

import arc.scene.ui.Image;
import arc.scene.ui.layout.Table;
import mindustry.gen.Icon;

public class AtlasContent extends WeirdContent {
    
    public AtlasContent(){
        super("atlas");
        uiIcon = fullIcon = Icon.eye.getRegion();
    }

    @Override
    public void checkStats() {
        super.checkStats();
        stats.add(Intelligence.sList, (Table table) -> {
            table.row();
            for(var texture : arc.Core.atlas.getTextures()){
                var img = new Image(texture);//TODO display region name
                table.add(img);
                table.row();
            }
        });
    }
}