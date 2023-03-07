package advanced;

import arc.*;
import arc.util.Time;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;

public class Main extends Mod{
    
    public static BulletDisplayContent bullet;

    public Main(){
        Events.on(ClientLoadEvent.class, e -> {
            Time.runTask(60, Intelligence::initStats);
            bullet = new BulletDisplayContent();
            new ListDisplayContent(Items.copper);
            new ListDisplayContent(Blocks.duo);
            new ListDisplayContent(Liquids.water);
            new ListDisplayContent(UnitTypes.dagger);
            new ListDisplayContent(Weathers.rain);
            new ListDisplayContent(SectorPresets.origin);
            new ListDisplayContent(Planets.tantros);
            //TODO TeamEntry
        });
    }
}