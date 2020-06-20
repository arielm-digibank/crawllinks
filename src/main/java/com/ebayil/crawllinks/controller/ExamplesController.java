package com.ebayil.crawllinks.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 *                test3
 *              /
 *        test2
 *      /      \
 * test1        test5 (not exists)
 *      \
 *       test4
 *            \
 *             test2 (exists under test)
 */
@RestController
@RequestMapping(value = "examples", produces = {APPLICATION_JSON_VALUE})
public class ExamplesController {

    @GetMapping(value = "/test1", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String test1() {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        String path = builder.build().toString();
        return "<html>\n" + "<header><title>Welcome</title></header>\n" +
                "<body>\n" + "Hello world\n" +
                "<a href="+path.replace("test1", "test2") + ">test2</a>" +
                "<a href="+path.replace("test1", "test4") + ">test4</a>" +
                "</body>\n" + "</html>";
    }

    @GetMapping(value = "/test2", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String test2() {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        String path = builder.build().toString();
        return "<html>\n" + "<header><title>Welcome</title></header>\n" +
                "<body>\n" + "Hello world\n" +
                "<a href="+path.replace("test2", "test3") + ">test3</a>" +
                "<a href="+path.replace("test2", "test5") + ">test5</a>" +
                "</body>\n" + "</html>";
    }

    @GetMapping(value = "/test3", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String test3() {
        return "<html>\n" + "<header><title>Welcome</title></header>\n" +
                "<body>\n" + "Hello world\n" +
                "</body>\n" + "</html>";
    }

    @GetMapping(value = "/test4", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String test4() {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        String path = builder.build().toString();
        return "<html>\n" + "<header><title>Welcome</title></header>\n" +
                "<body>\n" + "Hello world\n" +
                "<a href="+path.replace("test4", "test2") + ">test3</a>" +
                "</body>\n" + "</html>";
    }

}
