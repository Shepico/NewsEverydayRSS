/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.shepico.object;

/**
 *
 * @author PS.Sheenkov
 */
public class News {
    private String title;
    private String link;
    private String description;
    private String datePub;
    
    public News(String title, String link, String description, String datePub) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.datePub = datePub;
    }
    
}
