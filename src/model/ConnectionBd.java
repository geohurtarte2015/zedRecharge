
package model;

import Pojo.Subscription;
import configuration.ConnectionProperties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;



public class ConnectionBd {
    
    private Connection con = null;
    private PreparedStatement pr = null;
    private ResultSet rs = null;


    public ArrayList<String[]> getPendientFiles(String limit, String path, String[] parameters,DriverConnection driverConnection) throws SQLException {
          ArrayList<String[]> subscriptions = new ArrayList<String[]>();
        try { 
            ConnectionProperties properties = new ConnectionProperties(path);
            con = driverConnection.getCon();
            String sql = "SELECT id,phone,value,state,to_char(date_registered,'DDMMYYYYHH24MISS') FROM tb_zed_recharge WHERE ROWNUM <=? and state=1 order by id desc";

            pr = con.prepareStatement(sql);
            pr.setInt(1, Integer.parseInt(limit));
            rs = pr.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();    
            int valResp = rs.getRow();
            
            
           while (rs.next()) {
             String[] column = new String[columnsNumber];  
               int cont=0;
             while (columnsNumber >cont) {
             column[cont] = rs.getString(cont+1);
              cont++;
             }
               subscriptions.add(column);
           }
           
        } catch (SQLException ex) {
            System.out.println("Error SQL"+" "+ex);        
        }
        
        pr.close();
        rs.close();
      
            
         return subscriptions;
     }
    
    public void getUpdateState(ArrayList<String[]> list,String path,DriverConnection driverConnection) {
          int cont =  0;
          String sql="";
          ArrayList<Subscription> subscriptions = new ArrayList<Subscription>();
        try { 
            con = driverConnection.getCon();            
            while (list.size()>cont) {          
            sql = "update tb_zed_recharge set state="+list.get(cont)[3]+" where id="+list.get(cont)[0];  
            pr = con.prepareStatement(sql);
            pr.executeUpdate();
              
               cont++;
            }           
            
            con.commit();
           
        } catch (SQLException ex) {
            System.out.println("Error SQL"+" "+ex);
            System.out.println("Rolling back data here.....");
            try{
		 if(con!=null)
                 con.rollback();
            }catch(SQLException se2){
         se2.printStackTrace();
      }//end try
            
        }finally {
            if (con != null) {
                try {
                    pr.close();
                    con.close();
                } catch (Exception ignored) {
                    // ignore
                }

            }
        }
        
     }
    
     
  }
