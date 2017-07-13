<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/dse/supervisor/informeSupervision/verActaInspeccion.js" />' charset="utf-8"></script>
   </head>
	<body>
		<div class="form">	
				<div class="filaForm">
					<div id="gridZonasEmpresa" class="content-grilla"></div>
				</div>
		</div>
		<div class="botones">
            <input type="button" id="btnGuardarDocumento" class="btn-azul btn-small" value="Guardar">
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
        <div class="txt-obligatorio">(*) Campos obligatorios</div>
	</body>
</html>