package com.example.todofromscratch.model.net;

import java.util.List;

import com.example.todofromscratch.model.net.TweeterRemoteException;

public class TweeterRequestException extends TweeterRemoteException {

    public TweeterRequestException(String message, String remoteExceptionType, List<String> remoteStakeTrace) {
        super(message, remoteExceptionType, remoteStakeTrace);
    }
}