package Xinyuiii.VillageGenerator.piece;

import Xinyuiii.VillageGenerator.reecriture.Util;
import com.seedfinding.mccore.block.Block;
import com.seedfinding.mccore.block.Blocks;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.block.BlockBox;
import com.seedfinding.mccore.util.block.BlockDirection;
import com.seedfinding.mcterrain.terrain.OverworldTerrainGenerator;

import java.util.List;

public class Field2 extends Village {
    public Field2(Start start, int pieceID, ChunkRand rand, BlockBox box, BlockDirection direction) {
        super(start, pieceID);
        this.setCoordBaseMode(direction);
        this.box = box;
        Block cropTypeA = this.getRandomCropType(rand);
        Block cropTypeB = this.getRandomCropType(rand);
    }

    public static Block getRandomCropType(ChunkRand rand) {
        switch (rand.nextInt(10)) {
            case 0:
            case 1:
                return Blocks.CARROTS;
            case 2:
            case 3:
                return Blocks.POTATOES;
            case 4:
                return Blocks.BEETROOTS;
            default:
                return Blocks.WHEAT;
        }
    }

    public static Field2 createPiece(Start start, List<Piece> pieces, ChunkRand rand, int minX, int minY, int minZ, BlockDirection direction, int pieceID) {
        BlockBox box = Util.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 7, 4, 9, direction);
        return canVillageGoDeeper(box) && Piece.findIntersecting(pieces, box) == null ? new Field2(start, pieceID, rand, box, direction) : null;
    }

    public boolean addComponentParts(OverworldTerrainGenerator otg, ChunkRand rand, BlockBox box) {
        if (this.averageGroundLevel < 0) {
            this.averageGroundLevel = this.getAverageGroundLevel(otg, box);
            if (this.averageGroundLevel < 0) {
                return true;
            }
            this.box.offset(0, this.averageGroundLevel - this.box.maxY + 3, 0);
        }
        // Setting BlockState uses 28 random attempts here
        rand.advance(28);
        return true;
    }
}