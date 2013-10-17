package org.jboss.gpse;

import org.switchyard.component.bean.Service;

@Service(ISanityCheck.class)
public class SanityCheckBean implements ISanityCheck {
	public String health(){
		return "Great Success";
	}

}
