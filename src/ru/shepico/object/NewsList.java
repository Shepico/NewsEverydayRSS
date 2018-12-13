/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.shepico.object;

import java.util.ArrayList;

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
        newsList.add(news);        
    }
    
    public ArrayList<News> getNewsList(){
        return (newsList);
    }
}
