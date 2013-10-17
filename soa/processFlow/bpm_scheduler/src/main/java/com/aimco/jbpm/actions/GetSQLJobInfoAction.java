package com.aimco.jbpm.actions;

import java.util.Random;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.context.exe.ContextInstance;

import org.apache.log4j.Logger;
import com.aimco.domain.AimcoDomain;

public class GetSQLJobInfoAction implements ActionHandler  {
    private static final long serialVersionUID = 1L;
    private static Random rGenerator  = new Random();
    private Logger log = Logger.getLogger("GetSQLJobInfoAction");

    public void execute(ExecutionContext exCtx) throws Exception {
        try {
            int newVar = rGenerator.nextInt(5);
            Token token = exCtx.getToken();
            ContextInstance context = token.getProcessInstance().getContextInstance();
            AimcoDomain dObj = (AimcoDomain)context.getVariable("aimcoDomain",token);
            dObj.setAimcoVar(newVar);
            token.signal();
        } catch(Throwable x) {
            x.printStackTrace();
        }
    }
}
