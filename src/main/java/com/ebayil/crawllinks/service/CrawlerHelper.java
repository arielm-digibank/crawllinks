package com.ebayil.crawllinks.service;

import com.ebayil.crawllinks.data.Node;
import lombok.Getter;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
@Getter
public class CrawlerHelper {
    private final ExecutorService executor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());

    public Elements getLinksOnPage(Node node, int maxDepth) {
        int level = node.getLevel();
        String url = node.getUrl();

        Elements linksOnPage=null;
        try {
            Connection.Response r = Jsoup.connect(url).method(Connection.Method.GET)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                    .timeout(0).execute();
            node.setStatus(r.statusCode());
            if (level == maxDepth) return linksOnPage;
            System.out.println( node + ", thread:" + Thread.currentThread().getName());
            linksOnPage = r.parse().select("a[href]");
        } catch (HttpStatusException e1) {
            node.setStatus(e1.getStatusCode());
            node.setUrl(url + " !!!! Error Occured: " + e1.getMessage());
        } catch (Exception e2) {
            node.setUrl(url + " !!!! Error Occured: " + e2.getMessage());
        }
        return linksOnPage;
    }

    public Node buildChildNode(Node parent, String childUrl) {
        Node childNode = Node.builder()
                .level(parent.getLevel() + 1)
                .url(childUrl)
                .children((new ConcurrentHashMap<String, Object>()).newKeySet())
                .build();
        parent.getChildren().add(childNode);
        return childNode;
    }
}
