<%--
* Resumen		
* Objeto			: busquedaExpediente.jsp
* Descripción		: Jsp para la búsqueda de Expedientes según filtros, gerencia GSM.
* Fecha de Creación	: 26/10/2016.
* PR de Creación	: OSINE_SFS-1344.
* Autor				: Giancarlo Villanueva Andrade
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                 |     Descripción
* =====================================================================================================================================================================
* 
--%>

<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/GSM/common/expediente/busquedaExpediente.js" />' charset="utf-8"></script>
    </head>
    <body>
     	<input type="hidden" id="idGridBusqExpe" value="${grid}">
    	<div class="form">
            <form id="frmBusqExpe">
                <div id="divMensajeValidaFrmBusqExpe" class="errorMensaje" style="display: none"></div>
                <div>
					<!-- OSINE_SFS-1344 - Inicio -->
					<!-- OSINE_SFS-1344 - Fin -->
                    <div class="filaForm">
                        <div class="lbl-small vam"><label for="txtNumeExpeBusqExpe">Número Expediente:</label></div>
                        <div class="vam">
                            <input id="txtNumeExpeBusqExpe" type="text" maxlength="25" class="ipt-medium" />
                         </div>
                    </div>                    
                    <div class="filaForm">
                        <div class="lbl-small"><label for="txtNumeOSBusqExpe">Número OS:</label></div>
                        <div>
                            <input id="txtNumeOSBusqExpe" type="text" maxlength="25" class="ipt-medium" />
                         </div>
                    </div>
                    <div class="filaForm">
                        <div class="lbl-small vat"><label for="txtNomTitMinOSBusqExpe">Nombre Titular Minero:</label></div>
                        <div>
                            <input id="txtNomTitMinOSBusqExpe" type="text" maxlength="25" class="ipt-large" />
                         </div>
                    </div>
                    <div class="filaForm">
	                	<div class="lbl-small vat"><label for="slctEspOSBusqExpe">Especialidad:</label></div>
                        <div class="vam">
                        	<select id="slctEspOSBusqExpe">
                            	<option value="">--Seleccione--</option>
                            </select>
                    	</div>
	                </div>
                    <div class="filaForm">
                        <div class="lbl-small vat"><label for="txtNomUnidSupOSBusqExpe">Nombre Unidad Supervisada:</label></div>
                        <div>
                            <input id="txtNomUnidSupOSBusqExpe" type="text" maxlength="25" class="ipt-large" />
                         </div>
                    </div>  
                    
                </div>
            </form>
        </div>
        <div class="botones">
            <input id="btnBusqExpe" type="button" class="btn-azul btn-small" value="Buscar">
            <input id="btnCerrarBusqExpe" type="button" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
    </body>
</html>