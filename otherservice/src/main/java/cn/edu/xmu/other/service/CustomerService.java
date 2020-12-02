package cn.edu.xmu.other.service;

import cn.edu.xmu.ooad.util.ReturnObject;
import cn.edu.xmu.other.dao.CustomerDao;
import cn.edu.xmu.other.model.bo.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerDao customerDao;

    public ReturnObject<Object> banCustomer(Long id){
        return customerDao.changeCustomerState(id,Customer.State.FORBID);
    }

    public ReturnObject<Object> releaseCustomer(Long id){
        return customerDao.changeCustomerState(id,Customer.State.NORM);
    }
}
