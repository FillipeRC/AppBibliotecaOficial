package br.edu.uerr.biblio.appbiblio.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static org.apache.http.HttpHeaders.USER_AGENT;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

/**
 *
 * @author jklac
 */
public class WsViaCep {

    public static JSONObject getCepResponse(String cep) throws IOException {
        cep = cep.substring(0,2)+cep.substring(3,6)+cep.substring(7);
        JSONObject responseJO = null;
        String url = "http://viacep.com.br/ws/"+cep+"/json/";

        System.err.println(url);
        
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);

        HttpResponse response = client.execute(request);

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
                result.append(line);
        }
        System.out.println(result.toString().replace("  ","").replace(": ",""));
        responseJO = new JSONObject(result.toString().replace("  ","").replace(": ",""));
        return responseJO;
    }

}
