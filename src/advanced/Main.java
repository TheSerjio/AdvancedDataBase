package advanced;

import arc.*;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.graphics.MenuRenderer;
import mindustry.mod.*;
import mindustry.ui.fragments.MenuFragment;

//hidden mods cant loadContent()
public class Main extends Mod{
    
    public static BulletDisplayContent bullet;

    public Main(){
        Events.on(ClientLoadEvent.class, e -> {
            Time.runTask(10, () -> {
                Intelligence.initStats();//some icons are not loaded when event occurs
                funny();
            });
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

    public static void funny(){
        try{
            var f = MenuFragment.class.getDeclaredField("renderer");
            f.setAccessible(true);
            var ren = f.get(Vars.ui.menufrag);
            f = MenuRenderer.class.getDeclaredField("flyerType");
            f.setAccessible(true);
            var flyers = Seq.with(UnitTypes.elude);
            for(var u : Vars.content.units())
                if(u.flying)
                    flyers.add(u);
            f.set(ren, flyers.random());
            f = MenuRenderer.class.getDeclaredField("flyers");
            f.setAccessible(true);
            f.set(ren, 50);
        }
        catch(Throwable uwu){
            arc.util.Log.err(uwu);
        }
    }
}