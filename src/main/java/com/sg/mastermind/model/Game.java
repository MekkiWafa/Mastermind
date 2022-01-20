/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mastermind.model;

import lombok.Data;


/**
 * @author Wafa Mekki
 */
@Data
public class Game {
    private int id;
    private int answer;
    private boolean finished;
}
