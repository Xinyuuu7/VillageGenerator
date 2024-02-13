package Xinyuiii.VillageGenerator.piece;

import Xinyuiii.VillageGenerator.reecriture.Util;
import com.seedfinding.mccore.block.Block;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.block.BlockBox;
import com.seedfinding.mccore.util.block.BlockDirection;
import com.seedfinding.mcterrain.terrain.OverworldTerrainGenerator;

import java.util.List;

public class Field1 extends Village {
    public Field1(Start start, int pieceID, ChunkRand rand, BlockBox box, BlockDirection direction) {
        super(start, pieceID);
        this.setCoordBaseMode(direction);
        this.box = box;
        Block cropTypeA = Field2.getRandomCropType(rand);
        Block cropTypeB = Field2.getRandomCropType(rand);
        Block cropTypeC = Field2.getRandomCropType(rand);
        Block cropTypeD = Field2.getRandomCropType(rand);
    }

    public static Field1 createPiece(Start start, List<Piece> pieces, ChunkRand rand, int minX, int minY, int minZ, BlockDirection direction, int pieceID) {
        BlockBox box = Util.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 13, 4, 9, direction);
        return canVillageGoDeeper(box) && Piece.findIntersecting(pieces, box) == null ? new Field1(start, pieceID, rand, box, direction) : null;
    }

    public boolean addComponentParts(OverworldTerrainGenerator otg, ChunkRand rand, BlockBox box) {
        if (this.averageGroundLevel < 0) {
            this.averageGroundLevel = this.getAverageGroundLevel(otg, box);
            if (this.averageGroundLevel < 0) {
                return true;
            }
            this.box.offset(0, this.averageGroundLevel - this.box.maxY + 3, 0);
        }
        // Setting BlockState uses 56 random attempts here
        rand.advance(56);
        return true;
    }
}