<%--
* Resumen		
* Objeto		: jefe.jsp
* Descripción		: jefe
* Fecha de Creación	: 25/03/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Hernán Torres Saen
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-480  |   12/05/2016  |    Hernán Torres Saenz       |     Crear la interfaz para filtros de búsqueda en la funcionalidad "Generar orden de servicio"
* OSINE_SFS-480  |   19/05/2016  |    Hernán Torres Saenz       |     Transportar lista unidades supervisadas asociadas al expediente
* OSINE_SFS-480  |   06/06/2016  |    Mario Dioses Fernandez    |     Construir formulario de envio a Mensajeria, consumiendo WS
*                |               |                              |
*                |               |                              |
*                |               |                              |
* 
--%>

<%@ include file="/common/taglibs.jsp"%>
<t:template pageTitle="ADMINISTRACION DE EXPEDIENTES" scrollPanelTitle="">
    <jsp:attribute name="headArea">
        <script type="text/javascript" src='<c:url value="/javascript/app/GSM/gerenteDivision/gerenteDivision.js" />' charset="utf-8"></script>
        <script type="text/javascript" src='<c:url value="/javascript/app/GSM/common/common.js" />' charset="utf-8"></script>
    </jsp:attribute>
    <jsp:attribute name="bodyArea">
        <div class="container pui-panel ui-widget-content">
          <div class="tac txt-title">
            <span>ADMINISTRACIÓN DE EXPEDIENTES</span>
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
           <%--  <div id="tabsJefe" style="position:relative;">
                <ul>
                    <c:forEach items="${listadoOpcionJefe}" var="t">
                        <li><a href="#tab${t.idOpcion.identificadorOpcion}">${t.idOpcion.nombreOpcion}</a></li>
                    </c:forEach>
                </ul>
                <c:forEach items="${listadoOpcionJefe}" var="t">
                    <div id="tab${t.idOpcion.identificadorOpcion}">
                        <jsp:include page="../${t.idOpcion.pageOpcion}"/>
                    </div>
                </c:forEach>
            </div> --%>
            
		   <div id="tabsGerenteDivision" style="position:relative;">
                        <ul>
                           <li><a href="#tabIDENTIFICADOR_SUPERVISADO">EVALUACION</a></li>
<%--                              <c:forEach items="${listadoOpcionSupervisor}" var="t"> --%>
<%--                             <li><a href="#tab${t.idOpcion.identificadorOpcion}">${t.idOpcion.nombreOpcion}</a></li> --%>
<%--                             </c:forEach> --%>
                    </ul>
<%--                     <c:forEach items="${listadoOpcionSupervisor}" var="t"> --%>
<%--                         <div id="tab${t.idOpcion.identificadorOpcion}"> --%>
<%--                             <jsp:include page="../${t.idOpcion.pageOpcion}"/> --%>
<!--                         </div> -->
<%--                     </c:forEach> --%>
                    <div id="tabIDENTIFICADOR_SUPERVISADO">
                           <jsp:include page="../gerenteDivision/evaluacion/evaluacion.jsp"/>
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
            <jsp:include page="../../GSM/common/expediente/busquedaExpediente.jsp"/>      
        </div>
        <!-- OSINE_SFS-480 - RSIS 33 - Inicio -->
        <div id="dialogBusquedaUnidadOperativa" class="dialog" style="display:none">
           	<jsp:include page="../../GSM/common/unidadOperativa/busquedaUnidadOperativa.jsp"/>      
       	</div>
       	<!-- OSINE_SFS-480 - RSIS 33 - Fin -->
    </jsp:attribute>
</t:template>