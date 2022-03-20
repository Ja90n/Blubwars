package com.blub.blubwars.utils;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.instance.Arena;
import com.blub.blubwars.instance.Cuboid;
import com.blub.blubwars.manager.ConfigManager;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.*;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;

import java.io.*;

public class ArenaSchematics {

    private Arena arena;
    private Blubwars blubwars;

    public ArenaSchematics(Arena arena, Blubwars blubwars){
        this.arena = arena;
        this.blubwars = blubwars;
    }

    public void saveSchematic(){
        File file = new File(blubwars.getDataFolder().getAbsolutePath() + "/" + arena.getName() + ".schem");
        if (!file.exists()){
            CuboidRegion region = new CuboidRegion(BukkitAdapter.adapt(arena.getWorld()),
                    BukkitAdapter.asBlockVector(ConfigManager.getPosition(arena,1)),
                    BukkitAdapter.asBlockVector(ConfigManager.getPosition(arena,2)));
            BlockArrayClipboard clipboard = new BlockArrayClipboard(region);

            EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(region.getWorld(), -1);

            ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(editSession, region, clipboard, region.getMinimumPoint());
            forwardExtentCopy.setCopyingEntities(true);
            Operations.complete(forwardExtentCopy);

            try (ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(file))) {
                writer.write(clipboard);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void pasteSchematic(){
        File file = new File(blubwars.getDataFolder().getAbsolutePath() + "/" + arena.getName() + ".schem");
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            Clipboard clipboard = reader.read();
            Cuboid cuboid = new Cuboid(ConfigManager.getPosition(arena,1),ConfigManager.getPosition(arena,2));
            BlockVector3 blockVector3 = new BlockVector3() {
                @Override
                public int getX() {
                    return (int) cuboid.getCenter().getX();
                }
                @Override
                public int getY() {
                   return cuboid.getLowerY();
                }
                @Override
                public int getZ() {
                    return (int) cuboid.getCenter().getZ();
                }
            };
            try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(
                    BukkitAdapter.adapt(arena.getWorld()), -1)) {
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(blockVector3)
                        .ignoreAirBlocks(false)
                        .build();
                Operations.complete(operation);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}