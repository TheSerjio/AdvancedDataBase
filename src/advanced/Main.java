package advanced;

import arc.*;
import arc.util.Time;
import mindustry.game.EventType.*;
import mindustry.mod.*;

public class Main extends Mod{
    
    public static GhostContent ghost;

    public Main(){
        Events.on(ClientLoadEvent.class, e -> {
            Time.runTask(60, Intelligence::initStats);
        });
    }

    @Override
    public void loadContent() {
        ghost = new GhostContent();
    }
}