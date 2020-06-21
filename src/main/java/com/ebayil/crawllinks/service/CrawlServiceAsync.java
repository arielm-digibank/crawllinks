package com.ebayil.crawllinks.service;

import com.ebayil.crawllinks.data.Node;
import lombok.Data;
import lombok.NonNull;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class CrawlServiceAsync {
    @NonNull
    private int maxDepth;
    @NonNull
    private Node rootNode;

    private Set<String> links = (new ConcurrentHashMap<String, Object>()).newKeySet();

    @Autowired
    CrawlerHelper helper;

    public List<CompletableFuture> getPageLinks(Node node)  {
        int level = node.getLevel();
        String url = node.getUrl();
        List<CompletableFuture> futures = new ArrayList<>();

        links.add(url);
        Elements linksOnPage = helper.getLinksOnPage(node, maxDepth);
        if (linksOnPage==null) return futures;

        for (Element page : linksOnPage) {
            String childUrl = page.attr("abs:href");
            if (Strings.isEmpty(childUrl) || links.contains(childUrl)) continue;

            Node childNode = helper.buildChildNode(node, childUrl);
            futures.add(CompletableFuture.runAsync( () ->getPageLinks(childNode), helper.getExecutor()));
        }

        return futures;
    }

    public void startAsync() {
        List<CompletableFuture> futures = new ArrayList<>();
        futures.addAll(getPageLinks(rootNode));
        if (futures.size() > 0) CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

}
