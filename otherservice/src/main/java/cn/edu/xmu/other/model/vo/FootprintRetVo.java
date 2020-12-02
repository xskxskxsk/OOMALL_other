package cn.edu.xmu.other.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "足迹视图对象")
public class FootprintRetVo {
    @ApiModelProperty(value = "足迹id")
    private Long id;

    @ApiModelProperty(value = "商品id")
    private Long goodsSkuId;

    @ApiModelProperty(value = "浏览时间")
    private LocalDateTime gmtCreate;
}
