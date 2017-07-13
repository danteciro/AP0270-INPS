<%--
* Resumen		
* Objeto			: actaExpedientesZona.jsp
* Descripción		: Consultar actas por Zona.
* Fecha de Creación	: 11/10/2016
* PR de Creación	: OSINE_SFS-1063
* Autor				: Victoria Ubaldo G
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                 |     Descripción
* =====================================================================================================================================================================
*
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/dse/especialista/rechazoCarga/actaExpedientesZona.js"/>' charset="utf-8"></script>
    </head>
  <body>
  
         <input type="hidden" id=txtNumeroExpediente name="numeroExpediente" validate="[O]"   value="${numeroExpediente}">
	<div class="form">
		<form id="frmBandejaActaExpedientes">
					<div class="filaForm">
                    	<div class="lbl-medium-small">                   
                   			<h2>Acta de Expediente : </h2>
                  		</div>
                  		<div class="lbl-medium-small">
                  			<h2>${numeroExpediente}</h2>
                  		</div>
                  	</div>
                  	 
	                  <div class="tac">
	            		<div id="gridContenedorActasExpedientesZona" class="content-grilla"></div>
	            		<div id="divContextMenuActasExpedientesZona"></div>
	        		  </div>
        			 
		</form>	
	</div>

<div class="botones">
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
</body>
</html>
