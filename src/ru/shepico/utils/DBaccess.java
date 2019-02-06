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
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import ru.shepico.object.Channel;
import ru.shepico.object.ChannelList;
import ru.shepico.object.News;
import ru.shepico.object.NewsList;

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
// Общее
    public void closeConnect(){
        try{
            result.close();
            statement.close();
            connect.close();
        }catch (SQLException e){
            e.printStackTrace(); //todo in logger
        }
    }

// Работа с каналами
    
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

    private boolean itIsChannel(String link){
        boolean itIs = true;
        try {
            result = statement.executeQuery("SELECT COUNT(ID) FROM CHANNEL WHERE link='" + link + "'");
            while (result.next()){
                int count = result.getInt("COUNT(ID)");
                if (count == 0) {
                    itIs = false;
                }else {
                    itIs = true;
                }
            }

        }catch (SQLException e)    {
            e.printStackTrace(); //todo logger
        }
        return itIs;
    }
    
    public boolean addChannelDB(Channel channel){
        boolean resultOperation = false;
        int nextID = getIDmax()+1;
        if (itIsChannel(channel.getLink())) {
            return resultOperation;
        }else {
            String querySQL = "INSERT INTO CHANNEL VALUES(" + nextID + ", '" +
                    channel.getTitle() + "', '" + channel.getLink() + "', '" +
                    channel.getDesc() + "', '" + channel.getIcon() + "')";
            try {
                statement.execute(querySQL);
                resultOperation = true;
            } catch (SQLException e) {
                e.printStackTrace(); //todo logger
            } finally {
                return resultOperation;
            }
        }
    }
    
    public boolean removeChannelDB(Channel channel){
        boolean resultOperation = false;
        if (itIsChannel(channel.getLink())) {
            String querySQL = "DELETE FROM CHANNEL WHERE title = '" + channel.getTitle() + "'";
            try {
                statement.executeUpdate(querySQL);
                resultOperation = true;
            } catch (SQLException e) {
                e.printStackTrace(); //todo logger
            } finally {
                return resultOperation;
            }
        }
        return resultOperation;
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
            result.close();
            
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

// Работа с новостями
//
    public boolean addNewsDB(String guid, String title, String link, String datePub, String description) {
        boolean resultOperation = false;
        boolean isIt = selectNewsDBforGUID(guid);
        if (isIt) {
            return false;
        } else {

            String querySQL = "INSERT INTO NEWS VALUES( '" + guid + "' , '" +
                    title + "', '" + link + "', '" + datePub + "', '" + false + "', '" +
                    description + "')";
            try {
                statement.execute(querySQL);
                resultOperation = true;
            } catch (SQLException e) {
                e.printStackTrace(); //todo logger
            } finally {
                return resultOperation;
            }
        }
    }

    public boolean readNewsDB(String guid) {
        boolean resultOperation = false;
        String querySQL = "UPDATE News " +
                "   SET ISREAD = " + true +
                "   WHERE GUID = '" + guid + "'";
        try {
            statement.execute(querySQL);
            resultOperation = true;
        }catch (SQLException e) {
            e.printStackTrace(); //todo logger
        }finally {
            return resultOperation;
        }
    }

    private boolean selectNewsDBforGUID(String guid){
        boolean isIt = false;
        try{
            result = statement.executeQuery("SELECT * FROM NEWS WHERE GUID = '" + guid + "'");
            if (result.isBeforeFirst()) {
                isIt = true;
            }
            result.close();
        }catch (SQLException e) {
            e.printStackTrace(); //todo logger
        }
        return isIt;
    }

    public NewsList selectNewsDB(){
        NewsList newsList = null;
        try{
            result = statement.executeQuery("SELECT * FROM NEWS WHERE ISREAD = false");
            while (result.next()) {
                News news = createNewsObject(result);
                if (newsList == null) {
                    newsList = new NewsList();
                }

                //отсекаем лишние новости по дате
                long milliseconds = System.currentTimeMillis() - news.getLocalDatePub().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
                 int days = (int) (milliseconds / (24 * 60 * 60 * 1000));

                    if (days < 4) {
                       // news = new News(title, link, description, pubDate, guid, false);

                        newsList.addNews(news);
                    }
                //newsList.addNews(news);
            }
            result.close();
        }catch (SQLException e) {
            e.printStackTrace(); //todo logger
        }
        return newsList;
    }

    private News createNewsObject(ResultSet rs) {
        News news = null;
        try{
            String guid = result.getString("guid");
            String title = result.getString("title");
            String link = result.getString("link");
            String description = result.getString("description");
            String strDatePub = result.getString("datepub");
            String strIsRead = result.getString("isread");
            //конвертируем
            LocalDateTime pubDate = StaticUtils.convertStringToDate(strDatePub);
            boolean isRead = Boolean.parseBoolean(strIsRead);
            //
            news = new News(title, link, description, pubDate, guid, isRead);

        }catch(Exception e) {
            e.printStackTrace(); //todo logger
        }
        return news;
    }

}
