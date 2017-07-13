/**
* Resumen		
* Objeto		: evaluacion.js
* Descripción		: JavaScript donde se maneja las acciones de la pestaña de evaluaciones del Jefe de Unidad ó Regional.
* Fecha de Creación	: 25/03/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Hernan Torres Saenz
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-480  |  11/05/2016   |   Hernan Torres Saenz        |     Agregar criterios al filtro de búsqueda en la sección asignaciones,evaluación y notificación/archivado 
* OSINE_791      |  26/08/2016   |   Gullet Alvites Pisco       |     Habilitar los link de estados a partir de la funcion fxLinkGrilla y el metodo habilitarEstados 
*                                                                     que se encuentran en general.js
*        
*        
*                
*/

$(function() {
    espeEvalEval.procesarGridEvaluacion();
});
//jefe/evaluacion/evaluacion.js
var espeEvalEval={
    procesarGridEvaluacion:function(flg_load) {
        if(flg_load === undefined){flg_load=1;}
        var nombres = ['idExpe','flagOrigen','idTramite','idEtapTram','idProcTram','idProceso','idObliTipo','idUnidSupe',
                       'ID UNIDAD SUPERVISADA','COD. UNIDAD SUPERVISADA','NOMBRE UNIDAD SUPERVISADA',
                       'ID TITULAR MINERO','COD. TITULAR MINERO','NOMBRE TITULAR MINERO',
                       'C&Oacute;DIGO OSINERGMIN',
                       'RAZ&Oacute;N SOCIAL','idEmprSupe','ruc','tipoDocumentoIdentidad','nroIdentificacion',
                       'idOrdeServ','FECHA OS', 'N&Uacute;MERO OS','IdTipoAsig','fechaCreacionOS','N&Uacute;MERO EXPEDIENTE','fechCreaSige','idFlujSige','FLUJO SIGED','ASUNTO SIGED',
                       'ESTRATO',
                       'idLoca','nombCompArmaLo','idSupeEmpr','razoSociSE','Obs','Iter.', 
                       'flagConfirmaTipoAsignacion', 'flagEvaluaTipoAsignacion', 'fechaHoraAnalogicaCreacionOS'];
                   var columnas = [
                       {name: "idExpediente", hidden:true},
                       {name: "flagOrigen", hidden:true},
                       {name: "tramite.idTramite", hidden:true},
                       {name: "tramite.idEtapa", hidden:true},
                       {name: "tramite.idProceso", hidden:true},
                       {name: "proceso.idProceso", hidden:true},
                       {name: "obligacionTipo.idObligacionTipo", hidden:true},
                       {name: "unidadSupervisada.idUnidadSupervisada", hidden:true},
                       /* OSINE_SFS-1344 - Inicio */
                       {name: "unidadSupervisadaGSM.idDmUnidadSupervisada",hidden:true},//ID UNIDAD SUPERVISADA
                       {name: "unidadSupervisadaGSM.codigoUnidadSupervisada", width: 110, sortable: false, align: "left"},//CODIGO UNIDAD SUPERVISADA
                       {name: "unidadSupervisadaGSM.nombreUnidadSupervisada", width: 110, sortable: false, align: "left"},//NOMBRE UNIDAD SUPERVISADA
                       {name: "titularMinero.idTitularMinero",hidden:true},//ID TITULAR MINERO
                       {name: "titularMinero.codigoTitularMinero", width: 110, sortable: false, align: "left"},//CODIGO TITULAR MINERO
                       {name: "titularMinero.nombreTitularMinero", width: 110, sortable: false, align: "left"},//NOMBRE TITULAR MINERO
                       /* OSINE_SFS-1344 - Fin */
                       {name: "unidadSupervisada.codigoOsinergmin", width: 110, sortable: false, align: "left", hidden:true},
                       {name: "empresaSupervisada.razonSocial", width: 260, sortable: false, align: "left", hidden:true},
                       {name: "empresaSupervisada.idEmpresaSupervisada", hidden:true},
                       {name: "empresaSupervisada.ruc", hidden:true},
                       {name: "empresaSupervisada.tipoDocumentoIdentidad.descripcion", hidden:true},
                       {name: "empresaSupervisada.nroIdentificacion", hidden:true},
                       {name: "ordenServicio.idOrdenServicio", hidden:true},
                       {name: "ordenServicio.fechaCreacion", width: 80, sortable: false, align: "left",formatter:fecha},//FECHA OS
                       {name: "ordenServicio.numeroOrdenServicio", width: 130, sortable: false, align: "center"},//NUMERO OS
                       {name: "ordenServicio.idTipoAsignacion",hidden:true},
                       {name: "ordenServicio.fechaCreacionOS", hidden:true},
                       {name: "numeroExpediente", width: 100, sortable: false, align: "left"},//NUMERO EXPEDIENTE
                       {name: "fechaCreacionSiged", hidden:true,formatter:fecha_hora},
                       {name: "flujoSiged.idFlujoSiged", hidden:true},
                       {name: "flujoSiged.nombreFlujoSiged", width: 190, sortable: false, align: "left"},//FLUJO SIGED
                       {name: "asuntoSiged", width: 200, sortable: false, align: "left"},//ASUNTO SIGED
                       /* OSINE_SFS-1344 - Inicio */
                       {name: "unidadSupervisadaGSM.idEstrato.descripcion", width: 110, sortable: false, align: "left"},//ESTRATO
                       /* OSINE_SFS-1344 - Fin */
                       {name: "ordenServicio.locador.idLocador", hidden:true},
                       {name: "ordenServicio.locador.nombreCompletoArmado", hidden:true},
                       {name: "ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa", hidden:true},
                       {name: "ordenServicio.supervisoraEmpresa.razonSocial", hidden:true},
                       {name: "ordenServicio.motivoReasignacion",width: 30, sortable: false, align: "center",formatter:"mostrarObservacion"},//OBS.
                       {name: "ordenServicio.iteracion",width: 30, sortable: false, align: "center",hidden:true},
                       {name: "ordenServicio.flagConfirmaTipoAsignacion",width: 30, sortable: false, align: "center", hidden:true},
                       {name: "flagEvaluaTipoAsignacion",width: 30, sortable: false, align: "center", hidden:true},
                       {name: "ordenServicio.fechaHoraAnalogicaCreacionOS",width: 30, sortable: false, align: "center", hidden:true}                       
                   ];
        $("#gridContenedorEvaluacion").html("");
        var grid = $("<table>", {
            "id": "gridEvaluacion"
        });
        var pager = $("<div>", {
            "id": "paginacionEvaluacion"
        });
        $("#gridContenedorEvaluacion").append(grid).append(pager);
              
        var identificadorEstado= "'"+constant.identificadorEstado.osSupervisado+"','"+constant.identificadorEstado.osRevisado+"','"+constant.identificadorEstado.osAprobado+"'";   //mdiosesf
        /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Inicio */
        //if($('#slctEstadoProceso').val()!=null
        if($('#slctEstadoProceso').val()!=null && $('#slctEstadoProceso').val()!=''){
        /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin */
        	identificadorEstado= "'"+$('#slctEstadoProceso').val()+"'";
        } //mdiosesf
        grid.jqGrid({
            url: baseURL + "pages/expedienteGSM/findExpediente",
            datatype: "json",
            mtype : "POST",
            postData: {
            	  idPersonal:$('#idPersonalSesion').val(),
                  nombreRol:$('#rolSesion').val(),
                  identificadorEstado:constant.identificadorEstado.expAsignado,
                  cadIdentificadorEstadoOrdenServicio:"'"+constant.identificadorEstado.osRegistro+"'",
                  numeroExpediente:$('#txtNumeExpeBusqExpe').val(),
                  numeroOrdenServicio:$('#txtNumeOSBusqExpe').val(),
                  nombreTitularMinero:$('#txtNomTitMinOSBusqExpe').val(),
                  codigoOsinergmin:$('#txtCodigoOsiBusqExpe').val(),
                  razonSocial:$('#txtNomUnidSupOSBusqExpe').val()
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
                var row=$('#gridEvaluacion').getRowData(rowid); 
              //  $('#linkAprobarExpeEval').css('display','none');
                
                var link = fxLinkGrilla.habilitarEstados(row['ordenServicio.estadoProceso.identificadorEstado'], '',constant.rol.jefe);
                $('#'+link+'').css('display','');
                $('#linkAprobarExpeEval').attr('onClick', 'common.ordenServicio.abrirAprobar("' + rowid + '","gridEvaluacion")');
               
            },
            loadComplete: function(data) {
                $('#contextMenuEvaluacion').parent().remove();
                $('#divContextMenuEvaluacion').html("<ul id='contextMenuEvaluacion'>"
                        + "<li> <a id='linkAprobarExpeEval' data-icon='ui-icon-check' title='Aprobar'>Aprobar</a></li>"
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