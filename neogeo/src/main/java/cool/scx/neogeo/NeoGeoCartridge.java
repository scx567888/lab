package cool.scx.neogeo;

/// NeoGeo 卡带，字段按内存映射地址顺序排列
public class NeoGeoCartridge {

    // 主程序 ROM, 最多2块
    public byte[] programRom1;       // 0x000000 - 0x3FFFFF (4MB)
    public byte[] programRom2;       // 0x400000 - 0x7FFFFF (4MB)

    // 图像 ROM, 最多8块
    public byte[] characterRom1;     // 0x1000000 - 0x13FFFFF (4MB)
    public byte[] characterRom2;     // 0x1400000 - 0x17FFFFF (4MB)
    public byte[] characterRom3;     // 0x1800000 - 0x1BFFFFF (4MB)
    public byte[] characterRom4;     // 0x1C00000 - 0x1FFFFFF (4MB)
    public byte[] characterRom5;     // 0x2000000 - 0x23FFFFF (4MB)
    public byte[] characterRom6;     // 0x2400000 - 0x27FFFFF (4MB)
    public byte[] characterRom7;     // 0x2800000 - 0x2BFFFFF (4MB)
    public byte[] characterRom8;     // 0x2C00000 - 0x2FFFFFF (4MB)

    // 声音 CPU 程序 ROM, 1块
    public byte[] soundCpuRom;       // 0x2FE0000 - 0x2FFFFFF (128KB) —— 这个地址特殊，放在图像ROM之后，但实际大小较小

    // 固定布局 ROM, 1块
    public byte[] fixedLayerRom;     // 0x3000000 - 0x3FFFFFF (16MB)

    // 音频采样 ROM, 最多4块
    public byte[] soundSampleRom1;   // 0x4000000 - 0x47FFFFF (8MB)
    public byte[] soundSampleRom2;   // 0x4800000 - 0x4FFFFFF (8MB)
    public byte[] soundSampleRom3;   // 0x5000000 - 0x57FFFFF (8MB)
    public byte[] soundSampleRom4;   // 0x5800000 - 0x5FFFFFF (8MB)

    // 安全芯片加密ROM, 1块
    public byte[] securityRom;       // 位置不固定，通常是附加在某些卡带中

}
