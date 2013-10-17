package org.jboss.gpse.fusion;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Feed;
import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.abdera.protocol.Response.ResponseType;

import org.apache.log4j.Logger;

public class StockTickClient {

    private static Logger log = Logger.getLogger("StockTickClient");

    public static void main(String args[]) {
        Abdera abdera = new Abdera();
        AbderaClient client = new AbderaClient(abdera);
        ClientResponse resp = client.get("http://w1.weather.gov/xml/current_obs/KMYP.rss");
        if (resp.getType() == ResponseType.SUCCESS) {
            Document<Feed> doc = resp.getDocument();
            log.info("main() doc = "+doc);
        } else {
        }
    }
}
