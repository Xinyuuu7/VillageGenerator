package Xinyuiii.VillageGenerator.piece;

import Xinyuiii.VillageGenerator.properties.VillageGenerator;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.block.BlockBox;
import com.seedfinding.mccore.util.block.BlockDirection;
import com.seedfinding.mcterrain.terrain.OverworldTerrainGenerator;

import java.util.List;

public class Well extends Village {
    public Well(Start start, int pieceID, ChunkRand rand, int x, int z) {
        super(start, pieceID);
        this.setCoordBaseMode(BlockDirection.randomHorizontal(rand));
        this.box = new BlockBox(x, 64, z, x + 5, 78, z + 5);
    }

    public void buildComponent(Piece pieceIn, List<Piece> piecesIn, ChunkRand rand) {
        VillageGenerator.generateAndAddRoadPiece((Start)pieceIn, piecesIn, rand, this.box.minX - 1, this.box.maxY - 4, this.box.minZ + 1, BlockDirection.WEST, this.pieceID);
        VillageGenerator.generateAndAddRoadPiece((Start)pieceIn, piecesIn, rand, this.box.maxX + 1, this.box.maxY - 4, this.box.minZ + 1, BlockDirection.EAST, this.pieceID);
        VillageGenerator.generateAndAddRoadPiece((Start)pieceIn, piecesIn, rand, this.box.minX + 1, this.box.maxY - 4, this.box.minZ - 1, BlockDirection.NORTH, this.pieceID);
        VillageGenerator.generateAndAddRoadPiece((Start)pieceIn, piecesIn, rand, this.box.minX + 1, this.box.maxY - 4, this.box.maxZ + 1, BlockDirection.SOUTH, this.pieceID);
    }

    public boolean addComponentParts(OverworldTerrainGenerator otg, ChunkRand rand, BlockBox box) {
        if (this.averageGroundLevel < 0) {
            this.averageGroundLevel = this.getAverageGroundLevel(otg, box);
            if (this.averageGroundLevel < 0) {
                return true;
            }
            this.box.offset(0, this.averageGroundLevel - this.box.maxY + 3, 0);
        }
        return true;
    }
}