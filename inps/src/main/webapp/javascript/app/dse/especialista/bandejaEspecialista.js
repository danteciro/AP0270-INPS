$(function() {
    $('#tabsEspecialistaDse').tabs();
    confirmer.start();
    especialista.cargarUnidadDivisionDse();
    
    $('#tabsEspecialistaDse').tabs({
	    activate: function (event, ui) {
	    	tabSeleccionado=ui.newPanel[0].id;
	    	if(tabSeleccionado=='rechazoCarga'){
	    		$('#slctAnio').val('2015');
	    		$('#slctTipoEmpresa').val('');
	    		
	    		$('#txtRUC').val('');
	    		$('#txtEmpresa').val('');
	    		$('#txtNroExpediente').val('');
	    		especialistaRechazoCarga.procesarGridEmpresas('Empresas','buscar');
	    	} else if(tabSeleccionado=='tabRegistroActaInspeccion'){
	    		$('#slctAnioActa').val('2015');
	    		$('#slctTipoEmpresaActa').val('');
	    		$('#slctZona').val('');
	    		
	    		$('#idRUCActa').val('');
	    		$('#idEmpresaActa').val('');
	    		$('#idNroExpedienteActa').val('');
	    		especialistaActaInspeccion.procesarGridEmpresasZona('EmpresasZona','buscar');
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
                $('#divDivision').html(data.division);
                $('#divUnidad').html(data.unidad);
            },
            error:errorAjax
	});
}
};