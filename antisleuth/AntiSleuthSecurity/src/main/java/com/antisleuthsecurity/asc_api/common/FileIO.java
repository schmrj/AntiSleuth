/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.antisleuthsecurity.asc_api.common;

import com.antisleuthsecurity.asc_api.utilities.ASLog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Bobby
 */
public class FileIO {
    public static boolean writeObject(Object obj, String fileLocation){
        File file = new File(fileLocation);        
        try{
        	file.getParentFile().mkdirs();
        	
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(obj);
            oos.close();
            return true;
        }catch(Exception e){
            ASLog.error("Could not save: " + fileLocation, e);
        }
        return false;
    }
    
    public static Object readObject(String fileLocation){
        File file = new File(fileLocation);
        
        if(!file.exists()){
            ASLog.error("File does not exist: " + fileLocation);
            return null;
        }
         
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Object object = ois.readObject();
            ois.close();
            return object;
        }catch(Exception e){
            ASLog.error("Could not read: " + fileLocation, e);
            return null;
        }
    }
}
