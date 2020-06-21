package com.ebayil.crawllinks.controller;

import com.ebayil.crawllinks.controller.dto.InputDto;
import com.ebayil.crawllinks.data.Node;
import com.ebayil.crawllinks.service.CrawlService;
import com.ebayil.crawllinks.service.CrawlServiceAsync;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "crawler", produces = {APPLICATION_JSON_VALUE})
@Api(value = "REST API Html Crawler API")
public class CrawlerController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public CrawlServiceAsync getPrototypeBean() {
        return applicationContext.getBean(CrawlServiceAsync.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST ,produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "An API to crawl page links", notes = "An API to crawl page links", response = Node.class)
    public Node setNewCrawl(@RequestBody InputDto input) {
        CrawlService crawler = applicationContext.getBean(CrawlService.class, input.getStartUrl(), input.getDepth());
        crawler.start();
        return crawler.getRootNode();
    }

    @RequestMapping(value = "/async", method = RequestMethod.POST ,produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "An API to crawl page links - Async", notes = "An API to to crawl page links - Async", response = Node.class)
    public Node setNewCrawlAsync(@RequestBody InputDto input) throws Exception {
        CrawlServiceAsync crawler = applicationContext.getBean(CrawlServiceAsync.class, input.getStartUrl(), input.getDepth());
        crawler.startAsync();
        return crawler.getRootNode();
    }

}
