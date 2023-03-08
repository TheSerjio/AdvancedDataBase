package advanced;

import arc.struct.ObjectMap;
import mindustry.entities.bullet.BulletType;
import mindustry.world.meta.*;

final class BulletDisplayContent extends WeirdContent {

    public BulletDisplayContent() {
        super("bullet");
        uiIcon = fullIcon = mindustry.content.StatusEffects.corroded.uiIcon;
    }

    private BulletType bullet;

    public void execute(BulletType bt){
        bullet = bt;
        mindustry.Vars.ui.content.show(this);
    }

    @Override
    public void checkStats() {
        super.checkStats();
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