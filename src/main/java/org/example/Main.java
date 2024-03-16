package org.example;

import io.swagger.v3.jaxrs2.integration.OpenApiServlet;
import jakarta.servlet.ServletRegistration;
import jakarta.ws.rs.core.UriBuilder;
import org.example.service.Service;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.http.server.StaticHttpHandlerBase;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerConfig;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {

        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(MoxyJsonFeature.class);
        resourceConfig.register(new Service());

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
                UriBuilder.fromUri("http://0.0.0.0/").port(8080).build(),
                resourceConfig, false);
        deployOpenAPiServlet(server, "org.example.service");

        server.start();

    }

    private static void deployOpenAPiServlet(HttpServer server, String openApiResourcesPackage) {
        WebappContext context = new WebappContext("WebappContext", "");
        ServletRegistration registration = context.addServlet("ServletContainer", new OpenApiServlet());
        registration.setInitParameter("openApi.configuration.resourcePackage", openApiResourcesPackage);

        registration.addMapping("/openapi/*");
        context.deploy(server);
    }

    private static void deployOpenApiUi(HttpServer server) {
        ClassLoader classLoader = Main.class.getClassLoader();
        StaticHttpHandlerBase docsHandler = new CLStaticHttpHandler(classLoader, "openapi-ui");
        docsHandler.setFileCacheEnabled(true);

        ServerConfiguration serverConfig = server.getServerConfiguration();
        serverConfig.addHttpHandler(docsHandler, "/openapi-ui/*");
    }
}