// responsable/recepcion/popUpDerivados.js
var respRecePopUpDeri={
    procesarGridExpeDeriResponsable:function() {
        var nombres = ['C&Oacute;DIGO OSINERGMIN','RAZ&Oacute;N SOCIAL', 'N&Uacute;MERO EXPEDIENTE', 'FECHA CREACI&Oacute;N SIGED','','FLUJO SIGED','ASUNTO SIGED','ESPECIALISTA','','',''];
        var columnas = [
            {name: "unidadSupervisada.codigoOsinergmin", width: 60, sortable: false, align: "left"},
            {name: "empresaSupervisada.razonSocial", width: 250, sortable: false, align: "left"},
            {name: "numeroExpediente", width: 80, sortable: false, align: "left"},
            {name: "fechaCreacionSiged", width: 120, sortable: false, align: "left",hidden:true},
            {name: "flujoSiged.idFlujoSiged", hidden:true},
            {name: "flujoSiged.nombreFlujoSiged", width: 195, sortable: false, align: "left"},
            {name: "asuntoSiged", width: 300, sortable: false, align: "left"},
            {name: "especialistaDerivado", width: 200, sortable: false, align: "left",formatter: "especialistaDerivado"},
            {name: "personal.nombre", hidden:true},
            {name: "personal.apellidoPaterno", hidden:true},
            {name: "personal.apellidoMaterno", hidden:true}
        ];
        $("#gridContenedorExpeDeriResponsable").html("");
        var grid = $("<table>", {
            "id": "gridExpeDeriResponsable"
        });
        var pager = $("<div>", {
            "id": "paginacionExpeDeriResponsable"
        });
        $("#gridContenedorExpeDeriResponsable").append(grid).append(pager);

        grid.jqGrid({
            url: baseURL + "pages/expediente/findDerivados",
            datatype: "json",
            mtype:"POST",
            postData: {
                idPersonal:$('#idPersonalSesion').val()
            },
            hidegrid: false,
            rowNum: constant.rowNumPrinc,
            pager: "#paginacionExpeDeriResponsable",
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
                id: "codigo_expediente"
            },
            onSelectRow: function(rowid, status) {
                grid.resetSelection();
            }
        });
    }
};
jQuery.extend($.fn.fmatter, {
	especialistaDerivado: function(cellvalue, options, rowdata) {
        var nombre=rowdata.personal.nombre!=null?rowdata.personal.nombre:"";
        var ap=rowdata.personal.apellidoPaterno!=null?rowdata.personal.apellidoPaterno:"";
        var am=rowdata.personal.apellidoMaterno!=null?rowdata.personal.apellidoMaterno:"";
        return nombre+" "+ap+" "+am;
    }
});
//inicializacion
$(function() {
    boton.closeDialog();
    respRecePopUpDeri.procesarGridExpeDeriResponsable();    
});