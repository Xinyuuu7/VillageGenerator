package Xinyuiii.VillageGenerator.piece;

import Xinyuiii.VillageGenerator.reecriture.Util;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.block.BlockBox;
import com.seedfinding.mccore.util.block.BlockDirection;
import com.seedfinding.mcterrain.terrain.OverworldTerrainGenerator;

import java.util.List;

public class House3 extends Village {
    public House3(Start start, int pieceID, ChunkRand rand, BlockBox box, BlockDirection direction) {
        super(start, pieceID);
        this.setCoordBaseMode(direction);
        this.box = box;
    }

    public static House3 createPiece(Start start, List<Piece> pieces, ChunkRand rand, int minX, int minY, int minZ, BlockDirection direction, int pieceID) {
        BlockBox box = Util.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 9, 7, 12, direction);
        return canVillageGoDeeper(box) && Piece.findIntersecting(pieces, box) == null ? new House3(start, pieceID, rand, box, direction) : null;
    }

    public boolean addComponentParts(OverworldTerrainGenerator otg, ChunkRand rand, BlockBox box) {
        if (this.averageGroundLevel < 0) {
            this.averageGroundLevel = this.getAverageGroundLevel(otg, box);
            if (this.averageGroundLevel < 0) {
                return true;
            }
            this.box.offset(0, this.averageGroundLevel - this.box.maxY + 6, 0);
        }
        // 1 door not random
        this.spawnVillagers(box, 4, 1, 2, 2);
        return true;
    }
}