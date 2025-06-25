package cool.scx.cocos_decoder;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.type.TypeReference;

import static cool.scx.cocos_decoder.CocosUUIDUtils.compressUUID;

public class CocosRenamer {

    static String outFolder = "";

    public static List<String> traverseAllFiles(String folder) throws IOException {
        try (var paths = Files.walk(Paths.get(folder))) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(Path::toAbsolutePath)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        }
    }

    public static void copyFileWithUniqueName(String sourcePath, String destPath) throws IOException {
        Path source = Paths.get(sourcePath);
        Path dest = Paths.get(destPath);

        // 确保目录存在
        Files.createDirectories(dest.getParent());

        String fileName = dest.getFileName().toString();
        String base = fileName;
        String ext = "";

        int dot = fileName.lastIndexOf('.');
        if (dot != -1) {
            base = fileName.substring(0, dot);
            ext = fileName.substring(dot);
        }

        int i = 1;
        Path target = dest;
        while (Files.exists(target)) {
            target = dest.getParent().resolve(base + "_" + i + ext);
            i++;
        }

        Files.copy(source, target);
    }

    public static void traverseRename(String buildFolder, String outputFolder) throws IOException {
        outFolder = outputFolder; // 设置全局输出目录

        File dir = new File(buildFolder);
        if (!dir.isDirectory() || rename(buildFolder)) {
            return;
        }

        for (File subDir : dir.listFiles(File::isDirectory)) {
            traverseRename(subDir.getAbsolutePath(), outputFolder);
        }
    }

    public static boolean rename(String folder) throws IOException {
        if (!folder.endsWith(File.separator)) {
            folder += File.separator;
        }

        String renameFolder = outFolder.isEmpty()
                ? folder + "out" + File.separator
                : outFolder;

        File[] configFiles = new File(folder).listFiles((dir, name) -> name.startsWith("config."));
        if (configFiles == null || configFiles.length == 0) {
            return false;
        }

        // 读取 config JSON
        Map<String, Object> data;
        try {
            data = new ObjectMapper().readValue(configFiles[0], new TypeReference<>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // 获取字段
        List<String> uuids = (List<String>) data.get("uuids");
        Map<String, Object> paths = (Map<String, Object>) data.get("paths");

        List<String> allFiles = new ArrayList<>();
        try {
            allFiles = traverseAllFiles(folder + "native");
        } catch (IOException e) {
            // 忽略异常
        }

        for (String file : allFiles) {
            String fileName = new File(file).getName();
            String uuid = fileName.split("\\.")[0];
            String encoded = compressUUID(uuid);

            int id = uuids.indexOf(encoded);
            Object filePathObj = id >= 0 ? paths.get(String.valueOf(id)) : null;

            if (filePathObj instanceof List<?> filePathList && !filePathList.isEmpty()) {
                String targetRelPath = filePathList.get(0).toString();
                String ext = fileName.substring(fileName.lastIndexOf('.'));
                String targetPath = renameFolder + targetRelPath + ext;

                System.out.println("rename " + fileName + " -> " + targetRelPath);
                copyFileWithUniqueName(file, targetPath);
            } else {
                System.out.println("Can't find path at " + fileName + ", id = " + id);
            }
        }

        return true;
    }

}
