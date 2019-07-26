
package controller;

import Pojo.Subscription;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;




import org.apache.http.message.BasicNameValuePair;



public class ServiceConnectionZed implements InterfaceConnectionPost {
   
    int cont = 0;
    int resp = 0;

    public int sendPostHttp(String url,String[] paramsArray,String[] paramVal) {
        
        int CONNECTION_TIMEOUT_MS = 1 * 1000; // Timeout in millis.
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(CONNECTION_TIMEOUT_MS)
                .setConnectTimeout(CONNECTION_TIMEOUT_MS)
                .setSocketTimeout(CONNECTION_TIMEOUT_MS)
                .build();
           
        try {
            
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);
            
// Request parameters and other properties.
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            
            params.add(new BasicNameValuePair(paramsArray[0], paramVal[0]));
            params.add(new BasicNameValuePair(paramsArray[1], paramVal[1]));
            params.add(new BasicNameValuePair(paramsArray[2], paramVal[2]));
            params.add(new BasicNameValuePair(paramsArray[3], paramVal[4]));
            
//            while (paramsArray.length >cont) {
//            params.add(new BasicNameValuePair(paramsArray[cont], paramVal[cont]));
//            cont++;
//            }
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            
//Execute and get the response.
            HttpResponse response = null;
            try {
                response = httpClient.execute(httppost);
                
                 resp=response.getStatusLine().getStatusCode();   
              
            } catch (IOException ex) {
                System.out.println("Error connection post timeout"+ex);
           
                return resp;

            }
            //HttpEntity entity = response.getEntity();
             System.out.println("Response: "+response.getStatusLine().getStatusCode()+"\n"+
            params.get(0).getName()+": "+params.get(0).getValue()+"\n"+
            params.get(1).getName()+": "+params.get(1).getValue()+"\n"+
             params.get(2).getName()+": "+params.get(2).getValue()+"\n"+
             params.get(3).getName()+": "+params.get(3).getValue()+"\n");
            
                  
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ServiceConnectionZed.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiceConnectionZed.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedOperationException ex) {
            Logger.getLogger(ServiceConnectionZed.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resp;  
    }
    
    public ArrayList<String[]> sendArrayPost(ArrayList<String[]> list, String url, String[] paramsArray) throws InterruptedException{
        int cont=0;
        int response=0;
        int size=0;
        size=list.size();

        while (size > cont) {            
            resp = sendPostHttp(url,paramsArray,list.get(cont));            
            //send post                
            switch (resp) {
                        case 200:
                            list.get(cont)[3] = "2";
                            break;
                        default:
                            list.get(cont)[3] = "3";
                    }

            cont++;
        }
   
        
    return list;
    }
    
    



    
    
    
}
