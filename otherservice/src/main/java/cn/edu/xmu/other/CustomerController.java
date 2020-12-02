package cn.edu.xmu.other;

import cn.edu.xmu.ooad.util.Common;
import cn.edu.xmu.ooad.util.ResponseUtil;
import cn.edu.xmu.ooad.util.ReturnObject;
import cn.edu.xmu.other.model.bo.Customer;
import cn.edu.xmu.other.model.vo.StateVo;
import cn.edu.xmu.other.service.CustomerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "买家用户服务", tags = "user")
@RestController /*Restful的Controller对象*/
@RequestMapping(value = "/user", produces = "application/json;charset=UTF-8")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @ApiOperation(value="获得买家的所有状态")
    @ApiResponses({
            @ApiResponse(code = 0,message = "成功")
    })
    @GetMapping("users/states")
    public Object getAllstates(){
        Customer.State[] states=Customer.State.class.getEnumConstants();
        List<StateVo> stateVos=new ArrayList<StateVo>();
        for(int i=0;i<states.length;i++){
            stateVos.add(new StateVo(states[i]));
        }
        return ResponseUtil.ok(new ReturnObject<List>(stateVos).getData());
    }

    @ApiOperation(value="平台管理员解禁买家")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="Token", required = true, dataType="String", paramType="header"),
            @ApiImplicitParam(name="id", required = true, dataType="Integer", paramType="path")
    })
    @ApiResponses({
            @ApiResponse(code = 0,message = "成功")
    })
    @PutMapping("users/{id}/ban")
    public Object releaseCustomer(@PathVariable Long id){
       ReturnObject retObject=customerService.banCustomer(id);
       return Common.decorateReturnObject(retObject);
    }

    @ApiOperation(value="平台管理员封禁买家")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="Token", required = true, dataType="String", paramType="header"),
            @ApiImplicitParam(name="id", required = true, dataType="Integer", paramType="path")
    })
    @ApiResponses({
            @ApiResponse(code = 0,message = "成功")
    })
    @PutMapping("users/{id}/release")
    public Object banCustomer(@PathVariable Long id){
        ReturnObject retObject=customerService.releaseCustomer(id);
        return Common.decorateReturnObject(retObject);
    }
}

