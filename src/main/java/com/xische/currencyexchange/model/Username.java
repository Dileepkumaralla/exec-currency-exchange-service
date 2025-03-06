package com.xische.currencyexchange.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class Username implements Serializable {

    private String username;

    @JsonCreator
    public Username(String username) {
        this.username = username;
    }
}