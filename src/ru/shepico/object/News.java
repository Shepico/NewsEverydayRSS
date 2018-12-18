/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.shepico.object;

import java.time.LocalDateTime;
import org.w3c.dom.Element;

/**
 *
 * @author PS.Sheenkov
 */
public class News implements Comparable<News> {

    //required
    private String title;
    private String link;   
    private LocalDateTime datePub;
    private String guid;
    private boolean isRead;
        
    public News(String title, String link, String description, 
                    LocalDateTime datePub, String guid, boolean isRead) {        
        this.title = title;
        this.link = link;
        this.description = description;
        this.datePub = datePub;
        this.guid = guid;
        this.isRead = isRead;
        
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

    public LocalDateTime getDatePub() {
        return datePub;
    }
    private String description;

    public void setIsRead(){
        isRead = true;
    }
    
    public boolean isRead(){
        return isRead;
    }
    
    @Override
    public String toString(){
      
      System.out.println(title + " " + datePub);
      System.out.println(description);
      return null;
    }    
    
    @Override
    public int compareTo(News o) {
        return getDatePub().compareTo(o.getDatePub());
    }
}
