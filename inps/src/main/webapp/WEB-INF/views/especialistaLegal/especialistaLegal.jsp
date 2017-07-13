<%--
* Resumen		
* Objeto		: especialista.jsp
* Descripción		: especialista
* Fecha de Creación	: 16/06/2015
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-480  |   12/05/2016  |    Hernán Torres Saenz       |     Crear la interfaz para filtros de búsqueda en la funcionalidad "Generar orden de servicio"
* OSINE_SFS-480  |   19/05/2016  |    Hernán Torres Saenz       |     Adecuar  interfaz para la nueva forma de generación de órdenes de servicio (masivo).
* OSINE_SFS-480  |   27/05/2016  |    Luis García Reyna         |     Funcionalidad de Datos de Mensajeria: Datos de Envio, Datos del Cargo
* OSINE_SFS-480  |   06/06/2016  |    Mario Dioses Fernandez    |     Construir formulario de envio a Mensajeria, consumiendo WS
*                |               |                              |
*                |               |                              |
*                |               |                              |
* 
--%>

<%@ include file="/common/taglibs.jsp"%>
<t:template pageTitle="ADMINISTRACION DE EXPEDIENTES" scrollPanelTitle="">
    <jsp:attribute name="headArea">
        <script type="text/javascript" src='<c:url value="/javascript/app/especialistaLegal/especialistaLegal.js" />' charset="utf-8"></script>
        <script type="text/javascript" src='<c:url value="/javascript/app/common/common.js" />' charset="utf-8"></script>
    </jsp:attribute>
    <jsp:attribute name="bodyArea">
        <div class="container pui-panel ui-widget-content">
          <div class="tac txt-title">
            <span>CONSULTA DE EXPEDIENTES</span>
          </div>
          <div class="pui-panel-content">
              <div class="form form-large">
				<!-- cargarUnidadDivision() derivado.js -->
                  <div class="filaForm">
                      <div class="lbl-small"><label class="fwb">División:</label></div>
                      <div id="divDivision"></div>
                  </div>
                  <div class="filaForm">
                      <div class="lbl-small"><label class="fwb">Unidad:</label></div>
                      <div id="divUnidad"></div>
                  </div>
              </div>
            <div id="tabsEspecialista" style="position:relative;">
                <ul>
  <!--                <li><a href="#tabRecepcion">Derivados</a></li>
                  <li><a href="#tabAsignaciones">Asignaciones</a></li>
                  <li><a href="#tabEvaluacion">Evaluación</a></li>
                  <li><a href="#tabNotificadoArchivado">Notificado/Archivado</a></li>-->
                    <c:forEach items="${listadoOpcionEspecialista}" var="t">
                        <li><a href="#tab${t.idOpcion.identificadorOpcion}">${t.idOpcion.nombreOpcion}</a></li>
                    </c:forEach>
                </ul>
                <c:forEach items="${listadoOpcionEspecialista}" var="t">
                    <div id="tab${t.idOpcion.identificadorOpcion}">
                        <jsp:include page="../${t.idOpcion.pageOpcion}"/>
                    </div>
                </c:forEach>
<!--                <div id="tabRecepcion">
                  <//jsp:include page="../especialista/derivado/derivado.jsp"/>
                </div>
                <div id="tabAsignaciones">
                  <//jsp:include page="../especialista/asignacion/asignacion.jsp"/>
                </div>
                <div id="tabEvaluacion">
                  <//jsp:include page="../especialista/evaluacion/evaluacion.jsp"/>
                </div>
                <div id="tabNotificadoArchivado">
                  <//jsp:include page="../especialista/notificadoArchivado/notificadoArchivado.jsp"/>
                </div>-->
            </div>
          </div>
        </div>
        <!-- dialogs -->
        <div id="divVersionesDocumento" style="display: none;" class="dialog">
            <div id="gridContenedorVersionesDocumentos" class="content-grilla"></div>	
            <div class="botones">
                <input id="botonGenerarVersionesDocumentos"type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
            </div>

        </div>
        <div id="dialogGestionarDocumento" class="dialog" style="display:none;"></div>
        <div id="dialogConsultarVersiones" class="dialog" style="display:none;"></div>
        <div id="dialogOrdenServicio" class="dialog" style="display:none;"></div>
        <!-- OSINE_SFS-480 - RSIS 27 - Inicio -->
        <div id="dialogOrdenServicioMasivo" class="dialog" style="display:none;"></div>
        <!-- OSINE_SFS-480 - RSIS 27 - Fin -->
        <div id="dialogMantUnidadOperativa" class="dialog" style="display:none;"></div>
        <div id="dialogReasignarExpediente" class="dialog" style="display:none;"></div>
        <div id="dialogTrazabilidadOrdeServ" class="dialog" style="display:none;"></div>
        <!--  OSINE_SFS-480 - RSIS 06 - Inicio -->
        <div id="dialogConsMensajeriaExpediente" class="dialog" style="display:none;"></div>
        <!--  OSINE_SFS-480 - RSIS 06 - Fin --> 
        <div id="dialogSupervision" class="dialog" style="display:none;"></div>
        <div id="dialogAdjuntoSupervision" class="dialog" style="display:none;"></div>
        <div id="dialogHallazgo" class="dialog" style="display: none;"></div>
        <!-- OSINE_SFS-480 - RSIS 03 - Inicio -->
        <div id="dialogEnviarMensajeria" class="dialog" style="display: none;"></div> 
        <!-- OSINE_SFS-480 - RSIS 03 - Fin -->
        <div id="dialogDescargo" class="dialog" style="display: none;"></div>
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
        <!--busqueda Expedientes-->
        <div id="dialogBusquedaExpediente" class="dialog" style="display:none">
            <jsp:include page="../common/expediente/busquedaExpediente.jsp"/>      
        </div>
        <!-- OSINE_SFS-480 - RSIS 33 - Inicio -->
        <div id="dialogBusquedaUnidadOperativa" class="dialog" style="display:none">
           	<jsp:include page="../common/unidadOperativa/busquedaUnidadOperativa.jsp"/>      
       	</div>
       	<!-- OSINE_SFS-480 - RSIS 33 - Fin -->
    </jsp:attribute>
</t:template>