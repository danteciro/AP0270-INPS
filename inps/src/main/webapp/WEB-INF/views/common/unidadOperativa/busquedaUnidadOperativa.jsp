<%-- 
 * Resumen.
 * Objeto 				:	busquedaUnidadOperatva.jsp
 * Descripcion 			: 	Página jsp para el formulario de búsqueda de Unidades Operativas.
 * Fecha de Creación 	: 	17/05/2016.
 * Autor 				: 	Hernán Torres.
 * ================================================================================================
 * Modificaciones
 * Motivo 				Fecha 			Nombre 					Descripcion
 * ================================================================================================
 *
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/common/unidadOperativa/busquedaUnidadOperativa.js" />' charset="utf-8"></script>
    </head>
    <body>
    	<input type="hidden" id="idGridBusqUnidad" value="${grid}">
    	<div class="form">
            <form id="frmBusqUnidad">
                <div id="divMensajeValidaFrmBusqUnidad" class="errorMensaje" style="display: none"></div>
                <div>
                    
                   	<div class="filaForm">
                        <div class="lbl-small vam"><label for="txtNumeroRHUnidad">Número RH:</label></div>
                        <div>
                        	<input id="txtNumeroRHUnidad" type="text" maxlength="20" class="ipt-medium" />
                        </div>
                    </div>
                  
                    <div class="filaForm">
                        <div class="lbl-small"><label for="txtCadCodigoOsiBusqUnidad">Código OSINERGMIN:</label></div>
                        <div>
                            <input id="txtCadCodigoOsiBusqUnidad" type="text" maxlength="12" class="ipt-medium" />
                         </div>
                    </div>
                    <div class="filaForm">
                    	<div class="lbl-small"><label for="txtRazoSociBusqUnidad">Razón Social:</label></div>
                       	<div>
                           	<input id="txtRazoSociBusqUnidad" type="text" maxlength="25" class="ipt-medium-small-medium" />
                          </div>
                    </div>
                   	<div class="filaForm">
	                    <div class="lbl-small vam"><label for="slctDepartamentoUnidad">Departamento:</label></div>
                        <div class="vam">
                            <select id="slctDepartamentoUnidad" class="slct-medium" name="distrito.idDepartamento" validate="[O]">
                            </select>
                        </div>
                    </div>
                    <div class="filaForm">
                        <div class="lbl-small vam"><label for="slctProvinciaUnidad">Provincia:</label></div>
                        <div class="vam">
                            <select id="slctProvinciaUnidad" class="slct-medium" name="distrito.idProvincia" validate="[O]">
                            </select>
                        </div>
                    </div>  
                    <div class="filaForm">  
                        <div class="lbl-small vam"><label for="slctDistritoUnidad">Distrito:</label></div>
                        <div class="vam">
                            <select id="slctDistritoUnidad"class="slct-medium" name="distrito.idDistrito" validate="[O]">
                                <option value="">--Seleccione--</option>
                            </select>
                        </div>
                    </div>
	                
                </div>
            </form>
        </div>
        <div class="botones">
            <input id="btnBusqUnidad" type="button" class="btn-azul btn-small" value="Buscar">
            <input id="btnCerrarBusqUnidad" type="button" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
    </body>
</html>