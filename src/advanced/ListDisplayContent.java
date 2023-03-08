package advanced;

import arc.scene.ui.layout.Table;
import arc.util.Scaling;
import mindustry.ctype.*;

public class ListDisplayContent extends WeirdContent {
    public final ContentType type;

    public ListDisplayContent(UnlockableContent u) {
        super(u.getContentType().name() + "-content");
        type = u.getContentType();
        localizedName = arc.Core.bundle.get("content." + type.name() + ".name");
        uiIcon = u.uiIcon;
        fullIcon = u.fullIcon;
    }

    @Override
    public void checkStats() {
        super.checkStats();
        stats.add(Intelligence.sListing, (Table t) -> {
            t.row();
            for (var c : mindustry.Vars.content.getBy(type)) {
                var u = (UnlockableContent) c;
                t.label(() -> u.localizedName).get().clicked(() -> mindustry.Vars.ui.content.show(u));
                var img = t.image(u.uiIcon).get().setScaling(Scaling.fit);
                img.setSize(36, 36);
                img.clicked(() -> mindustry.Vars.ui.content.show(u));
                t.row();
            }
        });
    }
}