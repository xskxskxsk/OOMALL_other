package cn.edu.xmu.other.service;

import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import cn.edu.xmu.other.dao.FootprintDao;
import cn.edu.xmu.other.model.bo.Footprint;
import cn.edu.xmu.other.model.po.FootPrintPo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FootprintService {
    private Logger logger = LoggerFactory.getLogger(FootprintService.class);

    @Autowired
    private FootprintDao footprintDao;

    public ReturnObject<VoObject> insertFootprint(Long userId, Long goodsSkuId) {
        Footprint footprint=new Footprint();
        footprint.setCustomerId(userId);
        footprint.setGoodsSkuId(goodsSkuId);
        footprint.setGmtCreate(LocalDateTime.now());
        ReturnObject<Footprint> retObj=footprintDao.insertFootprint(footprint);
        ReturnObject<VoObject> ret = null;
        if (retObj.getCode().equals(ResponseCode.OK)) {
            ret = new ReturnObject<>(retObj.getData());
        } else {
            ret = new ReturnObject<>(retObj.getCode(), retObj.getErrmsg());
        }
        return ret;
    }

    public ReturnObject<PageInfo<VoObject>> getFootprint(Long userId,String beginTime,String endTime,Integer page, Integer pagesize){
        PageHelper.startPage(page, pagesize);
        PageInfo<FootPrintPo> footprintPos=footprintDao.getFootprint(userId,beginTime,endTime,page,pagesize);

        List<VoObject> footprints=footprintPos.getList().stream().map(Footprint::new).collect(Collectors.toList());

        PageInfo<VoObject> returnObject=new PageInfo<>(footprints);
        returnObject.setPages(footprintPos.getPages());
        returnObject.setPageNum(footprintPos.getPageNum());
        returnObject.setPageSize(footprintPos.getPageSize());
        returnObject.setTotal(footprintPos.getTotal());

        return new ReturnObject<>(returnObject);
    }
}
