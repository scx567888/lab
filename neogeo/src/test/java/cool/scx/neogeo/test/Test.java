package cool.scx.neogeo.test;

import cool.scx.neogeo.NeoGeoCartridge;
import cool.scx.neogeo.NeoGeoCartridgeLoader;

import java.io.IOException;
import java.nio.file.Path;

public class Test {
    public static void main(String[] args) throws IOException {
        NeoGeoCartridge load = NeoGeoCartridgeLoader.load(Path.of("C:\\Users\\scx\\Documents\\Fbas-Files-master\\roms\\kof2000.zip"));
        System.out.println("Loaded Roms:");
    }
}
