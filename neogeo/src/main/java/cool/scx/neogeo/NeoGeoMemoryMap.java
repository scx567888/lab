package cool.scx.neogeo;

public class NeoGeoMemoryMap {

    public final NeoGeoCartridge cartridge;

    public NeoGeoMemoryMap(NeoGeoCartridge cartridge) {
        this.cartridge = cartridge;
    }

    public byte readByte(int address) {
        // 程序ROM1：0x000000 - 0x3FFFFF (4MB)
        if (address >= 0x000000 && address <= 0x3FFFFF) {
            int offset = address - 0x000000;
            if (cartridge.programRom1 != null && offset < cartridge.programRom1.length) {
                return cartridge.programRom1[offset];
            }
            return 0;
        }

        // 程序ROM2：0x400000 - 0x7FFFFF (4MB)
        if (address >= 0x400000 && address <= 0x7FFFFF) {
            int offset = address - 0x400000;
            if (cartridge.programRom2 != null && offset < cartridge.programRom2.length) {
                return cartridge.programRom2[offset];
            }
            return 0;
        }

        // 内存地址重叠时优先读取 声音CPU程序ROM
        // 声音CPU程序ROM M1: 0x2FE0000 - 0x2FFFFFF (128KB)
        if (address >= 0x2FE0000 && address <= 0x2FFFFFF) {
            int offset = address - 0x2FE0000;
            if (cartridge.soundCpuRom != null && offset < cartridge.soundCpuRom.length) {
                return cartridge.soundCpuRom[offset];
            }
            return 0;
        }

        // 图像ROM 块1：0x1000000 - 0x13FFFFF (4MB)
        if (address >= 0x1000000 && address <= 0x13FFFFF) {
            int offset = address - 0x1000000;
            if (cartridge.characterRom1 != null && offset < cartridge.characterRom1.length) {
                return cartridge.characterRom1[offset];
            }
            return 0;
        }

        // 图像ROM 块2：0x1400000 - 0x17FFFFF (4MB)
        if (address >= 0x1400000 && address <= 0x17FFFFF) {
            int offset = address - 0x1400000;
            if (cartridge.characterRom2 != null && offset < cartridge.characterRom2.length) {
                return cartridge.characterRom2[offset];
            }
            return 0;
        }

        // 图像ROM 块3：0x1800000 - 0x1BFFFFF (4MB)
        if (address >= 0x1800000 && address <= 0x1BFFFFF) {
            int offset = address - 0x1800000;
            if (cartridge.characterRom3 != null && offset < cartridge.characterRom3.length) {
                return cartridge.characterRom3[offset];
            }
            return 0;
        }

        // 图像ROM 块4：0x1C00000 - 0x1FFFFFF (4MB)
        if (address >= 0x1C00000 && address <= 0x1FFFFFF) {
            int offset = address - 0x1C00000;
            if (cartridge.characterRom4 != null && offset < cartridge.characterRom4.length) {
                return cartridge.characterRom4[offset];
            }
            return 0;
        }

        // 图像ROM 块5：0x2000000 - 0x23FFFFF (4MB)
        if (address >= 0x2000000 && address <= 0x23FFFFF) {
            int offset = address - 0x2000000;
            if (cartridge.characterRom5 != null && offset < cartridge.characterRom5.length) {
                return cartridge.characterRom5[offset];
            }
            return 0;
        }

        // 图像ROM 块6：0x2400000 - 0x27FFFFF (4MB)
        if (address >= 0x2400000 && address <= 0x27FFFFF) {
            int offset = address - 0x2400000;
            if (cartridge.characterRom6 != null && offset < cartridge.characterRom6.length) {
                return cartridge.characterRom6[offset];
            }
            return 0;
        }

        // 图像ROM 块7：0x2800000 - 0x2BFFFFF (4MB)
        if (address >= 0x2800000 && address <= 0x2BFFFFF) {
            int offset = address - 0x2800000;
            if (cartridge.characterRom7 != null && offset < cartridge.characterRom7.length) {
                return cartridge.characterRom7[offset];
            }
            return 0;
        }

        // 图像ROM 块8：0x2C00000 - 0x2FFFFFF (4MB)
        if (address >= 0x2C00000 && address <= 0x2FFFFFF) {
            int offset = address - 0x2C00000;
            if (cartridge.characterRom8 != null && offset < cartridge.characterRom8.length) {
                return cartridge.characterRom8[offset];
            }
            return 0;
        }

        // 固定图层ROM S1: 0x3000000 - 0x3FFFFFF (16MB)
        if (address >= 0x3000000 && address <= 0x3FFFFFF) {
            int offset = address - 0x3000000;
            if (cartridge.fixedLayerRom != null && offset < cartridge.fixedLayerRom.length) {
                return cartridge.fixedLayerRom[offset];
            }
            return 0;
        }

        // 声音采样ROM V1: 0x4000000 - 0x47FFFFF (8MB)
        if (address >= 0x4000000 && address <= 0x47FFFFF) {
            int offset = address - 0x4000000;
            if (cartridge.soundSampleRom1 != null && offset < cartridge.soundSampleRom1.length) {
                return cartridge.soundSampleRom1[offset];
            }
            return 0;
        }

        // 声音采样ROM V2: 0x4800000 - 0x4FFFFFF (8MB)
        if (address >= 0x4800000 && address <= 0x4FFFFFF) {
            int offset = address - 0x4800000;
            if (cartridge.soundSampleRom2 != null && offset < cartridge.soundSampleRom2.length) {
                return cartridge.soundSampleRom2[offset];
            }
            return 0;
        }

        // 声音采样ROM V3: 0x5000000 - 0x57FFFFF (8MB)
        if (address >= 0x5000000 && address <= 0x57FFFFF) {
            int offset = address - 0x5000000;
            if (cartridge.soundSampleRom3 != null && offset < cartridge.soundSampleRom3.length) {
                return cartridge.soundSampleRom3[offset];
            }
            return 0;
        }

        // 声音采样ROM V4: 0x5800000 - 0x5FFFFFF (8MB)
        if (address >= 0x5800000 && address <= 0x5FFFFFF) {
            int offset = address - 0x5800000;
            if (cartridge.soundSampleRom4 != null && offset < cartridge.soundSampleRom4.length) {
                return cartridge.soundSampleRom4[offset];
            }
            return 0;
        }

        return 0;

    }
    

    public void writeByte(int address, byte value) {

    }


}
