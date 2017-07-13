<%--
* Resumen		
* Objeto			: adjuntarActaInspeccion.jsp
* Descripción		: Adjuntar actas por la Zona de empresa.
* Fecha de Creación	: 16/09/2016
* PR de Creación	: OSINE_SFS-1063.
* Autor				: Hernan Torres Saenz.
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
        <script type="text/javascript" src='<c:url value="/javascript/app/dse/especialista/actaInspeccion/adjuntarActaInspeccion.js" />' charset="utf-8"></script>
    </head>
    <body>
        <form id="formAdjuntarActaInspeccion" action="/inps/pages/actaInspeccion/adjuntarActaInspeccion" method="post" enctype="multipart/form-data" encoding="multipart/form-data">
            <div class="form">
                <div id="divMensajeValidaFormAdjuntarActaInspeccion" class="errorMensaje" style="display: none"></div>
                
					<input type="hidden" id="idSupeCampRechCargaActa" name="idSupeCampRechCargaActa" validate="[O]" value="${idSupeCampRechCargaActa}"/>
					<input type="hidden" id="numeroExpedienteActa" name="numeroExpedienteActa" value="${numeroExpedienteActa}"/>
					<input type="hidden" id="descripcionEmpresaActa" name="descripcionEmpresaActa" value="${descripcionEmpresaActa}"/>
					<input type="hidden" id="anioActa" name="anioActa" validate="[O]" value="${anioActa}"/>
					<input type="hidden" id="rucActa" name="rucActa" value="${rucActa}"/>
				
                <div class="filaForm">
                    <div class="lbl-small"><label>Acta Inspecci&oacute;n:</label></div>
                    <div class="container-file vam">
                        <input id="fileActa" name="acta" placeholder="" value="" type="file" class="fileUpload" />
                        <input id="file_name_acta" type="text" disabled="disabled" style="width:200px" validate="[O]"> 
                        <a href="#" class="search_file button" style="width:100px" >Examinar</a>
                    </div>

                </div>  
            </div>
        </form>
        
        <div class="botones">
            <input type="button" id="btnGuardarAdjuntarInspeccion" class="btn-azul btn-small" value="Guardar">
            <input type="button" title="Cancelar" class="btnCloseDialog btn-azul btn-small" value="Cancelar">
        </div>
        
    </body>
</html>
