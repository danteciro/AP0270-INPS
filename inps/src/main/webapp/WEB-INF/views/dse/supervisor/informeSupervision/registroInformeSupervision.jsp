<%--
* Resumen		
* Objeto			: registroInformeSupervision.jsp
* Descripción		: Consultar y registrar actas de inspeccion.
* Fecha de Creación	: 14/09/2016
* PR de Creación	: OSINE_SFS-480
* Autor				: Víctor Rojas Barboza
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
		<script type="text/javascript"
			src='<c:url value="/javascript/app/dse/supervisor/informeSupervision/registroInformeSupervision.js" />' charset="utf-8">
		</script>
	</head>
	<body>
	
		<div class="form">
			<form id="frmRegistroActaInspeccion">
			  
				<div id="divMensajeValidaFrmRegistroActaInspeccion" class="errorMensaje" style="display: none"></div>
				<input type="hidden" id="ruc" name="ruc" value="${ruc}">
                <input type="hidden" id="numeroExpediente" name="numeroExpediente" value="${numeroExpediente}"  >
                	
               		
				<div class="filaForm">
                    <div class="lbl-small-small" ><label>Año:</label></div>
                    <div class="vam">
                        <select id="comboAnioInf" name="comboAnioInf" class="slct-small"> 
                        </select>
                    </div>       
                    <div class="lbl-small vam">
                        <input type="button" id="btnBuscarInforme" class="btn-azul btn-small" value="Buscar">
                    </div>
                    <div class="lbl-small vam">
                        <input type="button" id="btnLimpiarInforme" class="btn-azul btn-small" value="Limpiar">
                    </div>
                </div>
                
                <div class="filaForm">
                    <div class="lbl-small-small vam" ><label>Tipo Empresa:</label></div>
                    <div class="vam">
                         <select id="comboTipoEmpresaInf" name="comboTipoEmpresaInf" class="slct-small"> 
                         </select>
                    </div>
                    
                    <div class="lbl-small-small vam" ><label>RUC:</label></div>
                    <div class="vam">
                        <input id="idRUCInf" name="idRUCInf" type="text" maxlength="11" class="ipt-medium" value="" />
                    </div>
                    
                    <div class="lbl-small-small vam" ><label>Empresa:</label></div>
                    <div class="vam">
                        <input id="idEmpresaInf" name="idEmpresaInf" maxlength="100" type="text" style="width:240px;" value="" />
                    </div>
                    
                    <div class="lbl-small-small vam" ><label>Nro. Expediente:</label></div>
                    <div class="vam">
                        <input id="idNroExpedienteInf" name="idNroExpedienteInf" maxlength="12" type="text" class="ipt-medium" value="" />
                    </div>
                </div>
                
             </form>
		</div>

		<div class="tac">
            <div id="gridContenedorInformeSupervision" class="content-grilla" ></div>
        </div>
		<div id="dialogVerDocumento" class="dialog" style="display:none;"></div>
		<div id="dialogAdjuntarInforme" class="dialog" style="display:none;"></div>
<!-- 		<div id="dialogActaExpedientesZonaSup" class="dialog" style="display:none;"></div> -->
		
		
		
	</body>
</html>
