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
    public ScreenShotContent() {
        super("screen-shot", Icon.fileImage);
    }
    
    @Override
    public void checkStats() {
        super.checkStats();
        stats.add(Intelligence.sList, (Table t) -> {
            t.label(() -> "Take map screenshot.\n[scarlet]Warning: high chance of out of memory error.[]\nPixels per tile: ");
            t.row();
            for(int ppt = 1; ppt <= 32; ppt++){
                var i = ppt;
                t.button(Integer.toString(i), () -> takeMapScreenshot(i));
                t.row();
            }
        });
    }

    public static void takeMapScreenshot(int pixelsPerTile){
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
            for(int i = 0; i < lines.length; i += 4){
                lines[i + 3] = (byte)255;
            }
            Pixmap fullPixmap = new Pixmap(w, h);
            Buffers.copy(lines, 0, fullPixmap.pixels, lines.length);
            Fi file = screenshotDirectory.child("screenshot-" + Time.millis() + ".png");
            PixmapIO.writePng(file, fullPixmap);
            fullPixmap.dispose();
            arc.Core.app.post(() -> ui.showInfoFade(Core.bundle.format("screenshot", file.toString())));
        });
    }
}