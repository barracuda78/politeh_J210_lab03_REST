<%-- 
    Document   : index
    Created on : Apr 24, 2021, 10:38:51 AM
    Author     : ENVY
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style01.css"/>
        <title>RWS Client</title>
        <style>
            td,th{
                text-align: center;
                
            }
            
        </style>
    </head>
    <body>
        <%
        String lookDeep = (String)request.getAttribute("lookDeep");
        %>
        
        <h1><center>REST WS Client</center></h1>
        <div class="container">
            <div class="box-1">
                <form action="DemoRS">
                    <table border="1">
                        <thead>
                            <tr>
                                <th>Directory:</th>
                                <th>Fragment:</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><input type="text" name="directory" value="" class="b1"/></td>
                                <td><input type="text" name="fragment" value="" class="b1"/></td>
                            </tr>
                            <tr>
                                <td width="260px">
                                    <input type="submit" value="Show folder" name="showDir" class="b1"/>
                                    <%
                                    if("checked".equals(lookDeep)){
                                    %>
                                    <input type="checkbox" name="deep" value="DEEP" id="happy" class="b1" checked="checked"/>look deep
                                    <%
                                    }else{
                                    %>
                                    <input type="checkbox" name="deep" value="DEEP" id="happy" class="b1"/>look deep
                                    <%
                                    }
                                    %>
                                </td>
                                <td width="260px">
                                    <input type="submit" value="Find content" name="findFile" class="b1"/>
                                    <input type="checkbox" name="regexp" value="RE" class="b1"/>regex
                                </td>
                            </tr>
                            <!--tr>
                                <td>
                                    <input type="radio" name="radioPath" value="givenPath" checked="checked"/>given path
                                    <input type="radio" name="radioPath" value="absolutePath" />absolute path

                                </td>
                                <td>
                                    
                                </td>
                            </tr-->
                        </tbody>
                    </table>
                </form>
            </div>
        </div>
        
        <div class="container">
            
                <%
                String result = (String)request.getAttribute("result");
                if(result != null){
                    %>
                    <div class="box-2">
                    <%= result %>
                    </div>
                    <%
                }
                %>
            
        </div>
    </body>
</html>
