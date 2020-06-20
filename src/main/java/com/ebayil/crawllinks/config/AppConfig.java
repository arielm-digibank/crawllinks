package com.ebayil.crawllinks.config;

import com.ebayil.crawllinks.data.Node;
import com.ebayil.crawllinks.service.CrawlService;
import com.ebayil.crawllinks.service.CrawlServiceAsync;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class AppConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public CrawlService getCrawlService(String url, int maxDepth) {
        Node rootNode = Node.builder().level(0).url(url).children(new HashSet()).build();
        return new CrawlService(maxDepth, rootNode);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public CrawlServiceAsync getCrawlServiceAsync(String url, int maxDepth) {
        Node rootNode = Node.builder().level(0).url(url).children((new ConcurrentHashMap<String, Object>()).newKeySet()).build();
        return new CrawlServiceAsync(maxDepth, rootNode);
    }

}
