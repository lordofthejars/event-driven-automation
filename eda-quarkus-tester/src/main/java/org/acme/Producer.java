package org.acme;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.alertmanager.model.Alert;
import org.acme.alertmanager.service.AlertService;
import org.acme.webhook.model.WebhookEvent;
import org.acme.webhook.service.WebhookService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;

@Path("/")
public class Producer {

    @Inject
    @RestClient
    WebhookService webhookService;
    @Inject
    @RestClient
    AlertService alertService;

    @GET
    @Path("/webhook/greeting")
    @Produces(MediaType.APPLICATION_JSON)
    public void sendWebhookEvent() {
        WebhookEvent testEvent = new WebhookEvent("greeting", "Hello from Quarkus", null);
        webhookService.sendEvent(testEvent);
    }

    @GET
    @Path("/alertmanager/greeting")
    @Produces(MediaType.APPLICATION_JSON)
    public void sendAlert() throws JsonProcessingException {
        List<Alert> alertList = new ArrayList<>();
        Alert testAlert = new Alert("quarkusAlert", "Quarkus fired this alert", "localhost");
        alertList.add(testAlert);
        alertService.sendAlert(alertList);
    }

}
