#!/bin/bash

USERNAME=''
TARGET_HOSTS=()

ENVIRONMENT=$1

cd ../
mvn clean package -pl telcong-anqp-platform -am
STATUS=$?
if [ $STATUS == 1 ]; then
    echo "Build failed with errors."
    exit
fi

cd telcong-anqp-platform

if [[ $ENVIRONMENT = 'test' ]]; then

    USERNAME=clouway
    TARGET_HOSTS=(dev.telcong.com)

fi

if [[ $ENVIRONMENT = 'production' ]]; then

    USERNAME=clouway
    TARGET_HOSTS=(node2.telcong.com node5.telcong.com)

fi

echo "Selected user:" $USERNAME
echo "Destination deployment addresses:" $TARGET_HOSTS

if [ $USERNAME ] && [ ${#TARGET_HOSTS[@]} ] ;then
    for TARGET_HOST in "${TARGET_HOSTS[@]}"
        do

        echo Copying files to $TARGET_HOST

        scp target/telcong-anqp-platform-1.0-SNAPSHOT.jar target/telcong-anqp-platform.jar

        scp target/telcong-anqp-platform.jar $USERNAME@$TARGET_HOST:/opt/telcong/telcong-anqp

        echo Stopping existing instance
        ssh $USERNAME@$TARGET_HOST sudo stop telcong-anqp

        echo Starting new instance
        ssh $USERNAME@$TARGET_HOST sudo start telcong-anqp

    done
fi
