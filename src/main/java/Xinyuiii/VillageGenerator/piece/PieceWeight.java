package Xinyuiii.VillageGenerator.piece;

public class PieceWeight {
    public Class <? extends Village> pieceClass;
    public final int pieceWeight;
    public int piecesSpawned;
    public int piecesLimit;

    public PieceWeight(Class <? extends Village > pieceClass, int pieceWeight, int piecesLimit) {
        this.pieceClass = pieceClass;
        this.pieceWeight = pieceWeight;
        this.piecesLimit = piecesLimit;
    }

    public boolean canSpawnMoreVillagePiecesOfType(int pieceID) {
        return this.piecesLimit == 0 || this.piecesSpawned < this.piecesLimit;
    }

    public boolean canSpawnMoreVillagePieces() {
        return this.piecesLimit == 0 || this.piecesSpawned < this.piecesLimit;
    }
}