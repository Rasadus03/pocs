/*
 * Copyright 2011 Red Hat, Inc. and/or its affiliates.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA
 */
package org.jboss.as.quickstarts.datagrid.hotrod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Lock;
import javax.ejb.LockType;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Martin Gencur
 */

@Path("/")
@Singleton(name="FootballManager")
@Startup
@Lock(LockType.READ)
public class FootballManager {

    private static final String JDG_HOST = "jdg.host";
    private static final String HOTROD_PORT = "jdg.hotrod.port";
    private static final String PROPERTIES_FILE = "jdg.properties";
    private static final String msgTeamMissing = "The specified team \"%s\" does not exist, choose next operation\n";
    private static final String msgEnterTeamName = "Enter team name: ";
    private static final String initialPrompt = "Choose action:\n" + "============= \n" + "at  -  add a team\n"
            + "ap  -  add a player to a team\n" + "rt  -  remove a team\n" + "rp  -  remove a player from a team\n"
            + "p   -  print all teams and players\n" + "q   -  quit\n";
    private static final String teamsKey = "teams";

    private RemoteCacheManager cacheManager;
    private RemoteCache<String, Object> cache;
    private Logger log = LoggerFactory.getLogger("FootballManager");

    @PostConstruct
    public void start() {
        String jdgHost = System.getProperty(JDG_HOST, "localhost");
        String hotrodPort = System.getProperty(HOTROD_PORT, "11222");
        log.info("FootballManager() JDG_HOST = "+jdgHost +" : HOTROD_PORT = "+hotrodPort);
        cacheManager = new RemoteCacheManager(jdgHost + ":" + hotrodPort);
        cache = cacheManager.getCache("teams");
        if(!cache.containsKey(teamsKey)) {
            List<String> teams = new ArrayList<String>();
            Team t = new Team("Barcelona");
            t.addPlayer("Messi");
            t.addPlayer("Pedro");
            t.addPlayer("Puyol");
            cache.put(t.getName(), t);
            teams.add(t.getName());
            cache.put(teamsKey, teams);
        }
    }

    public void addTeam(String teamName) {
        @SuppressWarnings("unchecked")
        List<String> teams = (List<String>) cache.get(teamsKey);
        if (teams == null) {
            teams = new ArrayList<String>();
        }
        Team t = new Team(teamName);
        cache.put(teamName, t);
        teams.add(teamName);
        // maintain a list of teams under common key
        cache.put(teamsKey, teams);
    }

    public void addPlayers(String teamName, String playerName) {
        Team t = (Team) cache.get(teamName);
        if (t != null) {
            t.addPlayer(playerName);
            cache.put(teamName, t);
        } else {
            log.info("addPlayers() "+ msgTeamMissing+" "+ teamName);
        }
    }

    public void removePlayer(String teamName, String playerName) {
        Team t = (Team) cache.get(teamName);
        if (t != null) {
            t.removePlayer(playerName);
            cache.put(teamName, t);
        } else {
            log.info("removePlayer() "+ msgTeamMissing+" "+ teamName);
        }
    }

    public void removeTeam(String teamName) {
        Team t = (Team) cache.get(teamName);
        if (t != null) {
            cache.remove(teamName);
            @SuppressWarnings("unchecked")
            List<String> teams = (List<String>) cache.get(teamsKey);
            if (teams != null) {
                teams.remove(teamName);
            }
            cache.put(teamsKey, teams);
        } else {
            log.info("removeTeam() "+ msgTeamMissing+" "+ teamName);
        }
    }

    /*
     *   curl -v -X GET http://$HOSTNAME:8080/hotrod-endpoint/teams  
     */
    @GET
    @Path("/teams")
    @Produces({ "text/plain" })
    public Response printTeams() {
        StringBuilder sBuilder = new StringBuilder();
        List<String> teams = (List<String>) cache.get(teamsKey);
        if (teams != null) {
            for (String teamName : teams) {
                sBuilder.append(cache.get(teamName).toString());
            }
        }else{
            sBuilder.append("no teams");
        }
        ResponseBuilder builder = Response.ok(sBuilder.toString());
        return builder.build();
    }

    @PreDestroy
    public void stop() {
        cacheManager.stop();
    }
}
