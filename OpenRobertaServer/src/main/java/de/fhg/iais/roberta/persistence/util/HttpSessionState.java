package de.fhg.iais.roberta.persistence.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhg.iais.roberta.factory.IRobotFactory;
import de.fhg.iais.roberta.robotCommunication.RobotCommunicator;
import de.fhg.iais.roberta.util.dbc.Assert;

public class HttpSessionState {
    private static final Logger LOG = LoggerFactory.getLogger(HttpSessionState.class);
    public final static int NO_USER = -1;

    private int userId = HttpSessionState.NO_USER;
    private String robotName;
    private String token = "1Q2W3E4R";
    private String programName;
    private String program;
    private String configurationName;
    private String configuration;
    private String toolboxName;
    private String toolbox;
    private final RobotCommunicator robotCommunicator;
    private Map<String, IRobotFactory> robotPluginMap;

    public HttpSessionState(RobotCommunicator robotCommunicator, Map<String, IRobotFactory> robotPluginMap) {
        this.robotCommunicator = robotCommunicator;
        this.robotPluginMap = robotPluginMap;
    }

    public static HttpSessionState init(RobotCommunicator robotCommunicator, Map<String, IRobotFactory> robotPluginMap) {
        return new HttpSessionState(robotCommunicator, robotPluginMap);
    }

    public int getUserId() {
        return this.userId;
    }

    public boolean isUserLoggedIn() {
        return this.userId >= 1;
    }

    public void setUserClearDataKeepTokenAndRobotId(int userId) {
        Assert.isTrue(userId >= 1 || userId == HttpSessionState.NO_USER);
        // token is not cleared. This would annoy the user.
        // robotId is not cleared. This would annoy the user.
        this.userId = userId;
        this.programName = null;
        this.program = null;
        this.configurationName = null;
        this.configuration = null;
    }

    public String getRobotName() {
        return this.robotName;
    }

    public void setRobotName(String robotName) {
        Assert.notNull(robotName);
        this.robotName = robotName;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        Assert.notNull(token);
        this.token = token;
    }

    public String getProgramName() {
        return this.programName;
    }

    public String getProgram() {
        return this.program;
    }

    public void setProgramNameAndProgramText(String programName, String program) {
        this.programName = programName;
        this.program = program;
    }

    public String getConfigurationName() {
        return this.configurationName;
    }

    public String getConfiguration() {
        return this.configuration;
    }

    public void setConfigurationNameAndConfiguration(String configurationName, String configuration) {
        this.configurationName = configurationName;
        this.configuration = configuration;
    }

    public String getToolboxName() {
        return this.toolboxName;
    }

    public String getToolbox() {
        return this.toolbox;
    }

    public void setToolboxNameAndToolbox(String toolboxName, String toolbox) {
        this.toolboxName = toolboxName;
        this.toolbox = toolbox;
    }

    public IRobotFactory getSimulationFactory() {
        try {
            return (IRobotFactory) this.getClass().getClassLoader().loadClass("de.fhg.iais.roberta.factory.SimFactory").newInstance();
        } catch ( InstantiationException | IllegalAccessException | ClassNotFoundException | SecurityException | IllegalArgumentException e ) {
            LOG.error("Simulation Factory Not Found!. Check the robot configuration property. System will crash!", e);
            return null;
        }
    }

    public Collection<String> getAllRobotsPluggedIn() {
        return Collections.unmodifiableSet(robotPluginMap.keySet());
    }

    public IRobotFactory getRobotFactory() {
        IRobotFactory robotFactory = robotPluginMap.get(this.robotName);
        if ( robotFactory == null ) {
            LOG.error("robot factory for robot \"" + this.robotName + "\" not found. Fallback is \"ev3\"");
            robotFactory = robotPluginMap.get("ev3");
        }
        return robotFactory;
    }

    /**
     * temporary fix until DB is updated
     *
     * @return the temporary robot id
     */
    public int getRobotId() {
        if ( robotPluginMap == null ) {
            return 42;
        }
        IRobotFactory robotFactory = robotPluginMap.get(this.robotName);
        if ( robotFactory == null ) {
            LOG.error("robot factory for robot \"" + this.robotName + "\" not found. Fallback is \"ev3\"");
            robotFactory = robotPluginMap.get("ev3");
        }
        return robotFactory.getRobotId();
    }
}