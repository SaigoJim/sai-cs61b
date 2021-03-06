package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 80;

    private static final long SEED = 273;
    private static final Random RANDOM = new Random(SEED);

    private static class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /** Picks a RANDOM tile. */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0: return Tileset.TREE;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.PLAYER;
            default: return Tileset.MOUNTAIN;
        }
    }

    /** Initialize tiles with setting background nothing. */
    public static void initializeTiles(TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    /** Add a hexagon of side length Length to a given position (X, Y) in the Tiles. */
    public static void addHexagon(TETile[][] tiles, Position p, int length, TETile tile) {
        for (int rowNum = 0; rowNum < 2 * length; rowNum += 1) {
            int startX = p.x + xPointOffSet(length, rowNum);
            int startY = p.y + rowNum;
            int rowLength = length + rowLengthOffSet(length, rowNum);
            addRow(tiles, new Position(startX, startY), rowLength, tile);
        }
    }

    private static int rowLengthOffSet(int length, int rowNum) {
        if (rowNum < length) {
            return rowNum * 2;
        }
        return (2 * length - 1 - rowNum) * 2;
    }

    private static int xPointOffSet(int length, int rowNum) {
        if (rowNum < length) {
            return -rowNum;
        }
        return -(2 * length - 1 - rowNum);
    }

    /** Add a row of tile from Position p with length of rowLength in the Tiles. */
    private static void addRow(TETile[][] tiles, Position p, int rowLength, TETile tile) {
        for (int i = 0; i < rowLength; i += 1) {
            tiles[p.x + i][p.y] = tile;
        }
    }

    public static void drawTessellatedHexagons(TETile[][] tiles, Position p, int length) {
        int columnNum = 5;
        int rowNum = 3;
        // From left to right and From down to up
        // Draw adjacent hexagons column by column
        for (int i = 0; i < columnNum; i += 1) {
            int startX = p.x + rowOffSetOfTHs(length, i);
            int startY = p.y + colOffSetOfTHx(length, i);
            int rows = rowNum + rowNumOffSetOfTHs(i);
            addOneColumnOfHexagons(tiles, new Position(startX, startY), length, rows);
        }
    }

    private static int rowOffSetOfTHs(int length, int col) {
        return (2 * length - 1) * col;
    }

    private static int colOffSetOfTHx(int length, int col) {
        if (col < 3) {
            return -(length * col);
        }
        return length * (col - 4);
    }

    private static int rowNumOffSetOfTHs(int col) {
        if (col < 3) {
            return col;
        }
        return 4 - col;
    }

    private static void addOneColumnOfHexagons(TETile[][] tiles, Position p, int length, int num) {
        for (int i = 0; i < num; i += 1) {
            int startX = p.x;
            int startY = p.y + yOffSet(i, length);
            addHexagon(tiles, new Position(startX, startY), length, randomTile());
        }
    }
    private static int yOffSet(int num, int length) {
        return num * (2 * length);
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // Initialize the hexWorld
        TETile[][] hexWorld = new TETile[WIDTH][HEIGHT];
        initializeTiles(hexWorld);

        // Tessellate 19 hexagons together in the hexWorld
        drawTessellatedHexagons(hexWorld, new Position(30, 40), 5);

        ter.renderFrame(hexWorld);
    }
}
