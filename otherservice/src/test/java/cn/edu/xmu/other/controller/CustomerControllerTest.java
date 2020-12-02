package cn.edu.xmu.other.controller;

import cn.edu.xmu.ooad.util.JwtHelper;
import cn.edu.xmu.ooad.util.ResponseCode;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CustomerControllerTest {
    @Autowired
    private MockMvc mvc;
   // private static final Logger logger = LoggerFactory.getLogger(PrivilegeControllerTest2.class);
    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        //logger.debug(token);
        return token;
    }

    /*
    获取所有状态
     */
    @Test
    public void getAllStateTest()throws Exception{
        String responseString=this.mvc.perform(get("/users/states"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse="{ \"errno\": 0, \"data\": [ { \"name\": \"新注册\", \"code\": 0 }, { \"name\": \"正常\", \"code\": 1 }, { \"name\": \"封禁\", \"code\": 2 }, { \"name\": \"废弃\", \"code\": 3 } ], \"errmsg\": \"成功\" }";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }

    /*
    平台管理员封禁买家(正常）
     */
     @Test
    public void banCustomerTest1()throws Exception{
         String token = creatTestToken(1L, 0L, 100);
         String responseString=this.mvc.perform(put("/users/2/ban").header("authorization", token))
                         .andExpect(status().isOk())
                         .andExpect(content().contentType("application/json;charset=UTF-8"))
                         .andExpect(jsonPath("$.errno").value(ResponseCode.OK.getCode()))
                         .andExpect(jsonPath("$.errmsg").value("成功"))
                         .andReturn().getResponse().getContentAsString();
     }

     /*
     平台管理员封禁买家(id不存在）
      */
     @Test
     public void banCustomerTest2()throws Exception{
         String token = creatTestToken(1L, 0L, 100);
         String responseString=this.mvc.perform(put("/users/0/ban").header("authorization", token))
                 .andExpect(status().isNotFound())
                 .andExpect(content().contentType("application/json;charset=UTF-8"))
                 .andExpect(jsonPath("$.errno").value(ResponseCode.RESOURCE_ID_NOTEXIST.getCode()))
                 .andExpect(jsonPath("$.errmsg").value("买家用户id不存在或已删除"))
                 .andReturn().getResponse().getContentAsString();
     }

    /*
    平台管理员封禁买家(已被逻辑删除）
     */
    @Test
    public void banCustomerTest3()throws Exception{
        String token = creatTestToken(1L, 0L, 100);
        String responseString=this.mvc.perform(put("/users/1/ban").header("authorization", token))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.errno").value(ResponseCode.RESOURCE_ID_NOTEXIST.getCode()))
                .andExpect(jsonPath("$.errmsg").value("买家用户id不存在或已删除"))
                .andReturn().getResponse().getContentAsString();
    }
}
