package advanced;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.graphics.g2d.TextureAtlas.AtlasRegion;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.ai.types.AssemblerAI;
import mindustry.content.*;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.part.DrawPart;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;
import mindustry.type.Weapon;
import mindustry.type.Weather;
import mindustry.world.blocks.Attributes;
import mindustry.world.draw.DrawBlock;
import mindustry.world.meta.*;
import static mindustry.Vars.*;

public class Intelligence {
    static final StatCat category = new StatCat("advanced");
    static final Stat sDps = new Stat("dps", category),
            sEnv = new Stat("env", category),
            sEnvRequired = new Stat("envRequired", category),
            sEnvEnabled = new Stat("envEnabled", category),
            sEnvDisabled = new Stat("envDisabled", category),
            sRamDamage = new Stat("ramDamage", category),
            sID = new Stat("id", category),
            sSectors = new Stat("sectors", category),
            sAlbedo = new Stat("albedo", category),
            sEffect = new Stat("effect", category),
            sBoil = new Stat("boil", category),
            sHardness = new Stat("hardness", category),
            sCost = new Stat("cost", category),
            sHealthScale = new Stat("healthScale", category),
            sAttributes = new Stat("attributes", category),
            sImages = new Stat("icons", category),
            sListing = new Stat("listing", category);

    public static final Seq<EnvPack> envs = Seq.with(
            new EnvPack(Env.terrestrial, "terrestrial", UnitTypes.dagger.emoji()),
            new EnvPack(Env.space, "space", UnitTypes.flare.emoji()),
            new EnvPack(Env.underwater, "underwater", Liquids.water.emoji()),
            new EnvPack(Env.spores, "spores", Blocks.cultivator.emoji()),
            new EnvPack(Env.scorching, "scorching", StatusEffects.melting.emoji()),
            new EnvPack(Env.groundOil, "groundOil", Blocks.oilExtractor.emoji()),
            new EnvPack(Env.groundWater, "groundWater", Blocks.waterExtractor.emoji()),
            new EnvPack(Env.oxygen, "oxygen", StatusEffects.corroded.emoji()));

    public static final ObjectMap<Attribute, String> attrs = ObjectMap.of(
            Attribute.heat, StatusEffects.melting.emoji(),
            Attribute.spores, Items.sporePod.emoji(),
            Attribute.water, Liquids.water.emoji(),
            Attribute.oil, Liquids.oil.emoji(),
            Attribute.light, Blocks.solarPanel.emoji(),
            Attribute.sand, Items.sand.emoji(),
            Attribute.steam, Blocks.turbineCondenser.emoji());

    static final StringBuilder builder = new StringBuilder();

    static void addIcons(String path, Table table, Object obj, int depth, Object last) {
        if (depth++ > 5)
            return;
        if (obj == null || obj == last)
            return;
        for (var f : obj.getClass().getFields()) {
            Object o;
            if (obj instanceof StatusEffect st)
                if (f.getName() == "affinities" || f.getName() == "opposites")
                    continue;
            try {
                o = f.get(obj);
            } catch (Throwable __) {
                continue;
            }
            if (o == null)
                continue;
            builder.setLength(0);
            builder.append(path);
            builder.append('.');
            builder.append(f.getName());
            if (o instanceof BulletType b) {
                table.button(builder.toString(), () -> {
                    Main.bullet.execute(b);
                }).height(50).width(500);
                table.row();
            }
            if (o instanceof TextureRegion tr) {
                if (tr == arc.Core.atlas.find("error"))
                    continue;
                var str = builder.toString();
                table.label(() -> str);
                table.image(tr);
                if (tr instanceof AtlasRegion ar)
                    if (ar.name != null)
                        table.label(() -> ar.name);
                table.row();
            } else if (o instanceof Iterable i) {
                int counter = 0;
                for (var q : i)
                    addIcons(path + "." + f.getName() + "[" + counter++ + "]", table, q, depth, obj);// TODO use builder
            } else if (o instanceof Object[] objs) {
                for (int i = 0; i < objs.length; i++)
                    addIcons(path + "." + f.getName() + "[" + i + "]", table, objs[i], depth, obj);
            } else if (o instanceof DrawPart || o instanceof DrawBlock || o instanceof BulletType
                    || o instanceof Weapon)
                addIcons(path + "." + f.getName(), table, o, depth, obj);
        }
    }

    public static void setStats(UnlockableContent q) {
        q.stats.add(sID, q.id);
        q.stats.add(sImages, (Table t) -> {
            t.row();
            addIcons("", t, q, 0, null);
        });
    }

    public static void initStats() {
        Listing.init();

        for (var seq : content.getContentMap())
            for (var c : seq)
                if (c instanceof UnlockableContent u) {
                    setStats(u);
                    u.stats.useCategories = true;
                    u.hideDetails = false;
                }

        for (var weather : content.getContentMap()[ContentType.weather.ordinal()]) {
            var w = (Weather) weather;
            attri(w.stats, w.attrs);
            w.stats.add(sEffect, w.status.localizedName);
            w.stats.add(Stat.targetsAir, w.statusAir);
            w.stats.add(Stat.targetsGround, w.statusGround);
        }

        for (var p : content.planets()) {
            p.stats.add(sSectors, p.sectors.size);
            p.visible = true;
            p.accessible = true;
            p.drawOrbit = true;
            env(p.stats, sEnv, p.defaultEnv, "");
            attri(p.stats, p.defaultAttributes);
        }

        for (var u : content.units()) {
            u.hidden = false;
            u.playerControllable = !(u.aiController.get() instanceof AssemblerAI);// it crashes
            u.createScorch = true;
            u.abilities.each(a -> a.display = true);

            u.stats.add(sDps, u.dpsEstimate);
            if (u.crushDamage > 0)
                u.stats.add(sRamDamage, u.crushDamage * 60, StatUnit.perSecond);
            if (u.legSplashDamage > 0) {
                u.stats.add(sRamDamage, u.legSplashDamage);
                u.stats.add(sRamDamage, u.legSplashRange / 8, StatUnit.blocks);
            }
            u.stats.add(Stat.targetsAir, u.targetAir);
            u.stats.add(Stat.targetsGround, u.targetGround);
            env(u.stats, u.envRequired, u.envEnabled, u.envDisabled);
        }

        StatusEffects.none.color = Pal.lightishGray;
        StatusEffects.invincible.color = Color.magenta;
        for (var q : content.statusEffects()) {
            q.show = true;
            if (q.localizedName.equals(q.name)) {
                var name = q.localizedName;
                q.localizedName = name.substring(0, 1).toUpperCase() + name.substring(1);
            }
        }

        for (var i : content.items()) {
            i.hidden = false;

            i.stats.addPercent(sCost, i.cost);
            i.stats.addPercent(sHealthScale, i.healthScaling);
            i.stats.add(sHardness, i.hardness);
        }

        for (var f : content.liquids()) {
            f.hidden = false;

            f.stats.add(sEffect, f.effect.emoji() + " " + f.effect.localizedName);
            f.stats.addPercent(sBoil, f.boilPoint);
        }

        for (var b : content.blocks()) {
            b.displayFlow = true;
            if (b.buildVisibility == BuildVisibility.debugOnly)
                b.buildVisibility = BuildVisibility.shown;
            if (b.alwaysReplace && b.buildVisibility != BuildVisibility.shown) {
                b.buildVisibility = BuildVisibility.shown;
                b.envEnabled = Env.any;
                b.requirements(mindustry.type.Category.logic, mindustry.type.ItemStack
                        .with(mindustry.content.Items.graphite, 1, mindustry.content.Items.silicon, 1));
            }

            env(b.stats, b.envRequired, b.envEnabled, b.envDisabled);
            b.stats.addPercent(sAlbedo, b.albedo);
            attri(b.stats, b.attributes);
        }
    }

    public static void env(Stats stats, int required, int enabled, int disabled) {
        env(stats, sEnvRequired, required, "[accent]");
        env(stats, sEnvEnabled, enabled, "[white]");
        env(stats, sEnvDisabled, disabled, "[scarlet]");
    }

    public static void env(Stats stats, Stat stat, int env, String metaColor) {
        if (env == Env.none) {
            stats.add(stat, Core.bundle.get("env.none"));
            return;
        }
        if (env == Env.any) {
            stats.add(stat, Core.bundle.get("env.any"));
            return;
        }
        var s = "";
        for (var e : envs)
            if ((env | e.flag) == env)
                s += metaColor + e.name + " | ";
        if (s.length() > 4)
            s = s.substring(0, s.length() - 3);
        stats.add(stat, s);
    }

    public static void attri(Stats stats, Attributes at) {
        var s = "";
        for (var a : Attribute.all) {
            var g = at.get(a);
            if (g != 0) {
                s += arc.Core.bundle.get("attribute." + a.name) + attrs.get(a, "") + ": [accent]";
                if (g == (int) g)
                    s += (int) g;
                else
                    s += g;
                s += "[] | ";
            }
        }
        if (!s.isEmpty())
            stats.add(sAttributes, s.substring(0, s.length() - 3));
    }

    public static final class EnvPack {
        public int flag;
        public String name;

        public EnvPack(int flag, String name, String emoji) {
            this.flag = flag;
            this.name = arc.Core.bundle.get("env." + name) + "[]" + emoji;
        }
    }
}