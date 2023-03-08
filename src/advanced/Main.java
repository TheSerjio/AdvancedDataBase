package advanced;

import arc.*;
import arc.util.Time;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;

public class Main extends Mod{
    
    public static BulletDisplayContent bullet;

    public static AdvancedDataBaseDialog dialog;

    public Main(){
        Events.on(ClientLoadEvent.class, e -> {
            Time.runTask(60, Intelligence::initStats);
            dialog = new AdvancedDataBaseDialog();
            //hidden mods cant loadContent()
            bullet = new BulletDisplayContent();
            new AtlasContent();
            new DataBaseContent();
            new ListDisplayContent(Items.copper);
            new ListDisplayContent(Blocks.duo);
            new ListDisplayContent(Liquids.water);
            new ListDisplayContent(UnitTypes.dagger);
            new ListDisplayContent(Weathers.rain);
            new ListDisplayContent(SectorPresets.origin);
            new ListDisplayContent(Planets.serpulo);
            //no TeamEntry :(
        });
    }
}