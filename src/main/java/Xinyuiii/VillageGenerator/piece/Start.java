package Xinyuiii.VillageGenerator.piece;

import com.seedfinding.mcbiome.biome.Biome;
import com.seedfinding.mcbiome.biome.Biomes;
import com.seedfinding.mccore.rand.ChunkRand;

import java.util.ArrayList;
import java.util.List;

public class Start extends Well {
    public int terrainType;
    public PieceWeight pieceWeight;
    public List<PieceWeight> pieceWeightList;
    public List<Piece> pendingHouses = new ArrayList<>();
    public List<Piece> pendingRoads = new ArrayList<>();

    public Start(Biome biome, int pieceID, ChunkRand rand, int x, int z, List<PieceWeight> pieceWeightList, int terrainType) {
        super(null, 0, rand, x, z);
        this.pieceWeightList = pieceWeightList;
        this.terrainType = terrainType;
        if (Biomes.PLAINS.equals(biome)) {
            this.villageType = Type.OAK;
        }
        if (Biomes.DESERT.equals(biome)) {
            this.villageType = Type.SANDSTONE;
        }
        if (Biomes.SAVANNA.equals(biome)) {
            this.villageType = Type.ACACIA;
        }
        if (Biomes.TAIGA.equals(biome)) {
            this.villageType = Type.SPRUCE;
        }
        this.setVillageType(this.villageType);
        this.isZombieVillage = rand.nextInt(50) == 0;
    }
}