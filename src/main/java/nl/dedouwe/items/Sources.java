package nl.dedouwe.items;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public enum Sources {
    Nature,
    Light,
    Dark,
    Fire,
    Ground,
    Air,
    Water,
    Unobtainable;

    public int GetCustomModelData() {
        return  this == Sources.Nature ? 48360 : 
                this == Sources.Dark ? 48361 : 
                this == Sources.Light ? 48362 : 
                this == Sources.Fire ? 48363 : 
                this == Sources.Ground ? 48364 : 
                this == Sources.Air ? 48365 :
                this == Sources.Water ? 48366 :
                this == Sources.Unobtainable ? 48367 : null;
    }

    public TextColor GetColor() {
        return  this == Sources.Nature ? NamedTextColor.GREEN : 
                this == Sources.Dark ? NamedTextColor.BLACK : 
                this == Sources.Light ? NamedTextColor.YELLOW : 
                this == Sources.Fire ? NamedTextColor.RED : 
                this == Sources.Ground ? TextColor.color(153,97,0) : 
                this == Sources.Air ? NamedTextColor.WHITE :
                this == Sources.Water ? NamedTextColor.BLUE :
                this == Sources.Unobtainable ? NamedTextColor.DARK_PURPLE : NamedTextColor.GRAY;
    }
}
