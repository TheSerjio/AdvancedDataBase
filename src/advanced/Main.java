package advanced;

import arc.*;
import arc.util.Time;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;

//hidden mods cant loadContent()
public class Main extends Mod{
    
    public static BulletDisplayContent bullet;

    public Main(){
        Events.on(ClientLoadEvent.class, e -> {
            Time.runTask(60, Intelligence::initStats);
            new DataBaseContent();
            new SpriteContent();
            new AtlasContent();
            new IconsContent();
            new ListDisplayContent(Items.copper);
            new ListDisplayContent(Blocks.duo);
            new ListDisplayContent(Liquids.water);
            new ListDisplayContent(UnitTypes.dagger);
            new ListDisplayContent(Weathers.rain);
            new ListDisplayContent(SectorPresets.origin);
            new ListDisplayContent(Planets.serpulo);
            //no TeamEntry :(
            bullet = new BulletDisplayContent();
        });
    }
}