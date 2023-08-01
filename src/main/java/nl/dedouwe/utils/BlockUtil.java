package nl.dedouwe.utils;

import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.data.type.NoteBlock;

public class BlockUtil {
    public static void CreateCustomBlock(Location l, Instrument i, Note n) {
        l.getBlock().setType(Material.NOTE_BLOCK);
        NoteBlock b = (NoteBlock)l.getBlock().getBlockData();
        b.setInstrument(i);
        b.setNote(n);
        l.getBlock().setBlockData(b);
    }
}
