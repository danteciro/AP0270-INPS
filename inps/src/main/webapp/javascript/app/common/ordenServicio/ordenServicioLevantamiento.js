/**
* Resumen		
* Objeto			: ordenServicioLevantamiento.js
* Descripción		: ordenServicioLevantamiento
* Fecha de Creación	: 26/07/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: Mario Dioses Fernandez
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* * OSINE_SFS-791     06/10/2016      Mario Dioses Fernandez          Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
*/
var path="pages/levantamiento";

var coOrSeOrSeLev = {
    idTipoAsigLevantamiento:"",
    actualizaIdTipoAsigLevantamiento:function(){
        coOrSeOrSeLev.idTipoAsigLevantamiento=$('#slctConfirmaLevantamiento').val();
    },
    inicio:function (){
        coOrSeOrSeLev.armaConsultarFormOS();   
        coOrSeOrSeLev.procesarGridInfraccionLev();

        if($('#txtIdObligacionSubTipoOSLev').val()!=''){
            common.obligacionTipo.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSLev').val(),'#cmbSubTipoSupervisionOSLev');
            $('#subTipoOSLev').css('display','inline-block');
            $('#cmbSubTipoSupervisionOSLev').val($('#txtIdObligacionSubTipoOSLev').val());
        }

        $('#slctUnidSupeOSLev').change(function() {
        $('#txtIdActividadCon,#txtRubroOSLev,#txtDireOperOSLev,#txtIdDepartamentoOSCon,#txtIdProvinciaOSLev,#txtIdDistritoOSLev').val('');            
        if ($('#slctUnidSupeOSLev').val() != "") {
            common.empresaSupervisora.cargaDataUnidadOperativa($('#slctUnidSupeOSLev').val(),coOrSeOrSeLev.fillDataUnidadOperativa);
        }
        });	

        $('#btnConfirmarVisitaLevantamiento').click(function(){
            var html="¿Confirmar el tipo asignaci&oacute;n para la orden de servicio?<br>";
            html+='<div class="filaForm tac"><select id="slctConfirmaLevantamiento" onchange="coOrSeOrSeLev.actualizaIdTipoAsigLevantamiento()" class="slct-medium">';   
            html+=$('#slctTipoAsigLev').html().replace('<option value="">--Seleccione--</option>','');
            html+='</select><div>';
            confirmer.open(html,"coOrSeOrSeLev.confirmarOrdenServicio()", "Confirmar Visita");
            coOrSeOrSeLev.actualizaIdTipoAsigLevantamiento();
        });

        $('#tabsLevantamiento').tabs();
        $('#slctUnidSupeOSLev').trigger('change');

    },
    armaConsultarFormOS : function() {
        if ($('#txtflagOrigenOSLev').val() == constant.flgOrigenExpediente.siged) {
            fill.clean("#slctIdEtapaOSLev,#slctIdTramiteOSLev");
                common.serie.cargarEtapa("#slctIdProcesoOSLev", "#slctIdEtapaOSLev");
            $('#slctIdEtapaOSLev').val($('#txtIdEtapaTrOSLev').val());
            $('#slctIdEtapaOSLev').trigger('change');
            $('#slctIdTramiteOSLev').val($('#txtIdTramiteOSLev').val());
        }
        // para datos de empresa supervisora
        if ($('#txtIdLocadorOSLev').val() != '' && $('#txtIdLocadorOSLev').val() != undefined) {
            $('#slctTipoEmprSupeOSLev').val($('#slctTipoEmprSupeOSLev').find('option[codigo="'+constant.empresaSupervisora.personaNatural+'"]').val())
            $('#slctTipoEmprSupeOSLev').trigger("change");
            $('#slctEmprSupeOSLev').val($('#txtIdLocadorOSLev').val());
        } else if ($('#txtIdSupervisoraEmpresaOSLev').val() != '' && $('#txtIdSupervisoraEmpresaOSLev').val() != undefined){
            $('#slctTipoEmprSupeOSLev').val($('#slctTipoEmprSupeOSLev').find('option[codigo="'+constant.empresaSupervisora.personaJuridica+'"]').val())
            $('#slctTipoEmprSupeOSLev').trigger("change");
            $('#slctEmprSupeOSLev').val($('#txtIdSupervisoraEmpresaOSLev').val());
        }
    },
    fillDataUnidadOperativa : function(data) {
        $('#txtRubroOSLev,#txtDireOperOSLev').val("");
        if (data != null && data.registro!=null) {
            $('#txtIdActividadLev').val(data.registro.idActividad);
            $('#txtRubroOSLev').val(data.registro.actividad);

            if(data.registro.direccionUnidadsupervisada.direccionCompleta!=null && data.registro.direccionUnidadsupervisada.direccionCompleta!=undefined){
                $('#txtDireOperOSLev').val(data.registro.direccionUnidadsupervisada.direccionCompleta);
                $('#txtIdDepartamentoOSLev').val(data.registro.direccionUnidadsupervisada.departamento.idDepartamento); 
                $('#txtIdProvinciaOSLev').val(data.registro.direccionUnidadsupervisada.provincia.idProvincia);
                $('#txtIdDistritoOSLev').val(data.registro.direccionUnidadsupervisada.distrito.idDistrito); 
            }           
        }
    },
    procesarGridInfraccionLev:function(){	
                // Listar infraccion Levantamiento
                        index=0;
                        var nombres = ['idDetalleSupervision', 'idSupervision', '.idObligacion', 'idInfraccion', 'Descripci&oacute;n Infracci&oacute;n', 'Levantamiento'];
                    var columnas = [
                        { name : "idDetalleSupervision", width : 20, sortable : false, hidden:true, align : "left" },
                        { name : "supervision.idSupervision", width : 20, sortable : false, hidden:true, align : "left" },
                        { name : "obligacion.idObligacion", width : 20, sortable : false, hidden:true, align : "left" },
                                { name : "infraccion.idInfraccion", width : 20, sortable : false, hidden:true, align : "left" },					
                                { name : "infraccion.descripcionInfraccion", width : 750, sortable : false, align : "left" },
                                { name : "verLevantamiento", width : 80, sortable : false, align : "center", formatter:"icoVerLevantamiento" }
                    ];

                    var nombreGrid="InfraccionLev";
                    var url = baseURL + path +"/listarInfaccionLevantamiento";
                    $("#gridContenedor"+nombreGrid).html("");
                var grid = $("<table>", {
                    "id": "grid"+nombreGrid
                });
                var pager = $("<div>", {
                    "id": "paginacion"+nombreGrid
                });
                $("#gridContenedor"+nombreGrid).append(grid).append(pager);
                grid.jqGrid({
                    url: url,
                    datatype: "json",
                    postData: {  
                        idExpediente: $('#txtIdExpedienteOSLev').val(),
                        iteracion: constant.iteracion.primera 
                    },
                    hidegrid: false,
                    rowNum: 10,
                    mtype:'POST',
                    pager: "#paginacion"+nombreGrid,
                    emptyrecords: "No se encontraron resultados",
                    recordtext: "{0} - {1}",
                    loadtext: "Cargando",
                    colNames: nombres,
                    colModel: columnas,
                    height: "auto",
                    viewrecords: true,
                    caption: "Listado de Infracciones",
                    jsonReader: { root: "filas", page: "pagina", total: "total", records: "registros", repeatitems: false, id: "idDetalleSupervision" },
                    onSelectRow: function(rowid, status) {
                        grid.resetSelection();
                    },
                    onRightClickRow: function(rowid, iRow, iCol, e) {},
                    loadComplete: function(data){
                    }
                });  
         },
         abrirLevantamientoInfraccionSup:function(idDetalleSupervision){			 
                var title="LEVANTAMIENTO DE INFRACCIÓN DE SUPERVISIÓN";
    $.ajax({
        url:  baseURL + path +"/abrirLevantamientoInfraccionSup",
        type:'POST',
        async:false,
        data:{
                idDetalleSupervision:idDetalleSupervision
        },
        beforeSend:loading.open,
        success:function(data){
            loading.close();
            $("#dialogLevantamientoInfraccionSup").html(data);
            $("#dialogLevantamientoInfraccionSup").dialog({
                resizable: false,
                draggable: true,
                autoOpen: true,
                height:"auto",
                width: "780",
                modal: true,
                dialogClass: 'dialog',
                title: title,
                close: function() {
                    $("#dialogLevantamientoInfraccionSup").dialog("destroy");
                }
            });
        },
        error:errorAjax
    });
         },
         confirmarOrdenServicio : function(){
     var estado=(coOrSeOrSeLev.idTipoAsigLevantamiento==$('#slctTipoAsigLev').val())?constant.estado.activo:constant.estado.inactivo;
            $.ajax({
                url:  baseURL + path +"/confirmarOrdenServicioLevantamiento",
                type:'POST',
                async:false,
                data:{
                    idOrdenServicio:$('#txtIdOrdenServicioOSLev').val(),
                    flagConfirmaTipoAsignacion:estado,                	
                    direccionOperativa : $('#txtDireOperOSLev').val(),
                    idDepartamento :  $('#txtIdDepartamentoOSLev').val(),
                    idProvincia : $('#txtIdProvinciaOSLev').val(),
                    idDistrito :  $('#txtIdDistritoOSLev').val(),
                    descTipoAsignacion:$('#slctTipoAsigLev').find('option[value='+coOrSeOrSeLev.idTipoAsigLevantamiento+']').html(),
                    idExpediente : $('#txtIdExpedienteOSLev').val()
                },
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    if(data.resultado=="0"){
                        supeAsigAsig.procesarGridAsignacion();
                        $("#dialogOrdenServicioLevantamiento").dialog("close");
                        mensajeGrowl("success", data.mensaje, "");	                    	
                    } else {	                    	
                        mensajeGrowl("error", data.mensaje, ""); 
                    }
                },
                error:errorAjax
            });			 
         }
};

jQuery.extend($.fn.fmatter, { 
	icoVerLevantamiento: function(cellvalue, options, rowdata) {
    	var html='';
    	html+="<a class='link' onClick='coOrSeOrSeLev.abrirLevantamientoInfraccionSup(\""+rowdata.idDetalleSupervision+"\")' >" +
    			"<img class='vam' src='/inps/images/cuaderno.png' height='18' width='17' />" +
    		  "</a>";    	
    	return html;
    }
});

$(function() {
    boton.closeDialog();     
    coOrSeOrSeLev.inicio();
});