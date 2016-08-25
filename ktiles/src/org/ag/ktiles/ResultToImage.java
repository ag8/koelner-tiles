package org.ag.ktiles;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.ImageLineHelper;
import ar.com.hjg.pngj.ImageLineInt;
import ar.com.hjg.pngj.PngWriter;
import org.apache.commons.lang3.ArrayUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ResultToImage {
    public static void makeTestImage(PngWriter png, List<int[]> values) {
        int cols = png.imgInfo.cols;
        int rows = png.imgInfo.rows;
//        png.getMetadata().setDpi(123.0);
//        png.getMetadata().setTimeNow(0);
//        png.getMetadata().setText(PngChunkTextVar.KEY_Software, "pngj test");
        ImageLineInt iline = new ImageLineInt(png.imgInfo);
        png.setIdatMaxSize(2000);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                iline.getScanline()[j] = new Color(0, 0, values.get(i)[j]).getRGB();
            }
            png.writeRow(iline, i);
        }
        png.end();
    }

    public static void createTest(String name) throws IOException {
        List<String> list = Files.readAllLines(new File("C:\\development\\koelner-tiles\\ktiles\\test1472098726657.result").toPath(), Charset.defaultCharset() );
        List<int[]> values = new ArrayList<>();

        int rowNum = Integer.parseInt(list.get(0).split("steps, size: ")[1].split(" x ")[0]);
        int colNum = Integer.parseInt(list.get(0).split(" x ")[1].split("\\)")[0]);


        for (int i = 2; i < rowNum + 2; i++) {
            List<Integer> valuesInRowList = new ArrayList<>();

            String[] parts = list.get(i).split(" ");
            for (String part : parts) {
                int num = Integer.parseInt(part);
                valuesInRowList.add(num);
            }

            values.add(ArrayUtils.toPrimitive(valuesInRowList.toArray(new Integer[valuesInRowList.size()])));
        }

        PngWriter i2 =
                new PngWriter(new File(name), new ImageInfo(colNum, rowNum, 2, false, true, false), true);
        makeTestImage(i2, values);
        System.out.println("Done: " + name);
    }

    public static void main(String[] args) throws Exception {
        args = new String[3];
        args[0] = "C:\\development\\koelner-tiles\\ktiles\\imgs\\test.png";
        args[1] = "1000";
        args[2] = "100";
        if (args.length < 3) {
            System.err.println("Arguments: [pngdest] [cols] [rows]");
            System.exit(1);
        }
        int cols = Integer.parseInt(args[1]);
        int rows = Integer.parseInt(args[2]);
        createTest(args[0]);
    }
}
