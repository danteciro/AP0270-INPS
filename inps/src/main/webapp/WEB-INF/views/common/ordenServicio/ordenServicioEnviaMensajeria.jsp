<%-- 
* Resumen
* Objeto                :              OrdenServicioEnviaMensajeria.jsp
* Descripción           :          	   Diseño web del formulario Envia Mensajeria
* Fecha de Creación     :              25/05/2016, 10:22:51 AM
* Autor                 :              mdiosesf
* --------------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Nro RSIS    Fecha         Nombre           Descripción
* --------------------------------------------------------------------------------------------------------------
*  OSINE_SFS-480    RSIS03      25/05/2016    mdiosesf		   RSIS03         
*                                                          
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript"
	src='<c:url value="/javascript/app/common/ordenServicio/ordenServicioEnviaMensajeria.js" />'
	charset="utf-8"></script>
</head>
<body>
<input type="hidden" id="IdPersonalSigedOS" value="${idPersonalSIGED}">
	<input type="hidden" id="nroExpedienteOS" value="${expDto.numeroExpediente}">
	<input type="hidden" id="IdExpedienteOS" value="${expDto.idExpediente}">
	<input type="hidden" id="IdOrdenServicioOS" value="${expDto.ordenServicio.idOrdenServicio}">
	<input type="hidden" id="IdClienteSIGEDOS" value="">
	<input type="hidden" id="nroIdentClienteSIGEDOS" value="">
	<input type="hidden" id="flagNotificarOS" value="${flagNotificar}">
	<div class="form" id="divFormOS">
		<form id="frmOSMensajeria">
			<div id="divMensajeValidaFrmOS" class="errorMensaje"
				style="display: none"></div>
			<div class="filaForm" style="width:800px;margin: 0 auto;">
				<div id="divDocExpediente" style="float: right; width: 350px; height: 300px;">
					<div class="filaForm fila">
						<div class="lbl-small vam" style="width: 100%;">
							<label style="font-weight: bold; font-size: 14px;">Documentos a Enviar </label>
						</div>
					</div>
					<div class="filaForm fila">
						<div class="vam">
							<div class="listMultipleMensajeria" id="panelDocEnvia"
								style="height: 250px;"></div>
						</div>
					</div>
				</div>
				<div id="divDocEnviar"
					style="float: left; width: 350px; height: 300px;">
					<div class="filaForm fila">
						<div class="lbl-small vam" style="width: 100%;">
							<label style="font-weight: bold; font-size: 14px;">Documentos del Expediente</label>
						</div>
					</div>
					<div class="filaForm fila">
						<div class="vam" style="width: 100%; height: 100%;">
							<div class="listMultipleMensajeria" id="panelDocExpediente"
								style="height: 250px;"></div>
						</div>
					</div>
				</div>
				<div id="divBotones" style="width: 100px; height: 300px;">
					<div class="filaForm fila">
						<div class="vam" style="margin-top: 140px; margin-left: 25px;">
							<input type="button" id="btnDerDoc"
								onclick="coOrSeOrSeEnMe.moverAlaDerecha();" class="btn-azul btn-min-small" value="&gt;&gt;" />
						</div>
					</div>
					<div class="filaForm fila">
						<div class="vam" style="margin-left: 25px;">
							<input type="button" id="btnIzqDoc"
								onclick="coOrSeOrSeEnMe.moverAlaIzquierda();" class="btn-azul btn-min-small" value="&lt;&lt;" />
						</div>
					</div>
				</div>
			</div>
			<div class="filaForm" style="margin-top: 20px;">
				<div class="lbl-small vam">
					<label>Tipo de envío:</label>
				</div>
				<div class="vam">
					<select id="slctTipoEnvioOS" class="slct-medium-small" validate="[O]">
						<option value="">--Seleccione--</option>
					</select>
				</div>
			</div>
			<div class="filaForm">
				<div class="lbl-small vam">
					<label>Oficina que Notificará:</label>
				</div>
				<div class="vam">
					<select id="slctOficinaOS" class="slct-medium-small" validate="[O]">
						<option value="">--Seleccione--</option>
					</select>
				</div>
			</div>
			<div class="filaForm">
				<div class="lbl-small vam">
					<label>Destinatarios:</label>
				</div>
				<div class="vam">
					<input id="txtDestinatariosOS" type="text" class="ipt-medium-small" />
					<input type="hidden" id="txtDestinatariosIdOS" />
					<input type="hidden" id="txtNroDocumentoDestinatariosOS" />
				</div>
				<div class="vam" style="margin-left: 10px;">
					<input type="button" id="btnAgregarDestinatario"
						class="btn-azul btn-medium" value="Agregar Destinatario">
				</div>
			</div>
			<div class="filaForm" style="display: none;">
				<select id="slctDepartamentoTestOS" class="slct-medium-small"></select>
				<select id="slctProvinciaTestOS" class="slct-medium-small"></select>
				<select id="slctDistritoTestOS" class="slct-medium-small"></select>
			</div>
			<div>
				<div class="vam" style="margin-top: 20px;">
					<div id="ordDestinatarioSeleccionados" style="display: none;"></div>
					<div id="gridContenedorDestinatario" class="content-grilla"></div>	
					<div id="divContextMenuDestinatario"></div>
				</div>
			</div>
		</form>
	</div>
	<div id="dlgCliente" style="display: none;" class="dialog">
		<div class="filaForm">
			<div class="lbl-small vam">
				<label for="txtRazonSocial">Razón Social:</label>
			</div>
			<div class="vam">
				<input id="txtRazonSocial" type="text" validate="[O]"
					class="ipt-medium-small" disabled />
			</div>
		</div>
		<div class="filaForm">
			<div class="lbl-small vam">
				<label for="txtDireccion">Dirección Destino:</label>
			</div>
			<div class="vam">
				<input id="txtDireccion" type="text" class="ipt-medium-small" />
			</div>
		</div>
		<div class="filaForm">
			<div class="lbl-small vam">
				<label for="slctDepartamentoOS">Departamento:</label>
			</div>
			<div class="vam">
				<select id="slctDepartamentoOS" class="slct-medium-small">
				</select>
			</div>
		</div>
		<div class="filaForm">
			<div class="lbl-small vam">
				<label for="slctProvinciaOS">Provincia:</label>
			</div>
			<div class="vam">
				<select id="slctProvinciaOS" class="slct-medium-small">
				</select>
			</div>
		</div>
		<div class="filaForm">
			<div class="lbl-small vam">
				<label for="slctDistritoOS">Distrito:</label>
			</div>
			<div class="vam">
				<select id="slctDistritoOS" class="slct-medium-small">
				</select>
			</div>
		</div>
		<div class="filaForm">
			<div class="lbl-small vam">
				<label for="txtReferencia">Referencia:</label>
			</div>
			<div class="vam">
				<input id="txtReferencia" type="text" class="ipt-medium-small" />
			</div>
		</div>
		<div class="botones" style="margin-top: 10px;">
			<input type="button" id="btnAceptarCliente" class="btn-azul btn-small" value="Guardar"> 
			<input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">			
		</div>
	</div>
	<div class="botones" style="margin-top: 10px;">
		<input type="button" id="btnEnviarMensajeria" class="btn-azul btn-small" value="Enviar"> 
		<input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
	</div>
</body>
</html>