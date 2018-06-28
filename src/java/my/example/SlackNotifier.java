/*
 * The MIT License
 *
 * Copyright 2018 Grzegorz Skorupa <g.skorupa at gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package my.example;

import java.util.HashMap;
import org.cricketmsf.in.http.Result;
import org.cricketmsf.out.http.HttpClient;
import org.cricketmsf.out.http.Request;

public class SlackNotifier extends HttpClient implements NotifierIface{
    
    private String token;
    
    @Override
    public void loadProperties(HashMap<String, String> properties, String adapterName){
        super.loadProperties(properties, adapterName);
    }

    @Override
    public Result send(String message) {
        String data="{\"text\":\""+message+"\"}";
        Request request = new Request()
                .setMethod("POST")
                .setUrl(endpointURL)
                .setProperty("Content-type", "application/json")
                .setData(data);
        return send(request);
    }
    
}
