<!-- 
    Document   : template
    Created on : 16/06/2015, 11:00:41 AM
    Author     : jpiro
-->

<%@tag language="java" pageEncoding="ISO-8859-15"%>
<%@ include file="/common/taglibs.jsp"%>
<%@attribute name="pageTitle"%>
<%@attribute name="scrollPanelTitle"%>
<%@attribute name="fecha"%>
<%@attribute name="usuario"%>
<%@attribute name="nombreRol"%>
<%@attribute name="idPersonal"%>
<%@attribute name="headArea" fragment="true" %>
<%@attribute name="bodyArea" fragment="true" %>

<!DOCTYPE html> 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page ="/WEB-INF/templates/css.jspf"/>
        <jsp:include page ="/WEB-INF/templates/js.jspf"/>
        
        <jsp:invoke fragment="headArea"/>
        
        <title>INPS | ${pageTitle}</title>
    </head>
    <body>
        <div id="overlay_loading" style="display:none;">
            <div class="fancytree-loading"><span class="fancytree-expander"></span></div>
        </div>
        <div id="notifynormal"></div>
        <div id="header">
            <div id="logoWrapper">
                <img id="logo" alt="logo" src="/inps/images/osinergminLogo.png">
            </div>
            <div id="userWrapper">
                <table>
                    <tr>
                        <td class="tar fwb">Usuario: ${usuario}</td> 
                    <input type="hidden" id="idPersonalSesion" value="${idPersonal}">
                    <input type="hidden" id="rolSesion" value="${nombreRol}">
                    </tr>
                    <tr>
                        <td class="tar"><c:if test="${nombreRol!=''}" >Perfil: ${nombreRol}</c:if></td> 
                    </tr>
                    <tr>
                        <td class="tar">Fecha: ${fecha}</td>
                    </tr>
                </table>
            </div>
        </div>
        
        <div title="${scrollPanelTitle}" class="scrollPanel">
            <jsp:invoke fragment="bodyArea"/>
        </div>
    </body>
</html>