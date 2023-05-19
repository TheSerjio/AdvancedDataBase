package advanced;

import arc.scene.ui.layout.Table;
import mindustry.gen.Icon;

public class DataBaseContent extends WeirdContent {

    public DataBaseContent() {
        super("database", Icon.book);
    }
    
    @Override
    public void checkStats() {
        super.checkStats();
        stats.add(Intelligence.sList, (Table t) -> {
            new AdvancedDataBaseDialog().show();
        });
    }
}