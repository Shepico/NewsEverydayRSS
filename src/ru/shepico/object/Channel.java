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
public class Channel {
    //required
    private String title; //name channel
    private String link; //link to channel  
    private String desc; //channel description
    
    //optional
    private String icon; //icon channel link
    
    
    public Channel(String title, String link, String desk){
        this.title = title;
        this.link = link;
        this.desc = desk;        
    }
    
    public Channel(String title, String link, String desk, String icon){
        this.title = title;
        this.link = link;
        this.desc = desk;
        this.icon = icon;
    }
    
    //get
    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDesc() {
        return desc;
    }

    public String getIcon() {
        return icon;
    }
    
    //set
    public void setIcon() {
        // TODO тут сделать загрузку картинку по дефолту
        this.icon = "";
    }
    
    public void setIcon(String image) {
        this.icon = image;
    }
    
    
    
}
