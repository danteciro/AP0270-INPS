<%--
* Resumen		
* Objeto		: terminarSupervision.jsp
* Descripción		: terminar la supervision Levantamiento 
* Fecha de Creación	: 11/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
 OSINE791–RSIS40   | 11/10/2016 | Zosimo Chaupis Santur | Crear la funcionalidad para generar los resultados de una supervisión de orden de levantamiento DSR-CRITICIDAD la cual tenga todas sus obligaciones incumplidas subsanadas. 
               |               |                              |
* 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript"
                src='<c:url value="/javascript/app/common/supervision/dsr/terminarSupervision/terminarSupervision.js" />'
                charset="utf-8">
        </script>
    </head>
    <body>
        <!--   OSINE791 - RSIS40 - Inicio -->
        <input type="hidden" id="flagInfraccionesTermSup" name="flagInfraccionesTermSup" value="">
        <div class="form" style="width:100%">
            <c:if test="${modo=='registro'}">
            <form id="frmTerminarSupervision">
                </c:if>
            <!--OSINE_SFS-791 - RSIS 39 - Inicio--> 
                <div id="divMensajeValidaFrmTerminarSup" class="errorMensaje" style="display: none"></div>
                <div id='infraccionesNoSubsanadas' style="margin: 15px 0px">
                    <div <c:if test="${modo=='registro'}">class="pui-subpanel ui-widget-content"</c:if>>
                        <c:if test="${modo=='registro'}">
                            <div class="pui-subpanel-subtitlebar"><span class="ui-panel-title">Infracciones no subsanadas</span></div>
                        </c:if>
                        <div class="pui-subpanel-content">
                            <div class="filaForm tal" id="seccMsjInfracSubsanadas">
                                <div class="lbl-large vam">
                                    <label>No se encontraron infracciones no subsanadas</label>
                                </div>
                            </div> 
                            <div class="tac" style="margin: 20px 0px;" id="seccGridInfNoSubsan">
                                <div id="gridContenedorInfraccionesNoSubsanadas" class="content-grilla"></div>
                            </div>

                        </div>   
                    </div>
                </div>
                <!--OSINE_SFS-791 - RSIS 39 - Fin-->     
                <div id='divDatosFinalesTerminarSupervision' style="margin: 15px 0px">
                    <div class="pui-subpanel ui-widget-content">
                        <div class="pui-subpanel-subtitlebar"><span class="ui-panel-title">Datos Finales Supervisi&oacuten</span></div>
                        <div class="tac">    
                            <div class="pui-subpanel-content form">
                                <div class="filaForm tal" id="seccCartaVisitaINS">
                                    <div class="lbl-small vam"><label>Carta Visita : (*)</label></div>
                                    <div class="vam">
                                        <input  id="cartaVisitaTerSup" validate="[O]" maxlength="10" type="text" class="ipt-medium" value="${sup.cartaVisita}" />
                                    </div>
                                </div>
                                <div class="filaForm tac">                  
                                    <div class="lbl-small vam"><label>Fecha Fin (*):</label></div>
                                    <div class="vam">
                                        <input id="fechaTerminoTerSup" validate="" type="text" maxlength="10" class="ipt-medium" name="fechaTerminoTerSup" value="${sup.fechaFin}" disabled="disabled" <c:if test="${modo=='consulta'}">disabled</c:if>/>
                                        </div>

                                        <div class="lbl-small vam"><label>Hora Fin (*):</label></div>
                                        <div class="vam">
                                            <input id="horaTerminoTerSup" validate="" type="text" maxlength="8" class="timepicker ipt-medium" name="horaTerminoTerSup" value="${sup.horaFin}" disabled="disabled" <c:if test="${modo=='consulta'}">disabled</c:if>/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
            <c:if test="${modo=='registro'}"><div class="txt-obligatorio">(*) Campos obligatorios</div></c:if>
            </div>
        <c:if test="${modo=='registro'}">
            <div class="botones">
                <input type="button" id='btnGenerarResultadosTerminarSup' title="Generar Resultados" class="btn-azul btn-medium" value="Generar Resultados">
            </div>
        </c:if>
        <!--   OSINE791 - RSIS40 - Fin -->
        <!--OSINE_SFS-791 - RSIS 39 - Inicio--> 
        <div class="btnNavSupervisionDsr">
            <c:if test="${modo=='registro'}">
                <input type="button" id='btnAnteriorInfrac' title="Anterior" class="btn-azul btn-small" value="Anterior">
            </c:if>
        </div>
        <!--OSINE_SFS-791 - RSIS 39 - Fin--> 
        <!--   OSINE791 - RSIS41 - Inicio -->
        <div id="divGenerarResultadosDsrTerminarSupervision" style="display: none;" class="dialog" title="RESULTADOS">
            <div id="gridContenedorGenerarResultadosDsrTerminarSupervision" class="content-grilla"></div>
            <div id="divContextMenuContenedorGenerarResultadosDsrTerminarSupervision"></div>
            <div class="botones">
                <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
            </div>
        </div>
        <!--   OSINE791 - RSIS41 - Fin -->
    </body>
</html>
