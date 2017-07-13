<%--
* Resumen		
* Objeto		: supervisor.jsp
* Descripción		: JSP donde se maneja las acciones de ADMINISTRACION DE EXPEDIENTES de la bandeja del Supervisor.
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-1063
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-1063 |  25/10/2016   |   Giancarlo Villanueva A.    |     Crear la interfaz "devolver asignaciones" de acuerdo a especificaciones.
*                |               |                              |
*                |               |                              |
* 
--%>

<%@ include file="/common/taglibs.jsp"%>
<t:template pageTitle="ADMINISTRACION DE EXPEDIENTES" scrollPanelTitle="">
    <jsp:attribute name="headArea">
        <script type="text/javascript" src='<c:url value="/javascript/app/GSM/supervisor/supervisor.js" />' charset="utf-8"></script>
        <script type="text/javascript" src='<c:url value="/javascript/app/GSM/common/common.js" />' charset="utf-8"></script>
    </jsp:attribute>
    <jsp:attribute name="bodyArea">
        <div class="container pui-panel ui-widget-content">
            <div class="tac txt-title">
                <span>ADMINISTRACIÓN DE EXPEDIENTES</span>
            </div>
            <div class="pui-panel-content">
                <div class="form form-large">
                    <div class="filaForm">
                        <div class="lbl-medium"><label class="fwb">Tipo/Número de Documento:</label></div>
                        <div>
                            <c:if test="${personal.locador.idLocador!=null}">${personal.locador.idTipoDocumento.descripcion} - ${personal.locador.numeroDocumento}</c:if>

                            <c:if test="${personal.supervisoraEmpresa.idSupervisoraEmpresa!=null}">RUC - ${personal.supervisoraEmpresa.ruc}</c:if>
                            </div>
                        </div>
                        <div class="filaForm">
                            <div class="lbl-medium"><label class="fwb">Titular Minero (Empresa Supervisora):</label></div>
                            <div>
                            <c:if test="${personal.locador.idLocador!=null}">${personal.locador.primerNombre} ${personal.locador.segundoNombre} ${personal.locador.apellidoPaterno} ${personal.locador.apellidoMaterno}</c:if>

                            <c:if test="${personal.supervisoraEmpresa.idSupervisoraEmpresa!=null}">${personal.supervisoraEmpresa.razonSocial}${personal.supervisoraEmpresa.nombreConsorcio}</c:if>
                            </div>
                        </div>
                    </div>
                    <div id="tabsSupervisor" style="position:relative;">
                        <ul>
                        	<li><a href="#tabIDENTIFICADOR_SUPERVISADO">ASIGNACIONES</a></li>
<%--                         	<c:forEach items="${listadoOpcionSupervisor}" var="t"> --%>
<%--                             <li><a href="#tab${t.idOpcion.identificadorOpcion}">${t.idOpcion.nombreOpcion}</a></li> --%>
<%--                             </c:forEach> --%>
                    </ul>
<%--                     <c:forEach items="${listadoOpcionSupervisor}" var="t"> --%>
<%--                         <div id="tab${t.idOpcion.identificadorOpcion}"> --%>
<%--                             <jsp:include page="../${t.idOpcion.pageOpcion}"/> --%>
<!--                         </div> -->
<%--                     </c:forEach> --%>
                    <div id="tabIDENTIFICADOR_SUPERVISADO">
                    	<jsp:include page="../supervisor/asignacion/asignacion.jsp"/>
                    </div>
                </div>
                <div id="leyenda" style="margin:5px 0px;">
                    <div><b>Leyenda:</b></div>
                    <div style="margin:3px 0px;">
                        <div class="ilb vam" style="width: 37px;height: 20px;background: #d5e6f7;border:1px solid #999;"></div>
                        <div class="ilb vam">Proveniente del INPS</div>
                    </div>
                    <div>
                        <div class="ilb vam" style="width: 37px;height: 20px;background: #FFF;border:1px solid #999;"></div>
                        <div class="ilb vam">Proveniente del SIGED</div>
                    </div>
                </div>
            </div>
        </div>
        <!-- dialogs -->
        <div id="dialogOrdenServicio" class="dialog" style="display:none;"></div>
        <div id="dialogSubirDocumento" class="dialog" style="display:none;"></div>
        <div id="dialogOrdenServicioSupervisor" class="dialog" style="display:none;"></div>
        <!-- OSINE_SFS-480 - RSIS 38 - Inicio -->
        <div id="dialogOrdenServicioSupervisorDevolver" class="dialog" style="display:none;"></div>
        <!-- OSINE_SFS-480 - RSIS 38 - Fin -->
        <div id="dialogSupervision" class="dialog" style="display:none;"></div>
        <div id="dialogAdjuntoSupervision" class="dialog" style="display:none;"></div>
        <div id="dialogHallazgo" class="dialog" style="display: none;"></div>
        <div id="dialogDescargo" class="dialog" style="display: none;"></div>       
        <!-- htorress - RSIS 9 - Inicio -->
        <div id="dialogGestionarDocumento" class="dialog" style="display:none;"></div>
        <!-- htorress - RSIS 9 - Fin -->
        <!-- htorress - RSIS 13 - Inicio -->
        <div id="dialogConsultarVersiones" class="dialog" style="display:none;"></div>
        <!-- htorress - RSIS 13 - Fin -->
        <!--OSINE_SFS-791 - RSIS 16 - Inicio-->
        <div id="dialogEjecucionMedidaComentario" class="dialog" style="display:none;"></div>
        <!--OSINE_SFS-791 - RSIS 16 - Inicio-->
        <!-- OSINE_SFS-791 - RSIS 33 - Inicio -->
        <div id="dialogOrdenServicioLevantamiento" class="dialog" style="display:none;"></div>
        <!-- OSINE_SFS-791 - RSIS 33 - Fin --> 
        <!--OSINE_SFS-791 - RSIS 03 - Inicio-->
        <div id="dialogAgregarAdjunto" class="dialog" style="display:none;"></div>
        <!--OSINE_SFS-791 - RSIS 03 - Inicio-->

        <div id="divDetalleIncumplidos" style="display: none;" class="dialog">
            <div id="gridContenedorOligacionIncumplida" class="content-grilla"></div>
            <div id="divContextMenuContenedorOligacionIncumplida"></div>
            <div class="botones">
                <input id="botonPopup"type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
            </div>
        </div>
        <div id="divGenerarResultados" style="display: none;" class="dialog">
            <div id="gridContenedorGenerarResultados" class="content-grilla"></div>
            <div id="divContextMenuContenedorGenerarResultados"></div>
            <div class="botones">
                <input id="botonGenerarResultados"type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
            </div>
        </div>
        <!-- htorress - RSIS 13 - Inicio -->
        <div id="divVersionesDocumento" style="display: none;" class="dialog">
            <div id="gridContenedorVersionesDocumentos" class="content-grilla"></div>	
            <div class="botones">
                <input id="botonGenerarVersionesDocumentos"type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
            </div>

        </div>
        <!-- htorress - RSIS 13 - Fin -->
        <!--busqueda Expedientes-->
        <div id="dialogBusquedaExpediente" class="dialog" style="display:none">
            <jsp:include page="../../GSM/common/expediente/busquedaExpediente.jsp"/>      
        </div>
        <!--  OSINE791 - RSIS8 - Inicio --> 
        <div id="dialogMediosProbatorios" class="dialog" style="display:none;"></div>
        <!--  OSINE791 - RSIS8 - Fin --> 
        <!--  OSINE791 - RSIS9 - Inicio --> 
        <div id="dialogComentarioIncumplimiento" class="dialog" style="display:none;"></div>
        <div id="dialogComentarioComplemento" class="dialog" style="display:none;"></div>
        <!--  OSINE791 - RSIS9 - Fin --> 
    </jsp:attribute>
</t:template>