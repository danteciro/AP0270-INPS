<%--
* Resumen		
* Objeto			: actaInspeccion.jsp
* Descripción		: Consultar y registrar actas de inspeccion para el Supervisor.
* Fecha de Creación	: 14/09/2016
* PR de Creación	: OSINE_SFS-1063
* Autor				: Hernan Torres Saenz.
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
			src='<c:url value="/javascript/app/dse/supervisor/actaInspeccion/actaInspeccion.js" />' charset="utf-8">
		</script>
	</head>
	<body>
		<div class="form">
			<form id="frmRegistroActaInspeccion">
				<div id="divMensajeValidaFrmRegistroActaInspeccion" class="errorMensaje" style="display: none"></div>
				
				<div class="filaForm">
                    <div class="lbl-small-small" ><label>Año:</label></div>
                    <div class="vam">
                        <select id="slctAnioActaSup" name="slctAnioActaSup" class="slct-small">
                        </select>
                    </div>       
                    <div class="lbl-small vam">
                        <input type="button" id="btnBuscarRegistroActaInspeccionSup" class="btn-azul btn-small" value="Buscar">
                    </div>
                    <div class="lbl-small vam">
                        <input type="button" id="btnLimpiarActaInspeccion" class="btn-azul btn-small" value="Limpiar">
                    </div>
                </div>
                
                <div class="filaForm">
                    <div class="lbl-small-small vam" ><label>Tipo Empresa:</label></div>
                    <div class="vam">
                        <select id="slctTipoEmpresaActaSup" name="slctTipoEmpresaActaSup" class="slct-small">
                        </select>
                    </div>
                       
                    <div class="lbl-small-small vam" ><label>Zona:</label></div>
                    <div class="vam">
                        <select id="slctZonaSup" name="slctZonaSup" class="slct-small">
                        </select>
                    </div>
                    
                    <div class="lbl-small-small vam" ><label>RUC:</label></div>
                    <div class="vam">
                        <input id="idRUCActaSup" name="idRUCActaSup" type="text" maxlength="11" class="ipt-small" value="" />
                    </div>
                    
                    <div class="lbl-small-small vam" ><label>Empresa:</label></div>
                    <div class="vam">
                        <input id="idEmpresaActaSup" name="idEmpresaActaSup" type="text" maxlength="100" style="width:240px;" value="" />
                    </div>
                    
                    <div class="lbl-small-small vam" ><label>Nro. Expediente:</label></div>
                    <div class="vam">
                        <input id="idNroExpedienteActaSup" name="idNroExpedienteActaSup" type="text" maxlength="12" class="ipt-small" value="" />
                    </div>
                </div>
                <div id="dialogRegistrarActaSupervisor" class="dialog" style="display:none;"></div>
                <div id="dialogAdjuntarActaSupervisor" class="dialog" style="display:none;"></div>
                <div id="dialogGenerarActaInspeccionSupervisor" class="dialog" style="display:none;"></div>
			</form>
		</div>
		<div class="tac">
            <div id="gridContenedorEmpresasZonaSupervisor" class="content-grilla"></div>
            <div id="divContextMenuEmpresasZonaSupervisor"></div>
        </div>
	
	</body>
</html>
