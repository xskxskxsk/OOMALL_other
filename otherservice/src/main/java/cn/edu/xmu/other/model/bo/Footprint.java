package cn.edu.xmu.other.model.bo;

import cn.edu.xmu.other.model.po.FootPrintPo;
import lombok.Data;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.other.model.vo.FootprintRetVo;

import java.time.LocalDateTime;

@Data
public class Footprint implements VoObject {
    private Long id;

    private Long customerId;

    private Long goodsSkuId;

    private LocalDateTime gmtCreate;

    /**
     *用po建立bo
     * @param po
     */
    public Footprint(FootPrintPo po){
        this.id=po.getId();
        this.customerId=po.getCustomerId();
        this.goodsSkuId=po.getGoodsSkuId();
        this.gmtCreate=po.getGmtCreate();
    }

    public Footprint() {

    }


    /**
     * 生成Vo返回前端
     * @return FootprintVo
     */
    @Override
    public FootprintRetVo createVo() {
        FootprintRetVo footprintRetVo =new FootprintRetVo();
        footprintRetVo.setId(id);
        footprintRetVo.setGoodsSkuId(goodsSkuId);
        footprintRetVo.setGmtCreate(gmtCreate);

        return footprintRetVo;
    }

    @Override
    public Object createSimpleVo() {
        return null;
    }

    /**
     * 用bo建立po
     * @return
     */
    public FootPrintPo createpo(){
        FootPrintPo po=new FootPrintPo();
        po.setId(this.id);
        po.setCustomerId(this.customerId);
        po.setGoodsSkuId(this.goodsSkuId);
        po.setGmtCreate(this.gmtCreate);

        return po;
    }

}
