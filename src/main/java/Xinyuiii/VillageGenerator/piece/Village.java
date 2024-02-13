package Xinyuiii.VillageGenerator.piece;

import Xinyuiii.VillageGenerator.properties.VillageGenerator;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.block.BlockBox;
import com.seedfinding.mccore.util.block.BlockDirection;
import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mcterrain.terrain.OverworldTerrainGenerator;

import java.util.List;

public class Village extends Piece {
    public boolean addComponentParts(OverworldTerrainGenerator otg, ChunkRand rand, BlockBox box) {
        return true;
    }

    public enum Type {
        OAK(0),
        SANDSTONE(1),
        ACACIA(2),
        SPRUCE(3);

        final int type;

        Type(int type) {
            this.type = type;
        }
    }

    public int averageGroundLevel = -1;
    public int villagersSpawned;
    public Type villageType;
    public boolean isZombieVillage;


    public Village(Start start, int pieceID) {
        super(pieceID);
        if (start != null) {
            this.villageType = start.villageType;
            this.isZombieVillage = start.isZombieVillage;
        }
    }

    public Piece getNextComponentNN(Start start, List<Piece> pieces, ChunkRand rand, int a, int b) {
        BlockDirection direction = this.direction;
        if (direction != null) {
            switch (direction) {
                case NORTH, SOUTH:
                default:
                    return VillageGenerator.generateAndAddComponent(start, pieces, rand, this.box.minX - 1, this.box.minY + a, this.box.minZ + b, BlockDirection.WEST, this.pieceID);
                case WEST, EAST:
                    return VillageGenerator.generateAndAddComponent(start, pieces, rand, this.box.minX + b, this.box.minY + a, this.box.minZ - 1, BlockDirection.NORTH, this.pieceID);
            }
        }
        else {
            return null;
        }
    }

    public Piece getNextComponentPP(Start start, List<Piece> pieces, ChunkRand rand, int a, int b) {
        BlockDirection direction = this.direction;
        if (direction != null) {
            switch (direction) {
                case NORTH, SOUTH:
                default:
                    return VillageGenerator.generateAndAddComponent(start, pieces, rand, this.box.maxX + 1, this.box.minY + a, this.box.minZ + b, BlockDirection.EAST, this.pieceID);
                case WEST, EAST:
                    return VillageGenerator.generateAndAddComponent(start, pieces, rand, this.box.minX + b, this.box.minY + a, this.box.maxZ + 1, BlockDirection.SOUTH, this.pieceID);
            }
        }
        else {
            return null;
        }
    }

    public int getAverageGroundLevel(OverworldTerrainGenerator otg, BlockBox box) {
        int i = 0;
        int j = 0;
        BPos pos;
        for (int k = this.box.minZ; k <= this.box.maxZ; k++) {
            for (int l = this.box.minX; l <= this.box.maxX; l++) {
                pos = new BPos(l, 64, k);
                if (box.contains(pos)) {
                    i += otg.getHeightInGround(l,k);
                    j++;
                }
            }
        }
        if (j == 0) {
            return -1;
        }
        else {
            return i / j;
        }
    }

    public static boolean canVillageGoDeeper(BlockBox box) {
        return box != null && box.minY > 10;
    }

    public void spawnVillagers(BlockBox box, int x, int y, int z, int count) {
        if (this.villagersSpawned < count) {
            for (int i = this.villagersSpawned; i < count; i++) {
                int j = this.getXWithOffset(x + i, z);
                int k = this.getYWithOffset(y);
                int l = this.getZWithOffset(x + i, z);
                if (!box.contains(new BPos(j, k, l))) {
                    break;
                }
                this.villagersSpawned++;
            }
        }
    }

    public void setVillageType(Type type) {
        this.villageType = type;
    }
}