package cool.scx.neogeo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipInputStream;

/// 卡带加载器
public class NeoGeoCartridgeLoader {


    public static NeoGeoCartridge load(Path romZipPath) throws IOException {
        var cartridge = new NeoGeoCartridge();

        try (var is = Files.newInputStream(romZipPath); var zis = new ZipInputStream(is)) {

            // 遍历每一个文件
            var zipEntry = zis.getNextEntry();

            while (zipEntry != null) {

                if (zipEntry.isDirectory()) { // 文件夹跳过
                    zis.closeEntry();
                    zipEntry = zis.getNextEntry();
                    continue;
                }
                
                // 文件
                var name = zipEntry.getName().toLowerCase();

                if (name.endsWith(".p1")) {
                    cartridge.programRom1 = zis.readAllBytes();
                    System.out.println("加载 programRom1 成功 !!!");
                } else if (name.endsWith(".p2") || name.endsWith(".sp2")) {
                    cartridge.programRom2 = zis.readAllBytes();
                    System.out.println("加载 programRom2 成功 !!!");
                } else if (name.endsWith(".c1")) {
                    cartridge.characterRom1 = zis.readAllBytes();
                    System.out.println("加载 characterRom1 成功 !!!");
                } else if (name.endsWith(".c2")) {
                    cartridge.characterRom2 = zis.readAllBytes();
                    System.out.println("加载 characterRom2 成功 !!!");
                } else if (name.endsWith(".c3")) {
                    cartridge.characterRom3 = zis.readAllBytes();
                    System.out.println("加载 characterRom3 成功 !!!");
                } else if (name.endsWith(".c4")) {
                    cartridge.characterRom4 = zis.readAllBytes();
                    System.out.println("加载 characterRom4 成功 !!!");
                } else if (name.endsWith(".c5")) {
                    cartridge.characterRom5 = zis.readAllBytes();
                    System.out.println("加载 characterRom5 成功 !!!");
                } else if (name.endsWith(".c6")) {
                    cartridge.characterRom6 = zis.readAllBytes();
                    System.out.println("加载 characterRom6 成功 !!!");
                } else if (name.endsWith(".c7")) {
                    cartridge.characterRom7 = zis.readAllBytes();
                    System.out.println("加载 characterRom7 成功 !!!");
                } else if (name.endsWith(".c8")) {
                    cartridge.characterRom8 = zis.readAllBytes();
                    System.out.println("加载 characterRom8 成功 !!!");
                } else if (name.endsWith(".m1")) {
                    cartridge.soundCpuRom = zis.readAllBytes();
                    System.out.println("加载 soundCpuRom 成功 !!!");
                } else if (name.endsWith(".v1")) {
                    cartridge.soundSampleRom1 = zis.readAllBytes();
                    System.out.println("加载 soundSampleRom1 成功 !!!");
                } else if (name.endsWith(".v2")) {
                    cartridge.soundSampleRom2 = zis.readAllBytes();
                    System.out.println("加载 soundSampleRom2 成功 !!!");
                } else if (name.endsWith(".v3")) {
                    cartridge.soundSampleRom3 = zis.readAllBytes();
                    System.out.println("加载 soundSampleRom3 成功 !!!");
                } else if (name.endsWith(".v4")) {
                    cartridge.soundSampleRom4 = zis.readAllBytes();
                    System.out.println("加载 soundSampleRom4 成功 !!!");
                } else if (name.endsWith(".s1")) {
                    cartridge.fixedLayerRom = zis.readAllBytes();
                    System.out.println("加载 fixedLayerRom 成功 !!!");
                } else if (name.endsWith("neo-sma")) {
                    cartridge.securityRom = zis.readAllBytes();
                    System.out.println("加载 securityRom 成功 !!!");
                }

                zis.closeEntry();
                zipEntry = zis.getNextEntry();

            }

        }

        return cartridge;

    }

}
