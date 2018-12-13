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
import java.sql.Statement;

/**
 *
 * @author PS.Sheenkov
 */
public class DBaccess {
    
    public DBaccess(){
        try {
            //jdbc:h2:C:/Users/PS.Sheenkov/Documents/NetBeansProjects/NewsEverydayRss/db/rssDB
            Class.forName("org.h2.Driver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:h2:./db/rssDB", "sa", "");
            System.out.println("all ok");        
            Statement st = null;
            st = conn.createStatement();
            st.execute("INSERT INTO CHANNEL VALUES(1,'yaandex', 'ww.ya.ru', 'about', '')");
            /*st.execute("INSERT INTO TEST(NAME) VALUES('JOHN')");
            String name1 = "Jack";
            String q = "insert into TEST(name) values(?)";
            PreparedStatement st1 = null;

            st1 = conn.prepareStatement(q);
            st1.setString(1, name1);
            st1.execute();*/

            ResultSet result;
            result = st.executeQuery("SELECT * FROM CHANNEL");
            while (result.next()) {
                String title = result.getString("title");
                String link = result.getString("link");
                String desc = result.getString("desc");
                System.out.println(result.getString("ID")+" "+title);
                System.out.println(link+" "+desc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
    }
    
    public static void main(String[] args) {
        new DBaccess();
    }
    
}
