package org.switchyard.quickstarts.domain;

import org.switchyard.annotations.Transformer;
import org.switchyard.component.bean.Service;

@Service(IBeanTwo.class)
public class BeanTwo implements IBeanTwo {
   
    public TestDomain modifyField2(TestDomain tObj) {
        if(tObj != null) { 
            tObj.setField2(new Integer(2));
            System.out.println("BeanTwo.modifyField2() modified field 2");
        } else{
            System.out.println("BeanTwo.modifyField2() oh-no.  TestDomain == null");
        }
        return tObj;
    }
    
    @Transformer(from="java:org.switchyard.quickstarts.domain.ModifyField2")
    public TestDomain transform(ModifyField2 mf2) {
        return mf2.getArg0();
    }
    @Transformer(from="java:org.switchyard.quickstarts.domain.TestDomain")
    public ModifyField2Response transform(TestDomain tObj) {
        ModifyField2Response mfObj = new ModifyField2Response();
        mfObj.setReturn(tObj);
        return mfObj;
    }

}
