package com.epam.rd.jsp.currencies;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import javax.servlet.ServletException;
import java.io.File;

public class Launcher {
    private static final int PORT_NUM = 8080;

    public static void main(String[] args) throws ServletException, LifecycleException {

        int port = PORT_NUM;

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);

        String webappDirLocation = "src/main/webapp/";
        StandardContext ctx = (StandardContext) tomcat.addWebapp(
                "", new File(webappDirLocation).getAbsolutePath()
        );

        File additionWebInfClasses = new File("target/classes");
        File jstlLib = new File("src/test/resources/taglib");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(
                new DirResourceSet(
                        resources,
                        "/WEB-INF/classes",
                        additionWebInfClasses.getAbsolutePath(), "/"));
        resources.addPreResources(
                new DirResourceSet(
                        resources,
                        "/WEB-INF/lib",
                        jstlLib.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();

    }
}
