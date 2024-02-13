package Xinyuiii.VillageGenerator.piece;

import Xinyuiii.VillageGenerator.reecriture.Util;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.block.BlockBox;
import com.seedfinding.mccore.util.block.BlockDirection;
import com.seedfinding.mcterrain.terrain.OverworldTerrainGenerator;

import java.util.List;

public class House4Garden extends Village {
    public boolean isRoofAccessible;

    public House4Garden(Start start, int pieceID, ChunkRand rand, BlockBox box, BlockDirection direction) {
        super(start, pieceID);
        this.setCoordBaseMode(direction);
        this.box = box;
        this.isRoofAccessible = rand.nextBoolean();
    }

    public static House4Garden createPiece(Start start, List<Piece> pieces, ChunkRand rand, int minX, int minY, int minZ, BlockDirection direction, int pieceID) {
        BlockBox box = Util.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 5, 6, 5, direction);
        return Piece.findIntersecting(pieces, box) != null ? null : new House4Garden(start, pieceID, rand, box, direction);
    }

    public boolean addComponentParts(OverworldTerrainGenerator otg, ChunkRand rand, BlockBox box) {
        if (this.averageGroundLevel < 0) {
            this.averageGroundLevel = this.getAverageGroundLevel(otg, box);
            if (this.averageGroundLevel < 0) {
                return true;
            }
            this.box.offset(0, this.averageGroundLevel - this.box.maxY + 5, 0);
        }
        this.spawnVillagers(box, 1, 1, 2, 1);
        return true;
    }
}