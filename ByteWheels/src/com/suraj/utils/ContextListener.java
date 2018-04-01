package com.suraj.utils;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.log4j.PropertyConfigurator;

/**
 * Context Listener class to initialize log4j
 * @author suraj.udayashankar
 *
 */
@WebListener("application context listener")
public class ContextListener implements ServletContextListener {
 
    /**
     * Initialize log4j when the application is being started
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        // initialize log4j here
        ServletContext context = event.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath =  context.getRealPath("WEB-INF")+ File.separator +log4jConfigFile;         
        PropertyConfigurator.configure(fullPath);
         
    }
     
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // do nothing
    }  
}
