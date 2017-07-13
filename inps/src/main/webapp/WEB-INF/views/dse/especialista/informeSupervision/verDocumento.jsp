<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/dse/especialista/informeSupervision/verDocumento.js" />' charset="utf-8"></script>
   </head>
	<body>
		<input type="hidden" id="nroExpedienteDoc" name="nroExpedienteDoc" value="${nroExpedienteDoc}"  >
                	
		<div class="">
            <div id="gridContenedorVerDocumentos" class="content-grilla"></div>
        </div>
		
		<div class="botones">
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
	</body>
</html>