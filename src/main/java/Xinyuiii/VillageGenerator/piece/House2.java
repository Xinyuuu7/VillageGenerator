package Xinyuiii.VillageGenerator.piece;

import Xinyuiii.VillageGenerator.reecriture.OldLootTables;
import Xinyuiii.VillageGenerator.reecriture.Util;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.block.BlockBox;
import com.seedfinding.mccore.util.block.BlockDirection;
import com.seedfinding.mccore.util.data.Pair;
import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mccore.version.MCVersion;
import com.seedfinding.mcfeature.loot.LootContext;
import com.seedfinding.mcfeature.loot.item.ItemStack;
import com.seedfinding.mcterrain.terrain.OverworldTerrainGenerator;

import java.util.List;

public class House2 extends Village {

    public House2(Start start, int pieceID, ChunkRand rand, BlockBox box, BlockDirection direction) {
        super(start, pieceID);
        this.setCoordBaseMode(direction);
        this.box = box;
    }

    public static House2 createPiece(Start start, List<Piece> pieces, ChunkRand rand, int minX, int minY, int minZ, BlockDirection direction, int pieceID) {
        BlockBox box = Util.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 10, 6, 7, direction);
        return canVillageGoDeeper(box) && Piece.findIntersecting(pieces, box) == null ? new House2(start, pieceID, rand, box, direction) : null;
    }

    public boolean addComponentParts(OverworldTerrainGenerator otg, ChunkRand rand, BlockBox box) {
        if (this.averageGroundLevel < 0) {
            this.averageGroundLevel = this.getAverageGroundLevel(otg, box);
            if (this.averageGroundLevel < 0) {
                return true;
            }
            this.box.offset(0, this.averageGroundLevel - this.box.maxY + 5, 0);
        }
        BPos chestPos = new BPos(this.getXWithOffset(5, 5), this.getYWithOffset(1), this.getZWithOffset(5, 5));
        if (box.contains(chestPos)) {
            int chunkX = chestPos.getX() >> 4;
            int chunkZ = chestPos.getZ() >> 4;
            MCVersion version = otg.getVersion();
            if (version.isNewerOrEqualTo(MCVersion.v1_13)) {
                rand.setDecoratorSeed(otg.getWorldSeed(), chunkX << 4, chunkZ << 4, 30000, version);
            } else {
                rand.setPopulationSeed(otg.getWorldSeed(), chunkX, chunkZ, version);
            }
            LootContext lootContext = new LootContext(rand.nextLong(), version);
            List<ItemStack> items = OldLootTables.VILLAGE_BLACKSMITH_CHEST.generate(lootContext);
            this.chest = new Pair<>(chestPos, items);
        }
        this.spawnVillagers(box, 7, 1, 1, 1);
        return true;
    }
}