<%--
* Resumen		
* Objeto			: actaInspeccion.jsp
* Descripción		: Consultar y registrar actas de inspeccion.
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
			src='<c:url value="/javascript/app/dse/especialista/actaInspeccion/actaInspeccion.js" />' charset="utf-8">
		</script>
	</head>
	<body>
		<div class="form">
			<form id="frmRegistroActaInspeccion">
				<div id="divMensajeValidaFrmRegistroActaInspeccion" class="errorMensaje" style="display: none"></div>
				
				<div class="filaForm">
                    <div class="lbl-small-small" ><label>Año:</label></div>
                    <div class="vam">
                        <select id="slctAnioActa" name="slctAnioActa" class="slct-small">
                        </select>
                    </div>       
                    <div class="lbl-small vam">
                        <input type="button" id="btnBuscarRegistroActaInspeccion" class="btn-azul btn-small" value="Buscar">
                    </div>
                    <div class="lbl-small vam">
                        <input type="button" id="btnLimpiarActaInspeccion" class="btn-azul btn-small" value="Limpiar">
                    </div>
                </div>
                
                <div class="filaForm">
                    <div class="lbl-small-small vam" ><label>Tipo Empresa:</label></div>
                    <div class="vam">
                        <select id="slctTipoEmpresaActa" name="slctTipoEmpresaActa" class="slct-small">
                        </select>
                    </div>
                       
                    <div class="lbl-small-small vam" ><label>Zona:</label></div>
                    <div class="vam">
                        <select id="slctZona" name="slctZona" class="slct-small">
                        </select>
                    </div>
                    
                    <div class="lbl-small-small vam" ><label>RUC:</label></div>
                    <div class="vam">
                        <input id="idRUCActa" name="idRUCActa" type="text" maxlength="11" class="ipt-small" value="" />
                    </div>
                    
                    <div class="lbl-small-small vam" ><label>Empresa:</label></div>
                    <div class="vam">
                        <input id="idEmpresaActa" name="idEmpresaActa" maxlength="100" type="text" style="width:240px;" value="" />
                    </div>
                    
                    <div class="lbl-small-small vam" ><label>Nro. Expediente:</label></div>
                    <div class="vam">
                        <input id="idNroExpedienteActa" name="idNroExpedienteActa" type="text" maxlength="12" class="ipt-small" value="" />
                    </div>
                </div>
                <div id="dialogRegistrarActa" class="dialog" style="display:none;"></div>
                <div id="dialogAdjuntarActa" class="dialog" style="display:none;"></div>
                 <div id="dialogGenerarActaInspeccion" class="dialog" style="display:none;"></div>
			</form>
		</div>
		<div class="tac">
            <div id="gridContenedorEmpresasZona" class="content-grilla"></div>
            <div id="divContextMenuEmpresasZona"></div>
        </div>
	
	</body>
</html>
