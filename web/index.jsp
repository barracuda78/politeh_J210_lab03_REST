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
        <title>RWS Client</title>
        <style>
            td,th{
                text-align: center;
                background-color: lightgoldenrodyellow
            }
            
        </style>
    </head>
    <body>
        <h1>REST WS Client</h1>
        
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
                    <td><input type="text" name="directory" value="" /></td>
                    <td><input type="text" name="fragment" value="" /></td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" value="Show folder" name="showDir" />
                        <input type="checkbox" name="deep" value="DEEP" />look deep
                    </td>
                    <td>
                        <input type="submit" value="Find content" name="findFile" />
                        <input type="checkbox" name="regexp" value="RE" />regex
                    </td>
                </tr>
            </tbody>
        </table>
        </form>
    </body>
</html>
