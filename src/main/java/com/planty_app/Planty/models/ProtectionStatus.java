package com.planty_app.Planty.models;

import lombok.Getter;

@Getter
public enum ProtectionStatus {
    DISSAPEARED("DISSAPEARED"),
    THREATENED("THREATENED"),
    RARE("RARE"),
    USUAL("USUAL");
    
    private final String status;
    ProtectionStatus (String status){
        this.status=status;
    }
}
