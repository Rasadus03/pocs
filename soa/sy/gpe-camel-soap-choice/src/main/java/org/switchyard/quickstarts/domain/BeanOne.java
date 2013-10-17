package org.switchyard.quickstarts.domain;

import org.switchyard.annotations.Transformer;
import org.switchyard.component.bean.Service;

@Service(IBeanOne.class)
public class BeanOne implements IBeanOne {

    public TestDomain modifyField1(TestDomain tObj) {
        if(tObj != null) {
            tObj.setField1("modified field1");
            System.out.println("BeanOne.modifyField1():  modified field 1");
        }else {
            System.out.println("BeanOne.modifyField1() oh-no:  TestDomain obj = null ");
        }
        return tObj;
    }

    @Transformer(from="java:org.switchyard.quickstarts.domain.ModifyField1")
    public TestDomain transform(ModifyField1 mf1) {
        return mf1.getArg0();
    }
    @Transformer(from="java:org.switchyard.quickstarts.domain.TestDomain")
    public ModifyField1Response transform(TestDomain tObj) {
        ModifyField1Response mfObj = new ModifyField1Response();
        mfObj.setReturn(tObj);
        return mfObj;
    }

}
