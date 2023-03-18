package advanced;

import mindustry.content.*;
import mindustry.ctype.UnlockableContent;
import mindustry.type.UnitType;
import mindustry.world.meta.Env;

public class Listing {
    public static void init() {

        setIcon(Weathers.rain, StatusEffects.wet);
        setIcon(Weathers.fog, Blocks.illuminator);
        setIcon(Weathers.sandstorm, Items.sand);
        setIcon(Weathers.snow, Blocks.snowBoulder);
        setIcon(Weathers.sporestorm, StatusEffects.sporeSlowed);
        setIcon(Weathers.suspendParticles, Blocks.pebbles);

        setIcon(Planets.sun, Blocks.illuminator);
        setIcon(Planets.erekir, Liquids.slag);
        setIcon(Planets.tantros, Liquids.water);
        setIcon(Planets.serpulo, Liquids.cryofluid);
        setIcon(Planets.gier, Blocks.pebbles);
        setIcon(Planets.notva, Blocks.pebbles);
        setIcon(Planets.verilus, Blocks.pebbles);

        setIcon(SectorPresets.groundZero, Items.copper);
        setIcon(SectorPresets.frozenForest, Items.coal);
        setIcon(SectorPresets.craters, Items.silicon);
        setIcon(SectorPresets.biomassFacility, Items.sporePod);
        setIcon(SectorPresets.ruinousShores, Items.metaglass);
        setIcon(SectorPresets.saltFlats, Blocks.salt);
        setIcon(SectorPresets.windsweptIslands, Items.plastanium);
        setIcon(SectorPresets.stainedMountains, Items.titanium);
        setIcon(SectorPresets.extractionOutpost, Blocks.launchPad);
        setIcon(SectorPresets.coastline, UnitTypes.risso);
        setIcon(SectorPresets.navalFortress, UnitTypes.cyerce);
        setIcon(SectorPresets.fungalPass, UnitTypes.crawler);
        setIcon(SectorPresets.overgrowth, UnitTypes.spiroct);
        setIcon(SectorPresets.tarFields, Liquids.oil);
        setIcon(SectorPresets.impact0078, Blocks.impactReactor);
        setIcon(SectorPresets.nuclearComplex, Items.thorium);
        setIcon(SectorPresets.desolateRift, StatusEffects.boss);
        setIcon(SectorPresets.planetaryTerminal, Blocks.interplanetaryAccelerator);

        setIcon(SectorPresets.onset, Items.beryllium);
        setIcon(SectorPresets.aegis, Items.tungsten);
        setIcon(SectorPresets.lake, UnitTypes.elude);
        setIcon(SectorPresets.intersect, UnitTypes.merui);
        setIcon(SectorPresets.atlas, Liquids.hydrogen);
        setIcon(SectorPresets.split, Blocks.reinforcedPayloadConveyor);
        setIcon(SectorPresets.basin, StatusEffects.blasted);
        setIcon(SectorPresets.marsh, Liquids.arkycite);
        setIcon(SectorPresets.peaks, UnitTypes.avert);
        setIcon(SectorPresets.ravine, Items.surgeAlloy);
        setIcon(SectorPresets.caldera, UnitTypes.manifold);
        setIcon(SectorPresets.stronghold, Items.thorium);
        setIcon(SectorPresets.crevice, Items.carbide);
        setIcon(SectorPresets.siege, Liquids.cyanogen);
        setIcon(SectorPresets.crossroads, StatusEffects.blasted);
        setIcon(SectorPresets.karst, Items.phaseFabric);
        setIcon(SectorPresets.origin, StatusEffects.corroded);

        setIcons(mindustry.Vars.content.unit("quell-missile"), UnitTypes.crawler);
        setIcons(mindustry.Vars.content.unit("disrupt-missile"), UnitTypes.crawler);
        // setIcons(scatheMissile, UnitTypes.renale);
        UnitTypes.crawler.itemCircleRegion = Blocks.router.fullIcon;
        Blocks.purbush.localizedName = Blocks.purbush.localizedName.replace("Pur", "Fur");

        var q = Blocks.graphiticWall;
        q.buildVisibility = SettingsMenu.wall;
        q.envEnabled |= Env.any;
        q.requirements(mindustry.type.Category.production,
                mindustry.type.ItemStack.with(mindustry.content.Items.graphite, 1000));

        q = Blocks.darkMetal;
        q.buildVisibility = SettingsMenu.wall;
        q.envEnabled |= Env.any;
        q.requirements(mindustry.type.Category.defense, mindustry.type.ItemStack
                .with(mindustry.content.Items.surgeAlloy, 1000, mindustry.content.Items.phaseFabric, 200));
    }

    static void setIcon(UnlockableContent target, UnlockableContent source) {
        target.uiIcon = source.uiIcon;
        target.fullIcon = source.fullIcon;
    }

    static void setIcons(UnitType target, UnitType source) {
        target.region = source.region;
        target.cellRegion = source.cellRegion;
        target.previewRegion = source.previewRegion;
        target.shadowRegion = source.shadowRegion;
    }
}