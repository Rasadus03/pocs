background
    -- http://www.oraclejavamagazine-digital.com/javamagazine/20120102#pg50
    -- http://javahowto.blogspot.com/2010/03/simple-asynchronous-methods-in-ejb-31.html
    -- alternative to JMS for *local* Observer pattern / transient event delivery
    -- JMS still the correct tool for :
        1)  "once and only once" delivery semantics
        2)  durable subscriber paradigm
    -- javax.ejb.Asynchronous
        -- there is only one attempt
        -- if it fails, the trnx will be rolled back
        -- java.util.concurrent.Future makes it easy for an EJB 3.1 bean to return a pointer to the return value
    -- type-safety
        -- JMS is not type-safe ... need to wrap actual payload into appropriate javax.jms.Message at the sender and unwrap it in the message-driven bean
        -- CDI 'eventing' on the other hand is type-safe:  payload arrives in the message listener "as is"
    -- lean pub-sub
        -- w/ CDI & EJB3.1, can implement lean pub-sub without JMS
        -- CDI includes a built-in event:  the injectable javax.enterprise.event.Event
            -- allows for firing any object to all listeners locally
            -- if there are no listeners, the broadcasted payload just disappers
