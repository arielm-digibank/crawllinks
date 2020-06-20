package com.ebayil.crawllinks.data;

import lombok.*;

import java.util.Set;

@Builder
@Data
public class Node {
    private String url;
    private int status;
    private int level;
    private Set<Node> children;
}
