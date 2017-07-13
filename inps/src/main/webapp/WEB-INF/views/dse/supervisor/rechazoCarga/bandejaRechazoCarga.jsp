<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript"
	src='<c:url value="/javascript/app/dse/supervisor/rechazoCarga/bandejaRechazoCarga.js" />'
	charset="utf-8">
	
</script>

</head>
<body>
<div class="form">
	 
		<form id="frmBandejaRechazoCargaSuper">
			 <div id="divMensajeValidaFrmBandejaRechazoCarga" class="errorMensaje" style="display: none"></div>
					<div class="filaForm">
                    <div class="lbl-small-small" ><label>Año:</label></div>
                    <div class="vam">
                        <select id="slctAnio" name="slctAnio" class="slct-small"> 
                        </select>
                    </div>       
                    <div class="lbl-small vam">
                        <input type="button" id="btnBuscarRegistroRechazoCargaSupervisor" class="btn-azul btn-small" value="Buscar">
                    </div>
                    <div class="lbl-small vam">
                        <input type="button" id="btnLimpiarRechazoCarga" class="btn-azul btn-small" value="Limpiar">
                    </div>
                </div>
				<div class="filaForm">
				    <div class="lbl-small-small vam" ><label>Tipo Empresa:</label></div>
                   <div class="vam">
                        <select id="slctTipoEmpresa" name="slctTipoEmpresa" class="slct-small">
                        </select>
                    </div>
					 <div class="lbl-small-small vam" ><label>R.U.C.:</label></div>
                    <div class="vam">
                        <input id="txtRUC" name="txtRUC" type="text" maxlength="11" class="ipt-medium" value="" />
                    </div>

					<div class="lbl-small-small vam" ><label>Empresa:</label></div>
                    <div class="vam">
                        <input id="txtEmpresa" name="txtEmpresa" maxlength="100" type="text" style="width:240px;" value="" />
                    </div>
				<div class="lbl-small-small vam" ><label>Nro. Expediente:</label></div>
                    <div class="vam">
                        <input id="txtNumExpediente" name="txtNumExpediente" type="text" maxlength="12" class="ipt-medium" value="" />
                    </div>
				</div>

			 <div id="dialogGenerarOficioEspecialista" class="dialog" style="display:none;"></div>
<!-- 			  <div id="dialogActaExpedientesZonaSupervisor" class="dialog" style="display:none;"></div> -->
		</form>

</div>
<div class="tac">
            <div id="gridContenedorEmpresas" class="content-grilla"></div>
            <div id="divContextMenuEmpresas"></div>
        </div>

</body>
</html>
