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
package UML.Diagrammer.backend.objects;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UIUser {
    int id;
    String name;

    public UIUser(int id, String name){
        this.id = id;
        this.name = name;
    }

    public UIUser(){
        this.id = -1;
        this.name = "Default Name";
    }

    public String getIDAsJson(){
        return "{\"id\":\"" + id + "\"}";
    }

    //returns something like: {"id":"1","name":"brandNewUser","password":"brandnewpassword"}
    public String getFullUserString(String pw){
        return "{\"id\":\"" + id + "\"" +
                ",\"name\":\"" + name + "\"" +
                ",\"password\":\"" + pw + "\"}";
    }
}
