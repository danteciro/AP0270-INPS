<%--
* Resumen		
* Objeto			: especialista.jsp
* Descripción		: Jsp donde se centraliza las acciones para los formularios del Especialista, gerencia GSM.
* Fecha de Creación	: 16/06/2015
* PR de Creación	: OSINE_SFS-1344.
* Autor				: Hernán Torres.
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
*
--%>

<%@ include file="/common/taglibs.jsp"%>
<t:template pageTitle="ADMINISTRACION DE EXPEDIENTES" scrollPanelTitle="">
    <jsp:attribute name="headArea">
        <script type="text/javascript" src='<c:url value="/javascript/app/GSM/especialista/especialista.js" />' charset="utf-8"></script>
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
            <div id="tabsEspecialistaGSM" style="position:relative;">
                <ul>
                  <li><a href="#tabAsignaciones">Asignaciones</a></li>
                  <li><a href="#tabEvaluacion">Evaluación</a></li>
                </ul>
                <div id="tabAsignaciones">
                  <jsp:include page="../especialista/asignacion/asignacion.jsp"/>
                </div>
                <div id="tabEvaluacion">
                  <jsp:include page="../especialista/evaluacion/evaluacion.jsp"/>
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
        <div id="dialogOrdenServicioMasivo" class="dialog" style="display:none;"></div>
        <div id="dialogTrazabilidadOrdeServ" class="dialog" style="display:none;"></div>
        <div id="dialogSupervision" class="dialog" style="display:none;"></div>
        <div id="dialogAdjuntoSupervision" class="dialog" style="display:none;"></div>
        <div id="dialogHallazgo" class="dialog" style="display: none;"></div>
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
        <div id="dialogBusquedaUnidadOperativa" class="dialog" style="display:none">
           	<jsp:include page="../../GSM/common/unidadOperativa/busquedaUnidadOperativa.jsp"/>      
       	</div>
    </jsp:attribute>
</t:template>