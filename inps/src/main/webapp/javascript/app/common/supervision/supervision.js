/**
 * Resumen		
 * Objeto		: supervision.js
 * DescripciÃ³n		: JavaScript donde se maneja las acciones de la pestaÃ±a supervisado del Supervisor para Devolver Asignaciones.
 * Fecha de CreaciÃ³n	: 
 * PR de CreaciÃ³n	: OSINE_SFS-480
 * Autor			: GMD
 * =====================================================================================================================================================================
 * Modificaciones |
 * Motivo         |  Fecha        |   Nombre                     |     DescripciÃ³n
 * =====================================================================================================================================================================
 * OSINE_SFS-480  |  11/05/2016   |   Hernan Torres Saenz        |     Agregar criterios al filtro de bÃºsqueda en la secciÃ³n asignaciones,evaluaciÃ³n  y notificaciÃ³n/archivado 
 * OSINE_SFS-480  |  11/05/2016   |   HernÃ¡n Torres Saenz        |     Cargar Lista de Obligaciones luego de Registrar la Fecha-Hora de Inicio del "Registro de SupervisiÃ³n"
 * OSINE_SFS-480  |  09/06/2016   |   Mario Dioses Fernandez     |     Listar Empresas Supervisoras segÃºn filtros definidos para Gerencia (Unidad Organica).
 * OSINE_791-066  |  28/10/2016   |   Zosimo Chaupis Santur      |     Modificar la funcionalidad de registro de datos de supervisiÃ³n en la interfaz "Registrar SupervisiÃ³n", el cual debe validar el registro obligatorio de adjuntos.
 * OSINE_791-068  |  31/10/2016   |   Zosimo Chaupis Santur      |     Crear el campo de resultado de supervisiÃ³n en la secciÃ³n de datos de supervisiÃ³n de la interfaz "Registrar SupervisiÃ³n".
 * OSINE_791-069  |  02/11/2016   |   Zosimo Chaupis Santur      |     Implementar la funcionalidad para guardar el valor del campo Resultado de supervisiÃ³n (REQF-0068) al registrar los datos de supervisiÃ³n en la interfaz â€œRegistrar SupervisiÃ³nâ€�.
 * OSINE_MAN-001  |  15/06/2017   |   Claudio Chaucca Umana::ADAPTER      |     Realizar la validacion de existencia de cambios desde la interface de usuario 
 * OSINE_MANT_DSHL_003 |27/06/2017|   Claudio Chaucca Umana::ADAPTER | En el formulario registrar supervision implementar una tercera opcion denominada "otro". 
 */

//common/supervision/supervision.js
//variables globales
var iteracion;
var modo;
var primera_vez=false;
var coSupeSupe = (function() {
    function constructor() {
        modo = $('#modoSupervision').val();
        iteracion = $('#idIteracionOS').val();
        cargarClasificacion();
        cargarCriticidad();
        cargarCumple();
        cargarRegistrado();
        grillaObligaciones();
        comportamientos();
        if (iteracion == constant.iteracion.segunda) {
            activarSegundaItera();
        }
        if (modo == constant.modoSupervision.consulta) {
            activarConsulta();
        }
        validarCargaFormulario();
        validarCantidadArchivosAdjuntos();
    }
    
    function validarCantidadArchivosAdjuntos(){
    	$.ajax({
    		url: baseURL + "pages/archivo/obtenerPghDocumentoAdjunto",
            type: 'get',
            async: false,
            data: {idSupervision:$('#idSupervision').val()},
            success: function(data) {
                if (data.total == 0) {
                	$("#btnVerAdjuntoSupervision").hide();
                }else{
                	$("#btnVerAdjuntoSupervision").show();
                }
            }
        });
    }

    function comportamientos() {
        //combos
        $('#slctTipoDocumentoRS').change(function() {
            validarTipoDocumentoSupervision(this);
        });
        $('#txtCodigoBase').alphanum(constant.valida.alphanum.baseLegal);
        $('#slctTipoEmprSupeOSSuper').change(function() {
            fill.clean("#slctEmprSupeOSSuper");
            if ($('#slctTipoEmprSupeOSSuper').find(':checked').attr('codigo') == constant.empresaSupervisora.personaNatural) {
                /* OSINE_SFS-480 - RSIS 11 - Inicio  */
                common.empresaSupervisora.cargarLocador("#slctEmprSupeOSSuper", -1, -1, -1, -1, -1, -1);
                /* OSINE_SFS-480 - RSIS 11 - Fin  */
            } else if ($('#slctTipoEmprSupeOSSuper').find(':checked').attr('codigo') == constant.empresaSupervisora.personaJuridica) {
                /* OSINE_SFS-480 - RSIS 11 - Inicio  */
                common.empresaSupervisora.cargarSupervisoraEmpresa("#slctEmprSupeOSSuper", -1, -1, -1, -1, -1, -1);
                /* OSINE_SFS-480 - RSIS 11 - Fin  */
            }
        });
        $('#cmbCumple').change(function() {
            $('#hiddenCumple').val($('#cmbCumple').val());
            if ($('#cmbCumple').val() == "null") {
                $('#hiddenCumple').removeAttr('value');
            }
        });
        $('#cmbRegistrado').change(function() {
            $('#hiddenRegistrado').val($('#cmbRegistrado').val());
            if ($('#cmbRegistrado').val() == "null") {
                $('#hiddenRegistrado').removeAttr('value');
            }
        });
        $('#slctMotivoNoSuprvsnRS').change(function() {
            $('#txtEspecificarRS').val('');
            if ($("#slctMotivoNoSuprvsnRS option:selected").attr('especifica') == constant.motivoSupervision.especifica) {
                $('#divEspecificarRS').css('display', 'inline');
                $('#txtEspecificarRS').attr("validate", "[O]");
            }
            else {
                $('#divEspecificarRS').css('display', 'none');
                $('#txtEspecificarRS').removeAttr("validate");
            }
        });
        //botones
        $('#btnGuardarDatosSupervision').click(function() {
            guardarDatosSupervision();
        });
        $('#btnGuardarPersonaAtiende').click(function() {
        	guardarPersonaAtiende();
        });
        $('#btnAdjuntosSuperAnt').click(function() {
            common.abrirAdjuntoSupe(constant.modoSupervision.consulta, $('#idSupervisionAnt').val());
        });
        $('#btnAdjuntoSupervision').click(function() {
            common.abrirAdjuntoSupe(modo, $('#idSupervision').val());
        });
        $('#btnVerAdjuntoSupervision').click(function() {
            common.abrirVerAdjuntoSupe(modo, $('#idSupervision').val());
        });
        $('#btnGenerar').click(function() {
            var mensajeValidacion = "";
            
            /* OSINE_MAN-001 - Inicio */
            if(validarExisteCambios()){
            	var mensajeValidacion = "Porfavor Guardar los datos actualizados antes de Generar Resultados";
                mensajeGrowl('warn', mensajeValidacion);
            }else{
            /* OSINE_MAN-001 - Fin */
	            if (validarFormulario()) {
	                mensajeValidacion = validarRegistroSupervision();
	                if (mensajeValidacion != null) {
	                    if (mensajeValidacion == '') {
	                        if ($("#flagSupervision").val() == "1" && iteracion == constant.iteracion.primera) {
	                            /* OSINE-791 - RSIS 69 - Inicio */
	                            ValidarObligacionesIncumplidas();
	                            var flagCantIncumplidos = $("#flagInfraccionesDSHL").val();
	                            var ValorComboResultado = $('#slctResultadoSupervisionRS').val();
	                            if ((ValorComboResultado == "1" && flagCantIncumplidos > 0) || (ValorComboResultado == "0" && flagCantIncumplidos == 0)) {
	                                
	                            	grillaGenerarResultados();
	                                $('#divGenerarResultados').dialog('option', 'title', 'RESULTADOS');
	                                $('#divGenerarResultados').dialog('open');
	                            }else{
	                              var mensajeValidacion2 = "El registro de cumplimiento de obligaciones no corresponde con lo seleccionado en el campo resultado de supervisi&oacute;n, verificar";
	                              mensajeGrowl('warn', mensajeValidacion2);  
	                            }
	                            /* OSINE-791 - RSIS 69 - Fin */
	                        } else {
	                            grillaGenerarResultados();
	                            $('#divGenerarResultados').dialog('option', 'title', 'RESULTADOS');
	                            $('#divGenerarResultados').dialog('open');
	                        }
	                        
	                    } else {
	                        mensajeGrowl('warn', mensajeValidacion);
	                    }
	                }
	            } else {
	                $('#fldstDatosSuper .pui-fieldset-content').show();
	                $('#fldstPersonaAtiende .pui-fieldset-content').show();
	            }
	        /* OSINE_MAN-001 - Inicio */
            }
	        /* OSINE_MAN-001 - Fin */   
        });
        $('#btnBuscarPersonaAtiende').click(function() {
            if ($('#btnBuscarPersonaAtiende').val() == 'Buscar') {
                var valida = validarBuscaPersonaAtiende();
                //validar
                if (valida) {
                    if (validarTipoDocumento($('#slctTipoDocumentoRS').find(':checked').html(), $('#txtNumeroDocumentoRS').val())) {
                        buscarPersonaAtiende($('#slctTipoDocumentoRS').val(), $('#txtNumeroDocumentoRS').val().toUpperCase());
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
        $('#btnBuscarObligaciones').click(function() {
            grillaObligaciones();
            if (modo == constant.modoSupervision.consulta) {
                activarConsulta();
            }
            if (iteracion == constant.iteracion.segunda) {
                activarSegundaItera();
            }
            desbloquearModoConsultaGrillaObligacion(iteracion);
        });
        $('#btnLimpiar').click(function() {
            confirmer.open('Â¿Confirma que desea limpiar la b&uacute;squeda realizada?', 'coSupeSupe.limpiarObligacionBusqueda()');
        });

        //imagenes
        $('#btnOblIncumplida').click(function() {
            grillaOligacionIncumplida();
            $("#divDetalleIncumplidos").dialog('option', 'title', 'LISTA DE OBLIGACIONES CON INCUMPLIMIENTO');
            $('#divDetalleIncumplidos').dialog('open');
        });

        //radios
        $('#radioSiSupervision').click(function() {
            $('#radioSiSupervision').attr("checked", "checked");
            coSupeSupe.HabilitarCajasTexto();
            actualizarFlagSupervision();            
            $('#flagSupervision').val(1);
            grillaObligaciones();
            validarRadioSi();
            $('#radioNoSupervision').removeAttr("disabled");
            /* OSINE-791 - RSIS 69 - Inicio */
            $("#divResultadoSupervisionRS").css('display', 'block');
            $('#slctResultadoSupervisionRS').attr("validate", "[O]");
            
            if (iteracion == constant.iteracion.segunda) {
            $("#divResultadoSupervisionRS").css('display', 'none');
            $("#divMotivoNoSuprvsnRS").css('display', 'none');   
            $('#slctResultadoSupervisionRS').removeAttr("validate");
            $('#slctMotivoNoSuprvsnRS').removeAttr("validate");
            }
             /* OSINE-791 - RSIS 69 - Fin */
        });
        $('#radioNoSupervision').click(function() {
            coSupeSupe.HabilitarCajasTexto();
            $('#radioSiSupervision').removeAttr("checked", "checked");
            $('#radioSiSupervision').removeAttr("disabled", "disabled");
            personaSeIdentifico('ninguna');
            if ($('#flagSupervision').val() == constant.estado.activo) {
                $('#radioSiSupervision').attr("checked", "checked");
                $('#radioSiSupervision').attr("disabled", "disabled");
                /* OSINE-791 - RSIS 69 - Inicio */
                $("#divResultadoSupervisionRS").css('display', 'block');
                $('#slctResultadoSupervisionRS').attr("validate", "[O]");
                $("#divMotivoNoSuprvsnRS").css('display', 'none');
                /* OSINE-791 - RSIS 69 - Fin */
            }
            //$("#divResultadoSupervisionRS").css('display', 'none');
            if ($('#flagSupervision').val() == 1) {
                confirmer.open('Al realizar esta operaci&oacute;n se perder&aacute;n los datos ingresados.<br>Â¿Confirma que no se realiz&oacute; la supervisi&oacute;n de la orden de servicio: ' + $('#txtNumeroOrdenServicio').val() + ' ?', 'coSupeSupe.ejecutarNoSupervision()');
            } else {
                coSupeSupe.ejecutarNoSupervision();
                $('#radioNoSupervision').attr("checked", "checked");
                $('#radioNoSupervision').attr("disabled", "disabled");
                $("#divResultadoSupervisionRS").css('display', 'none');  
                $("#divMotivoNoSuprvsnRS").css('display', 'block');
                /* OSINE-791 - RSIS 69 - Inicio */
                $('#slctResultadoSupervisionRS').removeAttr("validate");
                /* OSINE-791 - RSIS 69 - Fin */
            }
            //alert("valir es : |"+$('#flagSupervision').val()+"|");
            if (iteracion == constant.iteracion.segunda) {
            $("#divResultadoSupervisionRS").css('display', 'none');
            $('#slctResultadoSupervisionRS').removeAttr("validate");
            }
            
        });
        
        
        
        //check
        $('#chkIdentificaRS').click(function() {
            validarIdentificaPersona();
        });
        //horas
        $('.timepicker').timepicker({
            showPeriod: true,
            showLeadingZero: true,
            hourText: 'Hora',
            minuteText: 'Minuto',
        });


        //fechas
        $('#fechaFinRS,#fechaInicioRS').datepicker();
        var time = $('#fechaCreacionOS').val().replace(' ', 'T');
        var fechaCrea = new Date(time);
        fechaCrea.setDate(fechaCrea.getDate());
        $("#fechaInicioRS").datepicker("option", "minDate", fechaCrea);
        $("#fechaInicioRS").datepicker("option", "maxDate", new Date());
        $('#fechaInicioRS').change(function() {
            if ($('#fechaInicioRS').val() != '') {
                var fecInicio = $(this).datepicker('getDate');
                fecInicioFormat = new Date(fecInicio.getTime());
                fecInicioFormat.setDate(fecInicioFormat.getDate());
                $('#fechaFinRS').val($('#fechaInicioRS').val());
                $("#fechaFinRS").datepicker("option", "minDate", fecInicioFormat);
            } else {
                $('#fechaFinRS').val('');
                $("#fechaFinRS").datepicker("option", "minDate", new Date(0, 0, 0, 0, 0, 0));
            }
        });

        //modales
        $("#divDetalleIncumplidos").dialog({
            resizable: false,
            draggable: true,
            autoOpen: false,
            height: "auto",
            width: "auto",
            position: ['top', 130],
            modal: true,
            dialogClass: 'dialog',
            closeText: "Cerrar"
        });
        $("#divGenerarResultados").dialog({
            resizable: false,
            draggable: true,
            autoOpen: false,
            height: "auto",
            width: "auto",
            modal: true,
            dialogClass: 'dialog',
            closeText: "Cerrar"
        });

        //acordion
        $('#fldstSuperAnterior').puifieldset({toggleable: true, collapsed: true});
        $('#fldstDatosSuper').puifieldset({toggleable: true, collapsed: true});
        $('#fldstPersonaAtiende').puifieldset({toggleable: true, collapsed: true});
        $('#fldstObligacion').puifieldset({toggleable: true, collapsed: true});
    }

    //cargar grillas
    function grillaObligaciones() {
        /* OSINE_SFS-480 - RSIS 13 - Inicio */
        if ($('#idSupervision').val() != '' && $('#idSupervision').val() != null) {
            /* OSINE_SFS-480 - RSIS 13 - Fin */
            var nombres = ['#', 'idDetalleSupervision', 'idDetalleSupervisionAnt', 'obligacion.idObligacion', 'supervision.idSupervision', 'tipificacion.idTipificacion', 'criterio.idCriterio', 'flagResultado', 'flagRegistrado',
                'descripcionResultado', 'C&Oacute;D. OBLIGACI&Oacute;N', 'OBLIGACI&Oacute;N NORMATIVA', 'BASE LEGAL', 'ADJ.', 'VER HALLAZGO', 'SI', 'NO','OTRO', 'SI', 'NO', 'VER', 'VER', ''];//OSINE_MANT_DSHL_003 - inicio - fin
            var columnas = [
                {name: "indice", width: 45, sortable: false, align: "center"},
                {name: "idDetalleSupervision", hidden: true},
                {name: "idDetalleSupervisionAnt", hidden: true},
                {name: "obligacion.idObligacion", hidden: true},
                {name: "supervision.idSupervision", hidden: true},
                {name: "tipificacion.idTipificacion", hidden: true},
                {name: "criterio.idCriterio", hidden: true},
                {name: "flagResultado", hidden: true},
                {name: "flagRegistrado", hidden: true},
                {name: "descripcionResultado", hidden: true},
                {name: "obligacion.codigoObligacion", width: 70, sortable: false, align: "left"},
                {name: "obligacion.descripcion", width: 240, sortable: false, align: "center"},
                {name: "obligacion.descripcionBaseLegal", width: 210, sortable: false, align: "left"},
                {name: "obligacion.idDocumentoAdjunto", width: 30, sortable: false, align: "center", formatter: descargarObligacionIMG},
                {name: "verHallazgo", width: 53, sortable: false, align: "center", formatter: verHallazgo, hidden: true},
                /* OSINE_SFS-480 - RSIS 13 - Inicio */
                {name: 'cumple', width: 30, sortable: false, align: "center", formatter: radioCumple, hidden: true},
                {name: 'incumple', width: 30, sortable: false, align: "center", formatter: radioIncumple, hidden: true},
                {name: 'otro', width: 30, sortable: false, align: "center", formatter: radioOtro, hidden: false},//OSINE_MANT_DSHL_003 - inicio - fin
                /* OSINE_SFS-480 - RSIS 13 - Fin */
                {name: 'sanciona', width: 30, sortable: false, hidden: true, align: "center", formatter: radioSanciona},
                {name: 'nosanciona', width: 30, sortable: false, hidden: true, align: "center", formatter: radioNoSanciona},
                {name: "ver", width: 20, sortable: false, align: "center", hidden: true, formatter: verDetalle},
                {name: "verDescargo", width: 20, sortable: false, align: "center", hidden: true, formatter: verDetalleDescargo},
                {name: "guardar", width: 20, sortable: false, align: "left", formatter: verGuardar}
            ];

            $("#gridContenedorObligaciones").html("");
            var grid = $("<table>", {
                "id": "gridObligaciones"
            });
            var pager = $("<div>", {
                "id": "paginacionObligaciones"
            });
            $("#gridContenedorObligaciones").append(grid).append(pager);

            criticidad = $('#cmbCriticidad').val() == null ? '-1' : $('#cmbCriticidad').val();
            temaObligacion = $('#cmbClasificacion').val() == null ? '-1' : $('#cmbClasificacion').val();
            FlagResultado = $('#hiddenCumple').val();
            FlagRegistrado = $('#hiddenRegistrado').val();
            CodigoBaseLegal = $('#txtCodigoBase').val();
            DescripcionObligacion = $('#txtDescripcionObli').val();
            grid.jqGrid({
                url: baseURL + "pages/supervision/findDetalleSupervisionDSHL",
                datatype: "json",
                mtype: "POST",
                postData: {
                    idSupervision: $('#idSupervision').val(),
                    idCriticidad: criticidad,
                    idTemaObligacion: temaObligacion,
                    codigoBaseLegal: CodigoBaseLegal,
                    descripcionObligacion: DescripcionObligacion,
                    flagResultado: FlagResultado,
                    flagRegistrado: FlagRegistrado,
                    flagSupervision: $("#flagSupervision").val(),
                    descripcionBaseLegal: $("#txtDescripcionBaseLegal").val()
                },
                hidegrid: false,
                rowNum: constant.rowNumPrinc,
                pager: "#paginacionObligaciones",
                emptyrecords: "No se encontraron resultados",
                loadtext: "Cargando",
                colNames: nombres,
                colModel: columnas,
                height: "auto",
                width: "800",
                viewrecords: true,
                caption: "",
                shrinkToFit: false,
                jsonReader: {
                    root: "filas",
                    page: "pagina",
                    total: "total",
                    records: "registros",
                    repeatitems: false,
                    id: "idDetalleSupervision"
                },
                onSelectRow: function(rowid, status) {
                    grid.resetSelection();
                },
                onRightClickRow: function(rowid) {
                },
                loadComplete: function(data) {
                    $('#gridObligaciones').find('td[aria-describedby="gridObligaciones_obligacion.descripcionBaseLegal"]').css('white-space', 'nowrap');
                    $("#gridObligaciones").css("width", "800px");
                    if ($('#gridObligaciones').prop('id') == "gridObligaciones") {
                        $("table[aria-labelledby=gbox_gridObligaciones]").css("width", "800px");
                    }
                },
                loadError: function(jqXHR) {
                    errorAjax(jqXHR);
                }
            });
            /* OSINE_SFS-480 - RSIS 13 - Inicio */
        }
        /* OSINE_SFS-480 - RSIS 13 - Fin */
    }


    function grillaOligacionIncumplida() { //mdiosesf - RSIS6
        var descripcionResultado = '';
        if (constant.iteracion.primera == iteracion) {
            descripcionResultado = 'Comentario del hallazgo';
        }
        else if (constant.iteracion.segunda == iteracion) {
            descripcionResultado = 'Evaluaci&oacute;n del descargo';
        }
        var nombres = ['idDetalleSupervision', 'idObligacion', 'C&Oacute;D. OBLIGACI&Oacute;N', 'OBLIGACI&Oacute;N NORMATIVA', descripcionResultado];
        var columnas = [
            {name: "idDetalleSupervision", hidden: true, sortable: true, align: "center"},
            {name: "obligacion.idObligacion", hidden: true, sortable: true, align: "center"},
            {name: "obligacion.codigoObligacion", width: 67, sortable: false, align: "left"},
            {name: "obligacion.descripcion", width: 568, sortable: false, align: "left"},
            {name: "descripcionResultado", sortable: false, align: "center", formatter: formatoDescripcionResultado}
        ];

        $("#gridContenedorOligacionIncumplida").html("");
        var grid = $("<table>", {
            "id": "gridOligacionIncumplida"
        });
        var pager = $("<div>", {
            "id": "paginacionOligacionIncumplida"
        });
        $("#gridContenedorOligacionIncumplida").append(grid).append(pager);
        FlagResultado = constant.tipoCumple.incumple;
        if (iteracion == constant.iteracion.segunda) { //mdiosesf - RSIS6
            FlagResultado = constant.tipoSancion.nosancion;
        }
        grid.jqGrid({
            url: baseURL + "pages/supervision/findDetalleSupervision",
            datatype: "json",
            mtype: "POST",
            postData: {
                idSupervision: $('#idSupervision').val(),
                flagResultado: FlagResultado
            },
            hidegrid: false,
            rowNum: constant.rowNumPrinc,
            pager: "#paginacionOligacionIncumplida",
            emptyrecords: "No se encontraron resultados",
            loadtext: "Cargando",
            colNames: nombres,
            colModel: columnas,
            height: "auto",
            width: "800",
            viewrecords: true,
            caption: "",
            shrinkToFit: false,
            jsonReader: {
                root: "filas",
                page: "pagina",
                total: "total",
                records: "registros",
                repeatitems: false,
                id: "idDetalleSupervision"
            },
            onSelectRow: function(rowid, status) {
            },
            onRightClickRow: function(rowid) {
            },
            loadComplete: function(data) {
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    }

    function grillaGenerarResultados() {
        var nombres = ['idDetalleSupervision', 'obligacion.idObligacion', 'supervision.idSupervision',
            'tipificacion.idTipificacion', 'criterio.idCriterio', '',
            'flagResultado', 'flagRegistrado',
            'Proyecto', 'Documentaci&oacute;n', 'BASE LEGAL',
            'Descarga'];
        var columnas = [
            {name: "idDetalleSupervision", hidden: true},
            {name: "obligacion.idObligacion", hidden: true},
            {name: "supervision.idSupervision", hidden: true},
            {name: "tipificacion.idTipificacion", hidden: true},
            {name: "criterio.idCriterio", hidden: true},
            {name: "flagResultado", hidden: true},
            {name: "flagRegistrado", hidden: true},
            {name: "obligacion.codigoObligacion", width: 70, sortable: false, align: "left", hidden: true},
            {name: "descripcionDocumento", width: 400, sortable: false, align: "left"},
            {name: "obligacion.descripcionBaseLegal", width: 370, sortable: false, align: "left", hidden: true},
            {name: "descripcionResultado", hidden: true},
            {name: "idPlantillaResultado", width: 60, sortable: false, align: "center", formatter: descargarGenerarResultadosIMG}
        ];

        $("#gridContenedorGenerarResultados").html("");
        var grid = $("<table>", {
            "id": "gridGenerarResultados"
        });
        var pager = $("<div>", {
            "id": "paginacionGenerarResultados"
        });
        $("#gridContenedorGenerarResultados").append(grid).append(pager);

        grid.jqGrid({
            url: baseURL + "pages/supervision/generarResultados",
            datatype: "json",
            postData: {
                idProceso: $('#txtIdProcesoSup').val(),
                idObligacionTipo: $('#txtIdObligacionTipoSup').val(),
                idActividad: $('#txtIdActividadSup').val(),
                iteracion: $('#idIteracionOS').val(),
                idSupervision: $('#idSupervision').val(),
                idTipoAsignacion: $('#idTipoAsignacion').val(),
                idMotivoNoSupervision: $('#slctMotivoNoSuprvsnRS').val() //mdiosesf - RSIS5
            },
            hidegrid: false,
            rowNum: constant.rowNumPrinc,
            pager: "#paginacionGenerarResultados",
            emptyrecords: "No se encontraron resultados",
            loadtext: "Cargando",
            colNames: nombres,
            colModel: columnas,
            height: "auto",
            width: "auto",
            viewrecords: true,
            caption: "",
            jsonReader: {
                root: "filas",
                page: "pagina",
                total: "total",
                records: "registros",
                repeatitems: false,
                id: "idDetalleSupervision"
            },
            onSelectRow: function(rowid, status) {
                grid.resetSelection();
            },
            onRightClickRow: function(rowid) {
            },
            loadComplete: function(data) {
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    }


    function cargarClasificacion() {
        $.getJSON(baseURL + "pages/supervision/obtenerClasificacion", {
            ajax: 'true'
        }, function(data) {
            var html = '<option value="-1">--Todos--</option>';
            if (data == null) {
                $('#cmbClasificacion').html(html);
                return;
            }

            var len = data.length;
            for (var i = 0; i < len; i++) {
                html += '<option value="' + data[i].idMaestroColumna + '">'
                        + data[i].descripcion + '</option>';
            }
            $('#cmbClasificacion').html(html);
        });
    }

    function cargarCriticidad() {
        $.getJSON(baseURL + "pages/supervision/obtenerCriticidad", {
            ajax: 'true'
        }, function(data) {
            var html = '<option value="-1">--Todos--</option>';
            if (data == null) {
                $('#cmbCriticidad').html(html);
                return;
            }

            var len = data.length;
            for (var i = 0; i < len; i++) {
                html += '<option value="' + data[i].idMaestroColumna + '">'
                        + data[i].descripcion + '</option>';
            }
            $('#cmbCriticidad').html(html);
        });
    }
    function cargarCumple() {
        $.getJSON(baseURL + "pages/supervision/obtenerCumple", {
            ajax: 'true'
        }, function(data) {
            var html = '<option value="null">--Todos--</option>';
            if (data == null) {
                $('#cmbCumple').html(html);
                return;
            }

            var len = data.length;
            for (var i = len; i >= 0; i--) {
                if (data[i] == 0) {
                    html += '<option value="' + data[i] + '">';
                    if (iteracion == constant.iteracion.primera) {
                        html += "NO";
                    }
                    if (iteracion == constant.iteracion.segunda) {
                        html += "SI";
                    }
                    html += '</option>';
                }
                if (data[i] == 1) {
                    html += '<option value="' + data[i] + '">';
                    if (iteracion == constant.iteracion.primera) {
                        html += "SI";
                    }
                    if (iteracion == constant.iteracion.segunda) {
                        html += "NO";
                    }
                    html += '</option>';
                }

            }
            $('#cmbCumple').html(html);
        });
    }
    function cargarRegistrado() {
        $.getJSON(baseURL + "pages/supervision/obtenerRegistrado", {
            ajax: 'true'
        }, function(data) {
            var html = '<option value="null">--Todos--</option>';
            if (data == null) {
                $('#cmbRegistrado').html(html);
                return;
            }

            var len = data.length;
            for (var i = len; i >= 0; i--) {
                if (data[i] == 0) {
                    html += '<option value="' + data[i] + '">'
                            + "NO" + '</option>';
                }
                if (data[i] == 1) {
                    html += '<option value="' + data[i] + '">'
                            + "SI" + '</option>';
                }

            }
            $('#cmbRegistrado').html(html);
        });
    }

    //Formatter
    function descargarObligacionIMG(id) {
        var editar = "";
        if (id != undefined && id != '') {
            editar = "<img src=\"" + baseURL + "/../images/stickers.png\"  width=17 height=18 onclick=\"coSupeSupe.descargaDocumentosObligacionesURL('" + id + "')\" style=\"cursor: pointer;\" alt=\"Descargar Obligacion\"/>";
        }
        return editar;
    }

    function descargarGenerarResultadosIMG(idPlantillaResultado) {
        var editar = "";
        if (idPlantillaResultado != undefined && idPlantillaResultado != '') {
            editar = '<a class="link" href="' + baseURL + 'pages/archivo/descargaResultadoGenerado?idPlantillaResultado=' + idPlantillaResultado + '&idSupervision=' + $('#idSupervision').val() + '" target="_blank" >' +
                    '<img class="vam" width="17" height="18" src="' + baseURL + 'images/stickers.png">' +
                    '</a>';
        }
        return editar;
    }
    
    // OSINE_MANT_DSHL_003 - inicio
    function radioOtro(cellvalue, options, rowdata) {
    	var idDetalleSupervision = rowdata.idDetalleSupervision;
        var flagResultado = rowdata.flagResultado;
        var editar = "<center>";
        editar += "<input id='radioz_" + idDetalleSupervision + "' type='radio' name='radioz_" + idDetalleSupervision + "' value='0' \""; 
        editar += " disabled";
        editar += "/><label for='radioz_" + idDetalleSupervision + "' class='radio'></label>";
        editar += " <center/>";
        return editar;
    }
    // OSINE_MANT_DSHL_003 - fin
    
    function radioIncumple(cellvalue, options, rowdata) {
        var idDetalleSupervision = rowdata.idDetalleSupervision;
        var flagResultado = rowdata.flagResultado;
        var editar = "<center>";
        editar += "<input id='radio1_" + idDetalleSupervision + "' type='radio' name='radio_" + idDetalleSupervision + "' value='0' onclick=\"common.abrirDescripcionHallazgo('','" + constant.tipoCumple.incumple + "','" + constant.modoSupervision.registro + "','true','" + idDetalleSupervision + "','gridObligaciones');\""; //mdiosesf - RSIS6
        if (flagResultado == '0') {
            editar += " checked";
        } //mdiosesf - RSIS6
        if (modo == constant.modoSupervision.consulta) {
            editar += " disabled";
        }
        editar += "/><label for='radio1_" + idDetalleSupervision + "' class='radio'></label>";
        editar += " <center/>";
        return editar;
    }
    function radioCumple(cellvalue, options, rowdata) {
        var idDetalleSupervision = rowdata.idDetalleSupervision;
        var flagResultado = rowdata.flagResultado;
        var editar = "<center>";
        editar += "<input id='radio2_" + idDetalleSupervision + "' type='radio' name='radio_" + idDetalleSupervision + "' value='1' onclick=\"common.abrirDescripcionHallazgo('','" + constant.tipoCumple.cumple + "','" + constant.modoSupervision.registro + "','true','" + idDetalleSupervision + "','gridObligaciones');\""; //mdiosesf - RSIS6
        if (flagResultado == '1') {
            editar += " checked";
        } //mdiosesf - RSIS6
        if (modo == constant.modoSupervision.consulta) {
            editar += " disabled";
        }
        editar += "/><label for='radio2_" + idDetalleSupervision + "' class='radio'></label>";
        editar += " <center/>";
        return editar;
    }
    function radioSanciona(cellvalue, options, rowdata) {
        var idDetalleSupervision = rowdata.idDetalleSupervision;
        var flagResultado = rowdata.flagResultado;
        var editar = "<center>";
        editar += "<input id='radio2Sancion_" + idDetalleSupervision + "' type='radio' name='radioSanciona_" + idDetalleSupervision + "' value='0' onclick=\"common.abrirEvaluacionDescargo('" + constant.tipoSancion.sancion + "','" + constant.modoSupervision.registro + "','true','" + idDetalleSupervision + "','gridObligaciones');\""; //mdiosesf - RSIS6
        if (flagResultado == '0') {
            editar += " checked";
        } //mdiosesf - RSIS6
        if (modo == constant.modoSupervision.consulta) {
            editar += " disabled";
        }
        editar += "/><label for='radio2Sancion_" + idDetalleSupervision + "' class='radio'></label>";
        editar += " <center/>";
        return editar;
    }
    function radioNoSanciona(cellvalue, options, rowdata) {
        var idDetalleSupervision = rowdata.idDetalleSupervision;
        var flagResultado = rowdata.flagResultado;
        var editar = "<center>";
        editar += "<input id='radio1Sancion_" + idDetalleSupervision + "' type='radio' name='radioSanciona_" + idDetalleSupervision + "' value='1' onclick=\"common.abrirEvaluacionDescargo('" + constant.tipoSancion.nosancion + "','" + constant.modoSupervision.registro + "','true','" + idDetalleSupervision + "','gridObligaciones');\"";  //mdiosesf - RSIS6
        if (flagResultado == '1') {
            editar += " checked";
        } //mdiosesf - RSIS6
        if (modo == constant.modoSupervision.consulta) {
            editar += " disabled";
        }
        editar += "/><label for='radio1Sancion_" + idDetalleSupervision + "' class='radio'></label>";
        editar += " <center/>";
        return editar;
    }
    function verDetalle(cellvalue, options, rowdata) {
        var idDetalleSupervision = rowdata.idDetalleSupervision;
        var tipoCumple = rowdata.flagResultado;
        var editar = "";
        editar = "<img src=\"" + baseURL + "/../images/lupa.png\" style=\"cursor: pointer;\" alt=\"Detalle Observacion\" width=\"20\" height=\"20\" onclick=\"common.abrirDescripcionHallazgo('','" + tipoCumple + "','" + constant.modoSupervision.consulta + "','true','" + idDetalleSupervision + "','gridObligaciones');\"/>"; //mdiosesf - RSIS6
        return editar;
    }
    function verDetalleDescargo(cellvalue, options, rowdata) {
        var idDetalleSupervision = rowdata.idDetalleSupervision;
        var tipoSancion = rowdata.flagResultado;
        var editar = "";
        editar = "<img src=\"" + baseURL + "/../images/lupa.png\" style=\"cursor: pointer;\" alt=\"Detalle Observacion\" width=\"20\" height=\"20\" onclick=\"common.abrirEvaluacionDescargo('" + tipoSancion + "','" + constant.modoSupervision.consulta + "','true','" + idDetalleSupervision + "','gridObligaciones');\"/>";  //mdiosesf - RSIS6
        return editar;
    }
    function verHallazgo(cellvalue, options, rowdata) {
        var idDetalleSupervision = rowdata.idDetalleSupervision;
        var editar = "";
        editar = "<img src=\"" + baseURL + "/../images/pergamino.jpg\" style=\"cursor: pointer;\" alt=\"Detalle Observacion\" width=\"20\" height=\"20\" onclick=\"common.abrirDescripcionHallazgo('respaldo','','" + constant.modoSupervision.consulta + "','true','" + idDetalleSupervision + "','gridObligaciones');\"/>"; //mdiosesf - RSIS6
        return editar;
    }
    function verGuardar(cellvalue, options, rowdata) {
        var id = rowdata.flagRegistrado;
        var ver = "";
        if (id == "1") {
            ver = "<input id=\"radioSiSupervision\" type=\"checkbox\"  disabled=\"disabled\"checked=\"checked\" width=\"20\" height=\"20\" ><label title=\"Trabajado\" for=\"radioSiSupervision\" class=\"checkbox\"></label>";
        }
        return ver;
    }
    function formatoDescripcionResultado(cellvalue, options, rowdata) { //mdiosesf - RSIS6 
        var idObligacion = rowdata.obligacion.idObligacion;
        var idDetalleSupervision = rowdata.idDetalleSupervision;
        var editar = "";
        editar = "<div class=\"botones\">";
        if (constant.iteracion.primera == iteracion) {
            editar += "<input id='boton_ver_" + idObligacion + "' type='button' title='Ver Comentario' class='btn-azul btn-small' value='Ver Comentario' onclick='common.abrirDescripcionHallazgo(\"" + "" + "\",\"" + constant.tipoCumple.incumple + "\",\"" + constant.modoSupervision.consulta + "\",\"" + "false" + "\", \"" + idDetalleSupervision + "\", \"" + "gridOligacionIncumplida" + "\")'>";
        } else {
            editar += "<input id='boton_ver_" + idObligacion + "' type='button' title='Ver Descargo' class='btn-azul btn-small' value='Ver Descargo' onclick='common.abrirEvaluacionDescargo(\"" + "" + "\",\"" + constant.modoSupervision.consulta + "\",\"" + "false" + "\", \"" + idDetalleSupervision + "\",\"" + "gridOligacionIncumplida" + "\")'>";
        }
        editar += "</div>";
        return editar;
    }
    //Funciones de los formatter

    function descargaDocumentosObligacionesURL(idDocumento) {
        document.location.href = baseURL + 'pages/archivo/descargaMdiArchivoAlfresco?idDocumentoAdjunto=' + idDocumento;
    }
    function activarConsulta() {
        if (iteracion == constant.iteracion.primera) {
            fxGrilla.mostrarCampos('gridObligaciones', 'ver');
        }
        else if (iteracion == constant.iteracion.segunda) {
            fxGrilla.mostrarCampos('gridObligaciones', 'verDescargo');
        }
    }
    function guardarDatosSupervision() {
        var mensajeValidacion = '';
        if (validarFormularioDatosSupervision()) {
            mensajeValidacion = validarDatosSupervision();
            if (mensajeValidacion == '') {
                confirmer.open('Â¿Confirma que desea registrar los datos de supervisi&oacute;n?', 'coSupeSupe.registrarDatosSupervision()');
            }
            else {
                mensajeGrowl('warn', mensajeValidacion);
            }
        }
    }
    function guardarPersonaAtiende() {
        var mensajeValidacion = '';
        if (validarFormularioPersonaAtiende()) {
            mensajeValidacion = validarPersonaAtiende();
            if (mensajeValidacion == '') {
                confirmer.open('Â¿Confirma que desea registrar los datos de la persona que atiende la visita?', 'coSupeSupe.registrarPersonaAtiende()');
            }
            else {
                mensajeGrowl('warn', mensajeValidacion);
            }
        }
    }
    function ejecutarNoSupervision() {
        
        $('#radioNoSupervision').attr("checked", "checked");
        actualizarFlagSupervision();        
        $('#flagSupervision').val(0);
        grillaObligaciones();
        validarRadioNo();
        $('#radioSiSupervision').removeAttr("disabled");        
        $("#divResultadoSupervisionRS").css('display', 'none');   
        $("#divMotivoNoSuprvsnRS").css('display', 'block');                
    }
    
    
    //CRUD
    function registrarDatosSupervision() {
        loading.open();
        var motivoNoSuprvsn = '-1';
        var descripcionMtvoNoSuprvsn = '';
        var fechaInicioSupervision = $('#fechaInicioRS').val() + " " + $('#horaInicioRS').val();
        var fechaFinSupervision = $('#fechaFinRS').val() + " " + $('#horaFinRS').val();
        var flagCumplimientoPrevio = "";
        if (iteracion == constant.iteracion.primera) {
            if ($('#radioNoSupervision').is(':checked')) {
                motivoNoSuprvsn = $('#slctMotivoNoSuprvsnRS').val().toUpperCase();
                if ($("#slctMotivoNoSuprvsnRS option:selected").attr('especifica') == constant.motivoSupervision.especifica) {
                    descripcionMtvoNoSuprvsn = $('#txtEspecificarRS').val().toUpperCase();
                }
            }
        }
        /* OSINE791- RSIS 69 - Inicio  */
        if (iteracion == constant.iteracion.primera) {
            if ($('#radioSiSupervision').is(':checked')) {
                flagCumplimientoPrevio = $('#slctResultadoSupervisionRS').val();
            }
        }
        /* OSINE791- RSIS 69 - Fin  */
        $.post(baseURL + "pages/supervision/guardarDatosSupervision",
                {
                    idSupervision: $('#idSupervision').val(),
                    fechaInicio: fechaInicioSupervision,
                    fechaFin: fechaFinSupervision,
                    actaProbatoria: $('#txtActaProbatoriaRS').val().toUpperCase(),
                    cartaVisita: $('#txtCartaVisitaRS').val().toUpperCase(),
                    observacion: $('#txtObservacionRS').val(),
                    'motivoNoSupervision.idMotivoNoSupervision': motivoNoSuprvsn,
                    descripcionMtvoNoSuprvsn: descripcionMtvoNoSuprvsn,
                    'ordenServicioDTO.idOrdenServicio': $('#idOrdenServicioRS').val(),
                     flagCumplimientoPrevio : flagCumplimientoPrevio,
                     flagSupervision : $("#flagSupervision").val(),
                    'ordenServicioDTO.iteracion' : $("#idIteracionOS").val()
                    
                })
                .done(function(data) {
            loading.close();
            /* OSINE_SFS-480 - RSIS 13 - Inicio*/
            if ($('#idSupervision').val() == null || $('#idSupervision').val() == '') {
                $('#idSupervision').val(data.sup.idSupervision);
                $('#idIteracionOS').val(data.sup.ordenServicioDTO.iteracion);
                $('#idTipoAsignacion').val(data.sup.ordenServicioDTO.idTipoAsignacion);
                $('#idOrdenServicioRS').val(data.sup.ordenServicioDTO.idOrdenServicio);
                $('#fechaCreacionOS').val(data.sup.ordenServicioDTO.fechaHoraCreacionOS);

                $('#flagSupervision').val(data.sup.flagSupervision);
                $('#txtNumeroOrdenServicio').val(data.sup.ordenServicioDTO.numeroOrdenServicio);
                if ($('#idSupervision').val() != null) {
                    $("fieldset").removeAttr("disabled");
                    $('#radioSiSupervision,#radioNoSupervision').removeAttr("disabled");
                    $('#radioSiSupervision').attr("checked", "checked");

                }
                actualizarFlagSupervision();
                grillaObligaciones();
                validarRadioSi();
            }
            /* OSINE_SFS-480 - RSIS 13 - Fin */
            /* OSINE_791 - RSIS 66 - Inicio */
            grillaObligaciones();
            if (modo == constant.modoSupervision.consulta) {
                activarConsulta();
            }
            if (iteracion == constant.iteracion.segunda) {
                activarSegundaItera();
            }
            desbloquearModoConsultaGrillaObligacion(iteracion);
            /* OSINE_791 - RSIS 66 - Fin */
            if (data.resultado == '0') {
                mensajeGrowl("success", constant.confirm.save);
                /* OSINE_MAN-001 - Inicio */
                $( "#btnGuardarDatosSupervision" ).attr("disabled", true);
                $("#btnGuardarDatosSupervision").addClass('btn-opaco');
                /* OSINE_MAN-001 - Inicio */
            }else{
                mensajeGrowl("warn", data.mensaje, "");
            }
        })
                .fail(errorAjax);
    }

    function registrarPersonaAtiende() {
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
        $.post(baseURL + "pages/supervision/guardarPersonaAtiende",
                {
                    idSupervision: $('#idSupervision').val(),
                    flagIdentificaPersona: flagIdentificaPers,
                    observacionIdentificaPers: $('#txtObservacionPersonaAtiendeRS').val(),
                    'ordenServicioDTO.idOrdenServicio': $('#idOrdenServicioRS').val(),
                    //supervisionPersona
                    'supervisionPersonaGral.cargo.idMaestroColumna': $('#slctCargoAtiendeRS').val(),
                    'supervisionPersonaGral.personaGeneral.idPersonaGeneral': $('#txtIdPersonaRS').val(),
                    'supervisionPersonaGral.personaGeneral.nombresPersona': $('#txtNombresRS').val().toUpperCase(),
                    'supervisionPersonaGral.personaGeneral.apellidoPaternoPersona': $('#txtApellidoPaternoRS').val().toUpperCase(),
                    'supervisionPersonaGral.personaGeneral.apellidoMaternoPersona': $('#txtApellidoMaternoRS').val().toUpperCase(),
                    'supervisionPersonaGral.personaGeneral.idTipoDocumento': $('#slctTipoDocumentoRS').val(),
                    'supervisionPersonaGral.personaGeneral.numeroDocumento': $('#txtNumeroDocumentoRS').val().toUpperCase(),
                    'supervisionAnterior.idSupervision': $('#idSupervisionAnt').val()
                })
                .done(function(data) {
            loading.close();
            if (data.resultado == '0') {
                mensajeGrowl("success", constant.confirm.save);
                /* OSINE_MAN-001 - Inicio */
                $( "#btnGuardarPersonaAtiende" ).attr("disabled", true);
                $("#btnGuardarPersonaAtiende").addClass('btn-opaco');
                /* OSINE_MAN-001 - Fin */
            }
        })
                .fail(errorAjax);
    }

    function actualizarFlagSupervision() {
        loading.open();
        var arrayCheck;
        var flagSupervision = '';
        arrayCheck = $("input:radio[name=radioSupervision]");
        for (var x = 0; x < arrayCheck.length; x++) {
            if (arrayCheck[x].checked) {
                flagSupervision = arrayCheck[x].value;
            }
        }
        $.getJSON(baseURL + "pages/supervision/cambiarSupervision",
                {
                    idSupervision: $('#idSupervision').val(),
                    flagSupervision: flagSupervision,
                    'ordenServicioDTO.iteracion': $('#idIteracionOS').val(),
                })
                .done(function(data) {
            loading.close();
            if (data.resultado == '0') {
                $('#flagSupervision').val(data.supervision.flagSupervision);
                //Ajuste para mostrar mensaje de edicion del flagSupervision
                if(!primera_vez)
                	mensajeGrowl("success", constant.confirm.edit);
                else
                	primera_vez=false;
            }
        })
                .fail(errorAjax);
    }

    function buscarPersonaAtiende(tipoDocumento, numeroDocumento) {
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
                        $('#txtIdPersonaRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS').val('');
                    }
                    $('#slctTipoDocumentoRS').attr("disabled", "disabled");
                    $('#txtNumeroDocumentoRS').attr("disabled", "disabled");
                    $('#txtIdPersonaRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#slctCargoAtiendeRS').removeAttr("disabled");
                    $('#btnBuscarPersonaAtiende').val("Corregir");
                    loading.close();
                } else if (data.resultado == 1) {
                    mensajeGrowl('error', data.mensaje);
                    loading.close();
                }
            },
            error: errorAjax
        });
    }

    function bloquearModoConsultaGrillaObligacion(iteracion) {
        if (constant.iteracion.primera == iteracion) {
            fxGrilla.ocultarCampos('gridObligaciones', 'cumple,incumple,guardar');
        } else if (constant.iteracion.segunda == iteracion) {
            $("#gridObligaciones").jqGrid('destroyGroupHeader');
            fxGrilla.ocultarCampos('gridObligaciones', 'verHallazgo,sanciona,nosanciona,guardar');
        }
    }
    function desbloquearModoConsultaGrillaObligacion(iteracion) {
        if (constant.iteracion.primera == iteracion) {
            fxGrilla.mostrarCampos('gridObligaciones', 'cumple,incumple,guardar');
            $("#gridObligaciones").jqGrid('setGroupHeaders', {
                useColSpanStyle: true,
                groupHeaders: [
                    {startColumnName: 'cumple', numberOfColumns: 3, titleText: 'Â¿CUMPLE?'}//OSINE_MANT_DSHL_003  - inicio - fin
                ]
            });
        } else if (constant.iteracion.segunda == iteracion) {
            fxGrilla.mostrarCampos('gridObligaciones', 'verHallazgo,sanciona,nosanciona,guardar');
            $("#gridObligaciones").jqGrid('setGroupHeaders', {
                useColSpanStyle: true,
                groupHeaders: [
                    {startColumnName: 'indice', numberOfColumns: 16, titleText: 'INFORME DE INICIO DE PAS'},
                    {startColumnName: 'sanciona', numberOfColumns: 5, titleText: 'I.F. PAS'},
                ]
            });
            $("#gridObligaciones").jqGrid('setGroupHeaders', {
                useColSpanStyle: true,
                groupHeaders: [
                    {startColumnName: 'sanciona', numberOfColumns: 2, titleText: 'Â¿SE SANCIONA?'}
                ]
            });
        }
    }
    //Validaciones
    function validarFormularioDatosSupervision() {
        return $('#divFrmDatosSuper').validateAllForm('#divMensajeValidaFrmSup');
    }
    function validarFormularioPersonaAtiende() {
        return $('#divFrmPersonaAtiende').validateAllForm('#divMensajeValidaFrmSup');
    }
    function validarFormulario() {
        return $('#formRegSupervision').validateAllForm('#divMensajeValidaFrmSup');
    }

    /* OSINE_MAN-001 - Inicio */
    function validarExisteCambios(){
        if($('#btnGuardarPersonaAtiende').is(':disabled')&&$('#btnGuardarDatosSupervision').is(':disabled')){
        	return false;
        }
        return true;
    }
    /* OSINE_MAN-001 - Fin */
    
    function validarDatosSupervision() {
        $('#fechaInicioRS,#horaInicioRS,#fechaFinRS,#horaFinRS,#txtObservacionRS').removeClass('error');
        var mensajeValidacion = "";
        var time = $('#fechaCreacionOS').val().replace(' ', 'T');
        var fechaCreaOS = new Date(time);
        fechaCreaOS.setDate(fechaCreaOS.getDate());
        var fechaInicioSuper = $("#fechaInicioRS").val();
        var horaInicio = $('#horaInicioRS').timepicker('getTimeAsDate');
        var fechaFinSuper = $("#fechaFinRS").val();
        var horaFin = $('#horaFinRS').timepicker('getTimeAsDate');
        if (!esFecha(fechaInicioSuper)) {
            $('#fechaInicioRS').addClass("error");
            return mensajeValidacion = "La fecha debe tener formato correcto, corregir";
        }
        if (!esFecha(fechaFinSuper)) {
            $('#fechaFinRS').addClass("error");
            return mensajeValidacion = "La fecha debe tener formato correcto, corregir";
        }
        if (!esHora($('#horaInicioRS').val())) {
            $('#horaInicioRS').addClass("error");
            return mensajeValidacion = "La hora debe tener formato correcto, corregir";
        }
        if (!esHora($('#horaFinRS').val())) {
            $('#horaFinRS').addClass("error");
            return mensajeValidacion = "La hora debe tener formato correcto, corregir";
        }

        //validamos fecha creacion y la fecha de inicio
        var resultadoFechaCreaIni = comparaFecha(parseDate(fechaCreaOS), fechaInicioSuper);
        if (resultadoFechaCreaIni == 1) {
            $('#fechaInicioRS').addClass("error");
            mensajeValidacion = "La fecha de inicio debe ser mayor que la fecha de creaci&oacute;n de orden de servicio, corregir <br>";
            return mensajeValidacion;
        } else if (resultadoFechaCreaIni == 0) {
            resultadoHorasCreaIni = comparaHora(fechaCreaOS, horaInicio);
            if (resultadoHorasCreaIni > 0) {
                $('#horaInicioRS').addClass("error");
                mensajeValidacion = "La hora de inicio debe ser mayor que la hora de creaci&oacute;n de orden de servicio, corregir <br>";
                return mensajeValidacion;
            }
        }
        var resultadoFechaCreaHoy = comparaFecha(fechaInicioSuper, obtenerFechaFormateada(new Date()));
        if (resultadoFechaCreaHoy == 1) {
            $('#fechaInicioRS').addClass("error");
            mensajeValidacion = "La fecha de inicio no debe ser mayor que la fecha del Sistema, corregir <br>";
            return mensajeValidacion;
        }
        //validamos fecha inicio y la fecha fin
        var resultadoFechaIniFin = comparaFecha(fechaInicioSuper, fechaFinSuper);
        if (resultadoFechaIniFin == 1) {
            $('#fechaFinRS').addClass("error");
            mensajeValidacion = "La fecha de fin debe ser mayor que la fecha de inicio, corregir <br>";
            return mensajeValidacion;
        } else if (resultadoFechaIniFin == 0) {
            resultadoHoras = comparaHora(horaInicio, horaFin);
            if (resultadoHoras > 0) {
                $('#horaFinRS').addClass("error");
                mensajeValidacion = "La hora de fin debe ser mayor que la hora de inicio, corregir <br>";
                return mensajeValidacion;
            }
        }
        if ($('#txtEspecificarRS').val().trim() != '' && $('#txtEspecificarRS').val().trim().length < 8) {
            $('#txtEspecificarRS').addClass('error');
            mensajeValidacion = "El dato especificar motivo no supervisi&oacute;n debe tener una longitud mayor o igual a 8 caracteres, corregir <br>";
            return mensajeValidacion;
        }
        if ($('#txtObservacionRS').val().trim() != '' && $('#txtObservacionRS').val().trim().length < 8) {
            $('#txtObservacionRS').addClass('error');
            mensajeValidacion = "La observaci&oacute;n de la supervisi&oacute;n debe tener una longitud mayor o igual a 8 caracteres, corregir <br>";
            return mensajeValidacion;
        }
        return mensajeValidacion;
    }
    function validarPersonaAtiende() {
        var mensajeValidacion = "";
        $('#txtObservacionPersonaAtiendeRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS').removeClass('error');
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
        }
        if ($('#txtObservacionPersonaAtiendeRS').val().trim() != '' && $('#txtObservacionPersonaAtiendeRS').val().trim().length < 8) {
            $('#txtObservacionPersonaAtiendeRS').addClass('error');
            mensajeValidacion = "La observaci&oacute;n de la persona que atiende la visita debe tener una longitud mayor o igual a 8 caracteres, corregir<br>";
            return mensajeValidacion;
        }
        return mensajeValidacion;
    }
    function validarRadioSi() {
        $('#radioSiSupervision').attr("disabled", "disabled");
        //grilla
        $('#txtCodigoBase,#cmbClasificacion,#cmbCriticidad,#cmbCumple,#cmbRegistrado,#txtDescripcionObli,#btnBuscarObligaciones,#btnLimpiar,#btnOblIncumplida').removeAttr("disabled");
        $('#btnBuscarObligaciones,#btnLimpiar,#btnOblIncumplida').addClass('btn-azul');
        $('span[class="obligatorioDS"]').css('display', 'none');
        $('#txtObservacionRS').removeAttr("validate");
        desbloquearModoConsultaGrillaObligacion(iteracion);
        $("#gridObligaciones").css("width", "800px");
        if ($('#gridObligaciones').prop('id') == "gridObligaciones") {
            $("table[aria-labelledby=gbox_gridObligaciones]").css("width", "800px");
        }
        //motivoNo
        if (constant.iteracion.primera == iteracion) {
            $('#divMotivoNoSuprvsnRS').css('display', 'none');
            $('#slctMotivoNoSuprvsnRS').removeAttr("validate");
            $('#slctMotivoNoSuprvsnRS').val("");
            $('#divEspecificarRS').css('display', 'none');
            $('#txtEspecificarRS').removeAttr("validate");
            $('#txtEspecificarRS').val("");
        }
        //personaAtiende
        if (modo != constant.modoSupervision.consulta) {
            if ($('#asignacionOS').val() == '1') {
                $('#btnBuscarPersonaAtiende').css('display', 'inline-block');
                $('#chkIdentificaRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#slctCargoAtiendeRS,#txtObservacionPersonaAtiendeRS').removeAttr("disabled");
                validarIdentificaPersona();
            }
            $('#btnGuardarPersonaAtiende').removeAttr("disabled");
            $('#btnGuardarPersonaAtiende').addClass('btn-azul');
        }
    }
    function validarRadioNo() {
        $('#radioNoSupervision').attr("disabled", "disabled");
        //grilla		
        $('#txtCodigoBase,#cmbClasificacion,#cmbCriticidad,#cmbCumple,#cmbRegistrado,#txtDescripcionObli,#btnBuscarObligaciones,#btnLimpiar,#btnOblIncumplida').attr("disabled", "disabled");
        $('#btnBuscarObligaciones,#btnLimpiar,#btnOblIncumplida').removeClass('btn-azul');
        $('span[class="obligatorioDS"]').css('display', 'inline');
        $('#txtObservacionRS').attr("validate", "[O]");
        bloquearModoConsultaGrillaObligacion(iteracion);
        $("#gridObligaciones").css("width", "800px");
        if ($('#gridObligaciones').prop('id') == "gridObligaciones") {
            $("table[aria-labelledby=gbox_gridObligaciones]").css("width", "800px");
        }
        //motivoNo
        if (constant.iteracion.primera == iteracion) {
            $('#divMotivoNoSuprvsnRS').css('display', 'inline');
            $('#slctMotivoNoSuprvsnRS').attr("validate", "[O]");
            $('#slctMotivoNoSuprvsnRS').val("");
        }
        //persona atiende
        if ($('#asignacionOS').val() == '1') {
            $('#chkIdentificaRS,#slctTipoDocumentoRS,#txtNumeroDocumentoRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#slctCargoAtiendeRS,#txtObservacionPersonaAtiendeRS').attr("disabled", "disabled");
            $('#slctTipoDocumentoRS,#txtNumeroDocumentoRS,#slctCargoAtiendeRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#txtObservacionPersonaAtiendeRS').removeAttr("validate");
            //limpiamos valores
            $('#chkIdentificaRS').removeAttr("checked");
            $('#btnBuscarPersonaAtiende').css('display', 'none');
            $('#slctTipoDocumentoRS,#txtNumeroDocumentoRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#slctCargoAtiendeRS,#txtObservacionPersonaAtiendeRS').val("");
            $('#radioNoSupervision').attr("checked", "checked");
        }
        //boton guardar
        if (modo != constant.modoSupervision.consulta) {
            $('#slctTipoDocumentoRS,#txtNumeroDocumentoRS,#slctCargoAtiendeRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#txtObservacionPersonaAtiendeRS').removeClass("error");
            $('#btnGuardarPersonaAtiende').attr("disabled", "disabled");
            $('#btnGuardarPersonaAtiende').removeClass('btn-azul');
        }
    }
    function validarCargaFormulario() {
        if ($('#radioSiSupervision').is(':checked')) {
            validarRadioSi();
            if ($('#asignacionOS').val() == '1') {
                if (!$('#chkIdentificaRS').is(':checked')) {
                    tipoDoc = $('#txtNumeroDocumentoRS').val();
                    $('#slctTipoDocumentoRS').trigger('change');
                    $('#txtNumeroDocumentoRS').val(tipoDoc);
                }
            }
            /* OSINE791- RSIS 69 - Inicio  */
            var valor = $("#flagCumplimientoPrevio").val();
            $("#slctResultadoSupervisionRS").val(valor);
            /* OSINE791- RSIS 69 - Fin  */
        }
        if ($('#radioNoSupervision').is(':checked')) {
            var motivoNoSuper = $('#slctMotivoNoSuprvsnRS').val();
            validarRadioNo();
            personaSeIdentifico('ninguna');
            $('#slctMotivoNoSuprvsnRS').val(motivoNoSuper);
            if ($("#slctMotivoNoSuprvsnRS option:selected").attr('especifica') == constant.motivoSupervision.especifica) {
                $('#divEspecificarRS').css('display', 'inline');
                $('#txtEspecificarRS').attr("validate", "[O]");
            }
        }
        if (iteracion == constant.iteracion.segunda) {
            //para empresa supervisora
            $('#slctTipoEmprSupeOSSuper').trigger("change");
            if ($('#idLocadorOSSupAnt').val() != '' && $('#idLocadorOSSupAnt').val() != undefined) {
                $('#slctEmprSupeOSSuper').val($('#idLocadorOSSupAnt').val());
            }
            else {
                $('#slctEmprSupeOSSuper').val($('#idSupervisoraEmpresaOSSupAnt').val());
            }
        }
        
        
    }
    function validarIdentificaPersona() {
        if ($('#asignacionOS').val() == '1') {//si es con visita
            if ($('#txtIdPersonaRS').val() != '') {
                $('#slctCargoAtiendeRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS').removeAttr("disabled");
                $('#slctTipoDocumentoRS,#txtNumeroDocumentoRS').attr("disabled", "disabled");
                $('#btnBuscarPersonaAtiende').val("Corregir");
            } else {
                $('#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#slctCargoAtiendeRS').attr("disabled", "disabled");
                $('#slctTipoDocumentoRS,#txtNumeroDocumentoRS').removeAttr("disabled");
                $('#btnBuscarPersonaAtiende').val("Buscar");
            }
            $('#slctTipoDocumentoRS,#txtNumeroDocumentoRS,#slctCargoAtiendeRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS').attr("validate", "[O]");
            $('#txtObservacionPersonaAtiendeRS').removeAttr("validate");
            $('#btnBuscarPersonaAtiende').css('display', 'inline-block');
            if ($('#chkIdentificaRS').is(':checked')) {
                personaSeIdentifico('no');
                $('#slctTipoDocumentoRS,#txtNumeroDocumentoRS,#slctCargoAtiendeRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS').attr("disabled", "disabled");
                $('#slctTipoDocumentoRS,#txtNumeroDocumentoRS,#slctCargoAtiendeRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS').removeAttr("validate");
                $('#btnBuscarPersonaAtiende').css('display', 'none');
                $('#slctTipoDocumentoRS,#txtNumeroDocumentoRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#slctCargoAtiendeRS,#txtIdPersonaRS').val("");
                $('#txtObservacionPersonaAtiendeRS').attr("validate", "[O]");
            } else {
                personaSeIdentifico('si');
            }
        } else {
            $('#btnBuscarPersonaAtiende').css('display', 'none');
            $('#chkIdentificaRS,#slctTipoDocumentoRS,#txtNumeroDocumentoRS,#slctCargoAtiendeRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#txtObservacionPersonaAtiendeRS').attr("disabled", "disabled");
        }
    }
    function validarTipoDocumentoSupervision(cbo) {
        $("#txtApellidoPaternoRS,#txtApellidoMaternoRS").removeAttr("validate");
        changeTipoDocu("#" + cbo.id, "#txtNumeroDocumentoRS", "#formRegSupervision");
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
    function validarBuscaPersonaAtiende() {
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
    }
    function validarTipoDocumento(tipoDocumento, numeroDocumento) {
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
    /* OSINE791- RSIS 69 - Inicio  */
    function ValidarObligacionesIncumplidas(){
        $('#flagInfraccionesDSHL').val("");
        var EstadoObligacionesIncumple = constant.estado.inactivo;
	$.ajax({
            url:baseURL + "pages/detalleSupervision/findDetalleSupervision",
            type:'post',
            async:false,
            data:{  
                idSupervision: $('#idSupervision').val(),  
                flagResultado: EstadoObligacionesIncumple
            },
            success:function(data){
                if(data!=null && data!=undefined){
                    if(data.length>0){
                        $('#flagInfraccionesDSHL').val(constant.estado.activo);
                    }else{
                        $('#flagInfraccionesDSHL').val(constant.estado.inactivo);
                    }
                }
            },
            error:errorAjax
	});
    }
    /* OSINE791- RSIS 69 - Fin  */
    function validarRegistroSupervision() {
        var mensajeValidacion = '';
        var motivoNoSuprvsn = '-1';
        var descripcionMtvoNoSuprvsn = '';
        var arrayCheck;
        var flagSupervision = '';
        var flagIdentificaPers = '';
        arrayCheck = $("input:radio[name=radioSupervision]");
        for (var x = 0; x < arrayCheck.length; x++) {
            if (arrayCheck[x].checked) {
                flagSupervision = arrayCheck[x].value;
            }
        }
        if ($('#radioNoSupervision').is(':checked')) {
            motivoNoSuprvsn = $('#slctMotivoNoSuprvsnRS').val();
            if ($("#slctMotivoNoSuprvsnRS option:selected").attr('especifica') == constant.motivoSupervision.especifica) {
                descripcionMtvoNoSuprvsn = $('#txtEspecificarRS').val().toUpperCase();
            }
        }

        if ($('#asignacionOS').val() == '1') {//si es con visita
            if ($('#chkIdentificaRS').is(':checked')) {
                flagIdentificaPers = '0';
            }
            else {
                flagIdentificaPers = '1';
            }
        }
        loading.open();
        $.ajax({
            url: baseURL + "pages/supervision/validarRegistroSupervision",
            type: 'post',
            async: false,
            data: {
                enPantalla: '1',
                idSupervision: $('#idSupervision').val(),
                flagSupervision: flagSupervision,
                'ordenServicioDTO.idTipoAsignacion': $('#idTipoAsignacion').val(),
                fechaInicio: $('#fechaInicioRS').val(),
                horaInicio: $('#horaInicioRS').val(),
                fechaFin: $('#fechaFinRS').val(),
                horaFin: $('#horaFinRS').val(),
                actaProbatoria: $('#txtActaProbatoriaRS').val().toUpperCase(),
                cartaVisita: $('#txtCartaVisitaRS').val().toUpperCase(),
             observacion: $('#txtObservacionRS').val(),
                'motivoNoSupervision.idMotivoNoSupervision': motivoNoSuprvsn,
                descripcionMtvoNoSuprvsn: descripcionMtvoNoSuprvsn,
                flagIdentificaPersona: flagIdentificaPers,
             observacionIdentificaPers: $('#txtObservacionPersonaAtiendeRS').val().toUpperCase(),
                'ordenServicioDTO.expediente.obligacionTipo.idObligacionTipo':$('#txtIdObligacionTipoAte').val(),
                'ordenServicioDTO.expediente.unidadSupervisada.actividad.idActividad':$('#txtIdActividadAte').val(),
                'ordenServicioDTO.expediente.proceso.idProceso':$('#txtIdProcesoAte').val(),
                //supervisionPersona
                'supervisionPersonaGral.personaGeneral.idTipoDocumento': $('#slctTipoDocumentoRS').val(),
                'supervisionPersonaGral.personaGeneral.numeroDocumento': $('#txtNumeroDocumentoRS').val().toUpperCase(),
                'supervisionPersonaGral.personaGeneral.apellidoPaternoPersona': $('#txtApellidoPaternoRS').val().toUpperCase(),
                'supervisionPersonaGral.personaGeneral.apellidoMaternoPersona': $('#txtApellidoMaternoRS').val().toUpperCase(),
                'supervisionPersonaGral.personaGeneral.nombresPersona': $('#txtNombresRS').val().toUpperCase(),
                'supervisionPersonaGral.cargo.idMaestroColumna': $('#slctCargoAtiendeRS').val(),
                 flagCumplimientoPrevio : $("#slctResultadoSupervisionRS").val()
            },
            success: function(data) {
                loading.close();
                if (data.resultado == '0') {
                    if (!data.validoSupervision) {
                        mensajeValidacion = data.mensaje;
                    }
                } else {
                    mensajeGrowl("error", data.mensaje, "");
                    mensajeValidacion = null;
                }
            },
            error: errorAjax
        });
        return mensajeValidacion;
    }
    function activarSegundaItera() {
        fxGrilla.ocultarCampos('gridObligaciones', 'cumple,incumple');
        $("#divSuperAnterior").css('display', 'inline');
    }
    function personaSeIdentifico(opcion) {
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
    }
    function limpiarObligacionBusqueda() {
        $('#hiddenCumple').removeAttr('value');
        $('#hiddenRegistrado').val("");
        $('#txtCodigoBase').val("");
        $('#txtDescripcionObli').val("");
        $("#cmbClasificacion").val("-1");
        $("#cmbCriticidad").val("-1");
        $("#cmbCumple").val("null");
        $("#cmbRegistrado").val("null");
        $("#txtDescripcionBaseLegal").val("");
        grillaObligaciones();
        if (modo == constant.modoSupervision.consulta) {
            activarConsulta();
        }
        if (iteracion == constant.iteracion.segunda) {
            activarSegundaItera();
        }
        desbloquearModoConsultaGrillaObligacion(iteracion);
    }
/* OSINE791- RSIS 69 - Inicio  */
    function HabilitarCajasTexto() {
        $('#fechaInicioRS').removeAttr("disabled", "disabled");
        $('#horaInicioRS').removeAttr("disabled", "disabled");
        $('#fechaFinRS').removeAttr("disabled", "disabled");
        $('#horaFinRS').removeAttr("disabled", "disabled");
        $('#txtActaProbatoriaRS').removeAttr("disabled", "disabled");
        $('#txtCartaVisitaRS').removeAttr("disabled", "disabled");
        $('#txtObservacionRS').removeAttr("disabled", "disabled");
        $('#btnAdjuntoSupervision').removeAttr("disabled", "disabled");
        $('#btnGuardarDatosSupervision').removeAttr("disabled", "disabled");
        $('#btnGuardarDatosSupervision').addClass('btn-azul');
        $('#btnAdjuntoSupervision').addClass('btn-azul');      
        $('#btnGenerar').removeAttr("disabled", "disabled");
    }
    /* OSINE791- RSIS 69 - Fin  */
    return{
        constructor: constructor,
        descargaDocumentosObligacionesURL: descargaDocumentosObligacionesURL,
        grillaObligaciones: grillaObligaciones,
        registrarDatosSupervision: registrarDatosSupervision,
        registrarPersonaAtiende: registrarPersonaAtiende,
        ejecutarNoSupervision: ejecutarNoSupervision,
        personaSeIdentifico: personaSeIdentifico,
        limpiarObligacionBusqueda: limpiarObligacionBusqueda,
        HabilitarCajasTexto: HabilitarCajasTexto,
    };
})();
/* OSINE791- RSIS 69 - Inicio  */
function BloquearCajasTexto(){
          $('#fechaInicioRS').attr("disabled", "disabled");  
          $('#horaInicioRS').attr("disabled", "disabled");
          $('#fechaFinRS').attr("disabled", "disabled");
          $('#horaFinRS').attr("disabled", "disabled");
          $('#txtActaProbatoriaRS').attr("disabled", "disabled");
          $('#txtCartaVisitaRS').attr("disabled", "disabled");
          $('#txtObservacionRS').attr("disabled", "disabled");
          $('#btnAdjuntoSupervision').attr("disabled", "disabled");
          $('#btnGuardarDatosSupervision').attr("disabled", "disabled");
          $('#btnGuardarDatosSupervision').removeClass('btn-azul');
          $('#btnAdjuntoSupervision').removeClass('btn-azul');
          
          
          $('#chkIdentificaRS,#slctTipoDocumentoRS,#txtNumeroDocumentoRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#slctCargoAtiendeRS,#txtObservacionPersonaAtiendeRS').attr("disabled", "disabled");
          $('#slctTipoDocumentoRS,#txtNumeroDocumentoRS,#slctCargoAtiendeRS,#txtApellidoPaternoRS,#txtApellidoMaternoRS,#txtNombresRS,#txtObservacionPersonaAtiendeRS').removeAttr("validate");
            //limpiamos valores
          $('#btnBuscarPersonaAtiende').css('display', 'none');
          $('#btnGuardarPersonaAtiende').attr("disabled", "disabled");
          $('#btnGuardarPersonaAtiende').removeClass('btn-azul');
          
          $('#txtCodigoBase,#cmbClasificacion,#cmbCriticidad,#cmbCumple,#cmbRegistrado,#txtDescripcionObli,#btnBuscarObligaciones,#btnLimpiar,#btnOblIncumplida').attr("disabled", "disabled");
          $('#btnBuscarObligaciones,#btnLimpiar,#btnOblIncumplida').removeClass('btn-azul');
          $('span[class="obligatorioDS"]').css('display', 'inline');
          
          $('#btnGuardarPersonaAtiende').attr("disabled", "disabled");
          $('#btnGuardarPersonaAtiende').removeClass('btn-azul');
          
          $('#btnGenerar').attr("disabled", "disabled");
}
/* OSINE791- RSIS 69 - Fin  */
/* OSINE791- RSIS 69 - Inicio  */
function validarDataInicio() {
    if($('#idSupervision').val() =='' || $('#idSupervision').val() == null){
      $('#radioSiSupervision').removeAttr("checked", "checked");
      //$('#radioSiSupervision').attr("disabled", "disabled");
      $('#radioNoSupervision').removeAttr("disabled");
      $('#radioSiSupervision').removeAttr("disabled");
      
    }
    if($('#flagSupervision').val() == constant.estado.activo) {
        $("#divResultadoSupervisionRS").css('display', 'block');   
        $("#divMotivoNoSuprvsnRS").css('display', 'none');
        $("#slctResultadoSupervisionRS").attr("validate", "[O]");     
    }
    if($('#flagSupervision').val() == "") {
         BloquearCajasTexto();
         //Ajuste checkear radioSiSupervision por defecto.
         primera_vez=true;
         $('#radioSiSupervision').trigger('click');
    }
    if (iteracion == constant.iteracion.segunda) {
        $("#divResultadoSupervisionRSAnt").css('display', 'block');
        $("#slctResultadoSupervisionRSAnt").attr("disabled", "disabled");
        var flagincumpleAnt = $("#flagCumplimientoPrevioAnt").val();
        $("#slctResultadoSupervisionRSAnt").val(flagincumpleAnt);
        $("#divResultadoSupervisionRS").css('display', 'none');
        $('#slctResultadoSupervisionRS').removeAttr("validate");
    }
}
/* OSINE791- RSIS 69 - Fin  */
$(function () {
    boton.closeDialog();
    coSupeSupe.constructor();
    /* OSINE791- RSIS 69 - Inicio  */
    validarDataInicio();
    /* OSINE791 - RSIS 69 - Fin  */

    /* OSINE_MAN-001 - Inicio */
    $( "#btnGuardarPersonaAtiende" ).attr("disabled", true);
    $("#btnGuardarPersonaAtiende").addClass('btn-opaco');
    $( "#btnGuardarDatosSupervision" ).attr("disabled", true);
    $("#btnGuardarDatosSupervision").addClass('btn-opaco');
    /* OSINE_MAN-001 - Fin */
    
});


