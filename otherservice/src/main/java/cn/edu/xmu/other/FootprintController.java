package cn.edu.xmu.other;

import cn.edu.xmu.ooad.annotation.Audit;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.Common;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import cn.edu.xmu.other.service.FootprintService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 @Api(value = "其他服务", tags = "footprint")
 @RestController /*Restful的Controller对象*/
 @RequestMapping(value = "/footprint", produces = "application/json;charset=UTF-8")
public class FootprintController {
    @Autowired
    private HttpServletResponse httpServletResponse;
    private  static  final Logger logger = LoggerFactory.getLogger(FootprintController.class);
    @Autowired
    private FootprintService footprintService;
    @ApiOperation(value = "增加足迹", produces = "application/json")
    @Audit
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "integer", name = "id", value = "用户id", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "integer", name = "goodSkuId", value = "可填写的信息", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @PostMapping("users/{id}/footprint")
    public Object insertFootprint(@PathVariable Long userId, @RequestBody Long goodsSkuId){
        ReturnObject<VoObject> retObject = footprintService.insertFootprint(userId, goodsSkuId);
        return Common.decorateReturnObject(retObject);
    }

    @ApiOperation(value = "管理员查看浏览记录", produces = "application/json")
    @Audit
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "integer", name = "userId", value = "用户id", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "endTime", value = "结束时间", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "integer", name = "page", value = "页码", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "integer", name = "pageSize", value = "每页数目", required = false)
    })
    @GetMapping("/footprints")
    public Object getFootprint(@RequestParam(defaultValue = "-1") Long  userId,
                               @RequestParam String beginTime,
                               @RequestParam String endTime,
                               @RequestParam(required = false, defaultValue = "1")  Integer page,
                               @RequestParam(required = false, defaultValue = "10")  Integer pageSize)  {
             Object object = null;
             if(page<=0||pageSize<=0){
                     object = Common.getNullRetObj(new ReturnObject<>(ResponseCode.FIELD_NOTVALID), httpServletResponse);
             }else{
                    ReturnObject<PageInfo<VoObject>> returnObject = footprintService.getFootprint(userId,beginTime,endTime, page, pageSize);
                    //logger.debug("findUserById: getUsers = " + returnObject);
                    object = Common.getPageRetObject(returnObject);
             }
             return object;
    }
}
