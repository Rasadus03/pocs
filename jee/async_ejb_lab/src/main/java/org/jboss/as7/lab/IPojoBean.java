package org.jboss.as7.lab;

import java.util.concurrent.Future;

public interface IPojoBean {

    public void multiplyAndIgnore(int a, int b);
    public Future<Integer> multiplyAndReturn(int a, int b);

}
