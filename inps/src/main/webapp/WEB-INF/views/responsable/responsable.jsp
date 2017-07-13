<%-- 
    Document   : responsable
    Created on : 30/06/2015, 12:00:48 AM
    Author     : jpiro
--%>

<%@ include file="/common/taglibs.jsp"%>
<t:template pageTitle="ADMINISTRACION DE EXPEDIENTES" scrollPanelTitle="">
    <jsp:attribute name="headArea">
        <script type="text/javascript" src='<c:url value="/javascript/app/responsable/responsable.js" />' charset="utf-8"></script>
        <script type="text/javascript" src='<c:url value="/javascript/app/common/common.js" />' charset="utf-8"></script>
    </jsp:attribute>
    <jsp:attribute name="bodyArea">
        <div class="container pui-panel ui-widget-content tac">
            <div class="txt-title">
                <span>ADMINISTRACIÓN DE EXPEDIENTES</span>
            </div>

            <div class="pui-panel-content">
                <div id="tabsResponsable">
                    <ul>
<!--                        <li><a href="#tabRecepcion">Recepción</a></li>-->
                        <c:forEach items="${listadoOpcionResponsable}" var="t">
                            <li><a href="#tab${t.idOpcion.identificadorOpcion}">${t.idOpcion.nombreOpcion}</a></li>
                        </c:forEach>
                    </ul>
                    <c:forEach items="${listadoOpcionResponsable}" var="t">
                        <div id="tab${t.idOpcion.identificadorOpcion}">
                            <jsp:include page="../${t.idOpcion.pageOpcion}"/>
                        </div>
                    </c:forEach>                    
<!--                    <div id="tabRecepcion">
                        <//jsp:include page="../responsable/recepcion/recepcion.jsp"/>
                    </div>-->
                </div>
                <div id="leyenda" style="margin:5px 0px;text-align: left">
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
        <div id="dialogDocumentoExpediente" class="dialog" style="display:none;"></div>
        <div id="dialogMantUnidadOperativa" class="dialog" style="display:none;"></div>
        <div id="dialogUnidadOperativa" class="dialog" style="display:none;"></div>
        <div id="dialogSeleccActividad" class="dialog" style="display:none;"></div>
        <div id="dialogTrazabilidadOrdeServ" class="dialog" style="display:none;"></div>
        <!--busqueda Expedientes-->
        <div id="dialogBusquedaExpediente" class="dialog" style="display:none">
            <jsp:include page="../common/expediente/busquedaExpediente.jsp"/>      
        </div>
    </jsp:attribute>
</t:template>