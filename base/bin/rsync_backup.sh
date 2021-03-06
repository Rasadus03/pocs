#!/bin/sh

### ======================================== ###
###    JA Bride , 29 Nov 2010                ###
### ======================================== ###

PHOTOS_HOME=/u02/photos
AUDIO_HOME=/u02/audio
CUSTOMERS_HOME=/u02/customers
OLD_SOFTWARE_HOME=/u02/oldSoftware
VIDEO_HOME=/u02/video
DOWNLOADS_HOME=/u02/downloads
VIRTUAL_MACHINES=/u02/virtual_machines
RECORDINGS=/u02/redhat/recordings

REMOTE_USER=jbride
REMOTE_IP=localhost
RSYNC_PATH="/run/media/jbride/_u03"

# simple-mtpfs $HOME/phone
# sudo umount $HOME/phone
RSYNC_DROID_PATH="$HOME/phone/"
LOCAL_DROID_PHOTOS_PATH=$PHOTOS_HOME/Nexus4


syncBackupFromLocal() {
    cd $HOME    
    echo " ***** now synching in : $RSYNC_PATH with $HOME"    
    rsync -trv --delete . --include=.bashrc --include=./My\ Kindle\ Content --include=.ssh --include=.m2 --include=.ethereum/keystore --include=.gnupg --exclude=.* --exclude=downloads $RSYNC_PATH/jbride    
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
    
    cd $AUDIO_HOME    
    echo " ***** now synching in : $AUDIO_HOME at :  $RSYNC_PATH"    
    rsync -trv . --exclude=.* $RSYNC_PATH/audio    
    rsyncReturnCode=$?    
    if [ $rsyncReturnCode -ne 0 ];then    
        exit 1;    
    fi    
        
    cd $RECORDINGS
    echo " ***** now synching in : $RECORDINGS at :  $RSYNC_PATH"    
    #rsync -trv --delete . --exclude=.* $RSYNC_PATH/recordings    
    rsync -trv . --exclude=.* $RSYNC_PATH/recordings    
    rsyncReturnCode=$?    
    if [ $rsyncReturnCode -ne 0 ];then    
        exit 1;    
    fi 
   
    cd $VIDEO_HOME    
    echo " ***** now synching in : $VIDEO_HOME at :  $RSYNC_PATH"    
    rsync -trv . --exclude=.* $RSYNC_PATH/video    
    rsyncReturnCode=$?    
    if [ $rsyncReturnCode -ne 0 ];then    
        exit 1;    
    fi    

    #cd $CUSTOMERS_HOME    
    #echo " ***** now synching in : $CUSTOMERS_HOME at :  $RSYNC_PATH"    
    #rsync -trv --delete . --exclude=.* --exclude=hp/sdm/jboss $RSYNC_PATH/customers    
    #rsyncReturnCode=$?    
    #if [ $rsyncReturnCode -ne 0 ];then    
    #    exit 1;    
    #fi    

    #cd $OLD_SOFTWARE_HOME    
    #echo " ***** now synching in : $OLD_SOFTWARE_HOME at :  $RSYNC_PATH"    
    #rsync -trv --delete . --exclude=.* $RSYNC_PATH/oldSoftware    
    #rsyncReturnCode=$?    
    #if [ $rsyncReturnCode -ne 0 ];then    
    #    exit 1;    
    #fi    
        
    #cd $DOWNLOADS_HOME    
    #echo " ***** now synching in : $DOWNLOADS_HOME at :  $RSYNC_PATH"    
    #rsync -trv --delete . --exclude=.* $RSYNC_PATH/downloads    
    #rsyncReturnCode=$?    
    #if [ $rsyncReturnCode -ne 0 ];then    
    #    exit 1;    
    #fi    

    #cd $VIRTUAL_MACHINES   
    #echo " ***** now synching in : $VIRTUAL_MACHINES at :  $RSYNC_PATH"    
    #rsync -trv --delete . --exclude=.* --exclude=docker* $RSYNC_PATH/virtual_machines    
    #rsyncReturnCode=$?    
    #if [ $rsyncReturnCode -ne 0 ];then    
    #    exit 1;    
    #fi    
}

syncDroidFromLocal() {
    cd $AUDIO_HOME/default    
    echo " ***** now synching in : $AUDIO_HOME at :  $RSYNC_DROID_PATH/Music/default"    
    rsync -trv --delete . $RSYNC_DROID_PATH/Music/default 
    rsyncReturnCode=$?    
    if [ $rsyncReturnCode -ne 0 ];then    
        exit 1;    
    fi    
}

syncLocalFromDroid() {
    if [ ! -d "$RSYNC_DROID_PATH" ]; then
        echo "$RSYNC_DROID_PATH does not exist";
        exit 1;
    fi
    if [ ! -d "$LOCAL_DROID_PHOTOS_PATH" ]; then
        echo "$LOCAL_DROID_PHOTOS_PATH does not exist";
        exit 1;
    fi
    cd $RSYNC_DROID_PATH/DCIM
    echo " ***** now synching in : $LOCAL_DROID_PHOTOS_PATH with $RSYNC_DROID_PATH/DCIM"
    rsync -trv . $LOCAL_DROID_PHOTOS_PATH/DCIM
    rsyncReturnCode=$?
    if [ $rsyncReturnCode -ne 0 ];then
        exit 1;
    fi

    cd $RSYNC_DROID_PATH/Download
    echo " ***** now synching in : $LOCAL_DROID_PHOTOS_PATH/download with $RSYNC_DROID_PATH/download"
    rsync -trv . --include=*.jpeg $LOCAL_DROID_PHOTOS_PATH/download
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

