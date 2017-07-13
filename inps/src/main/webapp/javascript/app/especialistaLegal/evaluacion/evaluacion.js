/**
* Resumen		
* Objeto		: evaluacion.js
* Descripción		: evaluacion
* Fecha de Creación	: 25/03/2016  
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-480  |  11/05/2016   |   Hernan Torres Saenz        |     Agregar criterios al filtro de búsqueda en la sección asignaciones,evaluación y notificación/archivado 
* OSINE_791      |  26/08/2016   |   Gullet Alvites Pisco       |     Habilitar los link de estados a partir de la funcion fxLinkGrilla y el metodo habilitarEstados 
*                                                                     que se encuentran en general.js
*        
*/

$(function() {
    espeEvalEval.procesarGridEvaluacion();
});
//especialista/evaluacion/evaluacion.js
var espeEvalEval={
    procesarGridEvaluacion:function(flg_load) {
        if(flg_load === undefined){flg_load=1;}
        var nombres = ['idExpe','flagOrigen','idTramite','idEtapTram','idProcTram','idProceso','idObliTipo','idUnidSupe','C&Oacute;DIGO OSINERGMIN',
                        'RAZ&Oacute;N SOCIAL','ruc',
                       'idOrdeServ','FECHA OS', 'N&Uacute;MERO OS','TIPO ASIGNACI&Oacute;N','fechaCreacionOS','N&Uacute;MERO EXPEDIENTE','fechCreaSige','idFlujSige','FLUJO SIGED','ASUNTO SIGED',
                       // htorress - RSIS 16 - Inicio
                       //'idLoca','nombCompArmaLo','idSupeEmpr','razoSociSE','ESTADO','IDENTIFICADOR','Iter.'];
                       'idLoca','nombCompArmaLo','idSupeEmpr','razoSociSE','ESTADO','IDENTIFICADOR','Obs','Iter.','',''];
                       // htorress - RSIS 16 - Fin
        var columnas = [
                       {name: "idExpediente", hidden:true},
                       {name: "flagOrigen", hidden:true},
                       {name: "tramite.idTramite", hidden:true},
                       {name: "tramite.idEtapa", hidden:true},
                       {name: "tramite.idProceso", hidden:true},
                       {name: "proceso.idProceso", hidden:true},
                       {name: "obligacionTipo.idObligacionTipo", hidden:true},
                       {name: "unidadSupervisada.idUnidadSupervisada", hidden:true},
                       {name: "unidadSupervisada.codigoOsinergmin", width: 80, sortable: false, align: "left"},
                       {name: "unidadSupervisada.nombreUnidad", width: 235, sortable: false, align: "left"},
                       {name: "unidadSupervisada.ruc", hidden:true},
//                       {name: "empresaSupervisada.razonSocial", width: 235, sortable: false, align: "left"},
//                       {name: "empresaSupervisada.idEmpresaSupervisada", hidden:true},
//                       {name: "empresaSupervisada.ruc", hidden:true},
//                       {name: "empresaSupervisada.tipoDocumentoIdentidad.descripcion", hidden:true},
//                       {name: "empresaSupervisada.nroIdentificacion", hidden:true},
                       {name: "ordenServicio.idOrdenServicio", width: 10, hidden:true},
                       {name: "ordenServicio.fechaCreacion", width: 60, sortable: false, align: "left",formatter:fecha},
                       {name: "ordenServicio.numeroOrdenServicio", width: 130, sortable: false, align: "left"},
                       {name: "ordenServicio.idTipoAsignacion",hidden:true},
                       {name: "ordenServicio.fechaCreacionOS", hidden:true},
                       {name: "numeroExpediente", width: 90, sortable: false, align: "left"},
                       {name: "fechaCreacionSiged", hidden:true,formatter:fecha_hora},
                       {name: "flujoSiged.idFlujoSiged", hidden:true},
                       {name: "flujoSiged.nombreFlujoSiged", width: 190, sortable: false, align: "left"},
                       {name: "asuntoSiged", width: 200, sortable: false, align: "left"},
                       {name: "ordenServicio.locador.idLocador", hidden:true},
                       {name: "ordenServicio.locador.nombreCompletoArmado", hidden:true},
                       {name: "ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa", hidden:true},
                       {name: "ordenServicio.supervisoraEmpresa.razonSocial", hidden:true},
                       {name: "ordenServicio.estadoProceso.nombreEstado",width: 70, sortable: false, align: "left" },                       
                       {name: "ordenServicio.estadoProceso.identificadorEstado",hidden: true },
                       // htorress - RSIS 16 - Inicio
                       {name: "ordenServicio.motivoReasignacion",width: 30, sortable: false, align: "center",formatter:"mostrarObservacion"},
                       // htorress - RSIS 16 - Fin
                       {name: "ordenServicio.iteracion",width: 30, sortable: false, align: "center"},
                       {name: "obligacionSubTipo.idObligacionSubTipo",hidden:true},
                       /* OSINE791 - RSIS 21 - Inicio */ 
                       {name: "ordenServicio.osIdentificadorEstadoSgt",hidden:true},
                       /* OSINE791 - RSIS 21 - Fin */ 
        ];
        $("#gridContenedorEvaluacion").html("");
        var grid = $("<table>", {
            "id": "gridEvaluacion"
        });
        var pager = $("<div>", {
            "id": "paginacionEvaluacion"
        });
        $("#gridContenedorEvaluacion").append(grid).append(pager);

        grid.jqGrid({
            url: baseURL + "pages/expediente/findExpediente",
            datatype: "json",
            mtype : "POST",
            postData: {
                idPersonal:$('#idPersonalSesion').val(),
                identificadorEstado:constant.identificadorEstado.expAsignado,
                cadIdentificadorEstadoOrdenServicio:"'"+constant.identificadorEstado.osSupervisado+"','"+constant.identificadorEstado.osRevisado+"','"+constant.identificadorEstado.osAprobado+"'",
                numeroExpediente:$('#txtNumeExpeBusqExpe').val(),
                numeroOrdenServicio:$('#txtNumeOSBusqExpe').val(),
                razonSocial:$('#txtRazoSociBusqExpe').val(),
                codigoOsinergmin:$('#txtCodigoOsiBusqExpe').val(),
                tipoEmpresaSupervisora:$('#slctTipoEmprSupeBusqExpe').val()!=null?$('#slctTipoEmprSupeBusqExpe').find(':checked').attr('codigo'):null,
                identificadorEmpresaSupervisora:$('#slctEmprSupeOSBusqExpe').val()==null||$('#slctEmprSupeOSBusqExpe').val().trim()==''?-1:$('#slctEmprSupeOSBusqExpe').val().trim()==''?-1:$('#slctEmprSupeOSBusqExpe').val(),
                identificadorOpcion:constant.identificadorBandeja.espEvaluacion
                /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Inicio */
                , numeroRegistroHidrocarburo:$('#txtNumeroRH').val()==null||$('#txtNumeroRH').val().trim()==''?'':$('#txtNumeroRH').val()
                , idDepartamento:$('#slctDepartamento').val()==null||$('#slctDepartamento').val().trim()==''?'':$('#slctDepartamento').val()
                , idProvincia:$('#slctProvincia').val()==null||$('#slctProvincia').val().trim()==''?'':$('#slctProvincia').val()
                , idDistrito:$('#slctDistrito').val()==null||$('#slctDistrito').val().trim()==''?'':$('#slctDistrito').val()
                , idPeticion:$('#slctPeticion').val()==null||$('#slctPeticion').val().trim()==''?'':$('#slctPeticion').val()
                /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin */
            },
            hidegrid: false,
            rowNum: constant.rowNumPrinc,
            pager: "#paginacionEvaluacion",
            emptyrecords: "No se encontraron resultados",
            loadtext: "Cargando",
            colNames: nombres,
            colModel: columnas,
            height: "auto",
            viewrecords: true,
            caption: "",
            jsonReader: {
                root: "filas",
                page: "pagina",
                total: "total",
                records: "registros",
                repeatitems: false,
                id: "idExpediente"
            },
            onSelectRow: function(rowid, status) {
                grid.resetSelection();
            },
            onRightClickRow: function(rowid) {
            	// htorress - RSIS 18 - Inicio
            	$('#contextMenuEvaluacion').parent().css('opacity','1');
            	// htorress - RSIS 18 - Fin
                var row=$('#gridEvaluacion').getRowData(rowid);
                $('#linkRevisarExpeEval,#linkAprobarExpeEval,#linkNotificarExpeEval').css('display','none');
                /* OSINE791 - RSIS 21 - Inicio */
//                if(row['ordenServicio.estadoProceso.identificadorEstado']==constant.identificadorEstado.osSupervisado){
//                    $('#linkRevisarExpeEval').css('display','');
//                }else if(row['ordenServicio.estadoProceso.identificadorEstado']==constant.identificadorEstado.osRevisado){
                /* OSINE791 - RSIS 21 - Fin */
                	// htorress - RSIS 18 - Inicio
                /* OSINE791 - RSIS 21 - Inicio */
//                	$('#contextMenuEvaluacion').parent().css('opacity','0');
                /* OSINE791 - RSIS 21 - Fin */
                    //$('#linkAprobarExpeEval').css('display','');
                	// htorress - RSIS 18 - Fin
                /* OSINE791 - RSIS 21 - Inicio */
//                }else if(row['ordenServicio.estadoProceso.identificadorEstado']==constant.identificadorEstado.osAprobado){
//                    $('#linkNotificarExpeEval').css('display','');
//                }
//                var link = fxLinkGrilla.habilitarEstados(row['ordenServicio.estadoProceso.identificadorEstado'], '',constant.rol.especialista);
                var link = fxLinkGrilla.habilitarEstados(row['ordenServicio.estadoProceso.identificadorEstado'], row['ordenServicio.osIdentificadorEstadoSgt'],constant.rol.especialista);
                if(link!=''){$('#'+link+'').css('display','');}
                //Mostrar/Ocultar
                fxLinkGrilla.mostrarOcultarMenu('contextMenuEvaluacion');
                /* OSINE791 - RSIS 21 - Fin */
                $('#linkRevisarExpeEval').attr('onClick', 'common.ordenServicio.abrirRevisar("' + rowid + '","gridEvaluacion")');
                $('#linkAprobarExpeEval').attr('onClick', 'common.ordenServicio.abrirAprobar("' + rowid + '","gridEvaluacion")');
                $('#linkNotificarExpeEval').attr('onClick', 'common.ordenServicio.abrirNotificar("' + rowid + '","gridEvaluacion")');
            },
            loadComplete: function(data) {
                $('#contextMenuEvaluacion').parent().remove();
                $('#divContextMenuEvaluacion').html("<ul id='contextMenuEvaluacion'>"
                        + "<li> <a id='linkRevisarExpeEval' data-icon='ui-icon-pencil' title='Revisar'>Revisar</a></li>"
                        + "<li> <a id='linkAprobarExpeEval' data-icon='ui-icon-check' title='Aprobar'>Aprobar</a></li>"
                        + "<li> <a id='linkNotificarExpeEval' data-icon='ui-icon-mail-open' title='Notificar'>Notificar</a></li>"
                    + "</ul>");
                $('#contextMenuEvaluacion').puicontextmenu({
                    target: $('#gridEvaluacion')
                });
                $('#contextMenuEvaluacion').parent().css('width','182px');
                cambioColorTrGrid($('#gridEvaluacion'));
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
//        grid.jqGrid('navGrid','#paginacionEvaluacion',{edit:false,add:false,del:false,search:false,view:false,refresh:false});
//        grid.jqGrid('navButtonAdd','#paginacionEvaluacion',{caption:"",buttonicon:"ui-icon-search",title:"Buscar Expediente",
//            onClickButton:function(){
//                common.abrirBusquedaExpediente(espeEvalEval.procesarGridEvaluacion,constant.bandeja.espEvaluacion);
//            }
//        });
    }
};
//htorress - RSIS 16 - Inicio
jQuery.extend($.fn.fmatter, {
	mostrarObservacion: function(cellvalue, options, rowdata) {
        var motivoReasignacion=rowdata.ordenServicio.motivoReasignacion;
        var html = '';
        if (motivoReasignacion != null && motivoReasignacion != '' && motivoReasignacion!=undefined){       
            html = '<div class="ilb cp" title="Observación" onclick="confirmer.open(\''+motivoReasignacion+'\',\'\',{textAceptar:\'\',textCancelar:\'Cerrar\',title:\'Observación\'});"><img class="vam" width="17" height="18" src="'+baseURL+'images/cuaderno.png"></div>';
        }
        return html;
    }
});
//htorress - RSIS 16 - Fin