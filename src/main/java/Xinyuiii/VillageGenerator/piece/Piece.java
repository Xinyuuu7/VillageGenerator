package Xinyuiii.VillageGenerator.piece;

import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.block.BlockBox;
import com.seedfinding.mccore.util.block.BlockDirection;
import com.seedfinding.mccore.util.block.BlockMirror;
import com.seedfinding.mccore.util.block.BlockRotation;
import com.seedfinding.mccore.util.data.Pair;
import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mcfeature.loot.item.ItemStack;
import com.seedfinding.mcterrain.terrain.OverworldTerrainGenerator;

import java.util.List;

public abstract class Piece {
    public BlockBox box;
    public BlockDirection direction;
    public BlockMirror mirror;
    public BlockRotation rotation;
    public int pieceID;
    public Pair<BPos,List<ItemStack>> chest = null;

    public Piece(int pieceID) {
        this.pieceID = pieceID;
    }

    public void buildComponent(Piece pieceIn, List<Piece> listIn, ChunkRand rand) {}

    public abstract boolean addComponentParts(OverworldTerrainGenerator otg, ChunkRand rand, BlockBox box);

    public static Piece findIntersecting(List<Piece> listIn, BlockBox boundingboxIn) {
        for (Piece piece : listIn) {
            if (piece.box != null && piece.box.intersects(boundingboxIn)) {
                return piece;
            }
        }
        return null;
    }

    public int getXWithOffset(int x, int z) {
        BlockDirection direction = this.direction;
        if (direction == null) {
            return x;
        }
        else {
            return switch (direction) {
                case NORTH, SOUTH -> this.box.minX + x;
                case WEST -> this.box.maxX - z;
                case EAST -> this.box.minX + z;
                default -> x;
            };
        }
    }

    public int getYWithOffset(int y) {
        return this.direction == null ? y : y + this.box.minY;
    }

    public int getZWithOffset(int x, int z) {
        BlockDirection direction = this.direction;
        if (direction == null) {
            return z;
        }
        else {
            return switch (direction) {
                case NORTH -> this.box.maxZ - z;
                case SOUTH -> this.box.minZ + z;
                case WEST, EAST -> this.box.minZ + x;
                default -> z;
            };
        }
    }

    public void setCoordBaseMode(BlockDirection direction) {
        this.direction = direction;
        if (direction == null) {
            this.rotation = BlockRotation.NONE;
            this.mirror = BlockMirror.NONE;
        }
        else {
            switch (direction) {
                case SOUTH:
                    this.mirror = BlockMirror.LEFT_RIGHT;
                    this.rotation = BlockRotation.NONE;
                    break;
                case WEST:
                    this.mirror = BlockMirror.LEFT_RIGHT;
                    this.rotation = BlockRotation.CLOCKWISE_90;
                    break;
                case EAST:
                    this.mirror = BlockMirror.NONE;
                    this.rotation = BlockRotation.CLOCKWISE_90;
                    break;
                default:
                    this.mirror = BlockMirror.NONE;
                    this.rotation = BlockRotation.NONE;
            }
        }
    }

    public String getName() {
        String name = getClass().getName();
        int start = name.indexOf("piece.") + "piece.".length();
        return name.substring(start);
    }
}
