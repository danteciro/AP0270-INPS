/**
 * Resumen		
 * Objeto		: recepcionVisita.js
 * Descripción		: Registro de los Datos de Recepcion de la Visita de una supervision
 * Fecha de Creación	: 18/08/2016
 * PR de Creación	: OSINE_SFS-791
 * Autor	        : GMD
 * =====================================================================================================================================================================
 * Modificaciones |
 * Motivo         |  Fecha        |   Nombre                     |     Descripción
 * =====================================================================================================================================================================
 * REQF-0006      |  18/08/2016   |   Zosimo Chaupis Santur      |     Crear la funcionalidad para registrar los datos de Recepción de Visita de una supervisión de orden de supervisión DSR-Criticidad de la casuística SUPERVISIÓN REALIZADA
 * REQF-0022      |  22/08/2016   |   Jose Herrera Pajuelo       |     Crear la funcionalidad para consultar una supervisión realizada cuando se debe aprobar una orden de supervisión DSR-CRITICIDAD.
 */

//common/supervision/dsr/recepcionVisita/recepcionVisita.js
//  OSINE791 - RSIS06 - Inicio -->
var coRecepcionVisita = {
    validarIdentificaPersona: function() {
        if ($('#asignacionOS').val() == '1') {//si es con visita
            if($('#modoSupervision').val()=='registro'){
                if ($('#txtIdPersonaRS').val() != '') {
                    $('#slctCargoAtiendeRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#txtCorreoElectronicoRS').removeAttr("disabled");
                    $('#slctTipoDocumentoRS,#txtNumeroDocumentoRS').attr("disabled", "disabled");
                    $('#btnBuscarPersonaAtiende').val("Corregir");
                } else {
                    $('#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#slctCargoAtiendeRS,#txtCorreoElectronicoRS').attr("disabled", "disabled");
                    $('#slctTipoDocumentoRS,#txtNumeroDocumentoRS').removeAttr("disabled");
                    $('#btnBuscarPersonaAtiende').val("Buscar");
                }
            }
            $('#slctTipoDocumentoRS,#txtNumeroDocumentoRS,#slctCargoAtiendeRS').attr("validate", "[O]");
            $('#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS').alphanum({allowNumeric: false, allowLatin: true, allowUpper: true, allowLower: true, allowCaseless: true, allowOtherCharSets: false, allowSpace: true,allow: 'Ññ'});
            
            $("#txtCorreoElectronicoRS").attr("validate", "[O][CORREO]");
            $('#txtObservacionPersonaAtiendeRS').removeAttr("validate");
            $('#btnBuscarPersonaAtiende').css('display', 'inline-block');
            if ($('#chkIdentificaRS').is(':checked')) {
                coRecepcionVisita.personaSeIdentifico('no');
                $('#slctTipoDocumentoRS,#txtNumeroDocumentoRS,#slctCargoAtiendeRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#txtCorreoElectronicoRS').attr("disabled", "disabled");
                $('#slctTipoDocumentoRS,#txtNumeroDocumentoRS,#slctCargoAtiendeRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#txtCorreoElectronicoRS').removeAttr("validate");
                $('#btnBuscarPersonaAtiende').css('display', 'none');
                $('#slctTipoDocumentoRS,#txtNumeroDocumentoRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#slctCargoAtiendeRS,#txtIdPersonaRS,#txtCorreoElectronicoRS').val("");
                $('#txtObservacionPersonaAtiendeRS').attr("validate", "[O]");
            } else {
                coRecepcionVisita.personaSeIdentifico('si');
            }
        } else {
            $('#btnBuscarPersonaAtiende').css('display', 'none');
            $('#chkIdentificaRS,#slctTipoDocumentoRS,#txtNumeroDocumentoRS,#slctCargoAtiendeRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#txtObservacionPersonaAtiendeRS,#txtCorreoElectronicoRS').attr("disabled", "disabled");
        }
    }
    ,
    personaSeIdentifico: function(opcion) {
        if (opcion == 'si') {
            $('span[class="obligatorioPV"]').css('display', 'inline');
            $('span[class="notObligatorioPV"]').css('display', 'none');
        }
        if (opcion == 'no') {
            $('span[class="obligatorioPV"]').css('display', 'none');
            $('span[class="notObligatorioPV"]').css('display', 'inline');
        }
        if (opcion == 'ninguna') {
            $('span[class="obligatorioPV"]').css('display', 'none');
            $('span[class="notObligatorioPV"]').css('display', 'none');
        }

    },
    validarBuscaPersonaAtiende: function() {
        var validarBuscaPersonaAtiendeBand = true;
        $('#slctTipoDocumentoRS,#txtNumeroDocumentoRS').removeClass('error');
        if ($('#slctTipoDocumentoRS').val() == "") {
            $('#slctTipoDocumentoRS').addClass('error');
            validarBuscaPersonaAtiendeBand = false;
        }
        if ($('#txtNumeroDocumentoRS').val() == "") {
            $('#txtNumeroDocumentoRS').addClass('error');
            validarBuscaPersonaAtiendeBand = false;
        }
        return validarBuscaPersonaAtiendeBand;
    },
    validarTipoDocumento: function(tipoDocumento, numeroDocumento) {
        var bandera = false;
        $('#txtNumeroDocumentoRS').addClass('error');
        if (tipoDocumento == 'DNI') {
            if (numeroDocumento.length == 8) {
                $('#txtNumeroDocumentoRS').removeClass('error');
                return true;
            }
        }
        else if (tipoDocumento == 'CE') {
            if (numeroDocumento.length >= 8 && numeroDocumento.length <= 12) {
                $('#txtNumeroDocumentoRS').removeClass('error');
                return true;
            }
        }
        else if (tipoDocumento == 'PASAPORTE') {
            if (numeroDocumento.length == 12) {
                $('#txtNumeroDocumentoRS').removeClass('error');
                return true;
            }
        }
        else if (tipoDocumento == 'CED. DIPLOMATICA DE IDENTIDAD') {
            if (numeroDocumento.length == 15) {
                $('#txtNumeroDocumentoRS').removeClass('error');
                return true;
            }
        }
        return bandera;
    }
    ,
    buscarPersonaAtiende: function(tipoDocumento, numeroDocumento) {
        $.ajax({
            url: baseURL + "pages/supervision/buscarPersona",
            type: 'post',
            async: false,
            data: {idTipoDocumento: tipoDocumento,
                numeroDocumento: numeroDocumento,
                flagUltimo: '1'},
            beforeSend: loading.open(),
            success: function(data) {
                if (data.resultado == 0) {
                    mensajeGrowl("success", data.mensaje);
                    if (data.persona != null) {
                        $('#txtIdPersonaRS').val(data.persona.idPersonaGeneral);
                        $('#txtApellidoPaternoRS').val(data.persona.apellidoPaternoPersona);
                        $('#txtApellidoMaternoRS').val(data.persona.apellidoMaternoPersona);
                        $('#txtNombresRS').val(data.persona.nombresPersona);
                    } else {
                        $('#txtIdPersonaRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#txtCorreoElectronicoRS').val('');
                    }
                    $('#slctTipoDocumentoRS').attr("disabled", "disabled");
                    $('#txtNumeroDocumentoRS').attr("disabled", "disabled");
                    $('#txtIdPersonaRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#slctCargoAtiendeRS,#txtCorreoElectronicoRS').removeAttr("disabled");
                    $('#btnBuscarPersonaAtiende').val("Corregir");
                    loading.close();
                } else if (data.resultado == 1) {
                    mensajeGrowl('error', data.mensaje);
                    loading.close();
                }
            },
            error: errorAjax
        });
    },
    guardarPersonaAtiendeSP: function(vista) {
		$('#gridObligacionListado').trigger( 'reloadGrid' );	
        var mensajeValidacion = '';
        if ($('#divFrmPersonaAtiendeSP').validateAllForm('#divMensajeValidaFrmSup')) {
            mensajeValidacion = coRecepcionVisita.validarPersonaAtiendeSP();
            if (mensajeValidacion == '') {
                var rpta = coRecepcionVisita.ValidarNuevosCambios()
                //alert("valor es : |" + rpta + "|");
                if (rpta != '') {
                    confirmer.open('¿Confirma que desea registrar la Recepción de Visita de la supervisión?', 'coRecepcionVisita.registrarPersonaAtiendeSP("' + vista + '")');
                } else {
                    //$("#tabObligacionesVer").tabs({active: 1});
                    if (vista == 'vistaObligaciones') {
                        //$("#tabObligacionesVer").trigger("click");
                        coSupeDsrSupe.MostrarTab(2, 1);
                    } else {
                        if (vista == 'VistaDatosIniciales') {
                            //$("#tabDatosInicialesVer").trigger("click");
                            coSupeDsrSupe.MostrarTab(0, 1);
                        }
                    }

                }
            }
            else {
                mensajeGrowl('warn', mensajeValidacion);
            }
        }
    },
    registrarPersonaAtiendeSP: function(vista) {
        loading.open();
        var flagIdentificaPers = '';
        if ($('#asignacionOS').val() == '1') {//si es con visita
            if ($('#chkIdentificaRS').is(':checked')) {
                flagIdentificaPers = '0';
            }
            else {
                flagIdentificaPers = '1';
            }
        }      
        
        $.ajax({
            url: baseURL + "pages/supervision/guardarPersonaAtiende",
            type: 'post',
            async: false,
            data: {
            	idSupervision: $('#idSupervision').val(),
                flagIdentificaPersona: flagIdentificaPers,
                observacionIdentificaPers: $('#txtObservacionPersonaAtiendeRS').val().toUpperCase(),
                'ordenServicioDTO.idOrdenServicio': $('#idOrdenServicioRS').val(),
                //supervisionPersona
                'supervisionPersonaGral.cargo.idMaestroColumna': $('#slctCargoAtiendeRS').val(),
                'supervisionPersonaGral.personaGeneral.idPersonaGeneral': $('#txtIdPersonaRS').val(),
                'supervisionPersonaGral.personaGeneral.nombresPersona': $('#txtNombresRS').val().toUpperCase(),
                'supervisionPersonaGral.personaGeneral.apellidoPaternoPersona': $('#txtApellidoPaternoRS').val().toUpperCase(),
                'supervisionPersonaGral.personaGeneral.apellidoMaternoPersona': $('#txtApellidoMaternoRS').val().toUpperCase(),
                'supervisionPersonaGral.personaGeneral.idTipoDocumento': $('#slctTipoDocumentoRS').val(),
                'supervisionPersonaGral.personaGeneral.numeroDocumento': $('#txtNumeroDocumentoRS').val().toUpperCase(),
                'supervisionPersonaGral.correo': $('#txtCorreoElectronicoRS').val().toUpperCase(),
                'supervisionAnterior.idSupervision': $('#idSupervisionAnt').val()
            },
            beforeSend: loading.open(),
            success: function(data) {
            	loading.close();
                if (data.resultado == '0') {
                    mensajeGrowl("success", constant.confirm.save);
                    var flag = 0;
                    if (flagIdentificaPers == '1') {
                        flag = 1;
                    } else {
                        flag = 0;
                    }
                    $("#DatochkIdentificaRS").val(flag);
                    $("#DatoslctTipoDocumentoRS").val($('#slctTipoDocumentoRS').val().toUpperCase());
                    $("#DatotxtNumeroDocumentoRS").val($('#txtNumeroDocumentoRS').val().toUpperCase());
                    $("#DatotxtApellidoPaternoRS").val($('#txtApellidoPaternoRS').val().toUpperCase());
                    $("#DatotxtApellidoMaternoRS").val($('#txtApellidoMaternoRS').val().toUpperCase());
                    $("#DatotxtNombresRS").val($('#txtNombresRS').val().toUpperCase());
                    $("#DatoslctCargoAtiendeRS").val($('#slctCargoAtiendeRS').val().toUpperCase());
                    $("#DatotxtCorreoElectronicoRS").val($('#txtCorreoElectronicoRS').val().toUpperCase());
                    $("#DatotxtObservacionPersonaAtiendeRS").val($('#txtObservacionPersonaAtiendeRS').val().toUpperCase());
                    if (vista == 'vistaObligaciones') {
                        //$("#tabObligacionesVer").trigger("click");
                        coSupeDsrSupe.MostrarTab(2, 1);
                    } else {
                        if (vista == 'VistaDatosIniciales') {
                            //$("#tabDatosInicialesVer").trigger("click");
                            coSupeDsrSupe.MostrarTab(0, 1);
                        }
                    }
                }
            },
            error: errorAjax
        });
    },
    validarPersonaAtiendeSP: function() {
        var mensajeValidacion = "";
        $('#txtObservacionPersonaAtiendeRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#txtCorreoElectronicoRS').removeClass('error');
        var text = $("#slctTipoDocumentoRS option:selected").text();
        if (text == "CE") {
            if (($('#txtApellidoPaternoRS').val() == undefined || $('#txtApellidoPaternoRS').val().trim() == '') &&
                    ($('#txtApellidoMaternoRS').val() == undefined || $('#txtApellidoMaternoRS').val().trim() == '')) {
                $('#txtApellidoPaternoRS,#txtApellidoMaternoRS').addClass('error');
                mensajeValidacion = "Debe ingresar uno de los dos apellidos: el Apellido Paterno o Materno<br>";
                return mensajeValidacion;
            }
        }
        if (!$('#chkIdentificaRS').is(':checked')) {
            if ($('#txtApellidoPaternoRS').val().trim() != '' && $('#txtApellidoPaternoRS').val().trim().length < 2) {
                $('#txtApellidoPaternoRS').addClass('error');
                mensajeValidacion = "El apellido paterno de la persona que atiende la visita debe tener una longitud mayor o igual a 2 caracteres, corregir<br>";
                return mensajeValidacion;
            }
            if ($('#txtApellidoMaternoRS').val().trim() != '' && $('#txtApellidoMaternoRS').val().trim().length < 2) {
                $('#txtApellidoMaternoRS').addClass('error');
                mensajeValidacion = "El apellido materno de la persona que atiende la visita debe tener una longitud mayor o igual a 2 caracteres, corregir<br>";
                return mensajeValidacion;
            }
            if ($('#txtNombresRS').val().trim() != '' && $('#txtNombresRS').val().trim().length < 2) {
                $('#txtNombresRS').addClass('error');
                mensajeValidacion = "Los nombres de la persona que atiende la visita debe tener una longitud mayor o igual a 2 caracteres, corregir<br>";
                return mensajeValidacion;
            }
            if ($('#txtCorreoElectronicoRS').val().trim() != '' && $('#txtCorreoElectronicoRS').val().trim().length < 5 || $('#txtCorreoElectronicoRS').val().trim().length > 50) {
                $('#txtCorreoElectronicoRS').addClass('error');
                mensajeValidacion = "El Correo Electronico debe tener una longitud Minima de 5 hasta 50 caracteres como maximo, corregir<br>";
                return mensajeValidacion;
            }
        }
        if ($('#txtObservacionPersonaAtiendeRS').val().trim() != '' && $('#txtObservacionPersonaAtiendeRS').val().trim().length < 8) {
            $('#txtObservacionPersonaAtiendeRS').addClass('error');
            mensajeValidacion = "La observaci&oacute;n de la persona que atiende la visita debe tener una longitud mayor o igual a 8 caracteres, corregir<br>";
            return mensajeValidacion;
        }
        return mensajeValidacion;
    },
    ValidarNuevosCambios: function() {
        var mensajeValidacion = '';
        var valcheck = "";
        var datocheck = "";
        var check = $("#DatochkIdentificaRS").val();
        if (check == 1) {
            valcheck = false;
        } else {
            valcheck = true;
        }
        if ($('#chkIdentificaRS').prop('checked')) {
            datocheck = true;
        } else {
            datocheck = false;
        }
        //alert("check es : |" + check + "| valcheck es : |" + valcheck + "| y dato check : |" + datocheck + "|")
        if (valcheck != datocheck) {
            mensajeValidacion = "Se Detecto un Cambio 1";
            return mensajeValidacion;
        }
        if ($("#DatoslctTipoDocumentoRS").val() != $("#slctTipoDocumentoRS").val().trim()) {
            //alert("1 es : |" + $("#DatoslctTipoDocumentoRS").val() + "|" + "2 es : |" + $("#slctTipoDocumentoRS").val() + "|");
            mensajeValidacion = "Se Detecto un Cambio 2";
            return mensajeValidacion;
        }
        if ($("#DatotxtNumeroDocumentoRS").val().toUpperCase().trim() != $("#txtNumeroDocumentoRS").val().toUpperCase().trim()) {
            //alert("1 es : |" + $("#DatotxtNumeroDocumentoRS").val() + "|" + "2 es : |" + $("#txtNumeroDocumentoRS").val() + "|");
            mensajeValidacion = "Se Detecto un Cambio 3";
            return mensajeValidacion;
        }
        if ($("#DatotxtApellidoPaternoRS").val().toUpperCase().trim() != $("#txtApellidoPaternoRS").val().toUpperCase().trim()) {
            //alert("1 es : |" + $("#DatotxtApellidoPaternoRS").val() + "|" + "2 es : |" + $("#txtApellidoPaternoRS").val() + "|");
            mensajeValidacion = "Se Detecto un Cambio 4";
            return mensajeValidacion;
        }
        if ($("#DatotxtApellidoMaternoRS").val().toUpperCase().trim() != $("#txtApellidoMaternoRS").val().toUpperCase().trim()) {
            // alert("1 es : |" + $("#DatotxtApellidoMaternoRS").val() + "|" + "2 es : |" + $("#txtApellidoMaternoRS").val() + "|");
            mensajeValidacion = "Se Detecto un Cambio 5";
            return mensajeValidacion;
        }
        if ($("#DatotxtNombresRS").val().toUpperCase().trim() != $("#txtNombresRS").val().toUpperCase().trim()) {
            //alert("1 es : |" + $("#DatotxtNombresRS").val() + "|" + "2 es : |" + $("#txtNombresRS").val() + "|");
            mensajeValidacion = "Se Detecto un Cambio 6 ";
            return mensajeValidacion;
        }
        if ($("#DatoslctCargoAtiendeRS").val().toUpperCase().trim() != $("#slctCargoAtiendeRS").val().toUpperCase().trim()) {
            //alert("1 es : |" + $("#DatoslctCargoAtiendeRS").val() + "|" + "2 es : |" + $("#slctCargoAtiendeRS").val() + "|");
            mensajeValidacion = "Se Detecto un Cambio 7";
            return mensajeValidacion;
        }
        if ($("#DatotxtCorreoElectronicoRS").val().toUpperCase().trim() != $("#txtCorreoElectronicoRS").val().toUpperCase().trim()) {
            //alert("1 es : |" + $("#DatotxtCorreoElectronicoRS").val() + "|" + "2 es : |" + $("#txtCorreoElectronicoRS").val() + "|");
            mensajeValidacion = "Se Detecto un Cambio 8 ";
            return mensajeValidacion;
        }
        if ($("#DatotxtObservacionPersonaAtiendeRS").val().toUpperCase().trim() != $("#txtObservacionPersonaAtiendeRS").val().toUpperCase().trim()) {
            //alert("1 es : |" + $("#DatotxtObservacionPersonaAtiendeRS").val() + "|" + "2 es : |" + $("#txtObservacionPersonaAtiendeRS").val() + "|");
            mensajeValidacion = "Se Detecto un Cambio 9 ";
            return mensajeValidacion;
        }
        return mensajeValidacion;
    },
    validarTipoDocumentoSupervision : function (cbo) {
        changeTipoDocu("#" + cbo.id, "#txtNumeroDocumentoRS", "#divFrmPersonaAtiendeSP");
        var text = $("#slctTipoDocumentoRS option:selected").text();
        if (text == "CE") {
            $("#txtApellidoPaternoRS").attr("validate", "[O]");
            $("#spApeMaterno").css("display", "none");
        }
        else {
            $("#txtApellidoPaternoRS,#txtApellidoMaternoRS").attr("validate", "[O]");
            $("#spApeMaterno").css("display", "inline");
        }
    }     
}

$('#chkIdentificaRS').click(function() {
    coRecepcionVisita.validarIdentificaPersona();
});
$('#btnBuscarPersonaAtiende').click(function() {
    if ($('#btnBuscarPersonaAtiende').val() == 'Buscar') {
        var valida = coRecepcionVisita.validarBuscaPersonaAtiende();
        if (valida) {
            if (coRecepcionVisita.validarTipoDocumento($('#slctTipoDocumentoRS').find(':checked').html(), $('#txtNumeroDocumentoRS').val())) {
                coRecepcionVisita.buscarPersonaAtiende($('#slctTipoDocumentoRS').val(), $('#txtNumeroDocumentoRS').val().toUpperCase());
            } else {
                mensajeGrowl('warn', "Corrija el formato seg&uacute;n el tipo de documento");
            }
        }
    } else if ($('#btnBuscarPersonaAtiende').val() == 'Corregir') {
        $('#btnBuscarPersonaAtiende').val("Buscar");
        $('#slctTipoDocumentoRS,#txtNumeroDocumentoRS').removeAttr("disabled");
        $('#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#slctCargoAtiendeRS').val('');
        $('#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#slctCargoAtiendeRS').attr('disabled', 'disabled');
    }
});
$('#slctTipoDocumentoRS').change(function() {
    coRecepcionVisita.validarTipoDocumentoSupervision(this);
}); 
$(function() {
    boton.closeDialog();
    coRecepcionVisita.validarIdentificaPersona();   
    $('#btnGuardarPersonaAtiendeSP').click(function() {
        coRecepcionVisita.guardarPersonaAtiendeSP('vistaObligaciones');
    });
    $('#btnAtrasPersonaAtiendeSP').click(function() {
        //coRecepcionVisita.guardarPersonaAtiendeSP('VistaDatosIniciales');
        coSupeDsrSupe.MostrarTab(0, 1);
    });
       /* OSINE791 – RSIS22 - Inicio */
    $('#btnAnteriorDatosIniciales').click(function() {
        coSupeDsrSupe.MostrarTab(0, 1);
    });
    $('#btnSiguienteEjecucionMedida').click(function() {
        coSupeDsrSupe.MostrarTab(2, 1);
    });
    /* OSINE791 – RSIS22 - Fin */
});
