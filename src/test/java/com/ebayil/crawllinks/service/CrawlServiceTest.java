package com.ebayil.crawllinks.service;

import com.ebayil.crawllinks.data.Node;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class CrawlServiceTest {

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testSimple() {
        Node rootNode = Node.builder().level(0).url("http://www.google.com").children(new HashSet()).build();
        CrawlService service = new CrawlService(1, rootNode);
        ReflectionTestUtils.setField(service, "helper", new CrawlerHelper());
        service.start();
        assertEquals(20,rootNode.getChildren().size());
    }
}
