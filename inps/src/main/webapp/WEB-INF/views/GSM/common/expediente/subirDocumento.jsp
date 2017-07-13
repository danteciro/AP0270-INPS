<%-- 
    Document   : asignarOT
    Created on : 17/06/2015, 12:59:51 PM
    Author     : jpiro
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_MAN-004  |  20/06/2017   |   Claudio Chaucca Umana ::ADAPTER     |     Realizar la validacion de existencia de cambios desde la interface de usuario     
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/common/expediente/subirDocumento.js" />' charset="utf-8"></script>
    </head>
    <body>
        <form id="formSubirDocuOS" action="/inps/pages/archivo/subirPghDocuAdjuntoOS" method="post" enctype="multipart/form-data" encoding="multipart/form-data">
            <div class="form">
                <div id="divMensajeValidaFormSubirDocuOS" class="errorMensaje" style="display: none"></div>
                <input type="hidden" name="ordenServicio.idOrdenServicio" validate="[O]" value="${idOrdenServicio}"/>
                <!-- htorress - RSIS 8 - Inicio -->
                <input type="hidden" name="idEmpresaSupervisada" validate="[O]" value="${idSupervisoraEmpresa}">
                <input type="hidden" name="idExpediente" validate="[O]" value="${idExpediente}">
                <input type="hidden" name="asuntoSiged" validate="[O]" value="${asuntoSiged}">
                <input type="hidden" name="codigoSiged" validate="[O]" value="${codigoSiged}">
                <input type="hidden" name="numeroExpediente" validate="[O]" value="${numeroExpediente}">
                <input type="hidden" name="idOrdenServicio" id="idOrdenServicioSubDoc" validate="[O]" value="${idOrdenServicio}">
                <!-- htorress - RSIS 8 - Fin -->
                <div class="filaForm">
                    <div class="lbl-medium"><label class="fwb">Número Expediente:</label></div>
                    <div class="ipt-medium-small">
                        <span class="fwb" id="txtNroExpediente">${numeroExpediente}</span>
                    </div>
                </div>
                <div class="filaForm">
                    <div class="lbl-medium"><label>Descripción(*):</label></div>
                    <div>
                    	<!-- OSINE_MAN-004 - Inicio -->
                    	<!-- <input type="text" id="txtDescDocuSubirDocuOS" name="descripcionDocumento" maxlength="25" class="ipt-medium-small" validate="[O]"/> -->
                        <input type="text" id="txtDescDocuSubirDocuOS" name="descripcionDocumento" maxlength="100" class="ipt-medium-small" validate="[O]"/>
                        <!-- OSINE_MAN-004 - Fin -->
                    </div>
                </div>
                <div class="filaForm">
                    <div class="lbl-medium"><label>Tipo Documento(*):</label></div>
                    <div>
                    	<!-- htorress - RSIS 9 - Inicio -->
                    	<!-- 
                        <select name="idTipoDocumento.idMaestroColumna" class="slct-medium-small" validate="[O]">
                         -->
                        <select name="idTipoDocumento.codigo" class="slct-medium-small" validate="[O]">
                        <!-- htorress - RSIS 9 - Fin -->
                            <option value="">--Seleccione--</option>
                            <c:forEach items="${listadoTipoDocumento}" var="t">
                            	<!-- htorress - RSIS 9 - Inicio -->
                            	<!-- 
                                <option value='${t.idMaestroColumna}'>${t.descripcion}</option>
                                 -->
                                <option value='${t.codigo}'>${t.descripcion}</option>
                                <!-- htorress - RSIS 9 - Fin -->
                            </c:forEach>
                        </select>
                    </div> 
                </div>  
                <div class="filaForm">
                    <div class="lbl-medium"><label>Archivo(*):</label></div>
                    <div class="container-file vam">
                        <input id="fileArchivo" name="archivo" placeholder="" value="" type="file" class="fileUpload" />
                        <input id="file_name" type="text" disabled="disabled" style="width:133px" validate="[O]"> 
                        <a href="#" class="search_file button" style="width:100px" >Examinar</a>
                    </div>

                </div>  
            </div>
        </form>
        
        <div class="botones">
            <input type="button" id="btnGuardarSubirDocumentoOS" class="btn-azul btn-small" value="Guardar">
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
        <div class="txt-obligatorio">(*) Campos obligatorios</div>
        
    </body>
</html>
