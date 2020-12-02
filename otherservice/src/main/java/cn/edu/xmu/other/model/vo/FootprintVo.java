package cn.edu.xmu.other.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FootprintVo {
    private Long id;
    private Long goodsSkuId;
    private Long customerId;
    private LocalDateTime gmtCreate;
}
