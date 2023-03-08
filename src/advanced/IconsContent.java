package advanced;

import java.lang.reflect.Field;
import java.util.Arrays;

import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.layout.Table;
import mindustry.gen.Icon;

public class IconsContent extends WeirdContent {

    public IconsContent() {
        super("icon");
        uiIcon = fullIcon = Icon.logic.getRegion();
    }

    @Override
    public void checkStats() {
        super.checkStats();
        stats.add(Intelligence.sList, (Table t) -> {
            t.row();
            var fields = Icon.class.getDeclaredFields();
            Arrays.sort(fields, (Field a, Field b) -> a.getName().compareTo(b.getName()));
            for (var f : fields) {
                if(f.getName().endsWith("Small"))
                    continue;
                TextureRegionDrawable region = null;
                try{
                    region = (TextureRegionDrawable)f.get(null);
                }
                catch(Throwable anyway){
                    continue;
                }
                if(region==null)
                    continue;
                
                t.label(() -> f.getName());
                t.image(region);
                t.row();
            }
        });
    }
}
