/**
* Resumen		
* Objeto		: ordenServicioSupervisorDevolver.js
* Descripción		: JavaScript donde se maneja las acciones de la pestaña supervisado del Supervisor para Devolver Asignaciones.
* Fecha de Creación	: 17/05/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Luis García Reyna
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                         |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-480     18/05/2016      Luis García Reyna           Crear la interfaz "devolver asignaciones" de acuerdo a especificaciones - Transportar rubro (Actividad) asociado por expediente
* OSINE_SFS-480     23/05/2016      Luis García Reyna           Implementar la funcionalidad de devolución de asignaciones de acuerdo a especificaciones.
*
*    
*/

//common/ordenServicio/OrdenServicioSupervisorDevolver.js
var coOrSeOrSeSuDev={
    procesarGridOrdeServSupeDev:function() {
        var nombres = ['idOrdSer','N&Uacute;MERO ORDEN DE SERVICIO','idExpediente','N&UacuteMERO EXPEDIENTE','AsuntoSiged','N° Reg. Hidrocarburo',
            'RAZ&Oacute;N SOCIAL','UNIDAD OPERATIVA','RUBRO','',''];
        var columnas = [
            {name: "ordenServicio.idOrdenServicio",index:'id',hidden:true},
            {name: "ordenServicio.numeroOrdenServicio", width: 150, sortable: false, align: "center"},
            {name: "idExpediente", sortable: false, hidden:true},
            {name: "numeroExpediente", width: 100, sortable: false, align: "center"},
            {name: "asuntoSiged", sortable: false, hidden:true},
            {name: "unidadSupervisada.nroRegistroHidrocarburo", width: 120, sortable: false, align: "center"},
            {name: "empresaSupervisada.razonSocial", width: 250, sortable: false, align: "center"},
            {name: "unidadSupervisada.nombreUnidad", width: 300, sortable: false, align: "center"},
            {name: "actividad.nombre", width: 150, sortable: false, align: "center"},
            /* OSINE_SFS-480 - lgarciar - RSIS 40 - Inicio */
            {name: 'check',width:21, sortable: false, align:"center",formatter: "checkboxSelecOrdServicio"},
            {name: 'flagMuestral',hidden:true}
            /* OSINE_SFS-480 - lgarciar - RSIS 40 - Inicio */
        ];
        $("#gridContenedorOrdeServSupeDev").html("");
        var grid = $("<table>", {
            "id": "gridOrdeServSupeDev"
        });
        var pager = $("<div>", {
            "id": "paginacionOrdeServSupeDev"
        });
        $("#gridContenedorOrdeServSupeDev").append(grid).append(pager);

        grid.jqGrid({
            url: baseURL + "pages/expediente/findExpediente",
            datatype: "json",
            mtype : "POST",
            postData: {
                idPersonal:$('#idPersonalSesion').val(),
                nombreRol:$('#rolSesion').val(),
                identificadorEstado:constant.identificadorEstado.expAsignado,
                cadIdentificadorEstadoOrdenServicio:"'"+constant.identificadorEstado.osRegistro+"'",
                /* OSINE_SFS-480 - lgarciar - RSIS 40 - Inicio */                
                flagDevolverAsignacion: '1'
                /* OSINE_SFS-480 - lgarciar - RSIS 40 - Fin */                
            },
            hidegrid: false,
            rowNum: constant.rowNum,
            pager: "#paginacionOrdeServSupeDev",
            emptyrecords: "No se encontraron resultados",
            recordtext: "{0} - {1}",
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
                id: "ordenServicio.idOrdenServicio"
            },
            /* OSINE_SFS-480 - lgarciar - RSIS 40 - Inicio */        
            loadComplete: function(data) {
                //agregar check seleccionar todos
                var html='<input id="chkSeleAllVisibles" type="checkbox" name="" value="" onclick="coOrSeOrSeSuDev.selecAllOrdenServicioVisibles(this);"><label for="chkSeleAllVisibles" class="checkbox"></label>';
                $('#gridOrdeServSupeDev_check').html(html);
                $('#gridOrdeServSupeDev_check').css('height','20px');
                $('#gridOrdeServSupeDev_check').removeClass('ui-jqgrid-sortable');                

                //para marcar los ya seleccionados, casos de navegar entre paginas de grilla
                $('#ordServicioSeleccionados').find('div.filaForm').map(function(){
                    $('#idOrdSer'+$(this).attr('id').replace('idOrdSerSele','')).attr('checked',true);
                });
            },
            onSelectRow: function(rowid, status) {
                grid.resetSelection();
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
          
    inicializarComponentes:function(){
        $("#cmbTipoPeticion").val("");
        $("#dvMotivo").show();
        
        $('#cmbTipoPeticion').change(function() {
            if (this.value !== "") {
                $("#dvMotivo").show();
                coOrSeOrSeSuDev.cargarMotivo(this.value);
            } else {
                $("#dvMotivo").show();
            }
        });
    },
    cargarMotivo:function(codigo){
        $.ajax({
            url:baseURL + "pages/maestroColumna/obtenerMotivos", 
            type:'get',
            async:false,
            data:{
                codigo:$("#cmbTipoPeticion").find(':checked').attr("codigo")
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                var html = '<option value="">--Seleccione--</option>';               
                for (var i = 0; i < data.length; i++) {
                    html += '<option codigo="'+data[i].codigo+'" value="'+data[i].idMaestroColumna+'">'+data[i].descripcion+'</option>';
                }
                $('#dvMotivo').html(html);
            },
            error:errorAjax
        });
    },
    selecAllOrdenServicioVisibles:function(obj){
        if($(obj).attr('checked')){
            $('#gridOrdeServSupeDev').find('input').attr('checked',true);
        }else{
            $('#gridOrdeServSupeDev').find('input').attr('checked',false);
        }
        $('#gridOrdeServSupeDev').find('input').map(function(){
            coOrSeOrSeSuDev.selecOrdenServicio(this);
        });
    },
    selecOrdenServicio:function(obj){
        if($(obj).attr('checked')){
            idOrdSerSele=$(obj).attr('id').replace('idOrdSer','');
            if($('#idOrdSerSele'+idOrdSerSele).length=='0'){
                var row=$('#gridOrdeServSupeDev').jqGrid("getRowData",idOrdSerSele);
                var html='<div id="idOrdSerSele'+idOrdSerSele+'" class="filaForm">'+
                        '<input type="text" codigo="idOrdenServicio" name="" value="'+row['ordenServicio.idOrdenServicio']+'" />'+
                        '<input type="text" codigo="idExpediente" name="" value="'+row['idExpediente']+'" />'+
                        '<input type="text" codigo="numeroExpediente" name="" value="'+row['numeroExpediente']+'" />'+
                        '<input type="text" codigo="asuntoSiged" name="" value="'+row['asuntoSiged']+'" />'+
                        '<input type="text" codigo="flagMuestral" name="" value="'+row['flagMuestral']+'" />'+
                    '</div>';
                $('#ordServicioSeleccionados').append(html);
            }
        }else{
            $('#idOrdSerSele'+$(obj).attr('id').replace('idOrdSer','')).remove();
        }
    },
    validaDevolverOrdenesServicio:function(){
        if($('#ordServicioSeleccionados div').length<1){
                mensajeGrowl('warn', "No ha seleccionado orden(es) de servicio a devolver", "");
        }else if($('#devolverOrdenServicio').validateAllForm("#divMensValidaDevolverOrdenServicio")){
            //Si es devolver-EDITAR valida que no existan registros tipo muestral
            var validaEditar=true;
            if($('#cmbTipoPeticion').find(':checked').attr('codigo')==constant.codTipoPeticion.editar &&
               $('#dvMotivo').find(':checked').attr('codigo')!=constant.codMotivoPeticionEditar.cambiar_empresa_supervisora){
                $('#ordServicioSeleccionados').find('[codigo="flagMuestral"]').map(function(){
                    if($(this).val()==constant.estado.activo){
                        var row=$('#gridOrdeServSupeDev').getRowData($(this).parent().children('[codigo="idOrdenServicio"]').val()); 
                        mensajeGrowl('warn',
                            'No se puede Devolver: '+$('#cmbTipoPeticion').find(':checked').text()+'-'+$('#dvMotivo').find(':checked').text()+'.<br>Orden de Servicio <b>'+row['ordenServicio.numeroOrdenServicio']+'</b> es del tipo Seleccion Muestral.');                      
                        validaEditar=false;
                        return false;
                    }
                })
            }
            if(validaEditar){
                confirmer.open("¿Confirma que desea devolver la(s) orden(es) de servicio seleccionada(s)?",
                "coOrSeOrSeSuDev.procesaDevolverOrdenesServicio()");
            }
        }
    },
    armaFormDevolver:function(){
        cont=0;
        $('#ordServicioSeleccionados div').map(function(){
            $(this).find('input').eq(0).attr('name','ordenesServicio['+cont+'].idOrdenServicio')
            $(this).find('input').eq(1).attr('name','ordenesServicio['+cont+'].expediente.idExpediente')
            $(this).find('input').eq(2).attr('name','ordenesServicio['+cont+'].expediente.numeroExpediente')
            $(this).find('input').eq(3).attr('name','ordenesServicio['+cont+'].expediente.asuntoSiged')
            $(this).find('input').eq(4).attr('name','ordenesServicio['+cont+'].expediente.flagMuestral')
            cont++;
        })
    },
    procesaDevolverOrdenesServicio:function(){
        coOrSeOrSeSuDev.armaFormDevolver();
        $.ajax({
            url:baseURL + "pages/ordenServicio/devolverOrdenServicio",
            type:'post',
            async:false,
            data:$('#devolverOrdenServicio').serialize(),
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                if(data.resultado=="0"){
                    mensajeGrowl('success', 'Se devolvieron satisfactoriamente la(s) orden(es) de servicio seleccionada(s)', '');
                    $('#ordServicioSeleccionados').html('');
                    $("#dialogOrdenServicioSupervisorDevolver").dialog('close');
                    supeAsigAsig.procesarGridAsignacion();
                }else{
                    mensajeGrowl('error', data.mensaje, '');
                }
            },
            error:errorAjax
        });
    }
};

jQuery.extend($.fn.fmatter, {
    checkboxSelecOrdServicio: function(cellvalue, options, rowdata) {
        var id=rowdata.ordenServicio.idOrdenServicio;
        var html = '';
        if (id != null && id != '' && id != undefined){       
            html='<input id="idOrdSer'+id+'" type="checkbox" name="" value="" onclick="coOrSeOrSeSuDev.selecOrdenServicio(this)">'+
                '<label for="idOrdSer'+id+'" class="checkbox"></label>';
        }
        return html;
    }
});
/* OSINE_SFS-480 - lgarciar - RSIS 40 - Fin */
$(function() {
    boton.closeDialog();    
    coOrSeOrSeSuDev.procesarGridOrdeServSupeDev();   
    coOrSeOrSeSuDev.inicializarComponentes();  
    $('#btnDevolverOrdenesServicio').click(coOrSeOrSeSuDev.validaDevolverOrdenesServicio);
    $('#devolverOrdenServicio').validarForm();
});
