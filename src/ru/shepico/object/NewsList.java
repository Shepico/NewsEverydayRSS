/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.shepico.object;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author PS.Sheenkov
 */
public class NewsList {
    private ArrayList<News> newsList;
    private Channel channel;    
    
    public NewsList(Channel channel){
        this.channel = channel;
    }
    
    public void addNews(News news){
        if (newsList == null) {
            newsList = new ArrayList<>();
        }
        newsList.add(news);        
    }
    
    public ArrayList<News> getNewsList(){
        return (newsList);
    }
    
    public int getSize(){
        return newsList.size();
    }
    
    public News getNews(int index){
        return newsList.get(index);
    }
    
    public void sortDatePub(){
        Collections.sort(newsList);
        Collections.reverse(newsList);
    }
}
