package org.ag.ktiles;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private int xDim = 100;
    private int yDim = 1000;
    private int[] currentRhombus = {0, 0};
    private int currentDir = 10;
    private String filename = "test" + System.currentTimeMillis() + ".result";

    public static void main(String[] args) throws InterruptedException, IOException {
        new Main().run();
    }

    private void run() throws InterruptedException, IOException {
        /* Initialize the tile */

        List<Rhombus> koelnerTile = new ArrayList<>();

        for (int i = 0; i < xDim; i++) {
            for (int j = 0; j < yDim; j++) {
                koelnerTile.add(new Rhombus(i, j));
            }
        }



        /* Run the steps */
        for (int i = 0; i < iterations; i++) {
            step(koelnerTile);
//            Thread.sleep(1000);
//            disp(koelnerTile);
        }

        disp(koelnerTile);
    }

    private void disp(List<Rhombus> koelnerTile) throws IOException {
        System.out.println("Saving...");
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        writer.println("\"NAMEOFRESULT\"    (" + iterations + " steps, size: " + xDim + " x " + yDim + ")");
        writer.println();
        for (int i = 0; i < xDim; i++) {
            for (int j = 0; j < yDim; j++) {
//                System.out.println("On rhombus " + (i * 1000 + j) + ".");
                Rhombus c = koelnerTile.get(getIndex(koelnerTile, new int[]{i, j}));
                if (c.getValue() != 0) {
                    writer.print(c.getValue() + " ");
                } else {
                    writer.print("  ");
                }
            }
            writer.println();
        }
        writer.println("____________________________");
        writer.println();
        List<String> list = Files.readAllLines(new File("C:\\development\\koelner\\ktiles\\src\\org\\ag\\ktiles\\Main.java").toPath(), Charset.defaultCharset());
        list.forEach(writer::println);
        writer.close();
    }

    private List<Rhombus> step(List<Rhombus> koelnerTile) {
        System.out.println("Current coordinates are " + Arrays.toString(currentRhombus) + ".");
        System.out.println("Current direction is " + currentDir + ".");

        int currentIndex = getIndex(koelnerTile, currentRhombus);
        Rhombus current;
        try {
            current = koelnerTile.get(currentIndex);
        } catch (ArrayIndexOutOfBoundsException e) {
            return koelnerTile;
        }
        assert current != null;
        current.increment();

        ImmutablePair<Integer, Integer> neighborCoords = current.getNeighbor(currentDir);
        System.out.println("Neighbor's coordinates are " + neighborCoords + ".");

        // If the neighbor exists, switch to it
        if (neighborCoords != null) {
            currentRhombus[0] = neighborCoords.getLeft();
            currentRhombus[1] = neighborCoords.getRight();
        }

        updateDir();

        if (currentRhombus[0] % 2 == 0) {
            currentRhombus[0] += (int) (Math.floor(current.getValue())) - (int) (Math.floor(Math.sqrt(currentRhombus[1])));
            if (currentRhombus[0] < 0) {
                currentRhombus[0] = 0;
            }
        }

        return koelnerTile;
    }

    private int iterations = 1000000;

    private void updateDir() {
        currentDir++;
        currentDir %= 4;
        currentDir += 10;
    }


    private int getIndex(List<Rhombus> koelnerTile, int[] coords) {
        for (Rhombus r : koelnerTile) {
            if (r.getX() == coords[0] && r.getY() == coords[1]) {
                return koelnerTile.indexOf(r);
            }
        }

        return -1;
    }
}
