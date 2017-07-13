<%--
* Resumen		
* Objeto		: comentarioIncumplimiento.jsp
* Descripción		: comentarioIncumplimiento
* Fecha de Creación	: 07/09/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: JPIRO
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_MAN_DSR_0022| 19/06/2017 | Carlos Quijano Chavez ::ADAPTER       |	Agregar un comentario Opcional a los comentarios predefinidos
*                |               |                              |
*                |               |                              |
*                |               |                              |
* 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/common/supervision/dsr/comentarioIncumplimiento/comentarioIncumplimiento.js" />' charset="utf-8"></script>
    </head>
    <body>
        <div class="form">
            <input type="hidden" id="idEsceIncumplimientoCI" value="${esceInc.idEsceIncumplimiento}">
			<!--	OSINE_MAN_DSR_0037 - Inicio	                     -->
            <input type="hidden" id="idComentarioOpcionalCI" value="${comentarioOpcional.idComentarioDetalleSupervisionOpcional}">
             <!--	OSINE_MAN_DSR_0037 - Fin	                     -->
            <input type="hidden" id="idDetalleSupervisionCI" value="${idDetalleSupervision}">
            <input type="hidden" id="idInfraccionCI" value="${idInfraccion}">
            <input type="hidden" id="codigoOsinergminCI" value="${codigoOsinergmin}">
            <input type="hidden" id="modoSupervisionCI" value="${modoSupervision}">
            <!--OSINE_SFS-791 - RSIS 16 - Inicio-->
            <input type="hidden" id="idInfraccion" value="${infraccion.idInfraccion}">
            <input type="hidden" id="txtDescripcionInfraccion" value="${infraccion.descripcionInfraccion}">
           
            <c:if test="${esceInc.idEsceIncumplimiento==null && infraccion.idInfraccion!=null}">
                <div class="filaForm">
                    <div class="lbl-medium vat"><label>Descripción Infracción :</label></div>
                    <div class="vam">
                        <textarea class="ipt-medium-small-medium-small-small" disabled="disabled">${infraccion.descripcionInfraccion}</textarea>  
                    </div>
                </div>
            </c:if>
            <!--OSINE_SFS-791 - RSIS 16 - Fin-->
            <c:if test="${esceInc.idEsceIncumplimiento!=null}">
                <div class="filaForm">
                    <div class="lbl-medium vat"><label>Escenario de incumplimiento:</label></div>
                    <div class="vam">
                        <textarea class="ipt-medium-small-medium-small-small" disabled="disabled">${esceInc.idEsceIncuMaestro.descripcion}</textarea>                		                         
                    </div>
                </div>
            </c:if>          
            <div>
                <!--OSINE_SFS-791 - RSIS 16 - Inicio-->
                <c:if test="${modoSupervision=='consulta'}">
                    <div class="lbl-medium ilb vat"><label>Comentario :</label></div>
                </c:if>
                <c:if test="${modoSupervision=='registro'}">
                    <div class="lbl-medium ilb vat"><label>Seleccione comentario:</label></div>	
                </c:if>
                <!--OSINE_SFS-791 - RSIS 16 - Fin-->
                <div class="ilb">
                    <div id="gridContenedorComentarioIncumplimiento" class="content-grilla"></div>
                </div>
				<!-- 	OSINE_MAN_DSR_0037 - Inicio	-->
                <c:if test="${modoSupervision=='registro'}">
					<div class="filaForm">
						<div class="lbl-medium vat">
							<label>Comentario Opcional :</label>
						</div>
						<div class="vam">
                        	<textarea id="comentarioOpcional" class="ipt-medium-small-medium-small-small" disabled="disabled">${comentarioOpcional.descripcion}</textarea>  
                    	</div>
                    	<div class="lbl-medium vat">
							<button id="guardarComentarioOpcional">Editar</button>
						</div>
						
					</div>
				</c:if>
				 <!--	OSINE_MAN_DSR_0037 - Fin	-->
            </div>
        </div>
        <div class="botones">		
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar" />         
        </div>
    </body>
</html>
