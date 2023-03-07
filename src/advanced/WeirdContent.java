package advanced;

import mindustry.ctype.*;

public class WeirdContent extends UnlockableContent {

    public WeirdContent(String name) {
        super(name);
    }

    @Override
    public ContentType getContentType() {
        return ContentType.error;
    }

    @Override
    public String emoji() {
        return name;
    }

    @Override
    public boolean hasEmoji() {
        return true;
    }
}