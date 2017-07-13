<%--
* Resumen		
* Objeto		: ordenServicioSupervisorDevolver.jsp
* Descripción		: ordenServicioSupervisorDevolver
* Fecha de Creación	: 17/05/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Luis García Reyna
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-480  |  17/05/2016   |   Luis García Reyna          |     Crear opción "devolver asignaciones" en la bandeja del supervisor el cual direccionará a la interfaz "devolver asignaciones".
* OSINE_SFS-480  |  18/05/2016   |   Luis García Reyna          |     Implementar la funcionalidad de devolución de asignaciones de acuerdo a especificaciones.
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
        <script type="text/javascript" src='<c:url value="/javascript/app/common/ordenServicio/ordenServicioSupervisorDevolver.js" />' charset="utf-8"></script>
    </head>
    <body>
        <div id="divMensValidaDevolverOrdenServicio" class="errorMensaje" tabindex='1' style="display: none" ></div>
        <div class="filaForm tac" style="margin: 20px 0px;">
            <div class="fila">
                <div id="gridContenedorOrdeServSupeDev" class="content-grilla"></div>
            </div>
        </div>
        <form id="devolverOrdenServicio">
            <div id="ordServicioSeleccionados" style="display: none;"></div>
            <div class="filaForm">
                <div class="lbl-medium"><label class="fwb" >Petición(*):</label></div>
                <div>               
                    <select id='cmbTipoPeticion' name="idPeticion" class="slct-medium" validate="[O]">
                        <option value="">--Seleccione--</option>
                        <c:forEach items="${listadoTipoPeticion}" var="ltp">
                            <option codigo='${ltp.codigo}' value='${ltp.idMaestroColumna}'>${ltp.descripcion}</option>
                        </c:forEach>
                    </select>              
                </div>                                 
            </div>          
            <div class="filaForm">
                <div class="lbl-medium"><label class="fwb">Motivo(*):</label></div>
                <div>
                    <select id="dvMotivo" name="idMotivo" class="slct-medium" validate="[O]" >
                        <option value="">--Seleccione--</option>
                        <c:forEach items="${listadoMotivos}" var="lm">
                            <option value='${listadoMotivos}'>${lm.descripcion}</option>
                        </c:forEach>
                    </select>
                </div>                                 
            </div> 
            <div class="filaForm">
                <div class="lbl-medium" style="vertical-align: top;"><label class="fwb">Descripción(*):</label></div>
                <textarea id="txtComPetMotivo" validate="[O]" rows="4" class="" name="comentarioDevolucion" style="width:75%" maxlength="400"></textarea>   
            </div>         
        </form>    
        <div class="txt-obligatorio">(*) Campos obligatorios</div>
        <div class="botones">
            <input type="button" id='btnDevolverOrdenesServicio' title="Devolver" class="btn-azul btn-small" value="Devolver">
            <input type="button" title="Cerrar" class="btnCloseDialog btn-azul btn-small" value="Cerrar">
        </div>
    </body>
</html>
