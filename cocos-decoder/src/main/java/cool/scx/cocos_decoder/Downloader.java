package cool.scx.cocos_decoder;

import cool.scx.http.x.HttpClient;
import cool.scx.http.ScxHttpClientResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;

public class Downloader {

    static HttpClient client = new HttpClient();
    static Path basePath = Paths.get("xxxx");
    static Set<String> visited = new HashSet<>();

    public static void main(String[] args) throws IOException {
        String baseURL = "https://xxxxx/";
        crawl(baseURL, basePath);
    }

    public static void crawl(String url, Path saveDir) {
        if (visited.contains(url)) {
            return; // 防止重复访问
        }
        visited.add(url);

        try {
            System.out.println("访问: " + url);
            ScxHttpClientResponse response = client.request().uri(url).send();

            if (url.endsWith("/")) {
                // 说明是目录，解析 HTML
                String html = response.body().asString();
                Document doc = Jsoup.parse(html, url); // 设置 base URI，自动处理相对路径

                Elements links = doc.select("a[href]");
                for (Element link : links) {
                    String href = link.attr("href");
                    if (href.equals("../")) continue; // 跳过返回上级
                    
                    if (href.startsWith("?")) continue;
                    String fullUrl = doc.baseUri() + href;
                    Path subPath = saveDir.resolve(href);
                    if (href.endsWith("/")) {
                        // 是子目录，递归爬取
                        Files.createDirectories(subPath);
                        crawl(fullUrl, subPath);
                    } else {
                        // 是文件，下载并保存
                        downloadFile(fullUrl, subPath);
                    }
                }
            } else {
                // 是文件，直接下载
                downloadFile(url, saveDir);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("下载失败：" + url + " - " + e.getMessage());
        }
    }

    public static void downloadFile(String url, Path savePath) {
        try {
            ScxHttpClientResponse fileResponse = client.request().uri(url).send();
            try (InputStream in = fileResponse.body().inputStream()) {
                Files.createDirectories(savePath.getParent());
                Files.copy(in, savePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("保存文件: " + savePath);
            }
        } catch (Exception e) {
            System.err.println("文件下载失败：" + url + " - " + e.getMessage());
        }
    }
}
