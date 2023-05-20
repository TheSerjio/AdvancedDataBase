package advanced;

import static mindustry.Vars.*;
import static arc.Core.*;

import arc.Core;
import arc.files.Fi;
import arc.graphics.*;
import arc.graphics.g2d.Draw;
import arc.graphics.gl.FrameBuffer;
import arc.scene.ui.layout.Table;
import arc.util.*;
import mindustry.gen.Icon;

public class ScreenShotContent extends WeirdContent {

    int pixelsPerTile = 8;

    public ScreenShotContent() {
        super("screen-shot", Icon.fileImage);
    }

    @Override
    public void checkStats() {
        super.checkStats();
        stats.add(Intelligence.sList, (Table t) -> {
            t.row();
            t.label(() -> bundle.format("screenshot.ppt", pixelsPerTile)).growX();
            t.row();
            t.slider(1, 64, 1, pixelsPerTile, f -> pixelsPerTile = (int) f).fillX();
            t.row();
            t.label(() -> {
                var w = world.width();
                var h = world.height();
                var ppt = pixelsPerTile;
                return bundle.format("screenshot.size", w, h, w * ppt, h * ppt, w * h * ppt * ppt);
            });
            t.row();
            t.button("@screenshot.normal", () -> takeMapScreenshot(true)).growX();
            t.row();
            t.button("@screenshot.weird", () -> takeMapScreenshot(false)).growX();
            t.row();
        });
    }

    void takeMapScreenshot(boolean normal) {
        int w = world.width() * pixelsPerTile, h = world.height() * pixelsPerTile;

        FrameBuffer buffer = new FrameBuffer(w, h);

        renderer.drawWeather = false;
        float vpW = camera.width, vpH = camera.height, px = camera.position.x, py = camera.position.y;

        disableUI = true;
        camera.width = world.width() * 8;
        camera.height = world.height() * 8;
        camera.position.x = camera.width / 2f + 4;
        camera.position.y = camera.height / 2f + 4;
        buffer.begin();
        renderer.draw();
        Draw.flush();
        byte[] lines = ScreenUtils.getFrameBufferPixels(0, 0, w, h, true);
        buffer.end();
        disableUI = false;
        camera.width = vpW;
        camera.height = vpH;
        camera.position.set(px, py);
        renderer.drawWeather = true;
        buffer.dispose();

        Threads.thread(() -> {
            if (normal) {
                for (int i = 0; i < lines.length; i += 4) {
                    lines[i + 3] = (byte) 255;
                }
            }
            Pixmap fullPixmap = new Pixmap(w, h);
            Buffers.copy(lines, 0, fullPixmap.pixels, lines.length);
            Fi file = screenshotDirectory.child(state.map.name() + "-" + Time.millis() + ".png");
            PixmapIO.writePng(file, fullPixmap);
            fullPixmap.dispose();
            arc.Core.app.post(() -> ui.showInfoFade(Core.bundle.format("screenshot", file.toString())));
        });
    }
}