#!/bin/sh

for var in $@
do
    case $var in
        -hostName=*)
            hostName=`echo $var | cut -f2 -d\=`
            ;;
        -serverConfig=*)
            serverConfig=`echo $var | cut -f2 -d\=`
            ;;
        -jbossSocketBindingPortOffset=*)
            jbossSocketBindingPortOffset=`echo $var | cut -f2 -d\=`
            ;;
        -jbossHome=*)
            jbossHome=`echo $var | cut -f2 -d\=`
            ;;
        -jbossServerBaseDir=*)
            jbossServerBaseDir=`echo $var | cut -f2 -d\=`
            ;;
        -jbossNodeName=*)
            jbossNodeName=`echo $var | cut -f2 -d\=`
            ;;
    esac
done


start() {
    cd $jbossHome
    chmod 755 bin/*.sh

    if [ "x$jbossModulePath" != "x" ]; then
        export JBOSS_MODULEPATH=$jbossModulePath
    fi
    
    export JBOSS_HOME=$jbossHome

    if [ "$jbossServerBaseDir" = "standalone" ]; then
        #  defining jboss.server.base.dir causes problems when deploying SOAP service on AS7.1.1    :   http://pastebin.com/qyX1crrT 
        #  "standalone" server will be reserved for core functionality that needs SOAP
        echo -en "./bin/standalone.sh -b=$hostName -bmanagement=$hostName --server-config=$serverConfig -Djboss.socket.binding.port-offset=$jbossSocketBindingPortOffset -Djboss.node.name=$jbossNodeName $JAVA_OPTS\n"
        nohup ./bin/standalone.sh -b=$hostName -bmanagement=$hostName --server-config=$serverConfig -Djboss.socket.binding.port-offset=$jbossSocketBindingPortOffset -Djboss.node.name=$jbossNodeName $JAVA_OPTS &
    else
        echo -en "./bin/standalone.sh -b=$hostName -bmanagement=$hostName --server-config=$serverConfig -Djboss.server.base.dir=$jbossServerBaseDir -Djboss.socket.binding.port-offset=$jbossSocketBindingPortOffset -Djboss.node.name=$jbossNodeName $JAVA_OPTS"
        nohup ./bin/standalone.sh -b=$hostName -bmanagement=$hostName --server-config=$serverConfig -Djboss.server.base.dir=$jbossServerBaseDir -Djboss.socket.binding.port-offset=$jbossSocketBindingPortOffset -Djboss.node.name=$jbossNodeName $JAVA_OPTS &
    fi

    if [ "x$sleepSec" !=  "x" ]; then
        sleep $sleepSec
    else
        sleep 3
    fi
}

case "$1" in
    start )
        $1
        ;;
    *)
    echo 1>&2 $"Usage: $0 {start}"
    exit 1
esac
