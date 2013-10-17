package org.jboss.gpse;

import org.switchyard.component.bean.Service;

@Service(SanityCheck.class)
public class SanityCheckBean implements SanityCheck {

    public void health(String payload){
        System.out.println("Great Success. payload = "+payload);
    }

}
