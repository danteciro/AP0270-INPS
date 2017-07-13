//common/ordenServicio/OrdenServicioSupervisor.js
var coOrSeOrSeSu={
    validaProcesaGridOrdeServSupe:function(){
        $('#txtFechaIniOSSup,#txtFechaFinOSSup').removeAttr('validate');
        if( $('#txtFechaIniOSSup').val()!='' || $('#txtFechaFinOSSup').val()!='' ){
            $('#txtFechaIniOSSup,#txtFechaFinOSSup').attr('validate','[O]');
        }
        if($('#frmBuscarOrdenServSupervisor').validateAllForm("")){
            common.procesarGridOrdeServSupe("OrdeServSupe");
        }
    },
    limpiarFiltrosBusq:function(){
        $('#frmBuscarOrdenServSupervisor').find('input,select').not('#idLocadorOSSuper,#idSupervisoraEmpresaOSSuper').val('');        
    }
};

$(function() {
    boton.closeDialog();
    $("#txtFechaIniOSSup").datepicker({
        onClose: function (selectedDate) {
            $("#txtFechaFinOSSup").datepicker("option", "minDate", selectedDate);
        }
    });
    $("#txtFechaFinOSSup").datepicker({
        onClose: function (selectedDate) {
            $("#txtFechaIniOSSup").datepicker("option", "maxDate", selectedDate);
        }
    });
    
    common.procesarGridOrdeServSupe("OrdeServSupe");    
    $('#btnOrdeServSupervisor').click(coOrSeOrSeSu.validaProcesaGridOrdeServSupe);
    $('#btnLimpiarOrdeServSupervisor').click(coOrSeOrSeSu.limpiarFiltrosBusq);
});