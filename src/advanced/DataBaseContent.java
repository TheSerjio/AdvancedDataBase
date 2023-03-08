package advanced;

import arc.scene.ui.layout.Table;
import mindustry.gen.Icon;

public class DataBaseContent extends WeirdContent {

    public DataBaseContent() {
        super("advanced-database");
        uiIcon = fullIcon = Icon.book.getRegion();
    }
    
    @Override
    public void checkStats() {
        super.checkStats();
        stats.add(Intelligence.sImages, (Table t) -> {
            Main.dialog.show();
        });
    }
}