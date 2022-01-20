/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mastermind.model;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Wafa Mekki
 */
@Data
public class Round {
    private int id;
    private LocalDateTime timeAttempt;
    private int guess;
    private String result;
    private int gameId;

}
