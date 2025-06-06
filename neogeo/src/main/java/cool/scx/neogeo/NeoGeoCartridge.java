package cool.scx.neogeo;

/// NeoGeo 卡带
public class NeoGeoCartridge {

    // 主程序 ROM, 最多2块
    public byte[] programRom1;
    public byte[] programRom2;

    // 声音 CPU 程序 ROM, 1块
    public byte[] soundCpuRom;

    // 图像 ROM, 最多8块
    public byte[] characterRom1;
    public byte[] characterRom2;
    public byte[] characterRom3;
    public byte[] characterRom4;
    public byte[] characterRom5;
    public byte[] characterRom6;
    public byte[] characterRom7;
    public byte[] characterRom8;

    // 固定布局 ROM, 1块
    public byte[] fixedLayerRom;

    // 音频采样 ROM, 最多4块
    public byte[] soundSampleRom1;
    public byte[] soundSampleRom2;
    public byte[] soundSampleRom3;
    public byte[] soundSampleRom4;

    // 安全芯片加密ROM, 1块
    public byte[] securityRom;

}
