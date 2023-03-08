package advanced;

import mindustry.ctype.*;

public class WeirdContent extends UnlockableContent {

    public WeirdContent(String name) {
        super("advanced-" + name);
        alwaysUnlocked = true;
        unlocked = true;
        uiIcon = fullIcon = arc.Core.atlas.find("error");
    }

    @Override
    public final ContentType getContentType() {
        return ContentType.error;
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
    }

    @Override
    public final String emoji() {
        return name;
    }

    @Override
    public final boolean hasEmoji() {
        return true;
    }
}