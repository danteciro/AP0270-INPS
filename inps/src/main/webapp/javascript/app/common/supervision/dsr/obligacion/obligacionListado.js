/**
 * Resumen		
 * Objeto		: obligacionListado.js
 * Descripción		: Crear la funcionalidad de consultar las obligaciones a verificar en una supervisión de orden de supervisión DSR-CRITICIDAD de la casuística supervisión realizada
 * Fecha de Creación	: 18/08/2016
 * PR de CreaciÃ³n	: OSINE_SFS-791
 * Autor	        : GMD
 * =====================================================================================================================================================================
 * Modificaciones  |
 * Motivo          |    Fecha        |         Nombre             |                    Descripcionn
 * =====================================================================================================================================================================
 *OSINE791–RSIS07 |  18/08/2016     | Zosimo Chaupis Santur      | Crear la funcionalidad de consultar las obligaciones a verificar en una supervisión de orden de supervisión DSR-CRITICIDAD de la casuística supervisión realizada
 *OSINE791-RSIS15 |  16/09/2016     | Alexander Vilca Narvaez 	 | Validar si existen infracciones pendientes de finalizar atención
 *OSINE791-RSIS44 |  17/10/2016     |	Cristopher Paucar Torre  | Deshabilitar Pestaña de Datos Iniciales y Recepción Visita.
 *OSINE791-RSIS65 |  20/10/2016     | Alexander Vilca Narvaez    | Crear la funcionalidad de consultar las obligaciones a verificar en la supervisión de orden de levantamiento.
 *OSINE_MAN_DSR_0025 | 19/06/2017   | Carlos Quijano Chavez::ADAPTER      | Agregar la Columna Codigo y eliminar prioridad
 **/

//common/supervision/dsr/obligacion/obligacionListado.js
var coSupeDsrOblOblLst = {
    procesarGridOblLst: function() {
    	var cuartaColumna='';
    	/* OSINE_SFS-791 - RSIS 65 - Inicio */ 
    	 if($('#idIteracionOS').val()==constant.iteracion.primera){
    		 cuartaColumna= 'CUMPLIMIENTO'; 
    	 }
    	 else if ($('#idIteracionOS').val()>constant.iteracion.primera){
    		 cuartaColumna= 'SUBSANO';
    	 }
		/* OSINE_MAN_DSR_0025 - Inicio */ 
    	//var nombres = ['IDDETALLESUPERVISION', 'PRIORIDAD', 'DESCRIPCIÓN DE LA INFRACCIÓN', cuartaColumna,'','']; 
		var nombres = [ 'IDDETALLESUPERVISION', 'CODIGO', 'PRIORIDAD','DESCRIPCIÓN DE LA INFRACCIÓN', cuartaColumna, '', '' ];
		/* OSINE_MAN_DSR_0025 - Fin */
    	 /* OSINE_SFS-791 - RSIS 65 - Fin */
		/* OSINE_MAN_DSR_0025 - Inicio */ 
        /**var columnas = [
            {name: "idDetalleSupervision", hidden: true},
            {name: "prioridad", hidden: false, align: "center", width: 15, },
            {name: "descripcionInfraccion", hidden: false},
            {name: "clasecss", width: 20, align: "center", formatter: 'VerImagen'},
            {name: "idDetalleSupervisionAnt", hidden: true},
            {name: "iteracion", hidden: true},
        ];**/
		var columnas = [ 
			{name : "idDetalleSupervision",hidden : true},
			{name : "codigoInfraccion",	hidden : false,	align : "center",width : 15},
			{name : "prioridad",hidden : true,align : "center",	width : 15},
			{name : "descripcionInfraccion", hidden : false	},
			{name : "clasecss",	width : 20,	align : "center",formatter : 'VerImagen'},
			{name : "idDetalleSupervisionAnt",	hidden : true},
			{name : "iteracion",hidden : true}
			];
		/* OSINE_MAN_DSR_0025 - Fin */
        $("#gridContenedorObligacionListado").html("");
        var grid = $("<table>", {
            "id": "gridObligacionListado"
        });
        var pager = $("<div>", {
            "id": "paginacionObligacionListado"
        });
        $("#gridContenedorObligacionListado").append(grid).append(pager);
        grid.jqGrid({
            url: baseURL + "pages/supervision/dsr/findObligacionesListado",
            datatype: "json",
            mtype: "POST",
            postData: {
                idSupervision: $('#idSupervision').val(),
            },
            hidegrid: false,
            rowNum: constant.rowNumPrinc,
            pager: "#paginacionObligacionListado",
            emptyrecords: "No se encontraron resultados",
            loadtext: "Cargando",
            colNames: nombres,
            colModel: columnas,
            height: "auto",
            viewrecords: true,
            width: 810,
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
                $('#contextMenuObligacionListado').parent().remove();
                $('#contextMenuObligacionListado').puicontextmenu({
                    target: $('#gridObligacionListado')
                });
                $('#contextMenuDerivado').parent().css('width', '382px');
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    verDetalleSubsanado:function(idDetSup,idDetaSupAnt){
        coSupeDsrOblOblDetSub.mostrarDetalleObligacion(idDetSup,idDetaSupAnt);
    }
}


jQuery.extend($.fn.fmatter, {
    VerImagen: function(cellvalue, options, rowdata) {
        //  OSINE791 - RSIS07 - Inicio -->
        var idDetalleSupervision = (rowdata.idDetalleSupervision != null)?rowdata.idDetalleSupervision:"";
        var idDetalleSupervisionAnt = (rowdata.idDetalleSupervisionAnt != null)?rowdata.idDetalleSupervisionAnt:"";
        var imgestilo = (rowdata.clasecss != null)?rowdata.clasecss:"";
        
        var onclick="";
        if(rowdata.iteracion>1){
            onclick="coSupeDsrOblOblLst.verDetalleSubsanado('"+idDetalleSupervision+"','"+idDetalleSupervisionAnt+"')";
        }else{
            onclick="VerDetalle('"+idDetalleSupervision+"')";
        }        
        
        var html = '<div title="'+rowdata.descripcionResSup+'" onclick="'+onclick+'" class="ilb cp btnGrid"><span class="ui-icon '+imgestilo+'"></span></div>'
        return html;
        //  OSINE791 - RSIS07 - Fin -->
    }
});
function VerDetalle(valor) {
    coSupeDsrOblOblDet.mostrarDetalleObligacion(valor);
}

$(function() {
    //  OSINE791 - RSIS07 - Inicio -->
    if ($('#idSupervision').val() !== '') {
        coSupeDsrSupe.VerificarObstaculizados();
        coSupeDsrOblOblLst.procesarGridOblLst();        
    }
    boton.closeDialog();
    $('#btnAtrasObligacionesSP').click(function() {
        coSupeDsrSupe.MostrarTab(1, 2);
    });
    $('#btnObligaciones').click(function() {
    	/*OSINE791 - RSIS15 - Inicio*/
    	//coSupeDsrSupe.MostrarTab(3, 2);
    	coSupeDsrSupe.VerificarCumplimientos();
    	/* OSINE791 - RSIS15 - Fin */

    });
    
    /*OSINE791 - RSIS36 - Inicio*/
    $('#btnTerminarSupervision').click(function() {
    	//coSupeDsrSupe.MostrarTab(3, 2);
        /* OSINE_SFS-791 - RSIS 39 - Inicio */ 
       // coSuDsrTerminarSup.fxGenerarResultadoTerminarSupervision.cargarFlagInfraccionesTerminarSup();
        /* OSINE_SFS-791 - RSIS 39 - Fin */ 
        coSupeDsrSupe.VerificarCumplimientosSub();
    });
    /* OSINE791 - RSIS36 - Fin */
    
    //  OSINE791 - RSIS07 - Fin -->
    /* OSINE791 - RSIS44 - Inicio */
    if($('#modoSupervision').val() == constant.modoSupervision.consulta){
         $("#btnAtrasObligacionesSP").css("display",'none');
         $("#btnTerminarSupervision").css("display",'none');        
    }
    /* OSINE791 - RSIS44 - Fin */
});