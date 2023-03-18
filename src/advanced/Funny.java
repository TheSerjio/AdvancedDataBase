package advanced;

import java.lang.reflect.Field;

import mindustry.Vars;
import mindustry.graphics.MenuRenderer;
import mindustry.type.UnitType;
import mindustry.ui.fragments.MenuFragment;

public class Funny {
    private static MenuRenderer mr;
    private static Field field;

    public static void init(){
        try{
            var f = MenuFragment.class.getDeclaredField("renderer");
            f.setAccessible(true);
            mr = (MenuRenderer)f.get(Vars.ui.menufrag);
            field = MenuRenderer.class.getDeclaredField("flyerType");
            field.setAccessible(true);
            f = MenuRenderer.class.getDeclaredField("flyers");
            f.setAccessible(true);
            f.set(mr, 50);
        }
        catch(Throwable uwu){
            arc.util.Log.err(uwu);
        }
    }

    public static void set(UnitType unit){
        try{
            field.set(mr, unit);
        }
        catch (Throwable owo){
            arc.util.Log.err(owo);
        }
    }
}