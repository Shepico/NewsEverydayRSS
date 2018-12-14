/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.shepico.object;

import org.w3c.dom.Element;

/**
 *
 * @author PS.Sheenkov
 */
public class News {
    //required
    private String title;
    private String link;   
    private String datePub;
        
    public News(String title, String link, String description, String datePub) {        
        this.title = title;
        this.link = link;
        this.description = description;
        this.datePub = datePub;
    }
    
    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getDatePub() {
        return datePub;
    }
    private String description;

    @Override
    public String toString(){
      
      System.out.println(title + " " + datePub);
      System.out.println(description);
      return null;
    }
    
}
