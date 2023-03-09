package advanced;

import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import mindustry.gen.*;
import mindustry.ui.dialogs.BaseDialog;

public class SpriteDialog extends BaseDialog {
    private TextField search;
    private TextField columns;
    private Table all = new Table();

    private static class ImageDialog extends BaseDialog {

        public ImageDialog(String title, TextureRegion region) {
            super(title);
            shown(() -> {
                cont.image(region);
            });
            addCloseButton();
        }
    }

    public SpriteDialog() {
        super("@sprites");

        shouldPause = true;
        addCloseButton();
        shown(this::rebuild);
        onResize(this::rebuild);

        all.margin(20).marginTop(0f);

        cont.table(s -> {
            s.image(Icon.zoom).padRight(8);
            search = s.field(null, text -> rebuild()).growX().get();
            search.setMessageText("@players.search");
        }).fillX().padBottom(4).row();
        cont.table(s -> {
            s.image(Icon.zoom).padRight(8);
            columns = s.field("10", text -> rebuild()).growX().get();
            columns.setMessageText("Columnds");
        }).fillX().padBottom(4).row();

        cont.pane(all).scrollX(true);
    }

    int i, c;

    void rebuild() {
        all.clear();
        var text = search.getText();

        var origin = arc.Core.atlas.getRegionMap();
        var keys = new Seq<String>(false, origin.size);
        for (var key : origin.keys())
            keys.add(key);
        keys.sort();
        i = 0;
        c = 0;
        try {
            c = Integer.parseUnsignedInt(columns.getText());
        } catch (Throwable lol) {
            arc.util.Log.err(lol);
        }
        if (c < 1)
            c = 1;
        keys.each((String name) -> {
            if (!name.contains(text))
                return;
            var region = origin.get(name);
            var cell = all.image(region).size(64);
            var img = cell.get();
            img.addListener(new Tooltip(i -> i.background(Tex.button).add(name)));
            img.clicked(() -> new ImageDialog(name, region).show());

            if ((++i % c) == 0)
                all.row();
        });

        if (all.getChildren().isEmpty())
            all.add("@none.found");
    }
}
