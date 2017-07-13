<%-- 
    Document   : asignarOT
    Created on : 17/06/2015, 12:59:51 PM
    Author     : jpiro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript"
	src='<c:url value="/javascript/app/dse/especialista/actaInspeccion/generarActaInspeccion.js"/>'
	charset="utf-8"></script>
</head>
<body>
	<form id="formGenerarActaInspeccion">
		<div class="form">
			<input type="hidden" id="idZona" value="${idZona}"> <input
				type="hidden" id="idEmpresa" value="${idEmpresa}"> <input
				type="hidden" id="comboEstacion" value="${comboEstacion}"> <input
				type="hidden" id="anio" value="${anio}"> <input
				type="hidden" id="idSupeCampRechCarga"
				value="${idSupeCampRechCarga}">

			<div id="divMensajeValidaFormPlantillaActa" class="errorMensaje"
				style="display: none"></div>


			<div class="filaForm">
				<div class="lbl-medium">
					<label>Año:</label>
				</div>
				<div>
					<input type="text" id="txtAnio" name="txtAnio"
						class="ipt-medium-small" style="width: 250px" value='${anio}'
						disabled />
				</div>
			</div>

			<div class="filaForm">
				<div class="lbl-medium">
					<label>Empresa:</label>
				</div>
				<div>
					<input type="text" id="empresa" name="empresa"
						class="ipt-medium-small" value='${empresa}' disabled />
				</div>
			</div>

			<div class="filaForm">
				<div class="lbl-medium">
					<label>Zona:</label>
				</div>
				<div>
					<input type="text" id="descripcionZona" name="descripcionZona"
						class="ipt-medium-small" value='${descripcionZona}' disabled />
				</div>
			</div>
			<div class="filaForm">
				<div class="lbl-medium vam">
					<label for="slctSubEstacion">Sub Estación(S.E.):</label>
				</div>
				<div class="vam">
					<select style="width: 275px" id="slctSubEstacion"
						class="slct-medium" name="slctSubEstacion" validate="[O]">
					</select>
				</div>
			</div>
			<div class="filaForm">
			<div class="lbl-medium vam etapaActa">
			<label for="divTipSan">Etapas:</label>
			</div>
			<div class="vam etapaActa" style="width:280px;">
				<div id="divTipSan"></div>
			</div>				
			</div>
		</div>


	</form>

	<div class="botones">
		<button id="btnGuardarConfReles" class="btnSimple" title="Guardar"
			type="button">Guardar</button>
		<button id="btnImprimirActa" class="btnSimple" title="Imprimir"
			type="button" style="display:none;">Imprimir</button>
	</div>




</body>
</html>
