<%--
* Resumen		
* Objeto			: bandehaRechazoCarga.jsp
* Descripción		: Consultar de Empresas
* Fecha de Creación	: 11/10/2016
* PR de Creación	: OSINE_SFS-1063
* Autor				: Victoria Ubaldo G
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
	src='<c:url value="/javascript/app/dse/consulta/rechazoCarga/bandejaRechazoCarga.js" />'
	charset="utf-8">
	
</script>

</head>
<body>
<div class="form">
	 
		<form id="frmBandejaRechazoCarga">
			 <div id="divMensajeValidaFrmBandejaRechazoCarga" class="errorMensaje" style="display: none"></div>

					<div class="filaForm">
                    <div class="lbl-small-small" ><label>Año:</label></div>
                    <div class="vam">
                        <select id="slctAnio" name="slctAnio" class="slct-small"> 
                        </select>
                    </div>       
                    <div class="lbl-small vam">
                        <input type="button" id="btnBuscarRegistroRechazoCarga" class="btn-azul btn-small" value="Buscar">
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
                        <input id="idRUC" name="idRUC" type="text" maxlength="11" class="ipt-medium" value="" />
                    </div>
					<div class="lbl-small-small vam" ><label>Empresa:</label></div>
                    <div class="vam">
                        <input id="idEmpresa" name="idEmpresa" type="text" maxlength="100" class="ipt-large-meidum" value="" />
                    </div>
					<div class="lbl-small-small vam" ><label>Nro. Expediente:</label></div>
                    <div class="vam">
                        <input id="idNroExpediente" name="idNroExpediente" type="text" maxlength="12" class="ipt-medium" value="" />
                    </div>
				   <div class="lbl-small-small vam" ><label>Observados:</label></div>
                    <div class="vam">
						<input id="radioSiSupervision" type="checkbox" name="radioSupervision" value="" >
 						<label class="checkbox" for="radioSiSupervision"></label>
				   </div>
                   
				</div>
				
			 <div id="dialogGenerarOficio" class="dialog" style="display:none;"></div>
			  <div id="dialogActaExpedientesZona" class="dialog" style="display:none;"></div>
			   <div id="dialogVerDocumento" class="dialog" style="display:none;"></div>
		</form>

</div>
<div class="tac">
            <div id="gridContenedorEmpresas" class="content-grilla"></div>
            <div id="divContextMenuEmpresas"></div>
        </div>

</body>
</html>
