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
package UML.Diagrammer.desktop;

import UML.Diagrammer.backend.apis.HTTP_Client;
import UML.Diagrammer.backend.objects.UIEdge.UIEdge;
import UML.Diagrammer.backend.objects.UIEdge.UIEdgeFactory;
import UML.Diagrammer.backend.objects.UIEdge.UINormalEdge;
import UML.Diagrammer.backend.objects.UINode.*;
import UML.Diagrammer.backend.objects.UIPage;
import UML.Diagrammer.backend.objects.UIUser;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.javalite.activejdbc.Base;

import java.util.ArrayList;

public class DatabaseConnection {

    // The current instance of the object requester and factories
    HTTP_Client HTTPClient = new HTTP_Client();



    // Putting either of these strings in postman work properly
    // http://127.0.0.1:8888/createuser/?user={"id":"-1","name":"newName"}
    // http://127.0.0.1:8888/createpage/?page={"name":"TESTPAGE"}&userid={"id":"1"}
    /**
     * Creates a new user and gets their ID from the database
     * @param name name of the user
     */
    public UIUser createNewUser(String name){
        try {
            UIUser newUser = new UIUser(-1, name);
            String dbUserString = HTTPClient.sendCreateUser(newUser.getIDAsJson());
            newUser.setId(stripNum(dbUserString));
            System.out.println("newUserId is now: " + newUser.getId());
            return newUser;
        }
        catch (Exception e){e.printStackTrace();}
        return null;
    }

    /**
     * creates a new page given an associated user.
     * @param user The user that this page should belong to.
     * @param pageName The name of the page that the user has specified.
     * @return A new page for the user to add things to.
     */
    public UIPage createNewPage(UIUser user, String pageName){
        try{
            UIPage newPage = new UIPage(-1, pageName);
            String dbPageString = HTTPClient.pageCreateRequest(newPage.getPageNameAsJSon(), user.getIDAsJson());
            newPage.setId(stripNum(dbPageString));
            System.out.println("newPageId is now: "+newPage.getId());
            return newPage;
        }
        catch (Exception e){e.printStackTrace();}
        return null;
    }

    /**
     * Saves a node to the database via a page.
     * @param node The node you'd like to save to a page.
     * @param page The page that the user is currently on.
     */
    public void saveNewNodeToDB(UINode node, UIPage page) {
        try {
            String returnedString = HTTPClient.sendAddNodeToPage(node.getNodeAsJSon(), page.getPageIdAsJSon());
            node.setId(stripNum(returnedString));
            System.out.println("Successfully saved node type: " + node.getType() + " to page: " + page.getId());
        }
        catch (Exception e){e.printStackTrace();System.out.println("FAILED TO SAVE");}
    }

    public void saveNewEdgeToDB(UIEdge edge, UIPage page){
        String returnedString = HTTPClient.sendAddEdgeToPage(edge.getEdgeAsJSon(), page.getPageIdAsJSon());
        edge.setId(stripNum(returnedString));
        System.out.println("Successfully saved \"normal\" edge " + edge.getId() + " to page: " + page.getId());
    }

    /**
     * Strips everything but integers from a string, should be used to get an Id from a JSon string
     * @param stringToStrip the string you'd like to strip
     * @return the numeber in the string
     */
    public int stripNum(String stringToStrip){
        return Integer.parseInt(stringToStrip.replaceAll("\\D", ""));
    }
}
