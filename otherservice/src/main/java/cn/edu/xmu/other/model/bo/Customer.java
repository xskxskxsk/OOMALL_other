package cn.edu.xmu.other.model.bo;

import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.encript.AES;
import cn.edu.xmu.other.model.po.CustomerPo;
import cn.edu.xmu.other.model.vo.CustomerVo;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class Customer implements VoObject {
    public void setState(State state) {
        this.state=state;
    }

    //public static String AESPASS = "OOAD2020-11-01";
    public enum State {
        NEW(0, "新注册"),
        NORM(1, "正常"),
        FORBID(2, "封禁"),
        DELETE(3, "废弃");

        private static final Map<Integer, Customer.State> stateMap;

        static { //由类加载机制，静态块初始加载对应的枚举属性到map中，而不用每次取属性时，遍历一次所有枚举值
            stateMap = new HashMap();
            for (Customer.State enum1 : values()) {
                stateMap.put(enum1.code, enum1);
            }
        }

        private int code;
        private String description;

        State(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public static Customer.State getTypeByCode(Integer code) {
            return stateMap.get(code);
        }

        public Integer getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }

    private Long id;
    private String userName;
    private String password;
    private String realName;
    private Byte gender;
    private LocalDateTime birthday;
    private Integer point;
    private String email;
    private String mobile;
    private Byte beDeleted;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private State state=State.NORM;

    public Customer(CustomerPo po){
        this.id=po.getId();
        this.userName=po.getUserName();
        this.password=po.getPassword();
        this.realName=po.getRealName();
        this.gender=po.getGender();
        this.birthday=po.getBirthday();
        this.point=po.getPoint();
        this.email=po.getEmail();
        this.mobile=po.getMobile();
        this.beDeleted=po.getBeDeleted();
        this.gmtCreate=po.getGmtCreate();
        this.gmtModified=po.getGmtModified();

    }
    public CustomerPo createUpdatePo(CustomerVo vo){
        //String nameEnc = vo.getName() == null ? null : AES.encrypt(vo.getName(), User.AESPASS);
        //String mobEnc = vo.getMobile() == null ? null : AES.encrypt(vo.getMobile(), User.AESPASS);
        //String emlEnc = vo.getEmail() == null ? null : AES.encrypt(vo.getEmail(), User.AESPASS);

       CustomerPo customerPo=new CustomerPo();
       Byte state=(byte)this.state.code;

       customerPo.setId(id);
       customerPo.setBirthday(vo.getBirthday());
       customerPo.setRealName(vo.getRealName());
       customerPo.setGender(vo.getGender());
       customerPo.setState(state);
       return customerPo;
    }












































    @Override
    public Object createVo() {
        return null;
    }

    @Override
    public Object createSimpleVo() {
        return null;
    }
}
