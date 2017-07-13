//common/OrdenServicio/OrdenServicioAsignar.js
var changeProceso = false;
var changeTipoSupervision = false;
var changeRubro = false;
var changeUbigeo = false;
var coOrSeOrSe = {            
    armaValidateFormOS: function() {
        $('#frmOSAsig').find('input, select, textarea').removeAttr('validate');
        $('#slctIdProcesoOSAsig,#slctIdEtapaOSAsig,#slctIdTramiteOSAsig,#cmbTipoAsigOSAsig,#cmbTipoSupervisionOSAsig,#slctUnidSupeOSAsig,#slctTipoEmprSupeOSAsig,#slctEmprSupeOSAsig').attr('validate', '[O]');
    },
    armaConsultarFormOS: function() {
        if ($('#txtflagOrigenOSAsig').val() == constant.flgOrigenExpediente.siged) {
            fill.clean("#slctIdEtapaOSAsig,#slctIdTramiteOSAsig");
            common.serie.cargarEtapa("#slctIdProcesoOSAsig", "#slctIdEtapaOSAsig");
            $('#slctIdEtapaOSAsig').val($('#txtIdEtapaTrOSAsig').val());
            $('#slctIdEtapaOSAsig').trigger('change');
            $('#slctIdTramiteOSAsig').val($('#txtIdTramiteOSAsig').val());
        }
        // para datos de empresa supervisora
        if ($('#txtIdLocadorOSAsig').val() != '' && $('#txtIdLocadorOSAsig').val() != undefined) {
            $('#slctTipoEmprSupeOSAsig').val($('#slctTipoEmprSupeOSAsig').find('option[codigo="' + constant.empresaSupervisora.personaNatural + '"]').val())
            $('#slctTipoEmprSupeOSAsig').trigger("change");
            $('#slctEmprSupeOSAsig').val($('#txtIdLocadorOSAsig').val());
        } else if ($('#txtIdSupervisoraEmpresaOSAsig').val() != '' && $('#txtIdSupervisoraEmpresaOSAsig').val() != undefined) {
            $('#slctTipoEmprSupeOSAsig').val($('#slctTipoEmprSupeOSAsig').find('option[codigo="' + constant.empresaSupervisora.personaJuridica + '"]').val())
            $('#slctTipoEmprSupeOSAsig').trigger("change");
            $('#slctEmprSupeOSAsig').val($('#txtIdSupervisoraEmpresaOSAsig').val());
        }
    },
    fxAsignar: {
        validaAsignarOS: function() {
            if ($('#frmOSAsig').validateAllForm("#divMensajeValidaFrmOSAsig")) {
                confirmer.open(
                        "¿Confirma que desea Generar una Orden de Servicio?",
                        "coOrSeOrSe.fxAsignar.procesaAsignarOrdenServicio()");
            }
        },
        procesaAsignarOrdenServicio: function() {
            loading.open();
            $.getJSON(
                    baseURL + "pages/expediente/asignarOrdenServicio",
                    {
                        idExpediente: $('#txtIdExpedienteOSAsig').val(),
                        'numeroExpediente': $('#txtNumeroExpedienteOSAsig').val(),
                        'flujoSiged.nombreFlujoSiged': $('#slctIdFlujoSigedOSAsig').find(':checked').text(),
                        asuntoSiged: $('#txtAsuntoSigedOSAsig').val(),
                        'personal.idPersonal': $('#idPersonalSesion').val(),
                        'tramite.idTramite': $('#slctIdTramiteOSAsig').val(),
                        'proceso.idProceso': $('#slctIdProcesoOSAsig').val(),
                        'ordenServicio.idTipoAsignacion': $('#cmbTipoAsigOSAsig').val(),
                        'obligacionTipo.idObligacionTipo': $('#cmbTipoSupervisionOSAsig').val(),                        
                        'obligacionSubTipo.idObligacionSubTipo': $('#cmbSubTipoSupervisionOSAsig').val(),
                        'unidadSupervisada.idUnidadSupervisada': $('#slctUnidSupeOSAsig').val(),
                        codigoTipoSupervisor: $('#slctTipoEmprSupeOSAsig').find(':checked').attr('codigo'),
                        'ordenServicio.locador.idLocador': $('#slctEmprSupeOSAsig').val(),
                        'ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa': $('#slctEmprSupeOSAsig').val()
                    }
            ).done(function(data) {
                loading.close();
                if (data.resultado == '0') {
                    $("#dialogOrdenServicio").dialog('close');
                    confirmer.open("Se generó satisfactoriamente la  Orden de Servicio: <b>" + data.expediente.ordenServicio.numeroOrdenServicio + "</b>", "", {textAceptar: '', textCancelar: 'Cerrar', title: 'Orden de Servicio'});
                    // recargar grillas involucradas
                    espeDeriDeri.procesarGridDerivado();
                    espeAsigAsig.procesarGridAsignacion();
                    espeNotiArchNotiArch.procesarGridArchivadoNotificado();
                } else {
                    mensajeGrowl("error", data.mensaje, "");
                }
            }).fail(errorAjax);
        }
    },
    cargaDataUnidadOperativa: function(fill) {
        $.ajax({
            url: baseURL + "pages/unidadSupervisada/cargaDataUnidadOperativa",
            type: 'get',
            async: false,
            dataType: 'json',
            data: {
                cadIdUnidadSupervisada: $('#slctUnidSupeOSAsig').val()
            },
            before: function() {
                loading.open();
            },
            success: function(data) {
                loading.close();
                fill(data);
            },           
            error: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },  
    fillDataUnidadOperativa: function(data) {
        $('#txtRubroOSAsig,#txtDireOperOSAsig').val("");
        if (data != null && data.registro != null) {
            $('#txtIdActividadAsig').val(data.registro.idActividad);
            $('#txtRubroOSAsig').val(data.registro.actividad);
            $('#txtIdUnidadSupervisadaOSAsig').val(data.registro.idUnidadSupervisada);

            if (data.registro.direccionUnidadsupervisada.direccionCompleta != null && data.registro.direccionUnidadsupervisada.direccionCompleta != undefined) {
                $('#txtDireOperOSAsig').val(data.registro.direccionUnidadsupervisada.direccionCompleta);
                $('#txtIdDepartamentoOSAsig').val(data.registro.direccionUnidadsupervisada.departamento.idDepartamento);
                $('#txtIdProvinciaOSAsig').val(data.registro.direccionUnidadsupervisada.provincia.idProvincia);
                $('#txtIdDistritoOSAsig').val(data.registro.direccionUnidadsupervisada.distrito.idDistrito);
            }

            if ($('#tipoAccionOSAsig').val() == constant.accionOS.asignar ||
                    $('#tipoAccionOSAsig').val() == constant.accionOS.generar ||
                    $('#tipoAccionOSAsig').val() == constant.accionOS.editar) {
                changeProceso = false;
                changeTipoSupervision = false;
                changeRubro = false;
                changeUbigeo = false;
                coOrSeOrSe.armaValidateFiltros(changeProceso, changeTipoSupervision, changeRubro, changeUbigeo);
            }
        }
    },
    activarSlctTipoEmprSupeOS: function(changeProceso, changeTipoSupervision, changeRubro, changeUbigeo) {
        if ($('#slctIdProcesoOSAsig').val() == '' || $('#cmbTipoSupervisionOSAsig').val() == '' || $('#txtIdActividadAsig').val() == '' || $('#txtIdDepartamentoOSAsig').val() == '' || $('#txtIdProvinciaOSAsig').val() == '' || $('#txtIdDistritoOSAsig').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeProceso || changeTipoSupervision || changeRubro || changeUbigeo) {
            $('#slctTipoEmprSupeOSAsig,#slctEmprSupeOSAsig').val('');
        }
    },
    armaValidateFiltros: function(changeProceso, changeTipoSupervision, changeRubro, changeUbigeo) {
        str = $('#txtFiltrosOSIAsig').val();
        array = str.split(',');
        contadorXfiltro = 0;
        supervision = 0;
        rubro = 0;
        ubigeo = 0;
        proceso = 0;
        for (var i = 0; i < array.length; i++) {
            if (array[i] == "OBLI") {
                contadorXfiltro++;
                supervision = 1;
            }
            if (array[i] == "RUBR") {
                contadorXfiltro++;
                rubro = 1;
            }
            if (array[i] == "UBIG") {
                contadorXfiltro++;
                ubigeo = 1;
            }
            if (array[i] == "PROC") {
                contadorXfiltro++;
                proceso = 1;
            }
        }
        coOrSeOrSe.armaCondicionFiltros(contadorXfiltro, supervision, rubro, ubigeo, proceso, changeProceso, changeTipoSupervision, changeRubro, changeUbigeo);

    },
    armaCondicionFiltros: function(contadorXfiltro, supervision, rubro, ubigeo, proceso, changeProceso, changeTipoSupervision, changeRubro, changeUbigeo) {
        switch (contadorXfiltro) {
            case 0:
                coOrSeOrSe.armaCondicionCeroFiltros();
                break;
            case 1:
                if (supervision == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxUnoTipoSupervision(changeTipoSupervision);
                } else if (rubro == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxUnoRubro(changeRubro);
                } else if (ubigeo == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxUnoUbigeo(changeUbigeo);
                } else if (proceso == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxUnoProceso(changeProceso);
                }
                break;
            case 2:
                if (supervision == 1 && rubro == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxDosSR(changeTipoSupervision, changeRubro);
                } else if (supervision == 1 && ubigeo == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxDosSU(changeTipoSupervision, changeUbigeo);
                } else if (supervision == 1 && proceso == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxDosSP(changeProceso, changeTipoSupervision);
                } else if (rubro == 1 && ubigeo == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxDosRU(changeRubro, changeUbigeo);
                } else if (rubro == 1 && proceso == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxDosRP(changeProceso, changeRubro);
                } else if (ubigeo == 1 && proceso == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxDosUP(changeProceso, changeUbigeo);
                }
                break;
            case 3:
                if (supervision == 1 && rubro == 1 && ubigeo == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxTresSRU(changeTipoSupervision, changeRubro, changeUbigeo);
                } else if (rubro == 1 && ubigeo == 1 && proceso == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxTresRUP(changeProceso, changeRubro, changeUbigeo);
                } else if (supervision == 1 && rubro == 1 && proceso == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxTresSRP(changeProceso, changeTipoSupervision, changeRubro);
                } else if (supervision == 1 && ubigeo == 1 && proceso == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxTresSUP(changeProceso, changeTipoSupervision, changeUbigeo);
                }
                break;
            case 4:
                coOrSeOrSe.activarSlctTipoEmprSupeOS(changeProceso, changeTipoSupervision, changeRubro, changeUbigeo);
                break;
        }

    },
    armaCondicionCeroFiltros: function() {
        coOrSeOrSe.habilitarEmpresa();
    },
    armaCondicionFiltrosxUnoProceso: function(changeProceso) {
        if ($('#slctIdProcesoOSAsig').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeProceso) {
            $('#slctTipoEmprSupeOSAsig,#slctEmprSupeOSAsig').val('');
        }
    },
    armaCondicionFiltrosxUnoTipoSupervision: function(changeTipoSupervision) {
        if ($('#cmbTipoSupervisionOSAsig').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeTipoSupervision) {
            $('#slctTipoEmprSupeOSAsig,#slctEmprSupeOSAsig').val('');
        }
    },
    armaCondicionFiltrosxUnoRubro: function(changeRubro) {
        if ($('#txtIdActividadAsig').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeRubro) {
            $('#slctTipoEmprSupeOSAsig,#slctEmprSupeOSAsig').val('');
        }
    },
    armaCondicionFiltrosxUnoUbigeo: function(changeUbigeo) {
        if ($('#txtIdDepartamentoOSAsig').val() == '' || $('#txtIdProvinciaOSAsig').val() == '' || $('#txtIdDistritoOSAsig').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeUbigeo) {
            $('#slctTipoEmprSupeOSAsig,#slctEmprSupeOSAsig').val('');
        }
    },
    armaCondicionFiltrosxDosSR: function(changeTipoSupervision, changeRubro) {
        if ($('#cmbTipoSupervisionOSAsig').val() == '' || $('#txtIdActividadAsig').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeTipoSupervision || changeRubro) {
            $('#slctTipoEmprSupeOSAsig,#slctEmprSupeOSAsig').val('');
        }
    },
    armaCondicionFiltrosxDosSU: function(changeTipoSupervision, changeUbigeo) {
        if ($('#cmbTipoSupervisionOSAsig').val() == '' || $('#txtIdDepartamentoOSAsig').val() == '' || $('#txtIdProvinciaOSAsig').val() == '' || $('#txtIdDistritoOSAsig').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeTipoSupervision || changeUbigeo) {
            $('#slctTipoEmprSupeOSAsig,#slctEmprSupeOSAsig').val('');
        }
    },
    armaCondicionFiltrosxDosSP: function(changeProceso, changeTipoSupervision) {
        if ($('#cmbTipoSupervisionOSAsig').val() == '' || $('#slctIdProcesoOSAsig').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeProceso || changeTipoSupervision) {
            $('#slctTipoEmprSupeOSAsig,#slctEmprSupeOSAsig').val('');
        }
    },
    armaCondicionFiltrosxDosRU: function(changeRubro, changeUbigeo) {
        if ($('#txtIdActividadOSAsig').val() == '' || $('#txtIdDepartamentoOSAsig').val() == '' || $('#txtIdProvinciaOSAsig').val() == '' || $('#txtIdDistritoOSAsig').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeRubro || changeUbigeo) {
            $('#slctTipoEmprSupeOSAsig,#slctEmprSupeOSAsig').val('');
        }
    },
    armaCondicionFiltrosxDosRP: function(changeProceso, changeRubro) {
        if ($('#txtIdActividadOSAsig').val() == '' || $('#slctIdProcesoOSAsig').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeProceso || changeRubro) {
            $('#slctTipoEmprSupeOSAsig,#slctEmprSupeOSAsig').val('');
        }
    },
    armaCondicionFiltrosxDosUP: function(changeProceso, changeUbigeo) {
        if ($('#slctIdProcesoOSAsig').val() == '' || $('#txtIdDepartamentoOSAsig').val() == '' || $('#txtIdProvinciaOSAsig').val() == '' || $('#txtIdDistritoOSAsig').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeProceso || changeUbigeo) {
            $('#slctTipoEmprSupeOSAsig,#slctEmprSupeOSAsig').val('');
        }
    },
    armaCondicionFiltrosxTresSRU: function(changeTipoSupervision, changeRubro, changeUbigeo) {
        if ($('#cmbTipoSupervisionOSAsig').val() == '' || $('#txtIdActividadOSAsig').val() == '' || $('#txtIdDepartamentoOSAsig').val() == '' || $('#txtIdProvinciaOSAsig').val() == '' || $('#txtIdDistritoOSAsig').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeTipoSupervision || changeRubro || changeUbigeo) {
            $('#slctTipoEmprSupeOSAsig,#slctEmprSupeOSAsig').val('');
        }
    },
    armaCondicionFiltrosxTresRUP: function(changeProceso, changeRubro, changeUbigeo) {
        if ($('#txtIdActividadOSAsig').val() == '' || $('#txtIdDepartamentoOSAsig').val() == '' || $('#txtIdProvinciaOSAsig').val() == '' || $('#txtIdDistritoOSAsig').val() == '' || $('#slctIdProcesoOSAsig').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeProceso || changeRubro || changeUbigeo) {
            $('#slctTipoEmprSupeOSAsig,#slctEmprSupeOSAsig').val('');
        }
    },
    armaCondicionFiltrosxTresSRP: function(changeProceso, changeTipoSupervision, changeRubro) {
        if ($('#cmbTipoSupervisionOSAsig').val() == '' || $('#txtIdActividadOSAsig').val() == '' || $('#slctIdProcesoOSAsig').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeProceso || changeTipoSupervision || changeRubro) {
            $('#slctTipoEmprSupeOSAsig,#slctEmprSupeOSAsig').val('');
        }
    },
    armaCondicionFiltrosxTresSUP: function(changeProceso, changeTipoSupervision, changeUbigeo) {
        if ($('#cmbTipoSupervisionOSAsig').val() == '' || $('#txtIdDepartamentoOSAsig').val() == '' || $('#txtIdProvinciaOSAsig').val() == '' || $('#txtIdDistritoOSAsig').val() == '' || $('#slctIdProcesoOSAsig').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeProceso || changeTipoSupervision || changeUbigeo) {
            $('#slctTipoEmprSupeOSAsig,#slctEmprSupeOSAsig').val('');
        }
    },
    habilitarEmpresa: function() {
        $("#slctTipoEmprSupeOSAsig").removeAttr('disabled');
        $("#slctEmprSupeOSAsig").removeAttr('disabled');
    },
    deshabilitarEmpresa: function() {
        $("#slctTipoEmprSupeOSAsig").attr('disabled', 'disabled');
        $("#slctEmprSupeOSAsig").attr('disabled', 'disabled');
    },
    inicializaObjetosEventos: function() {
        // change UnidadOperativa
        $('#slctUnidSupeOSAsig').change(function() {
            $('#txtIdActividadAsig,#txtIdUnidadSupervisadaOSAsig,#txtRubroOSAsig,#txtDireOperOSAsig,#txtIdDepartamentoOSAsigAsig,#txtIdProvinciaOSAsig,#txtIdDistritoOSAsig').val('');
            if ($('#slctUnidSupeOSAsig').val() != "") {
                coOrSeOrSe.cargaDataUnidadOperativa(coOrSeOrSe.fillDataUnidadOperativa);
            }
            
            changeProceso = false;
            changeTipoSupervision = false;
            changeRubro = true;
            changeUbigeo = true;
            coOrSeOrSe.armaValidateFiltros(changeProceso, changeTipoSupervision, changeRubro, changeUbigeo);
        });
        $('#slctIdProcesoOSAsig').change(function() {
            fill.clean("#slctIdEtapaOSAsig,#slctIdTramiteOSAsig");
            common.serie.cargarEtapa("#slctIdProcesoOSAsig", "#slctIdEtapaOSAsig");
        });
        $('#slctIdEtapaOSAsig').change(function() {
            fill.clean("#slctIdTramiteOSAsig");
            common.serie.cargarTramite("#slctIdEtapaOSAsig", "#slctIdTramiteOSAsig");
        });
        $('#slctTipoEmprSupeOSAsig').change(
                function() {
                    var idProceso = $("#slctIdProcesoOSAsig").val();
                    var idObligacionTipo = $("#cmbTipoSupervisionOSAsig").val();
                    var idRubro = $("#txtIdActividadAsig").val();
                    var idDepartamento = $("#txtIdDepartamentoOSAsig").val();
                    var idProvincia = $("#txtIdProvinciaOSAsig").val();
                    var idDistrito = $("#txtIdDistritoOSAsig").val();
                    fill.clean("#slctEmprSupeOSAsig");
                    if ($('#slctTipoEmprSupeOSAsig').find(':checked').attr('codigo') == constant.empresaSupervisora.personaNatural) {
                        common.empresaSupervisora.cargarLocador('#slctEmprSupeOSAsig', idObligacionTipo, idProceso, idRubro, idDepartamento, idProvincia, idDistrito);
                    } else if ($('#slctTipoEmprSupeOSAsig').find(':checked').attr('codigo') == constant.empresaSupervisora.personaJuridica) {
                        common.empresaSupervisora.cargarSupervisoraEmpresa('#slctEmprSupeOSAsig', idObligacionTipo, idProceso, idRubro, idDepartamento, idProvincia, idDistrito);
                    }
                });
        $('#slctIdProcesoOSAsig').change(function() {
            changeRubro = false;
            changeUbigeo = false;
            changeProceso = true;
            changeTipoSupervision = false;
            coOrSeOrSe.armaValidateFiltros(changeProceso, changeTipoSupervision, changeRubro, changeUbigeo);
        });
        $('#cmbTipoSupervisionOSAsig').change(function() {
            changeRubro = false;
            changeUbigeo = false;
            changeProceso = false;
            changeTipoSupervision = true;
            coOrSeOrSe.armaValidateFiltros(changeProceso, changeTipoSupervision, changeRubro, changeUbigeo);
        });

        // asignar
        $('#btnAsignarOSAsig').click(function() {
            coOrSeOrSe.fxAsignar.validaAsignarOS();
        });
        
        // mant UnidadOperativa EMPSUP
//        $('#btnCrearUnidOperOrdeServ').click(
//                function() {
//                    common.abrirMantUnidadOperativa("new", $('#nroDocumentoOS')
//                            .val().replace('RUC - ', ''),
//                            $('#razonSocialOS').val(), $(
//                            '#txtIdEmpresaSupervisadaOS').val());
//                });
        
        $('#txtAsuntoSigedOSAsig').alphanum(constant.valida.alphanum.descrip);
        $('#txtObligatorioUnidadOrganicaOSAsig').html(($('#descFiltroConfiguradoOSIAsig').val() != '') ? 'Datos requeridos: ' + $('#descFiltroConfiguradoOSIAsig').val() : '');
        
        $('#cmbTipoSupervisionOSAsig').change(function() {
            if ($("#cmbTipoSupervisionOSAsig").find(':checked').attr('codigo') > 0) {
                $('#subTipoOSAsig').css('display', 'inline-block');
                common.obligacionTipo.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSAsig').val(), '#cmbSubTipoSupervisionOSAsig');
            } else {
                $('#subTipoOSAsig').css('display', 'none');
                fill.clean('#cmbSubTipoSupervisionOSAsig');
            }
        });
    }
};

$(function() {
    coOrSeOrSe.inicializaObjetosEventos();
    $('#slctUnidSupeOSAsig').trigger('change');
    
    // arma validaciones de campos obligatorios en el formulario
    coOrSeOrSe.armaValidateFormOS();
    // cargar files del expediente
    common.procesarGridFilesExpediente($('#txtNumeroExpedienteOSAsig').val(),
        $('#txtIdOrdenServicioOSAsig').val(), "FilesOrdenServicioAsig",
        $('#tipoAccionOSAsig').val());
    

    // arma visualizacion de valores para casos "consultar" y diferentes a generar
    coOrSeOrSe.armaConsultarFormOS();
    // arma subtipo supervision, 
    if ($('#txtIdObligacionSubTipoOSAsig').val() != '') {
        common.obligacionTipo.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSAsig').val(), '#cmbSubTipoSupervisionOSAsig');
        $('#subTipoOSAsig').css('display', 'inline-block');
        $('#cmbSubTipoSupervisionOSAsig').val($('#txtIdObligacionSubTipoOSAsig').val());
    } 
    
    boton.closeDialog();
});