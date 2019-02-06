/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.shepico.object;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

/**
 * @author PS.Sheenkov
 */
public class News implements Comparable<News> {

    //required
    private String guid;
    private String title;
    private String link;
    private LocalDateTime datePub;
    private boolean isRead;
    private String description;


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

    public String getDatePub() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss", Locale.US);
        return (formatter.format(datePub));
    }

    public LocalDateTime getLocalDatePub() {
        return (datePub);
    }

    public String getGuid() {
        return guid;
    }

    public void setIsRead() {
        isRead = true;
    }

    public boolean isRead() {
        return isRead;
    }

    @Override
    public String toString() {

        System.out.println(title + " " + datePub);
        System.out.println(description);
        return null;
    }

    @Override
    public int compareTo(News o) {
        return getDatePub().compareTo(o.getDatePub());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.guid);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final News other = (News) obj;
        if (!Objects.equals(this.guid, other.guid)) {
            return false;
        }
        return true;
    }

}
