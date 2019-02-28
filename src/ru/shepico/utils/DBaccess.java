/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.shepico.utils;

import java.sql.*;
import java.time.ZoneOffset;
import java.util.List;

import ru.shepico.object.Channel;
import ru.shepico.object.ChannelList;
import ru.shepico.object.News;
import ru.shepico.object.NewsList;

/**
 * @author PS.Sheenkov
 */


public class DBaccess {
    private Connection connect;
    private Statement statement;
    private ResultSet result;
    private final String PATH_FILE = "jdbc:h2:./db/rssDB";

    public DBaccess() {
        try {
            Class.forName("org.h2.Driver").newInstance();

            connect = DriverManager.getConnection(PATH_FILE, "sa", "");
            statement = null;
            statement = connect.createStatement();
            result = null;
        } catch (ClassNotFoundException e_nfe) {
            LoggerMy.exLog(e_nfe);
        } catch (IllegalAccessException e_iae) {
            LoggerMy.exLog(e_iae);
        } catch (InstantiationException e_ie) {
            LoggerMy.exLog(e_ie);
        } catch (SQLException e_sql) {
            LoggerMy.exLog(e_sql);
        }

    }

    public String getPATH_FILE(){
        return PATH_FILE;
    }
    // Общее
    public void closeConnect() {
        try {
            result.close();
            statement.close();
            connect.close();
            LoggerMy.infoLog("SQL close connect ");
        } catch (SQLException e_sql) {
            LoggerMy.exLog(e_sql);
        }
    }

// Работа с каналами

    private int getIDmax() {
        int maxID = 0;
        try {
            result = statement.executeQuery("SELECT MAX(ID) FROM CHANNEL");
            if (result.next()) {
                maxID = result.getInt("MAX(ID)");
            }
            return maxID;
        } catch (SQLException e_sql) {
            LoggerMy.exLog(e_sql);
        }

        return maxID;
    }

    private boolean itIsChannel(String link) {
        boolean itIs = true;
        try {
            result = statement.executeQuery("SELECT COUNT(ID) FROM CHANNEL WHERE link='" + link + "'");
            while (result.next()) {
                int count = result.getInt("COUNT(ID)");
                if (count == 0) {
                    itIs = false;
                } else {
                    itIs = true;
                }
            }

        } catch (SQLException e_sql) {
            LoggerMy.exLog(e_sql);
        }
        return itIs;
    }

    public boolean addChannelDB(Channel channel) {
        boolean resultOperation = false;
        int nextID = getIDmax() + 1;
        if (itIsChannel(channel.getLink())) {
            return resultOperation;
        } else {
            String querySQL = "INSERT INTO CHANNEL VALUES(" + nextID + ", '" +
                    channel.getTitle() + "', '" + channel.getLink() + "', '" +
                    channel.getDesc() + "', '" + channel.getIcon() + "')";
            try {
                statement.execute(querySQL);
                resultOperation = true;
            } catch (SQLException e_sql) {
                LoggerMy.exLog(e_sql);
            } finally {
                LoggerMy.infoLog("add channel DB = " + resultOperation);
                return resultOperation;
            }
        }
    }

    public boolean removeChannelDB(Channel channel) {
        boolean resultOperation = false;
        if (itIsChannel(channel.getLink())) {
            String querySQL = "DELETE FROM CHANNEL WHERE title = '" + channel.getTitle() + "'";
            try {
                statement.executeUpdate(querySQL);
                resultOperation = true;
            } catch (SQLException e_sql) {
                LoggerMy.exLog(e_sql);
            } finally {
                LoggerMy.infoLog("remove channel DB = " + resultOperation);
                return resultOperation;
            }
        }
        return resultOperation;
    }

    public ChannelList selectChannelDB() {
        ChannelList channelList = null;
        /*try {
            result = statement.executeQuery("SELECT * FROM CHANNEL");
            while (result.next()) {
                Channel channel = createChannelObject(result);
                if (channelList == null) {
                    channelList = new ChannelList();
                }
                channelList.addChannel(channel);
            }
            result.close();

        } catch (SQLException e_sql) {
            LoggerMy.exLog(e_sql);
        }*/
        List<Channel> channels = (List<Channel>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Channel").list();
        for (int i=0; i<channels.size(); i++){
            if (channelList == null) {
                channelList = new ChannelList();
            }
            channelList.addChannel(channels.get(i));
        }
        return channelList;
    }

    private Channel createChannelObject(ResultSet rs) {
        Channel channel = null;
        try {
            String title = result.getString("title");
            String link = result.getString("link");
            String desc = result.getString("desc");
            String icon = result.getString("icon");
            channel = new Channel(title, link, desc, icon);
        } catch (SQLException e_sql) {
            LoggerMy.exLog(e_sql);
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
                    title + "', '" + link + "', '" + StaticUtils.convertStringToDate(datePub) + "', '" + false + "', '" +
                    description + "')";
            try {
                statement.execute(querySQL);
                resultOperation = true;
            } catch (SQLException e_sql) {
                LoggerMy.exLog(e_sql);
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
        } catch (SQLException e_sql) {
            LoggerMy.exLog(e_sql);
        } finally {
            return resultOperation;
        }
    }

    private boolean selectNewsDBforGUID(String guid) {
        boolean isIt = false;
        /*try {
            result = statement.executeQuery("SELECT * FROM NEWS WHERE GUID = '" + guid + "'");
            if (result.isBeforeFirst()) {
                isIt = true;
            }
            result.close();
        } catch (SQLException e_sql) {
            LoggerMy.exLog(e_sql);
        }*/
        if (HibernateSessionFactoryUtil.getSessionFactory().openSession().get(News.class, guid) != null) {
            isIt = true;
        };
        return isIt;
    }

    public NewsList selectNewsDB() {
        NewsList newsList = null;
        /*try {
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
                    newsList.addNews(news);
                }
            }
            result.close();
        } catch (SQLException e_sql) {
            LoggerMy.exLog(e_sql);
        }*/
        List<News> news = (List<News>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From News").list();
        for (int i=0; i<news.size(); i++){
            if (newsList == null) {
                newsList = new NewsList();
            }
            long milliseconds = System.currentTimeMillis() - news.get(i).getLocalDatePub().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
            int days = (int) (milliseconds / (24 * 60 * 60 * 1000));
            if (days < 4) {
                newsList.addNews(news.get(i));
            }
        }
        return newsList;
    }

    private News createNewsObject(ResultSet rs) {
        News news = null;
        try {
            String guid = result.getString("guid");
            String title = result.getString("title");
            String link = result.getString("link");
            String description = result.getString("description");
            //String strDatePub = result.getString("datepub");
            Timestamp TSpubDate = result.getTimestamp("datepub");
            String strIsRead = result.getString("isread");
            //конвертируем
            //LocalDateTime pubDate = StaticUtils.convertStringToDate(strDatePub);
            boolean isRead = Boolean.parseBoolean(strIsRead);
            //
            //news = new News(title, link, description, pubDate, guid, isRead);
            //news = new News(title, link, description, strDatePub, guid, isRead);
            news = new News(title, link, description, TSpubDate, guid, isRead);

        } catch (SQLException e_sql) {
            LoggerMy.exLog(e_sql);
        }
        return news;
    }
}
