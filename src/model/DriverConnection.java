/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import configuration.ConnectionProperties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DriverConnection {
    private Connection con = null;
    
    public DriverConnection(ConnectionProperties properties){
        
        try {
            this.con = DriverManager.getConnection(properties.getUrlconnection(), properties.getUser(), properties.getPassword());
        } catch (SQLException ex) {
            System.out.println("Error connection parameters login"+ex);
        }
    }


    public Connection getCon() {
        return con;
    }

  
    public void setCon(Connection con) {
        this.con = con;
    }

}