package advanced;

import mindustry.content.*;
import mindustry.ctype.UnlockableContent;
import mindustry.type.UnitType;

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