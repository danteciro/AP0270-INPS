<%-- 
    Document   : actividades
    Created on : 01/07/2015, 12:03:37 PM
    Author     : jpiro
--%>

<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <script type="text/javascript" src='<c:url value="/javascript/app/common/popupSeleccActividad.js" />' charset="utf-8"></script>
</head>
<body>

  <div class="pui-panel-content" >
    <div class="filaFormMarg">
      <input type="hidden" id="idActividadesSelecEspe"/>
      <div id="arbolActividadesEspe" style="height:300px;width:700px;" seleccion="${seleccion}" sufijo="${sufijo}">
      </div>
    </div>
  </div>
  <br/>
  <div align="center">
    <button id="btnSeleccionarAct" title="Seleccionar" type="button">Seleccionar</button>
  </div>
</body>
</html>                    