package com.example.todofromscratch.model.net;

import java.util.List;

import com.example.todofromscratch.model.net.TweeterRemoteException;

public class TweeterServerException extends TweeterRemoteException {


    public TweeterServerException(String message, String remoteExceptionType, List<String> remoteStakeTrace) {
        super(message, remoteExceptionType, remoteStakeTrace);
    }
}

