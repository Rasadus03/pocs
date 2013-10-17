package com.aimco.jbpm.actions;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.context.exe.ContextInstance;

import org.apache.log4j.Logger;

import com.aimco.domain.AimcoDomain;

public class FireSQLRulesAction implements ActionHandler  {
    private static final long serialVersionUID = 1L;
    private Logger log = Logger.getLogger("FireSQLRulesAction");

    public void execute(ExecutionContext exCtx) throws Exception {
        try {
            Token token = exCtx.getToken();
            ContextInstance context = token.getProcessInstance().getContextInstance();
            AimcoDomain dObj = (AimcoDomain)context.getVariable("aimcoDomain",token);
            if(dObj.getAimcoVar() > 3) { 
                context.setVariable("branchName", "startSQLJob");
            } else {
                context.setVariable("branchName", "getSQLJobInfo");
            }
            token.signal();
        } catch(Throwable x) {
            x.printStackTrace();
        }
    }
}
