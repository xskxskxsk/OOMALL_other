package cn.edu.xmu.other;

import io.swagger.annotations.Api;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Api(value="购物车服务",tags = "cart")
@RestController
@RequestMapping(value = "/cart",produces = "application/json;charset=UTF-8")
public class CartController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(CartController.class);



}
