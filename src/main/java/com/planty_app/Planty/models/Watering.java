package com.planty_app.Planty.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.Duration;
import java.time.Period;
import java.util.Arrays;

@Entity
@With
@AllArgsConstructor
@NoArgsConstructor
public class Watering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    private int[] amountOfWaterKey;//days
    private int[] amountOfWaterValue;//corresponding amount of water
    private Period period;
    
    public String getAmountOfWaterKey(){
        return Arrays.toString(amountOfWaterKey);
    }
    
    public String getAmountOfWaterValue(){
        return Arrays.toString(amountOfWaterValue);
    }
    
    public String getPeriod(){
        return Integer.toString(period.getDays());
    }
    
    public void setAmountOfWaterKey(String amountOfWaterKey){
        String[] values=amountOfWaterKey.split(",");
        this.amountOfWaterKey=new int[values.length];
        for (int i=0; i<values.length; i++) {
            this.amountOfWaterKey[i]=Integer.parseInt(values[i]);
        }
    }
    
    public void setAmountOfWaterValue(String amountOfWaterValue){
        String[] values=amountOfWaterValue.split(",");
        this.amountOfWaterValue=new int[values.length];
        for (int i=0; i<values.length; i++) {
            this.amountOfWaterValue[i]=Integer.parseInt(values[i]);
        }
    }
    
    public void setPeriod(String days){
        this.period=Period.ofDays(Integer.parseInt(days));
    }
}
