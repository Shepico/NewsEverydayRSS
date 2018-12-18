/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.shepico.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import ru.shepico.object.Channel;
import ru.shepico.object.ChannelList;

/**
 * 
 * @author PS.Sheenkov
 */

//for H2.console (jdbc:h2:C:/Users/PS.Sheenkov/Documents/NetBeansProjects/NewsEverydayRss/db/rssDB)
public class DBaccess {
    private Connection connect;
    private Statement statement;
    private ResultSet result;
    
    public DBaccess(){
        try {
            Class.forName("org.h2.Driver").newInstance();
            connect = DriverManager.getConnection("jdbc:h2:./db/rssDB", "sa", "");
            statement = null;
            statement = connect.createStatement();
            result = null;
           
            //statement.execute("INSERT INTO CHANNEL VALUES(1,'yaandex', 'ww.ya.ru', 'about', '')");
            
            /*st.execute("INSERT INTO TEST(NAME) VALUES('JOHN')");
            String name1 = "Jack";
            String q = "insert into TEST(name) values(?)";
            PreparedStatement st1 = null;

            st1 = conn.prepareStatement(q);
            st1.setString(1, name1);
            st1.execute();*/

            
            /*result = statement.executeQuery("SELECT * FROM CHANNEL");
            while (result.next()) {
                String title = result.getString("title");
                String link = result.getString("link");
                String desc = result.getString("desc");
                System.out.println(result.getString("ID")+" "+title);
                System.out.println(link+" "+desc);
            }*/
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
    }
    
    /*public static void main(String[] args) {
        new DBaccess();
        
    }*/
    
    private int getIDmax(){
        int maxID = 0;
        try{
            result = statement.executeQuery("SELECT MAX(ID) FROM CHANNEL");
            if (result.next()) {
                maxID = result.getInt("MAX(ID)");            
            }       
            return maxID;
        }catch (SQLException e)    {
            e.printStackTrace(); //todo logger
        }
       
        return maxID;
    }
    
    public boolean addChannelDB(Channel channel){
        boolean resultOperation = false;
        int nextID = getIDmax()+1;
        String querySQL = "INSERT INTO CHANNEL VALUES(" + nextID + ", '" + 
                channel.getTitle() + "', '" + channel.getLink() + "', '" + 
                channel.getDesc() + "', '" + channel.getIcon()+"')";
        try{
            statement.execute(querySQL);
            resultOperation = true;
        }catch (SQLException e)    {
            e.printStackTrace(); //todo logger
        }finally {
            return resultOperation;
        }
    }
    
    public boolean removeChannelDB(Channel channel){
        boolean resultOperation = false;
        String querySQL = "DELETE FROM CHANNEL WHERE title = "+channel.getTitle();        
        try{
            statement.executeUpdate(querySQL);
            resultOperation = true;
        }catch (SQLException e)    {
            e.printStackTrace(); //todo logger
        }finally {
            return resultOperation;
        }
    }
    
    public ChannelList selectChannelDB(){                
        ChannelList channelList = null;
        try{
            result = statement.executeQuery("SELECT * FROM CHANNEL");
            while (result.next()) {
                Channel channel = createChannelObject(result);          
                if (channelList == null) {
                    channelList = new ChannelList();
                }
                channelList.addChannel(channel);
            }
            
        }catch (SQLException e)    {
            e.printStackTrace(); //todo logger
        }
        return channelList;
    }
        
    private Channel createChannelObject(ResultSet rs){
        Channel channel = null; 
        try{
            String title = result.getString("title");
            String link = result.getString("link");
            String desc = result.getString("desc");
            String icon = result.getString("icon");
            channel = new Channel(title, link, desc, icon);
        }catch(Exception e) {
            e.printStackTrace(); //todo logger
        }        
        return channel;
    }    
    public void closeConnect(){
        try{ 
            result.close();
            statement.close();
            connect.close();
        }catch (SQLException e){
            e.printStackTrace(); //todo in logger
        }
    }
    
}
