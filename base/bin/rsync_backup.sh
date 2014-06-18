#!/bin/sh

### ======================================== ###
###    JA Bride , 29 Nov 2010           ###
### ======================================== ###

PHOTOS_HOME=/u02/photos
MUSIC_HOME=/u02/music
CUSTOMERS_HOME=/u02/customers/oldSoftware
MOVIES_HOME=/u02/movies
DOWNLOADS_HOME=/u02/downloads
VIRTUAL_MACHINES=/u02/virtual_machines
RECORDINGS=/u02/redhat/recordings

REMOTE_USER=jbride
REMOTE_IP=localhost
#RSYNC_PATH=$REMOTE_USER@$REMOTE_IP:/media/seagate
RSYNC_PATH="/run/media/jbride/_u03"

RSYNC_DROID_PATH="/home/jbride/Misc/Nexus4/"
LOCAL_DROID_PHOTOS_PATH=$PHOTOS_HOME/Nexus4


syncBackupFromLocal() {
    cd $HOME    
    echo " ***** now synching in : $RSYNC_PATH with $HOME"    
    rsync -trv --delete . --include=.bashrc --include=./My\ Kindle\ Content --include=.ssh --include=.m2 --exclude=.* --exclude=downloads $RSYNC_PATH/jbride    
    rsyncReturnCode=$?    
    if [ $rsyncReturnCode -ne 0 ];then    
        exit 1;    
    fi    
        
    cd $PHOTOS_HOME    
    echo " ***** now synching in : $PHOTOS_HOME at :  $RSYNC_PATH"    
    rsync -trv . --exclude=.* $RSYNC_PATH/photos    
    rsyncReturnCode=$?    
    if [ $rsyncReturnCode -ne 0 ];then    
        exit 1;    
    fi    
    cd $MUSIC_HOME    
    echo " ***** now synching in : $MUSIC_HOME at :  $RSYNC_PATH"    
    rsync -trv --delete . --exclude=.* $RSYNC_PATH/music    
    rsyncReturnCode=$?    
    if [ $rsyncReturnCode -ne 0 ];then    
        exit 1;    
    fi    
        
    cd $CUSTOMERS_HOME    
    echo " ***** now synching in : $CUSTOMERS_HOME at :  $RSYNC_PATH"    
    rsync -trv --delete . --exclude=.* --exclude=hp/sdm/jboss $RSYNC_PATH/customers    
    rsyncReturnCode=$?    
    if [ $rsyncReturnCode -ne 0 ];then    
        exit 1;    
    fi    
        
    cd $MOVIES_HOME    
    echo " ***** now synching in : $MOVIES_HOME at :  $RSYNC_PATH"    
    rsync -trv . --exclude=.* $RSYNC_PATH/movies    
    rsyncReturnCode=$?    
    if [ $rsyncReturnCode -ne 0 ];then    
        exit 1;    
    fi    
        
    cd $DOWNLOADS_HOME    
    echo " ***** now synching in : $DOWNLOADS_HOME at :  $RSYNC_PATH"    
    rsync -trv --delete . --exclude=.* $RSYNC_PATH/downloads    
    rsyncReturnCode=$?    
    if [ $rsyncReturnCode -ne 0 ];then    
        exit 1;    
    fi    

    cd $VIRTUAL_MACHINES   
    echo " ***** now synching in : $VIRTUAL_MACHINES at :  $RSYNC_PATH"    
    rsync -trv --delete . --exclude=.* --exclude=docker* $RSYNC_PATH/virtual_machines    
    rsyncReturnCode=$?    
    if [ $rsyncReturnCode -ne 0 ];then    
        exit 1;    
    fi    

    cd $RECORDINGS
    echo " ***** now synching in : $RECORDINGS at :  $RSYNC_PATH"    
    rsync -trv --delete . --exclude=.* $RSYNC_PATH/recordings    
    rsyncReturnCode=$?    
    if [ $rsyncReturnCode -ne 0 ];then    
        exit 1;    
    fi    
}

syncDroidFromLocal() {
    cd $MUSIC_HOME/default    
    echo " ***** now synching in : $MUSIC_HOME at :  $RSYNC_DROID_PATH/Music/default"    
    rsync -trv --delete . $RSYNC_DROID_PATH/Music/default 
    rsyncReturnCode=$?    
    if [ $rsyncReturnCode -ne 0 ];then    
        exit 1;    
    fi    
}

syncLocalFromDroid() {
    cd $RSYNC_DROID_PATH/DCIM
    echo " ***** now synching in : $LOCAL_DROID_PHOTOS_PATH with $RSYNC_DROID_PATH/DCIM"
    rsync -trv . $LOCAL_DROID_PHOTOS_PATH/
    rsyncReturnCode=$?
    if [ $rsyncReturnCode -ne 0 ];then
        exit 1;
    fi

    cd $RSYNC_DROID_PATH/Download
    echo " ***** now synching in : $LOCAL_DROID_PHOTOS_PATH with $RSYNC_DROID_PATH/download"
    rsync -trv . --include=*.jpeg $LOCAL_DROID_PHOTOS_PATH
    rsyncReturnCode=$?
    if [ $rsyncReturnCode -ne 0 ];then
        exit 1;
    fi
}

_TestSSH() {
    local user=${1}
    local timeout=${2}

    ssh -q -q -o "BatchMode=yes" -o "ConnectTimeout ${timeout}"  -l $REMOTE_USER $REMOTE_IP "echo 2>&1" && return 0 || {
        echo "WARN:  no access to: $REMOTE_IP";
        REMOTE_IP=ratwater.dyndns.org
        echo "WARN:  will try again with: $REMOTE_IP";
        ssh -q -q -o "BatchMode=yes" -o "ConnectTimeout ${timeout}" -l $REMOTE_USER $REMOTE_IP "echo 2>&1" && return 0 || {
            echo "ERROR: no access to: $REMOTE_IP";
            exit 1;
        }
    }
}

#_TestSSH jbride 5

case "$1" in
    syncDroidFromLocal|syncLocalFromDroid|syncBackupFromLocal)
        $1
        ;;
    *)
    echo 1>&2 $"Usage: $0 {syncDroidFromLocal|syncLocalFromDroid|syncBackupFromLocal}"
    exit 1
esac

