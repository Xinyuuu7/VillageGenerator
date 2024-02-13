package Xinyuiii.VillageGenerator.piece;

import Xinyuiii.VillageGenerator.properties.VillageGenerator;
import Xinyuiii.VillageGenerator.reecriture.Util;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.block.BlockBox;
import com.seedfinding.mccore.util.block.BlockDirection;
import com.seedfinding.mcterrain.terrain.OverworldTerrainGenerator;

import java.util.List;

public class Path extends Road {
    private int length;

    public Path(Start start, int pieceID, ChunkRand rand, BlockBox box, BlockDirection direction) {
        super(start, pieceID);
        this.setCoordBaseMode(direction);
        this.box = box;
        this.length = Math.max(box.getXSpan(), box.getZSpan());
    }

    public void buildComponent(Piece pieceIn, List<Piece> piecesIn, ChunkRand rand) {
        boolean flag = false;
        for (int i = rand.nextInt(5); i < this.length - 8; i += 2 + rand.nextInt(5)) {
            Piece piece1 = this.getNextComponentNN((Start)pieceIn, piecesIn, rand, 0, i);
            if (piece1 != null) {
                i += Math.max(piece1.box.getXSpan(), piece1.box.getZSpan());
                flag = true;
            }
        }
        for (int j = rand.nextInt(5); j < this.length - 8; j += 2 + rand.nextInt(5)) {
            Piece piece2 = this.getNextComponentPP((Start)pieceIn, piecesIn, rand, 0, j);
            if (piece2 != null) {
                j += Math.max(piece2.box.getXSpan(), piece2.box.getZSpan());
                flag = true;
            }
        }
        BlockDirection direction = this.direction;
        if (flag && rand.nextInt(3) > 0 && direction != null) {
            switch (direction) {
                case NORTH:
                default:
                    VillageGenerator.generateAndAddRoadPiece((Start)pieceIn, piecesIn, rand, this.box.minX - 1, this.box.minY, this.box.minZ, BlockDirection.WEST, this.pieceID);
                    break;
                case SOUTH:
                    VillageGenerator.generateAndAddRoadPiece((Start)pieceIn, piecesIn, rand, this.box.minX - 1, this.box.minY, this.box.maxZ - 2, BlockDirection.WEST, this.pieceID);
                    break;
                case WEST:
                    VillageGenerator.generateAndAddRoadPiece((Start)pieceIn, piecesIn, rand, this.box.minX, this.box.minY, this.box.minZ - 1, BlockDirection.NORTH, this.pieceID);
                    break;
                case EAST:
                    VillageGenerator.generateAndAddRoadPiece((Start)pieceIn, piecesIn, rand, this.box.maxX - 2, this.box.minY, this.box.minZ - 1, BlockDirection.NORTH, this.pieceID);
            }
        }
        if (flag && rand.nextInt(3) > 0 && direction != null) {
            switch (direction) {
                case NORTH:
                default:
                    VillageGenerator.generateAndAddRoadPiece((Start)pieceIn, piecesIn, rand, this.box.maxX + 1, this.box.minY, this.box.minZ, BlockDirection.EAST, this.pieceID);
                    break;
                case SOUTH:
                    VillageGenerator.generateAndAddRoadPiece((Start)pieceIn, piecesIn, rand, this.box.maxX + 1, this.box.minY, this.box.maxZ - 2, BlockDirection.EAST, this.pieceID);
                    break;
                case WEST:
                    VillageGenerator.generateAndAddRoadPiece((Start)pieceIn, piecesIn, rand, this.box.minX, this.box.minY, this.box.maxZ + 1, BlockDirection.SOUTH, this.pieceID);
                    break;
                case EAST:
                    VillageGenerator.generateAndAddRoadPiece((Start)pieceIn, piecesIn, rand, this.box.maxX - 2, this.box.minY, this.box.maxZ + 1, BlockDirection.SOUTH, this.pieceID);
            }
        }
    }

    public static BlockBox findPieceBox(Start start, List<Piece> pieces, ChunkRand rand, int minX, int minY, int minZ, BlockDirection direction) {
        for (int i = 7 * Util.getInt(rand, 3, 5); i >= 7; i -= 7) {
            BlockBox box = Util.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 3, 3, i, direction);
            if (Piece.findIntersecting(pieces, box) == null) {
                return box;
            }
        }
        return null;
    }

    public boolean addComponentParts(OverworldTerrainGenerator otg, ChunkRand rand, BlockBox box) {
        return true;
    }
}