package de.fhg.iais.roberta.javaServer.restServices.ev3;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import de.fhg.iais.roberta.robotCommunication.ev3.Ev3CommunicationData;
import de.fhg.iais.roberta.robotCommunication.ev3.Ev3Communicator;

/**
 * REST service for downloading user program
 */
@Path("/sensorlogging")
public class Ev3SensorLogging {
    private static final Logger LOG = LoggerFactory.getLogger(Ev3SensorLogging.class);

    private final Ev3Communicator brickCommunicator;

    @Inject
    public Ev3SensorLogging(Ev3Communicator brickCommunicator) {
        this.brickCommunicator = brickCommunicator;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response handle(JSONObject requestEntity) {
        String token = (String) requestEntity.remove("token");
        Ev3CommunicationData state = this.brickCommunicator.getState(token);
        state.setSensorValues(requestEntity);
        LOG.info(requestEntity.toString());
        return Response.ok().build();
    }
}