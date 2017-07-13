$(function() {
    $('#tabsSupervisorDse').tabs();
    confirmer.start();
    especialista.cargarUnidadDivisionDse();
    
    $('#tabsSupervisorDse').tabs({
	    activate: function (event, ui) {
	    	tabSeleccionado=ui.newPanel[0].id;
	    if(tabSeleccionado=='rechazoCarga'){
			$('#slctAnio').val('2015');
			$('#slctTipoEmpresa').val('');
			
			$('#txtRUC').val('');
			$('#txtEmpresa').val('');
			$('#txtNumExpediente').val('');
			supervisorRechazoCarga.procesarGridEmpresas('Empresas','buscar');
		} else if(tabSeleccionado=='tabRegistroActaInspeccion'){
			$('#slctAnioActaSup').val('2015');
			$('#slctTipoEmpresaActaSup').val('');
			$('#slctZonaSup').val('');
			
			$('#idRUCActaSup').val('');
			$('#idEmpresaActaSup').val('');
			$('#idNroExpedienteActaSup').val('');
			supervisorActaInspeccion.procesarGridEmpresasZona('EmpresasZonaSupervisor','buscar');
		} else if(tabSeleccionado=='tabRegistroInformeSupervision'){
			$('#comboAnioInf').val('2015');
			$('#comboTipoEmpresaInf').val('');
			
			$('#idRUCInf').val('');
			$('#idEmpresaInf').val('');
			$('#idNroExpedienteInf').val('');
			mantenimientoInformeSupervision.procesarGridInformeSupervision('InformeSupervision');
		}
	    }
	});   
});
var especialista={
    cargarUnidadDivisionDse : function (){
	$.ajax({
            url:baseURL + "pages/bandeja/findUnidadDivisionPersonal",
            type:'get',
            async:false,
            data:{  
                idPersonal:$('#idPersonalSesion').val()  
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
               // $('#divDivision').html(data.division);
               // $('#divUnidad').html(data.unidad);
            },
            error:errorAjax
	});
}
};