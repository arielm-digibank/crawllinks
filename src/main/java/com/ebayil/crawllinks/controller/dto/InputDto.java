package com.ebayil.crawllinks.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class InputDto {
    @ApiModelProperty(required = true)
    String startUrl;
    @ApiModelProperty(required = true)
    int depth;
}
