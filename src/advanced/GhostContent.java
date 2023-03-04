package advanced;

import arc.struct.ObjectMap;
import mindustry.ctype.*;
import mindustry.entities.bullet.BulletType;
import mindustry.world.meta.*;

final class GhostContent extends UnlockableContent{

    public GhostContent() {
        super("golden ray of the glorious sunshine");//TODO
    }

    public BulletType real;

    @Override
    public ContentType getContentType() {
        return ContentType.error;
    }

    @Override
    public void checkStats() {
        stats.intialized = false;
        try{
            var f = stats.getClass().getDeclaredField("map");
            f.setAccessible(true);
            f.set(stats, null);
        }
        catch (Throwable z){throw new RuntimeException(z);}
        Intelligence.setStats(this);
        setStats();
        stats.add(Stat.bullet, StatValues.ammo(ObjectMap.of(real, real)));
    }

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public String emoji() {
        return "uwu";
    }

    @Override
    public boolean hasEmoji() {
        return true;
    }
}