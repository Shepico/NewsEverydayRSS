/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.shepico.object;

import javax.persistence.*;

/**
 * @author PS.Sheenkov
 */
@Entity
@Table (name = "CHANNEL")
public class Channel {
    //required
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    private String id;

    @Column (name = "TITLE")
    private String title; //name channel

    @Column (name = "LINK")
    private String link; //link to channel

    @Column (name = "DESC")
    private String desc; //channel description

    //optional
    @Column (name = "ICON")
    private String icon; //channel description

//**************************************
    public Channel(String title, String link, String desk) {
        this.title = title;
        this.link = link;
        this.desc = desk;
    }

    public Channel(String title, String link, String desk, String icon) {
        this.title = title;
        this.link = link;
        this.desc = desk;
        this.icon = icon;
    }

    public Channel(){}

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

    public Object[] getRowChannel() {
        Object[] rowChannel = {getTitle(), getLink(), getDesc()};
        return rowChannel;
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
