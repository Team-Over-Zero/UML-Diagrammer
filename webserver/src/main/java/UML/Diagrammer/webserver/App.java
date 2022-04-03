/*Copyright 2022 Team OverZero
<p>
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
documentation files (the "Software"), to deal in the Software without restriction, including without limitation
the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
to permit persons to whom the Software is furnished to do so, subject to the following conditions:
<p>
The above copyright notice and this permission notice shall be included in all copies or substantial portions of
the Software.
<p>
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.*/
/**
 * webserver\App.java
 *
 * This class serves as the main class for the webserver package. Should initialize a client.
 * @author Alex
 */

package UML.Diagrammer.webserver;
import UML.Diagrammer.backend.objects.*;
import java.io.IOException;
public class App {

    public static void main(String[] args){
        WebServer webServer = new WebServer();
        System.out.println("SERVER STARTED");
        HTTP_Client http_req_client = new HTTP_Client("jsonplaceholder.typicode.com/posts","80");
        try {
            //System.out.print(http_req_client.exampleGetRequest()); //commented this out to to avoid log spam. Get request does work.
            System.out.println(http_req_client.examplePutRequest());
            System.out.println(http_req_client.examplePostRequest());
            //webServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
