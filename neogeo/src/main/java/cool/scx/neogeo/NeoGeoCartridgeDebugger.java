package cool.scx.neogeo;

public class NeoGeoCartridgeDebugger {

    public static void printRomSize(NeoGeoCartridge cartridge) {

        System.out.println("programRom1: " + cartridge.programRom1.length);
        System.out.println("programRom2: " + cartridge.programRom2.length);
        System.out.println("characterRom1: " + cartridge.characterRom1.length);
        System.out.println("characterRom2: " + cartridge.characterRom2.length);
        System.out.println("characterRom3: " + cartridge.characterRom3.length);
        System.out.println("characterRom4: " + cartridge.characterRom4.length);
        System.out.println("characterRom5: " + cartridge.characterRom5.length);
        System.out.println("characterRom6: " + cartridge.characterRom6.length);
        System.out.println("characterRom7: " + cartridge.characterRom7.length);
        System.out.println("characterRom8: " + cartridge.characterRom8.length);
        System.out.println("soundCpuRom: " + cartridge.soundCpuRom.length);
        System.out.println("fixedLayerRom: " + cartridge.fixedLayerRom.length);
        System.out.println("soundSampleRom1: " + cartridge.soundSampleRom1.length);
        System.out.println("soundSampleRom2: " + cartridge.soundSampleRom2.length);
        System.out.println("soundSampleRom3: " + cartridge.soundSampleRom3.length);
        System.out.println("soundSampleRom4: " + cartridge.soundSampleRom4.length);
        System.out.println("securityRom: " + (cartridge.securityRom == null ? null : cartridge.securityRom.length));

    }

}
