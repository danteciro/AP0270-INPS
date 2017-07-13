/**
* Resumen		
* Objeto		: comentarioIncumplimiento.js
* Descripción		: comentarioIncumplimiento
* Fecha de Creación	: 07/09/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: JPIRO
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  07/09/2016   |   Luis García Reyna          |     Consultar comenatrio Infraccion - Escenario Incumplimiento.    
* OSINE_MAN_DSR_0037|19/06/2017  |   Carlos Quijano Chavez::ADAPTER      |     Agregar comentario opcional a los comentarios ya predefinidos
*/

//common/supervision/dsr/comentarioIncumplimiento/comentarioIncumplimiento.js
var coSupeDsrComIncComInc={
    procesarGridComentarioIncumplimiento: function() {
        /* OSINE_SFS-791 - RSIS 16 - Inicio */
        var ocultaColumna = false;
        if ($('#modoSupervisionCI').val() == constant.modoSupervision.consulta) {
            ocultaColumna = true;
        }
        /* OSINE_SFS-791 - RSIS 16 - Fin */
        var nombres = ['','','','idComentarioIncumplimiento','COMENTARIO','countCoCo','EDITAR'];
        var columnas = [
            /* OSINE_SFS-791 - RSIS 16 - Inicio */
            {name: 'chkComenIncum',width:21, hidden:ocultaColumna,sortable: false, align:"center",formatter: coSupeDsrComIncComInc.checkboxComentIncum},
            /* OSINE_SFS-791 - RSIS 16 - Fin */
            {name: "flagComentDetSupEnEsceIncdo",hidden:true},
            {name: "idComentarioDetSupervision",hidden:true},
            {name: "idComentarioIncumplimiento",hidden:true},
            /* OSINE_SFS-791 - RSIS 16 - Inicio */
            {name: "descripcion", width: 620, sortable: false, align: "left"},
            /* OSINE_SFS-791 - RSIS 16 - Fin */
            {name: "countComentarioComplemento",hidden:true},
            {name: 'btnEditar',width:40,hidden:ocultaColumna, sortable: false, align:"center",formatter: coSupeDsrComIncComInc.btnEditarComentIncum}
        ];
        $("#gridContenedorComentarioIncumplimiento").html("");
        var grid = $("<table>", {
            "id": "gridComentarioIncumplimiento"
        });
        var pager = $("<div>", {
            "id": "paginacionComentarioIncumplimiento"
        });

        $("#gridContenedorComentarioIncumplimiento").append(grid).append(pager);
        grid.jqGrid({
            url: baseURL + "pages/comentarioIncumplimiento/findComentarioIncumplimiento",
            datatype: "json",
            postData: {
                idEsceIncumplimiento: $('#idEsceIncumplimientoCI').val(),
                idDetalleSupervision:$('#idDetalleSupervisionCI').val(),
                idInfraccion:$('#idInfraccionCI').val(),
                flagBuscaComentDetSup:constant.estado.activo
            },
            hidegrid: false,
            emptyrecords: "No se encontraron resultados",
            loadtext: "Cargando",
            colNames: nombres,
            colModel: columnas,
            height: 95,
            width: "auto",
            viewrecords: true,
            caption: "",
            jsonReader: {
                root: "filas",
                page: "pagina",
                total: "total",
                records: "registros",
                repeatitems: false,
                id: "idComentarioIncumplimiento"
            },
            onSelectRow: function(rowid, status) {
                grid.resetSelection();
            },
            loadComplete: function(data) {
                
                if($('#modoSupervisionCI').val()==constant.modoSupervision.consulta){
                    var lista = jQuery("#gridComentarioIncumplimiento").getDataIDs();
                    for(i=0;i<lista.length;i++){
                        rowData=jQuery("#gridComentarioIncumplimiento").getRowData(lista[i]);
                        rowData.flagComentDetSupEnEsceIncdo   
                        if (rowData.flagComentDetSupEnEsceIncdo=='' || rowData.flagComentDetSupEnEsceIncdo !=1) {
                            $('#gridComentarioIncumplimiento').jqGrid('delRowData',lista[i]);
                        }
                    }
                }
                //Validacion para no modificar comentarios despues de generar los resultados.
                if($('#EstadoResultadoSupervision').val()!=""){
                    $("#gridContenedorComentarioIncumplimiento").find('input').attr('disabled','disabled')
                    $("#btnEditarComentIncumpl").css('display','none');
                }     
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    checkboxComentIncum: function(cellvalue, options, rowdata) {
        var id=rowdata.idComentarioIncumplimiento,html = '';
        var checked=(rowdata.flagComentDetSupEnEsceIncdo!=null && rowdata.flagComentDetSupEnEsceIncdo==constant.estado.activo)?'checked':'';
        if (id != undefined && id != null && id != ''){
            html='<input id="idComenIncum'+id+'" '+checked+' type="checkbox" name="" value="" onclick="coSupeDsrComIncComInc.eventChkComentIncum(this,'+rowdata.countComentarioComplemento+','+rowdata.idComentarioDetSupervision+')">'+
                '<label for="idComenIncum'+id+'" class="checkbox"></label>';
        }
        return html;
    },
    btnEditarComentIncum: function(cellvalue, options, rowdata) {
        var id=rowdata.idComentarioIncumplimiento,html = '';
        if (rowdata.countComentarioComplemento!=null && rowdata.countComentarioComplemento>0 && 
            rowdata.flagComentDetSupEnEsceIncdo!=null && rowdata.flagComentDetSupEnEsceIncdo==constant.estado.activo
        ){
            html='<div id="btnEditarComentIncumpl" class="ilb cp btnGrid" onclick="coSupeDsrComIncComInc.abrirFrmComentarioComplemento('+ id +','+rowdata.idComentarioDetSupervision+');"><span class="ui-icon ui-icon-pencil"></span></div>'            
        }
        return html;
    },
    eventChkComentIncum:function(obj,countComentCompl,idComentarioDetSupervision){
        if($(obj).is(':checked')){
            if(countComentCompl>0){
                coSupeDsrComIncComInc.abrirFrmComentarioComplemento($(obj).attr('id').replace('idComenIncum',''),idComentarioDetSupervision);
            }else{
                coSupeDsrComIncComInc.asignaComentarioDetSupervision($(obj).attr('id').replace('idComenIncum',''));
            }
        }else{
            coSupeDsrComIncComInc.desasignaComentarioDetSupervision($(obj).attr('id').replace('idComenIncum',''),idComentarioDetSupervision);
        }
    },
    asignaComentarioDetSupervision:function(idComentarioIncumplimiento){
        $.ajax({
            url: baseURL + "pages/comentarioIncumplimiento/asignaComentarioDetSupervision",
            type: 'post',
            async: false,
            data: {
                'comentarioIncumplimiento.idComentarioIncumplimiento':idComentarioIncumplimiento,
                'detalleSupervision.idDetalleSupervision':$('#idDetalleSupervisionCI').val(),
                idEsceIncumplimiento:$('#idEsceIncumplimientoCI').val()
            },
            success: function(data) {
                loading.close();
                if (data.resultado == '0') {
                    mensajeGrowl("success", constant.confirm.save); 
                    coSupeDsrComIncComInc.procesarGridComentarioIncumplimiento();
                    coSupeDsrOblOblDet.procesarGridOblDtlEscenarioIncump();
                } else {
                    mensajeGrowl("error", data.mensaje);
                }
            },
            error: errorAjax
        });
    },
    desasignaComentarioDetSupervision:function(idComentarioIncumplimiento,idComentarioDetSupervision){
        if(idComentarioDetSupervision!=null){
            $.ajax({
                url: baseURL + "pages/comentarioIncumplimiento/desasignaComentarioDetSupervision",
                type: 'post',
                async: false,
                data: {
                    'comentarioIncumplimiento.idComentarioIncumplimiento':idComentarioIncumplimiento,
                    'detalleSupervision.idDetalleSupervision':$('#idDetalleSupervisionCI').val(),
                    idEsceIncumplimiento:$('#idEsceIncumplimientoCI').val(),
                    idComentarioDetSupervision:idComentarioDetSupervision
                },
                success: function(data) {
                    loading.close();
                    if (data.resultado == '0') {
                        mensajeGrowl("success", constant.confirm.anular);
                        coSupeDsrComIncComInc.procesarGridComentarioIncumplimiento();
                        coSupeDsrOblOblDet.procesarGridOblDtlEscenarioIncump();
                    } else {
                        mensajeGrowl("error", data.mensaje);
                    }
                },
                error: errorAjax
            });
        }
    },
    abrirFrmComentarioComplemento:function(idComentarioIncumplimiento,idComentarioDetSupervision){
        $.ajax({
            url: baseURL + "pages/comentarioComplemento/abrirFrm",
            type: 'post',
            async: false,
            data: {
                'comentarioIncumplimiento.idComentarioIncumplimiento':idComentarioIncumplimiento,
                'detalleSupervision.idDetalleSupervision':$('#idDetalleSupervisionCI').val(),
                idEsceIncumplimiento:$('#idEsceIncumplimientoCI').val(),
                idComentarioDetSupervision:(idComentarioDetSupervision!=null)?idComentarioDetSupervision:'',
                idInfraccion:$('#idInfraccionCI').val(),
                codigoOsinergmin:$('#codigoOsinergminCI').val()
            },
            beforeSend: loading.open,
            success: function(data) {
                loading.close();
                $("#dialogComentarioComplemento").html(data);
                $("#dialogComentarioComplemento").dialog({
                    resizable: false,
                    draggable: true,
                    height: "auto",
                    width: "auto",
                    modal: true,
                    title: "COMPLEMENTO",
                    closeText: "Cerrar",
                    position: 'top+50',
                    close:function(){
                        coSupeDsrComIncComInc.procesarGridComentarioIncumplimiento();
                        coSupeDsrOblOblDet.procesarGridOblDtlEscenarioIncump();
                    }
                });
            },
            error: errorAjax
        });
    },
    initEventos : function(){
    	
    }
}
/* OSINE_MAN_DSR_0037 - Inicio */
$("#guardarComentarioOpcional").click(function() {
	
	if($("#guardarComentarioOpcional").html()=="Guardar")
	{
		 $.ajax({
	    	 url: baseURL + "pages/comentarioIncumplimiento/setComentarioIncumplimientoOpcional",
	         type: 'post',
	         async: false,
	         dataType: 'json',
	         data: { 	idComentarioDetalleSupervisionOpcional:  $('#idComentarioOpcionalCI').val(),
	        	 		idEscenarioIncumplido: $('#idEsceIncumplimientoCI').val(),
	        	 		idDetalleSupervision:$('#idDetalleSupervisionCI').val(),
	        	 		descripcion: $('#comentarioOpcional').val()
	         },
	         beforeSend: loading.open,
	         success: function(data) {
	             loading.close();
	             	$("#guardarComentarioOpcional").html("Editar");
	     			$("#comentarioOpcional").prop("disabled",true);
	         },
	           error: function(jqXHR) {
	                errorAjax(jqXHR);
	         }
		 });
		
	}
	else if($("#guardarComentarioOpcional").html()=="Editar")
	{
		$("#guardarComentarioOpcional").html("Guardar");
		$("#comentarioOpcional").prop("disabled",false);
	}
});
/* OSINE_MAN_DSR_0037 - Fin */
$(function() {
    coSupeDsrComIncComInc.initEventos();
    coSupeDsrComIncComInc.procesarGridComentarioIncumplimiento();
    boton.closeDialog();
});
