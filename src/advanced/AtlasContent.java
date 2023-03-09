package advanced;

import mindustry.gen.Icon;

public class AtlasContent extends WeirdContent {
    
    public AtlasContent(){
        super("atlas");
        uiIcon = fullIcon = Icon.eye.getRegion();
    }

    @Override
    public void checkStats() {
        super.checkStats();
        new AtlasDialog().show();
    }
}