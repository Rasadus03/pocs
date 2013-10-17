
1)  create a java domain model :  TestDomain.java
2)  create java interfaces and bean implementations:  IBeanOne and BeanOne
3)  in sy eclipse plugin, add a component and bean implementation to the pallet
4)  execute:  mvn clean install -DskipTests  .... otherwise next step won't work
5)  in sy eclipse plugin, generate a wsdl from the bean component
6)  in sy eclipse plugin, add a Service to the pallet with a wsdl interface that leverages the previously generated wsdl
7)  in sy eclipse plugin, add a SOAP binding to the new Service
8)  in sy eclipse plugin, promote the component with the new SOAP implemented service
9)  using xjc, generate JABX mapping classes
    xjc -d src/main/java/org/switchyard/quickstarts/domain/ -wsdl src/main/resources/IBeanOne.wsdl
10) manually add jaxb transformations to switchyard.xml
11) add any Java @Transformers to BeanOne implementation
