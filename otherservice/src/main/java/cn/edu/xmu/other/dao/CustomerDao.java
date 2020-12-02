package cn.edu.xmu.other.dao;

import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import cn.edu.xmu.other.mapper.CustomerPoMapper;
import cn.edu.xmu.other.model.bo.Customer;
import cn.edu.xmu.other.model.po.CustomerPo;
import cn.edu.xmu.other.model.vo.CustomerVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao {

    @Autowired
    private CustomerPoMapper customerPoMapper;

    private static final Logger logger = LoggerFactory.getLogger(CustomerDao.class);

    public CustomerPo createCustomerStateModifyPo(Long id,Customer.State state){
        CustomerPo po=customerPoMapper.selectByPrimaryKey(id);
        if(po==null||po.getBeDeleted()==1) return null;
        CustomerVo customerVo=null;
        Customer customer=new Customer(po);
        customer.setState(state);
        return customer.createUpdatePo(customerVo);
    }
    public ReturnObject<Object> changeCustomerState(Long id, Customer.State state){
        CustomerPo po=createCustomerStateModifyPo(id,state);

        if (po == null) {
            logger.info("用户不存在或已被删除：id = " + id);
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        }
        ReturnObject<Object> retObj;
        int ret;
        try {
            ret = customerPoMapper.updateByPrimaryKeySelective(po);
            if (ret == 0) {
                logger.info("用户不存在或已被删除：id = " + id);
                retObj = new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
            } else {
                logger.info("用户 id = " + id + " 的状态修改为 " + state.getDescription());
                retObj = new ReturnObject<>();
            }
        } catch (DataAccessException e) {
            // 数据库错误
            logger.error("数据库错误：" + e.getMessage());
            retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR,
                    String.format("发生了严重的数据库错误：%s", e.getMessage()));
        } catch (Exception e) {
            // 属未知错误
            logger.error("严重错误：" + e.getMessage());
            retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR,
                    String.format("发生了严重的未知错误：%s", e.getMessage()));
        }
        return retObj;
    }
}
