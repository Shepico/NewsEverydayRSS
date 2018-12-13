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
    private String image; //image channel
    
    
    public Channel(String title, String link, String desk){
        this.title = title;
        this.link = link;
        this.desc = desk;        
    }
    
    public Channel(String title, String link, String desk, String image){
        this.title = title;
        this.link = link;
        this.desc = desk;
        this.image = image;
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

    public String getImage() {
        return image;
    }
    
    //set
    public void setImage() {
        // TODO тут сделать загрузку картинку по дефолту
        this.image = "";
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    
    
}
