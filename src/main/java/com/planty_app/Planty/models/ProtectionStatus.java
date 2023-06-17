package com.planty_app.Planty.models;

public enum ProtectionStatus {
    DISSAPEARED("Исчезнувшие"),
    THREATENED("Под угрозой исчезновения"),
    RARE("Редкие"),
    USUAL("Обычные");
    
    private final String status;
    ProtectionStatus(String status){
        this.status=status;
    }
}
