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
public class ChannelList {
    
    private ArrayList<Channel> channels;
    
    public ChannelList(){
        channels = new ArrayList();
    }
    
    public void addChannel(Channel channel){
        channels.add(channel);
    }
    
    public Channel getChannel(int index){
        return (channels.get(index));
    } 
    
    public ArrayList<Channel> getChannelList(){
        return (channels);
    } 
    
    public int getSizeChannelList(){
        return channels.size();
    }
    
}
