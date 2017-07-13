<%--
* Resumen		
* Objeto			: bandejaEspecialista.jsp
* Descripción		: Bandeja del Especialista de la DSE.
* Fecha de Creación	: 15/09/2016.
* PR de Creación	: OSINE_SFS-1063
* Autor				: Victoria Uvaldo.
* ====================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                 |     Descripción
* ====================================================================================
* 
--%>

<%@ include file="/common/taglibs.jsp"%>
<t:template pageTitle="PROCESO RECHAZO CARGA" scrollPanelTitle="">
    <jsp:attribute name="headArea">
        <script type="text/javascript" src='<c:url value="/javascript/app/dse/especialista/bandejaEspecialista.js" />' charset="utf-8"></script>
        <script type="text/javascript" src='<c:url value="/javascript/app/common/common.js" />' charset="utf-8"></script>
    </jsp:attribute>
    
    <jsp:attribute name="bodyArea">
        <div class="container pui-panel ui-widget-content">
          <input type="hidden" id="" name="anioInicial"  value="${anioInicial}"> 
          <div class="tac txt-title">
            <span>PROCESO DE RECHAZO AUTOM&Aacute;TICO DE CARGA Y GENERACI&Oacute;N</span>
          </div>
          <div class="pui-panel-content">
 			<div id="tabsEspecialistaDse" style="position:relative;">
 				<ul>
                    <li><a href="#rechazoCarga">Rechazo Carga</a></li>
                    <li><a href="#tabRegistroActaInspeccion">Registro Acta Inspecci&oacute;n</a></li>
                     <li><a href="#tabRegistroInformeSupervision">Registro Informe Supervisi&oacute;n</a></li>
                </ul>
                    <div id="rechazoCarga">
                        <jsp:include page="../especialista/rechazoCarga/bandejaRechazoCarga.jsp"/>
                    </div>
                    <div id="tabRegistroActaInspeccion">
                        <jsp:include page="../especialista/actaInspeccion/actaInspeccion.jsp"/>
                    </div>
                    <div id="tabRegistroInformeSupervision"> 
                         <jsp:include page="../especialista/informeSupervision/registroInformeSupervision.jsp"/> 
                    </div> 
 			</div>
             

          </div>
        </div>
       <div id="dialogActaExpedientesZonaEspecialista" class="dialog" style="display:none;"></div> -->
<!--         dialogs -->
<!--         <div id="dialogOrdenServicio" class="dialog" style="display:none;"></div> -->
<!--         OSINE_SFS-480 - RSIS 27 - Inicio -->
<!--         <div id="dialogOrdenServicioMasivo" class="dialog" style="display:none;"></div> -->
<!--         OSINE_SFS-480 - RSIS 27 - Fin -->
<!--         <div id="dialogMantUnidadOperativa" class="dialog" style="display:none;"></div> -->
<!--         <div id="dialogReasignarExpediente" class="dialog" style="display:none;"></div> -->
<!--         <div id="dialogTrazabilidadOrdeServ" class="dialog" style="display:none;"></div> -->
<!--          OSINE_SFS-480 - RSIS 06 - Inicio -->
<!--         <div id="dialogConsMensajeriaExpediente" class="dialog" style="display:none;"></div> -->
<!--          OSINE_SFS-480 - RSIS 06 - Fin  -->
<!--         <div id="dialogSupervision" class="dialog" style="display:none;"></div> -->
<!--         <div id="dialogAdjuntoSupervision" class="dialog" style="display:none;"></div> -->
<!--         <div id="dialogHallazgo" class="dialog" style="display: none;"></div> -->
<!--         OSINE_SFS-480 - RSIS 03 - Inicio -->
<!--         <div id="dialogEnviarMensajeria" class="dialog" style="display: none;"></div>  -->
<!--         OSINE_SFS-480 - RSIS 03 - Fin -->
<!--         <div id="dialogDescargo" class="dialog" style="display: none;"></div> -->
<!--         <div id="divDetalleIncumplidos" style="display: none;" class="dialog">         -->
<!-- 			<div id="gridContenedorOligacionIncumplida" class="content-grilla"></div> -->
<!-- 			<div id="divContextMenuContenedorOligacionIncumplida"></div> -->
<!-- 			<div class="botones"> -->
<!-- 				<input id="botonPopup"type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar"> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!--         <div id="divGenerarResultados" style="display: none;" class="dialog"> -->
<!--                     <div id="gridContenedorGenerarResultados" class="content-grilla"></div> -->
<!--                     <div id="divContextMenuContenedorGenerarResultados"></div> -->
<!--                     <div class="botones"> -->
<!--                             <input id="botonGenerarResultados"type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar"> -->
<!--                     </div> -->
<!--             </div> -->
    </jsp:attribute>
</t:template>