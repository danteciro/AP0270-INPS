<%-- 
    Document   : asignarOT
    Created on : 17/06/2015, 12:59:51 PM
    Author     : jpiro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src='<c:url value="/javascript/app/common/unidadOperativa/mantUnidadOperativa.js" />' charset="utf-8"></script>
    </head>
    <body>
        <div class="tac txt-title">
            <input type="hidden" id="tipoAccionUnidOper" value="${tipo}">
        </div>
        
        <div class="pui-panel-content">
            <div id="tabsUnidadOperativa">
                <ul>
                    <li><a href="#tabEmpresaSupervisada">Empresa Supervisada</a></li>
                    <li><a href="#tabUnidadOperativa">Unidad Operativa</a></li>
                </ul>
                <div id="tabEmpresaSupervisada">
                    <div class="tac" style="width:836px;">
                        <div class="form">
                            <div id="divMensajeValidaEmpresaSupervisada" class="errorMensaje" style="display: none"></div>
							<form id="formEmpresaSupervisadaInps">
                            <div class="filaForm">
                            	<div style="display:none;"><input  id="idEmpresaSupervisadaUO" type="text" name="idEmpresaSupervisada" value="${empresa.idEmpresaSupervisada}" /></div>
                                <div class="lbl-small"><label for="rucEmpresaSupervisada">RUC:</label></div>
                                <div class="vam">
                                    <input id="rucEmpresaSupervisada" name="ruc" type="text" maxlength="11" class="ipt-medium-small" value="${empresa.ruc}"  disabled />
                                </div>
                                <div class="lbl-small"><label for="razonSocialEmpresaSupervisada">Razón Social:</label></div>
                                <div class="vam">
                                    <input id="razonSocialEmpresaSupervisada" name="razonSocial" type="text" maxlength="200" class="ipt-medium-small" value="${empresa.razonSocial}" disabled />
                                </div>
                            </div>                            
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="nombreComercialEmpresaSupervisada">Nombre Comercial(*):</label></div>
                                <div class="vam">
                                    <input id="nombreComercialEmpresaSupervisada" name="nombreComercial" type="text" maxlength="99" class="ipt-medium-small" value="${empresa.nombreComercial}" validate="[O]" />
                                </div>
                                <div class="lbl-small vam"><label for="telefonoEmpresaSupervisada">Teléfono(*):</label></div>
                                <div class="vam">
                                    <input id="telefonoEmpresaSupervisada" name="telefono" type="text" maxlength="28" class="ipt-medium-small" value="${empresa.telefono}" validate="[O]" />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="ciuEmpresaSupervisada">CIIU(*):</label></div>
                                <div class="vam">
                                    <select id="ciuEmpresaSupervisada" class="slct-medium-small" name="ciiuPrincipal" validate="[O]" >
                                        <option value="">--Seleccione--</option>
                                        <c:forEach items="${listaCiius}" var="t">
                                        <option value='${t.idMaestroColumna}' <c:if test="${t.idMaestroColumna==empresa.ciiuPrincipal.idMaestroColumna}">selected</c:if>>${t.descripcion}</option>
                                    	</c:forEach>
                                    </select>
                                </div>
                            </div>
                            </form>
                        </div>
                    </div>
                    <div class="botones">
                        <input type="button" id="btnGuardarEmpresaSupervisada" class="btn-azul btn-small" value="Guardar">
                    </div>
                    <form id="formDireccionEmpresaSupervisada">
                    <fieldset id="fldstDireccionES">
                        <legend>DIRECCIONES</legend>
                        <input id="idEmpresaSupervisadaDireccionUO"  name="idEmpresaSupervisada" value="${empresa.idEmpresaSupervisada}" type="hidden" />
                        <input id="idDireccionEmpresaSupervisadaUO"  name="idDireccionEmpresaSup" type="hidden" />
                        
                        <div class="form" id="divFrmDireccionES">
                            <div class="filaForm">
                                <div class="lbl-small"><label for="tipoDireccionES">Tipo Dirección(*):</label></div>
                                <div>
                                    <select id="tipoDireccionES" class="slct-medium" name="tipoDireccion.idMaestroColumna" validate="[O]" >
                                    	<option value="">--Seleccione--</option>
                                        <c:forEach items="${listaTipoDirecciones}" var="t">
                                        <option value='${t.idMaestroColumna}' >${t.descripcion}</option>
                                    	</c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small"><label for="departamentoES">Departamento(*):</label></div>
                                <div>
                                    <select id="departamentoES" class="slct-medium" name="distrito.idDepartamento" validate="[O]">
                                        <option value="">--Seleccione--</option>
                                    	<c:forEach items="${listaDepartamentos}" var="t">
                                        <option value='${t.idDepartamento}' >${t.nombre}</option>
<%--                                         <c:if test="${t.idDepartamento==empresa.departamento.idDepartamento}">selected</c:if>  --%>
                                    	</c:forEach>
                                    </select>
                                </div>
                                <div class="lbl-small"><label for="provinciaES">Provincia(*):</label></div>
                                <div>
                                    <select id="provinciaES" class="slct-medium" name="distrito.idProvincia" validate="[O]">
                                        <option value="">--Seleccione--</option>
                                    </select>
                                </div>
                                <div class="lbl-small"><label for="distritoES">Distrito(*):</label></div>
                                <div>
                                    <select id="distritoES"class="slct-medium" name="distrito.idDistrito" validate="[O]">
                                        <option value="">--Seleccione--</option>
                                    </select>
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small"><label for="tipoViaES">Tipo Via(*):</label></div>
                                <div>
                                    <select id="tipoViaES" class="slct-medium" name="tipoVia.idMaestroColumna" validate="[O]">
										<option value="">--Seleccione--</option>
                                        <c:forEach items="${listaTipoVias}" var="t">
                                        <option value='${t.idMaestroColumna}' >${t.descripcion}</option>
                                    	</c:forEach>
                                    </select>
                                </div>
                                <div>
                                    <input id="descTipoViaES" type="text" maxlength="60" class="ipt-medium-small" name="descripcionVia" validate="[O]" />
                                </div>
                                <div class="lbl-small"><label for="">Numero:</label></div>
                                <div>
                                    <input id="numeroDireccionES" type="text" maxlength="8" class="ipt-medium" name="numeroVia" validate="[SN]" />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small"><label for="">Interior:</label></div>
                                <div>
                                    <input id="interiorDireccionES" type="text" maxlength="8" class="ipt-medium" name="interior" />
                                </div>
                                <div class="lbl-small"><label for="">Manzana:</label></div>
                                <div>
                                    <input id="manzanaDireccionES" type="text" maxlength="4" class="ipt-medium" name="manzana" />
                                </div>
                                <div class="lbl-small"><label for="">Lote:</label></div>
                                <div>
                                    <input id="loteDireccionES" type="text" maxlength="4" class="ipt-medium" name="lote" />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small"><label for="">Kilometro:</label></div>
                                <div>
                                    <input id="kilometroDireccionES" type="text" maxlength="4" class="ipt-medium" name="kilometro" />
                                </div>
                                <div class="lbl-small"><label for="">Block:</label></div>
                                <div>
                                    <input id="blockDireccionES" type="text" maxlength="3" class="ipt-medium" name="blockChalet" />
                                </div>
                                <div class="lbl-small"><label for="">Etapa:</label></div>
                                <div>
                                    <input id="etapaDireccionES" type="text" maxlength="4" class="ipt-medium" name="etapa" />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="">Nro. Departamento:</label></div>
                                <div class="vam">
                                    <input id="numeroDepartamentoDireccionES" type="text" maxlength="8" class="ipt-medium" name="numeroDepartamento" />
                                </div>
                                <div class="lbl-small vam"><label for="">Urbanización:</label></div>
                                <div class="vam">
                                    <input id="urbanizacionDireccionES" type="text" maxlength="30" class="ipt-medium-small-medium" name="urbanizacion" />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="">Dirección Completa:</label></div>
                                <div class="vam">
                                    <input id="direccionCompletaES" type="text" maxlength="500" class="ipt-medium-small-medium-small-medium" name="direccionCompleta" />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vat"><label for="">Referencia:</label></div>
                                <div class="vam">
                                    <textarea id="referenciaDireccionES" class="ipt-medium-small-medium-small-medium" name="referencia" maxlength="200" ></textarea>
                                </div>
                            </div>
                            <div class="botones">
                                <input type="button" id="btnGuardarDireccionES" class="btn-azul btn-small" value="Guardar">
                                <input type="button" id="btnEditarDireccionES" class="btn-azul btn-small" value="Editar" style="display:none;">
                            </div>
                        </div>
                        <div class="filaForm tac">
                            <div>
                                <div class="fila">
                                    <div id="gridContenedorDireEmprSupe" class="content-grilla"></div>
                                    <div id="divContextMenuDireEmprSupe"></div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    </form>
                    <form id="formEmpresaContactoEmpresaSupervisada">
                    <fieldset id="fldstRepresentanteES">
                        <legend>REPRESENTANTES</legend>
                        <input id="idEmpresaSupervisadaEmpresaContactoUO"  name="idEmpresaSupervisada" value="${empresa.idEmpresaSupervisada}" type="hidden" />
                        <input id="idEmpresaContactoUO"  name="idEmpresaContacto" type="hidden" />
                        <div class="form" id="divFrmRepresentanteES">
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="tipoDocumentoES">Tipo Documento(*):</label></div>
                                <div class="vam">
                                    <select id="tipoDocumentoES" class="slct-medium" name="tipoDocumentoIdentidad.idMaestroColumna" validate="[O]" >
                                        <option value="">--Seleccione--</option>
                                        <c:forEach items="${listaTipoDocumentos}" var="t">
                                        <option value='${t.idMaestroColumna}' >${t.descripcion}</option>
                                    	</c:forEach>
                                    </select>
                                </div>
                                <div class="lbl-small vam"><label for="numeroDocumentoRepresentanteES">Número Documento(*):</label></div>
                                <div class="vam">
                                    <input id="numeroDocumentoRepresentanteES" type="text" class="ipt-medium" name="numeroDocIdentidad" validate="[O]" />
                                </div>
                                <div class="lbl-small"><label for="cargoRepresentanteES">Cargo:</label></div>
                                <div>
                                    <input id="cargoRepresentanteES" type="text" maxlength="50" class="ipt-medium" name="cargo" />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small"><label for="apellidoPaternoRepresentanteES">Apellido Paterno(*):</label></div>
                                <div>
                                    <input id="apellidoPaternoRepresentanteES" type="text" maxlength="49" class="ipt-medium" name="apellidoPaterno" validate="[O][SL]" />
                                </div>
                                <div class="lbl-small"><label for="apellidoMaternoRepresentanteES">Apellido Materno(*):</label></div>
                                <div>
                                    <input id="apellidoMaternoRepresentanteES" type="text" maxlength="49" class="ipt-medium" name="apellidoMaterno" validate="[O][SL]" />
                                </div>
                                <div class="lbl-small"><label for="nombreRepresentanteES">Nombre(*):</label></div>
                                <div>
                                    <input id="nombreRepresentanteES" type="text" maxlength="99" class="ipt-medium" name="nombre" validate="[O][SL]" />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small"><label for="correoRepresentanteES">Correo(*):</label></div>
                                <div>
                                    <input id="correoRepresentanteES" type="text" maxlength="50" class="ipt-medium-small-medium" name="correoElectronico" validate="[O][CORREO]" />
                                </div>
                                <div class="lbl-small"><label for="telefonoRepresentanteES">Teléfono(*):</label></div>
                                <div>
                                    <input id="telefonoRepresentanteES" type="text" maxlength="30" class="ipt-medium" name="telefonoPersonal" validate="[O]" />
                                </div>
                            </div>
                            <div class="botones">
                            <input type="button" id="btnEditarRepresentanteES" class="btn-azul btn-small" value="Editar" style="display:none;" >
                                <input type="button" id="btnGuardarRepresentanteES" class="btn-azul btn-small" value="Guardar">
                            </div>
                        </div>
                        <div class="filaForm tac">
                            <div>
                                <div class="fila">
                                    <div id="gridContenedorReprEmprSupe" class="content-grilla"></div>
                                    <div id="divContextMenuReprEmprSupe"></div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    </form>
                    <div class="txt-obligatorio">(*) Campos obligatorios</div>
                </div>
                <div id="tabUnidadOperativa">                
                    <div class="form" style="width:836px;">
                    <form id="formUnidadSupervisada">
                        <div id="divMensajeValida" class="errorMensaje" style="display: none"></div>
                        
                        <div class="filaForm">
                            <div class="lbl-medium"><label for="txtCodigoOsinergminUS">Código Osinergmin:</label></div>
                            <div class="vam">
                            	<div style="display:none;"><input  id="idEmpresaSupervisadaUS" type="text" name="empresaSupervisada.idEmpresaSupervisada" value="${empresa.idEmpresaSupervisada}" /></div>
                            	<input id="txtIdUnidadSupervisadaUS" name="idUnidadSupervisada" type="hidden" maxlength="25" class="ipt-medium-small" value="" disabled style="width:200px;" />
                                <input id="txtCodigoOsinergminUS" type="text" maxlength="25" class="ipt-medium-small" value="" disabled name="codigoOsinergmin" />
                            </div>
                            <div style="width:120px;"><label for="txtCodigoRHUS">Codigo RH:</label></div>
                            <div class="vam">
                            	<input id="txtIdRHUS" name="idRegistroHidrocarburo" maxlength="10" class="ipt-medium-small" value="" style="width:200px;" type="hidden" />
                                <input id="txtCodigoRHUS" name="nroRegistroHidrocarburo" type="text" maxlength="20" class="ipt-medium-small" value="" style="width:200px;"  />
                            </div>
                        </div>
                        <div class="filaForm">
                            <div class="lbl-medium"><label for="txtNombreUS">Nombre(*):</label></div>
                            <div class="vam">
                                <input id="txtNombreUS" id="nombreUnidad" type="text" maxlength="200" class="ipt-medium-small-medium" value="" name="nombreUnidad" style="width:590px;" validate="[O]" />
                            </div>
                        </div>
                        <div class="filaForm">
                            <div class="lbl-medium vam"><label for="txtActivSel">Rubro(*):</label></div>
                            <div class="vam">
                                <input id="txtActivSel" type="text" class="ipt-medium-small-medium" style="cursor: pointer;width:590px;"  placeholder="Click para seleccionar rubro" validate="[O]"/>
                                <input id="txtIdActivSel" type="hidden" name="actividad.idActividad" validate="[O]" maxlength="10" />
<!--                                <input type="text" maxlength="25" class="ipt-medium-small-medium" />-->
                            </div>
                        </div>
                        <div class="filaForm">
                            <div class="lbl-medium vam"><label for="txaObservacionUS">Observaciones:</label></div>
                            <div class="vam">
                                <textarea id="txaObservacionUS" class="ipt-medium-small-medium" style="width:590px;" maxlength="1180" name="observacion" ></textarea>
                            </div>
                        </div>
                    </form>
                    </div>
                    <div class="botones">
                    	<input type="button" id="btnGuardarUnidadSupervisada" class="btn-azul btn-small" value="Guardar">
                        <input type="button" id="btnEditarUnidadSupervisada" class="btn-azul btn-small" value="Editar" style="display:none;">
                    </div>
                    <div class="filaForm tac">
                        <div>
                            <div class="fila">
                                <div id="gridContenedorUnidOper" class="content-grilla"></div>
                                <div id="divContextMenuUnidOper"></div>
                            </div>
                        </div>
                    </div>
                    <div id="detalleDireccionUnidadSupervisada" style="display:none;">
                    <form id="formDireccionUnidadSupervisada">
                    <fieldset id="fldstDireccionUO">
                        <legend>DIRECCIONES</legend>
                        <input id="idUnidadSupervisadaDireccionUS"  name="idUnidadSupervisada" value="" type="hidden" />
                        <input id="idDireccionUnidadSupervisadaUS"  name="idDirccionUnidadSuprvisada" type="hidden" />
                        <div class="form" id="divFrmDireccionUO">
                            <div class="filaForm">
                                <div class="lbl-small"><label for="tipoDireccionUS">Tipo Dirección(*):</label></div>
                                <div>
                                    <select id="tipoDireccionUS" validate="[O]" name="idTipoDireccion.idMaestroColumna" class="slct-medium">
                                        <option value="">--Seleccione--</option>
                                        <c:forEach items="${listaTipoDireccionesUS}" var="t">
                                        <option value='${t.idMaestroColumna}' >${t.descripcion}</option>
                                    	</c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small"><label for="departamentoUS">Departamento(*):</label></div>
                                <div>
                                    <select id="departamentoUS" validate="[O]" name="departamento.idDepartamento" class="slct-medium">
                                        <option value="">--Seleccione--</option>
                                        <c:forEach items="${listaDepartamentosUS}" var="t">
                                        <option value='${t.idDepartamento}' >${t.nombre}</option>
                                    	</c:forEach>
                                    </select>
                                </div>
                                <div class="lbl-small"><label for="provinciaUS">Provincia(*):</label></div>
                                <div>
                                    <select id="provinciaUS" validate="[O]" name="provincia.idProvincia" class="slct-medium">
                                        <option value="">--Seleccione--</option>
                                    </select>
                                </div>
                                <div class="lbl-small"><label for="distritoUS">Distrito(*):</label></div>
                                <div>
                                    <select id="distritoUS" validate="[O]" name="distrito.idDistrito" class="slct-medium">
                                        <option value="">--Seleccione--</option>
                                    </select>
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small"><label for="tipoViaUS">Tipo Vía(*):</label></div>
                                <div>
                                    <select id="tipoViaUS" validate="[O]" name="idTipoVia.idMaestroColumna" class="slct-medium">
                                        <option value="">--Seleccione--</option>
                                        <c:forEach items="${listaTipoViasUS}" var="t">
                                        <option value='${t.idMaestroColumna}' >${t.descripcion}</option>
                                    	</c:forEach>
                                    </select>
                                </div>
                                <div>
                                    <input id="descripcionViaUS" name="descripcionVia" type="text" maxlength="60" class="ipt-medium-small" />
                                </div>
                                <div class="lbl-small"><label for="">Número:</label></div>
                                <div>
                                    <input id="numeroViaUS" name="numeroVia" type="text" maxlength="8" class="ipt-medium" validate="[SN]" />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small"><label for="">Interior:</label></div>
                                <div>
                                    <input id="interiorUS" name="interior" type="text" maxlength="8" class="ipt-medium" />
                                </div>
                                <div class="lbl-small"><label for="">Manzana:</label></div>
                                <div>
                                    <input id="manzanaUS" name="manzana" type="text" maxlength="4" class="ipt-medium" />
                                </div>
                                <div class="lbl-small"><label for="">Lote:</label></div>
                                <div>
                                    <input id="loteUS" name="lote" type="text" maxlength="4" class="ipt-medium" />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small"><label for="">Kilometro:</label></div>
                                <div>
                                    <input id="kilometroUS" name="kilometro" type="text" maxlength="4" class="ipt-medium" />
                                </div>
                                <div class="lbl-small"><label for="">Block:</label></div>
                                <div>
                                    <input id="blockUS" name="blockChallet" type="text" maxlength="3" class="ipt-medium" />
                                </div>
                                <div class="lbl-small"><label for="">Etapa:</label></div>
                                <div>
                                    <input id="etapaUS" name="etapa" type="text" maxlength="4" class="ipt-medium" />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="">Nro. Departamento:</label></div>
                                <div class="vam">
                                    <input id="numeroDepartamentoUS" name="numeroDepartamento" type="text" maxlength="8" class="ipt-medium" />
                                </div>
                                <div class="lbl-small vam"><label for="">Urbanización:</label></div>
                                <div class="vam">
                                    <input id="urbanizacionUS" name="urbanizacion" type="text" maxlength="30" class="ipt-medium-small-medium" />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vam"><label for="">Dirección Completa:</label></div>
                                <div class="vam">
                                    <input id="direccionCompletaUS" name="direccionCompleta" type="text" maxlength="500" class="ipt-medium-small-medium-small-medium" />
                                </div>
                            </div>
                            <div class="filaForm">
                                <div class="lbl-small vat"><label for="">Referencia:</label></div>
                                <div class="vam">
                                    <textarea id="referenciaUS" maxlength="200" name="referencia" class="ipt-medium-small-medium-small-medium"></textarea>
                                </div>
                            </div>
                            <div class="botones">
                                <input type="button" id="btnGuardarDireccionUS" class="btn-azul btn-small" value="Guardar">
                                <input type="button" id="btnEditarDireccionUS" class="btn-azul btn-small" value="Editar" style="display:none;">
                            </div>
                        </div>
                        <div class="filaForm tac">
                            <div>
                                <div class="fila">
                                    <div id="gridContenedorDireUnidSupe" class="content-grilla"></div>
                                    <div id="divContextMenuDireUnidOper"></div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    </form>
                    </div>
                    <div class="txt-obligatorio">(*) Campos obligatorios</div>
                </div>
            </div>
        </div>
        
        <div class="botones">
            <br>
        </div>
    </body>
</html>
