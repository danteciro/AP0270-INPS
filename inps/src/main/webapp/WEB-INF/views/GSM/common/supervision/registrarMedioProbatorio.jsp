<%--
* Resumen		
* Objeto		: RegistrarMedioProbatorio.jsp
* Descripción		: Registro medio Probatorio.
* Fecha de Creación	: 31/08/2016
* PR de Creación	: OSINE_791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_791-RSIS8|  31/08/2016   |   Cristopher Paucar Torre    |     Registrar Medio Probatorio.
*                |               |                              |
*                |               |                              |
*                |               |                              |
* 
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/common/supervision/registrarMedioProbatorio.js" />' charset="utf-8"></script>
    </head>
    <body>
        <div class="form">
            <input type="hidden" id="idDetalleSupervisionMedioProb" value="${ds.idDetalleSupervision}">
        	
            
            <div>
	        <form id="formMedioAprobatorioDsr" action="/inps/pages/archivo/subirMedioProbatorioDetalleSuperDsr" method="post" enctype="multipart/form-data" encoding="multipart/form-data" >
                    <div id="divMensajeValidaFrmMDDsr" class="errorMensaje" style="display: none"></div>
                    <div class="filaForm tac">
                         <div class="lbl-small tal"><label for="txtDescripcionMPDsr">Descripci&oacute;n(*):</label></div>
                         <div>
                             <input type="text" id="txtDescripcionMPDsr" validate="[O]" name="descripcionDocumento" maxlength="200" class="ipt-medium-small" value="" />
                         </div>
                    </div>
                    <div class="filaForm tac">
                        <div class="lbl-small tal"><label>Archivo(*):</label></div>
                        <div class="container-file vam">
                            <input id="fileArchivoSuperDsr" name="archivos[0]" placeholder="" value="" type="file" class="fileUpload"/>
                            <input type="hidden" id="idDetalleSupervisionSuper" name="detalleSupervision.idDetalleSupervision" value="${ds.idDetalleSupervision}">
<!--                            <input type="hidden" id="flagResultadoSuperMP"  name="detalleSupervision.flagResultado" value="${tipoCumple}">-->
                            <input id="file_nameSuperDsr" type="text" validate="[O]" name="file_nameSuper" disabled="disabled" style="width:133px" maxlength="200"> 
                            <a href="#" class="search_file button" style="width:100px" >Examinar</a>
                        </div>
                    </div>
	        </form>	        
	    </div>
            
            <div class="txt-obligatorio">(*) Campos obligatorios</div>
            <div class="txt-obligatorio">Los tipos de archivos permitidos son: <c:out value="${archivosPermitidos}"/> y el tamaño máximo para cada uno debe ser <c:out value="${tamanioAdjuntoMB}"/> MB</div>
        </div>
        <div class="botones">
            <input type="button" id="btnAgregarMedioProbatorioDsr" title="Guardar" class="btn-azul btn-small" value="Guardar">
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>        
    </body>
</html>