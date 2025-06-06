package cool.scx.neogeo.test;

import cool.scx.neogeo.NeoGeoCartridgeDebugger;
import cool.scx.neogeo.NeoGeoCartridgeLoader;
import cool.scx.neogeo.NeoGeoMemoryMap;

import java.io.IOException;
import java.nio.file.Path;

public class Test {
    public static void main(String[] args) throws IOException {
        var cartridge = NeoGeoCartridgeLoader.load(Path.of("C:\\Users\\scx\\Documents\\Fbas-Files-master\\roms\\kof98.zip"));
        var memoryMap = new NeoGeoMemoryMap(cartridge);
        NeoGeoCartridgeDebugger.printRomSize(cartridge);
        byte b = memoryMap.readByte(0x00000f);
        System.out.println("Loaded Roms:");
    }
}
