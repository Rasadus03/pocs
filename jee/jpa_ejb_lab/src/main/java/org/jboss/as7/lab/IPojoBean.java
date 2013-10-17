package org.jboss.as7.lab;

import java.util.List;

public interface IPojoBean {

    // assumes client sets pojoId
    public Pojo createPojoViaJTAEntityManager(Pojo pojo);
    public Pojo createPojoViaResourceLocalEntityManager(Pojo pojo);

    public void updatePojo(Pojo pojoObj);
    public void removePojo(String pojoId);
    public Pojo getPojo(String id);
    public List<Pojo> listPojos();
}
