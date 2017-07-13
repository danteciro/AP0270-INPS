<%--
* Resumen		
* Objeto			: generarOficio.jsp
* Descripción		: Carga de oficio
* Fecha de Creación	: 11/10/2016
* PR de Creación	: OSINE_SFS-1063
* Autor				: Victoria Ubaldo G.
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
        <script type="text/javascript" src='<c:url value="/javascript/app/dse/especialista/rechazoCarga/generarOficio.js"/>' charset="utf-8"></script>
    </head>
    <body>
    <!--  -->
    
        <input type="hidden" id="txtRuc" name="ruc" validate="[O]"  value="${ruc}">
        <input type="hidden" id="txtIdEmpresa" name="idEmpresa" validate="[O]"   value="${idEmpresa}">
       <input type="hidden" id="txtAnio"  name="anio" validate="[O]"   value="${anio}">
        <input type="hidden" id="txtDescEmpresa" name="empresa" validate="[O]"   value="${empresa}">
    <!--  -->
         <form id="formSubirOficio" action="/inps/pages/empresasVw/subirOficioRechazoCarga" method="post" enctype="multipart/form-data" encoding="multipart/form-data">
            <div class="form">
                <div id="divMensajeValidaFormSubirOficio" class="errorMensaje" style="display: none"></div>
 
                <div class="filaForm">
                    <div class="lbl-medium"><label>Oficio: </label></div>
                    <div class="container-file vam">
                        <input id="fileArchivo" name="archivos[0]" placeholder="" value="" type="file" class="fileUpload" />
                        <input id="file_name" validate="[O]" type="text" disabled="disabled" style="width:250px"  > 
                        <a href="#" class="search_file button" style="width:100px" >Examinar</a>
                    </div>
                </div>  
                 <div class="filaForm">
                    <div class="lbl-medium"><label>Firma:</label></div>
                    <div>
                        <input type="text" id="txtFirma" class="ipt-medium-small"  style="width:250px" value='${firmaOficio}' disabled />
                    </div>
                </div>
            </div>
            	 
            
        </form>
        
        <div class="botones">
	 <button id="btnGuardarOficio" class="btnSimple" title="Guardar" type="button">Guardar</button>
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cancelar">
        </div>
        
 
    </body>
</html>
