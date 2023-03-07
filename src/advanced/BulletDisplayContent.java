package advanced;

import arc.struct.ObjectMap;
import mindustry.entities.bullet.BulletType;
import mindustry.world.meta.*;

final class BulletDisplayContent extends WeirdContent {

    public BulletDisplayContent() {
        super("bullet-content");
        uiIcon = fullIcon = mindustry.content.StatusEffects.corroded.uiIcon;
    }

    private BulletType bullet;

    public void execute(BulletType bt){
        bullet = bt;
        mindustry.Vars.ui.content.show(this);
    }

    @Override
    public void checkStats() {
        try {
            var f = stats.getClass().getDeclaredField("map");
            f.setAccessible(true);
            f.set(stats, null);
        } catch (Throwable owo) {
            throw new RuntimeException(owo);
        }
        Intelligence.setStats(this);
        if (bullet == null)
            return;
            
        stats.add(Stat.bullet, StatValues.ammo(ObjectMap.of(this, bullet)));
        stats.add(Stat.damage, bullet.estimateDPS());
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}