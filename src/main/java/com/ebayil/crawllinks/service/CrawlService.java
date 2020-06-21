package com.ebayil.crawllinks.service;

import com.ebayil.crawllinks.data.Node;
import lombok.Data;
import lombok.NonNull;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

@Data
public class CrawlService {
    @NonNull
    private int maxDepth;
    @NonNull
    private Node rootNode;

    private Set<String> links = new HashSet<>();

    @Autowired
    CrawlerHelper helper;

    public void getPageLinks(Node node) {
        links.add(node.getUrl());
        Elements linksOnPage = helper.getLinksOnPage(node, maxDepth);
        if (linksOnPage==null) return;

        for (Element page : linksOnPage) {
            String childUrl = page.attr("abs:href");
            if (Strings.isEmpty(childUrl) || links.contains(childUrl)) continue;

            Node childNode = helper.buildChildNode(node, childUrl);

            getPageLinks(childNode);
        }
    }

    public void start() {
        getPageLinks(rootNode);
    }

}
