<%--
* Resumen		
* Objeto		: obligacionDsrComentarioSubsanacion.jsp
* Descripción		: supervision
* Fecha de Creación	: 17/08/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
*OSINE791–RSIS14 |01/09/2016     | Zosimo Chaupis Santur        | Considerar la funcionalidad para subsanar una obligación marcada como "INCUMPLIDA" de una supervisión  de orden de supervisión DSR-CRITICIDAD
                 |               |                              |
*                |               |                              |
*                |               |                              |
* 
--%>
<!--  OSINE791 - RSIS14 - Inicio -->  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript"
                src='<c:url value="/javascript/app/common/supervision/dsr/obligacion/obligacionDsrComentarioSubsanacion.js" />'
        charset="utf-8"></script>
    </head>
    <body>
        <div class="form" id="formRegSupervisionDsrComentarioSubsanacion">
            <div class="obligacionComentarioSubsanacionContainer">
                <input type="hidden" id="flagEditComenSubsa" value="${flagEditar}">
                <div class="pui-panel-content">
                    <div class="filaForm">
                        <div class="vam"><label>Comentario Subsanaci&oacute;n de incumplimiento (*):</label></div>                        
                    </div>
                    <div class="filaForm" id="containerComentarioSubsanacion">
                        <div class="vam">
                            <textarea id="AreaComentarioSubsanacion" class="ipt-medium-small-medium-small-medium " validate="[O]" rows="6" maxlength="4000" ></textarea>                		                         
                        </div>
                    </div>
                </div>
            </div>
            <div class="txt-obligatorio">(*) Campos obligatorios</div>
        </div>
        <div class="botones">	            
            <input type="button" id="btnGuardarComentarioSubsanacion" class="btn-azul btn-small" style="margin: 0 25px 0 0" value="Guardar" >
            <input type="button" id="btnCerrarComentarioSubsanacion" title="Cerrar" class="btn-azul btn-small" value="Cerrar" />         
        </div>
    </body>
</html>
<!--  OSINE791 - RSIS14 - Fin -->  