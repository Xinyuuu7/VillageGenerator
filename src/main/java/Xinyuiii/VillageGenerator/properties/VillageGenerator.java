package Xinyuiii.VillageGenerator.properties;

import Xinyuiii.VillageGenerator.piece.*;
import Xinyuiii.VillageGenerator.reecriture.Util;
import com.seedfinding.mcbiome.biome.Biome;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.block.BlockBox;
import com.seedfinding.mccore.util.block.BlockDirection;
import com.seedfinding.mccore.util.data.Pair;
import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mccore.version.MCVersion;
import com.seedfinding.mccore.version.UnsupportedVersion;
import com.seedfinding.mcfeature.loot.item.ItemStack;
import com.seedfinding.mcterrain.terrain.OverworldTerrainGenerator;

import java.util.ArrayList;
import java.util.List;

public class VillageGenerator {
    private List<Piece> pieces;
    private BlockBox structureBox;
    private final MCVersion version;

    VillageGenerator(MCVersion version) {
        if (version.isNewerOrEqualTo(MCVersion.v1_14)) {
            throw new UnsupportedVersion(version, " village");
        }
        this.version = version;
    }

    public static List<PieceWeight> getStructureVillageWeightedPieceList(ChunkRand rand, int size) {
        List<PieceWeight> list = new ArrayList<>();
        list.add(new PieceWeight(House4Garden.class, 4, Util.getInt(rand, 2 + size, 4 + size * 2)));
        list.add(new PieceWeight(Church.class, 20, Util.getInt(rand, size, 1 + size)));
        list.add(new PieceWeight(House1.class, 20, Util.getInt(rand, size, 2 + size)));
        list.add(new PieceWeight(WoodHut.class, 3, Util.getInt(rand, 2 + size, 5 + size * 3)));
        list.add(new PieceWeight(Hall.class, 15, Util.getInt(rand, size, 2 + size)));
        list.add(new PieceWeight(Field1.class, 3, Util.getInt(rand, 1 + size, 4 + size)));
        list.add(new PieceWeight(Field2.class, 3, Util.getInt(rand, 2 + size, 4 + size * 2)));
        list.add(new PieceWeight(House2.class, 15, Util.getInt(rand, 0, 1 + size)));
        list.add(new PieceWeight(House3.class, 8, Util.getInt(rand, size, 3 + size * 2)));
        list.removeIf(pieceWeight -> (pieceWeight).piecesLimit == 0);
        return list;
    }

    public static int updatePieceWeight(List<PieceWeight> list) {
        boolean flag = false;
        int i = 0;
        for (PieceWeight structurevillagepieces$pieceweight : list) {
            if (structurevillagepieces$pieceweight.piecesLimit > 0 && structurevillagepieces$pieceweight.piecesSpawned < structurevillagepieces$pieceweight.piecesLimit) {
                flag = true;
            }
            i += structurevillagepieces$pieceweight.pieceWeight;
        }
        return flag ? i : -1;
    }

    public static Village findAndCreateComponentFactory(Start start, PieceWeight pieceWeight, List<Piece> pieces, ChunkRand rand, int minX, int minY, int minZ, BlockDirection direction, int pieceID) {
        Class<? extends Village> oclass = pieceWeight.pieceClass;
        Village piece = null;
        if (oclass == House4Garden.class) {
            piece = House4Garden.createPiece(start, pieces, rand, minX, minY, minZ, direction, pieceID);
        } else if (oclass == Church.class) {
            piece = Church.createPiece(start, pieces, rand, minX, minY, minZ, direction, pieceID);
        } else if (oclass == House1.class) {
            piece = House1.createPiece(start, pieces, rand, minX, minY, minZ, direction, pieceID);
        } else if (oclass == WoodHut.class) {
            piece = WoodHut.createPiece(start, pieces, rand, minX, minY, minZ, direction, pieceID);
        } else if (oclass == Hall.class) {
            piece = Hall.createPiece(start, pieces, rand, minX, minY, minZ, direction, pieceID);
        } else if (oclass == Field1.class) {
            piece = Field1.createPiece(start, pieces, rand, minX, minY, minZ, direction, pieceID);
        } else if (oclass == Field2.class) {
            piece = Field2.createPiece(start, pieces, rand, minX, minY, minZ, direction, pieceID);
        } else if (oclass == House2.class) {
            piece = House2.createPiece(start, pieces, rand, minX, minY, minZ, direction, pieceID);
        } else if (oclass == House3.class) {
            piece = House3.createPiece(start, pieces, rand, minX, minY, minZ, direction, pieceID);
        }
        return piece;
    }

    public static Village generateComponent(Start start, List<Piece> pieces, ChunkRand rand, int minX, int minY, int minZ, BlockDirection direction, int pieceID) {
        int i = updatePieceWeight(start.pieceWeightList);
        if (i <= 0) {
            return null;
        } else {
            int j = 0;
            while (j < 5) {
                j++;
                int k = rand.nextInt(i);
                for (PieceWeight pieceWeight : start.pieceWeightList) {
                    k -= pieceWeight.pieceWeight;
                    if (k < 0) {
                        if (!pieceWeight.canSpawnMoreVillagePiecesOfType(pieceID) || pieceWeight == start.pieceWeight && start.pieceWeightList.size() > 1) {
                            break;
                        }
                        Village piece = findAndCreateComponentFactory(start, pieceWeight, pieces, rand, minX, minY, minZ, direction, pieceID);
                        if (piece != null) {
                            ++pieceWeight.piecesSpawned;
                            start.pieceWeight = pieceWeight;
                            if (!pieceWeight.canSpawnMoreVillagePieces()) {
                                start.pieceWeightList.remove(pieceWeight);
                            }
                            return piece;
                        }
                    }
                }
            }
            BlockBox box = Torch.findPieceBox(start, pieces, rand, minX, minY, minZ, direction);
            if (box != null) {
                return new Torch(start, pieceID, rand, box, direction);
            } else {
                return null;
            }
        }
    }

    public static Piece generateAndAddComponent(Start start, List<Piece> pieces, ChunkRand rand, int minX, int minY, int minZ, BlockDirection direction, int pieceID) {
        if (pieceID > 50) {
            return null;
        } else if (Math.abs(minX - start.box.minX) <= 112 && Math.abs(minZ - start.box.minZ) <= 112) {
            Piece piece = VillageGenerator.generateComponent(start, pieces, rand, minX, minY, minZ, direction, pieceID + 1);
            if (piece != null) {
                pieces.add(piece);
                start.pendingHouses.add(piece);
                return piece;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static void generateAndAddRoadPiece(Start start, List<Piece> pieces, ChunkRand rand, int minX, int minY, int minZ, BlockDirection direction, int pieceID) {
        if (pieceID > 3 + start.terrainType) return;
        if (Math.abs(minX - start.box.minX) <= 112 && Math.abs(minZ - start.box.minZ) <= 112) {
            BlockBox box = Path.findPieceBox(start, pieces, rand, minX, minY, minZ, direction);
            if (box != null && box.minY > 10) {
                Piece piece = new Path(start, pieceID, rand, box, direction);
                pieces.add(piece);
                start.pendingRoads.add(piece);
            }
        }
    }

    public boolean generate(OverworldTerrainGenerator otg, ChunkRand rand, int chunkX, int chunkZ) {
        this.pieces = new ArrayList<>();
        rand.setCarverSeed(otg.getWorldSeed(), chunkX, chunkZ, version);
        Biome biome = otg.getBiomeSource().getBiomeForNoiseGen((chunkX << 4) + 9, 0, (chunkZ << 4) + 9);
        List<PieceWeight> list = getStructureVillageWeightedPieceList(rand, 0);
        Start start = new Start(biome, 0, rand, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, 0);
        this.pieces.add(start);
        start.buildComponent(start, this.pieces, rand);
        List<Piece> list1 = start.pendingRoads;
        List<Piece> list2 = start.pendingHouses;
        while (!list1.isEmpty() || !list2.isEmpty()) {
            if (list1.isEmpty()) {
                int i = rand.nextInt(list2.size());
                Piece piece1 = list2.remove(i);
                piece1.buildComponent(start, this.pieces, rand);
            } else {
                int j = rand.nextInt(list1.size());
                Piece piece2 = list1.remove(j);
                piece2.buildComponent(start, this.pieces, rand);
            }
        }
        this.structureBox = BlockBox.empty();
        for (Piece piece : this.pieces) {
            this.structureBox.encompass(piece.box);
        }
        this.pieces.removeIf(piece -> piece.box.intersects(this.structureBox)
                && !piece.addComponentParts(otg, rand, structureBox));
        return true;
    }

    public List<Pair<BPos, List<ItemStack>>> getChests() {
        List<Pair<BPos, List<ItemStack>>> chests = new ArrayList<>();
        for (Piece piece : this.pieces) {
            if (piece.chest != null) {
                chests.add(piece.chest);
            }
        }
        return chests;
    }

    public BlockBox getStructureBox() {
        return this.structureBox;
    }

    public List<Piece> getPieces() {
        return this.pieces;
    }
}