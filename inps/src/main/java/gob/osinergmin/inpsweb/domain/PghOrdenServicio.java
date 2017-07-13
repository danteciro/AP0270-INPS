/**
* Resumen		
* Objeto		: PghOrdenServicio.java
* Descripción		: Clase del modelo de dominio PghOrdenServicio
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     09/05/2016      Mario Dioses Fernandez      Registrar en BD campo FECHA_INICIO_PLAZO y FECHA_FIN_PLAZO luego de CONCLUIR_OS, considerando Plazo_Descargo por Rubro (MDI_ACTIVIDAD)
* OSINE_SFS-480     20/05/2016      Luis García Reyna           Adaptar y preparar la relación entre "petición" y "motivo" en la tabla MDI_MAESTRO_COLUMNA
* OSINE_SFS-480     23/05/2016      Hernán Torres Saenz         Implementar la funcionalidad de anular orden de servicio de acuerdo a especificaciones
* OSINE_SFS-791     06/10/2016      Mario Dioses Fernandez      Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
* OSINE_SFS-791     11/10/2016      Mario Dioses Fernandez      Crear la tarea automática que notifique vía correo que se debe elaborar el oficio por obligaciones incumplidas sin subsanar
* OSINE_SFS-791     11/10/2016      Mario Dioses Fernandez		Crear la tarea automática que cancele el registro de hidrocarburos
*/ 

package gob.osinergmin.inpsweb.domain;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_ORDEN_SERVICIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghOrdenServicio.findAll", query = "SELECT p FROM PghOrdenServicio p"),
    @NamedQuery(name = "PghOrdenServicio.findByIdOrdenServicio", query = "SELECT p FROM PghOrdenServicio p where p.idOrdenServicio=:idOrdenServicio"),
    /* OSINE_SFS-480 - RSIS 13 - Inicio */
    @NamedQuery(name = "PghOrdenServicio.findByIteracionIdExpediente", query = "SELECT p FROM PghOrdenServicio p where p.estado=1 and p.iteracion=:iteracion and p.idExpediente.idExpediente=:idExpediente")
    /* OSINE_SFS-480 - RSIS 13 - Fin */
})
public class PghOrdenServicio extends Auditoria {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ORDEN_SERVICIO")
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "PGH_ORDEN_SERVICIO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idOrdenServicio;
    @Column(name = "ID_TIPO_ASIGNACION")
    private Long idTipoAsignacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_FISCALIZACION")
    @Temporal(TemporalType.TIMESTAMP)    
    private Date fechaFiscalizacion;
    @Column(name = "FECHA_ESTADO_PROCESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstadoProceso;
    @Size(max = 50)
    @Column(name = "NUMERO_ORDEN_SERVICIO")
    private String numeroOrdenServicio;
//    @JoinColumn(name = "ID_OBLIGACION_TIPO", referencedColumnName = "ID_OBLIGACION_TIPO")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private PghObligacionTipo idObligacionTipo;
    @JoinColumn(name = "ID_EXPEDIENTE", referencedColumnName = "ID_EXPEDIENTE")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghExpediente idExpediente;
    @JoinColumn(name = "ID_ESTADO_PROCESO", referencedColumnName = "ID_ESTADO_PROCESO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghEstadoProceso idEstadoProceso;
    @JoinColumn(name = "ID_SUPERVISORA_EMPRESA", referencedColumnName = "ID_SUPERVISORA_EMPRESA")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiSupervisoraEmpresa idSupervisoraEmpresa;
    @JoinColumn(name = "ID_LOCADOR", referencedColumnName = "ID_LOCADOR")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiLocador idLocador;
    @Column(name = "ITERACION")
    private Long iteracion;
    @Column(name = "FLAG_CUMPLIMIENTO")
    private String flagCumplimiento;
    @Column(name = "FLAG_PRESENTO_DESCARGO")
    private String flagPresentoDescargo;
    @OneToOne(mappedBy="idOrdenServicio")
    private PghSupervision idSupervision;
    @OneToMany(mappedBy = "idOrdenServicio", fetch = FetchType.LAZY)
    private List<PghDocumentoAdjunto> pghDocumentoAdjuntoList;
    @OneToMany(mappedBy = "idOrdenServicio", fetch = FetchType.LAZY)
    private List<PghHistoricoEstado> pghHistoricoEstadoList;
    @Transient
    private String motivoReasignacion;  
    /* OSINE_SFS-480 - RSIS 17 - Inicio */
    @Column(name = "FECHA_INI_PLAZO_DESCARGO") 
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIniPlazoDescargo;    
	@Column(name = "FECHA_FIN_PLAZO_DESCARGO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinPlazoDescargo;	
	@Column(name = "ID_ARCHIVO_PLAZO_DESCARGO")
    private Long idArchivoPlazoDescargo;
	@Column(name = "ID_DEPART_PLAZO_DESCARGO")
    private String IdDepartPlazoDescargo;
    /* OSINE_SFS-480 - RSIS 17 - Fin */ 
    /* OSINE_SFS-480 - RSIS 39 - Inicio */ 
    @JoinColumn(name = "ID_PETICION")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiMaestroColumna idPeticion;        
    @Column(name = "ID_MOTIVO")
    private Long idMotivo;
    @Size(max = 400)
    @Column(name = "COMENTARIO_DEVOLUCION")
    private String comentarioDevolucion;
    /* OSINE_SFS-480 - RSIS 39 - Fin */   
    
    /* OSINE791 - RSIS 33 - Inicio */ 
    @Column(name = "FLAG_CONFIRMA_TIPO_ASIGNACION")
    private String flagConfirmaTipoAsignacion;    
    
    @Column(name = "COMENTARIOS")
    private String comentarios;
    /* OSINE791 - RSIS 33 - Fin */ 
    
    public PghOrdenServicio() {} 
	
    public PghOrdenServicio(Long idOrdenServicio) {
        this.idOrdenServicio = idOrdenServicio;
    }
    
    public PghOrdenServicio(Long idOrdenServicio,Long iteracion) {
        this.idOrdenServicio = idOrdenServicio;
        this.iteracion=iteracion;
    }
    
    /* OSINE_SFS-791 - RSIS 46-47 - Inicio */ 
    public PghOrdenServicio(Long idOrdenServicio, String numeroOrdenServicio, PghExpediente expediente) {
        this.idOrdenServicio = idOrdenServicio;
        this.numeroOrdenServicio = numeroOrdenServicio;
        this.idExpediente = expediente;
    }
    /* OSINE_SFS-791 - RSIS 46-47 - Fin */ 
    /* OSINE_SFS-791 - RSIS 17 - Inicio */
    public PghOrdenServicio(Long idOrdenServicio,String numeroOrdenServicio, Long idExpediente, String numeroExpediente, Long idUnidadSupervisada, String codigoOsinergmin,Long idLocador, String nombrecompleto, Long idEmpresaSupervisora, String razonSocial) {
        this.idOrdenServicio = idOrdenServicio;
        this.numeroOrdenServicio=numeroOrdenServicio;
        this.idExpediente = new PghExpediente(idExpediente, numeroExpediente, idUnidadSupervisada, codigoOsinergmin); 
        this.idLocador = new MdiLocador(idLocador, nombrecompleto, "");
        this.idSupervisoraEmpresa = new MdiSupervisoraEmpresa(idEmpresaSupervisora,razonSocial);
    }
    /* OSINE_SFS-791 - RSIS 17 - Fin */
    public PghOrdenServicio(Long idOrdenServicio, String estado, Date fechaCreacion, String usuarioCreacion, String terminalCreacion) {
        this.idOrdenServicio = idOrdenServicio;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.terminalCreacion = terminalCreacion;
    }
    
    public PghOrdenServicio(Long idOrdenServicio, Date fechaCreacion, String numeroOrdenServicio, String codigoOsinergmin, String razonSocial, 
            String numeroExpediente,String nombreEstado,Date fechaEstadoProceso,String flagPresentoDescargo,Long iteracion,Long idEstadoProceso ) {
        this.idOrdenServicio = idOrdenServicio;
        this.fechaCreacion = fechaCreacion;
        this.numeroOrdenServicio=numeroOrdenServicio;
        PghExpediente expediente = new PghExpediente();
        expediente.setNumeroExpediente(numeroExpediente);
        MdiUnidadSupervisada unidadSupervisada = new MdiUnidadSupervisada();
        unidadSupervisada.setCodigoOsinergmin(codigoOsinergmin);
        expediente.setIdUnidadSupervisada(unidadSupervisada);
        MdiEmpresaSupervisada empresaSupervisada = new MdiEmpresaSupervisada();
        empresaSupervisada.setRazonSocial(razonSocial);
        expediente.setIdEmpresaSupervisada(empresaSupervisada);
        this.idExpediente=expediente;
        PghEstadoProceso estadoProceso=new PghEstadoProceso();
        estadoProceso.setNombreEstado(nombreEstado);
        this.idEstadoProceso=estadoProceso;
        this.fechaEstadoProceso=fechaEstadoProceso;
        this.flagPresentoDescargo=flagPresentoDescargo;
        this.iteracion=iteracion;
        estadoProceso.setIdEstadoProceso(idEstadoProceso);
    }

    public PghOrdenServicio(Long idOrdenServicio, Date fechaCreacion, String numeroOrdenServicio,Long idTipoAsignacion,Long idLocador,
            String nombreCompletoArmadoLo,Long idSupervisoraEmpresa,String razonSocialSE,String nombreConsorcioSE,Long idEstadoProceso,String identificadorEstado,String nombreEstado,Long iteracion,String flagCumplimiento,
            /* OSINE_SFS-480 - RSIS 43 - Inicio */
            //String motivoReasignacion) {
            String motivoReasignacion, Long idPeticion, Long idMotivo, String comentarioDevolucion) {
            /* OSINE_SFS-480 - RSIS 43 - Fin */
        this.idOrdenServicio = idOrdenServicio;
        this.fechaCreacion = fechaCreacion;
        this.numeroOrdenServicio=numeroOrdenServicio;
        this.idEstadoProceso=new PghEstadoProceso(idEstadoProceso,identificadorEstado,nombreEstado);
        this.idTipoAsignacion=idTipoAsignacion;
        this.idLocador=new MdiLocador(idLocador,nombreCompletoArmadoLo);
        this.idSupervisoraEmpresa=new MdiSupervisoraEmpresa(idSupervisoraEmpresa,razonSocialSE,nombreConsorcioSE);
        this.iteracion = iteracion;
        this.flagCumplimiento = flagCumplimiento;
        this.motivoReasignacion=motivoReasignacion;
        /* OSINE_SFS-480 - RSIS 43 - Inicio */
        this.idPeticion = new MdiMaestroColumna(idPeticion);
        this.idMotivo = idMotivo;
        this.comentarioDevolucion=comentarioDevolucion;
        /* OSINE_SFS-480 - RSIS 43 - Fin */
    }
    
    public PghOrdenServicio(Long idOrdenServicio, Date fechaCreacion, String numeroOrdenServicio,Long idTipoAsignacion,Long idLocador,
            String nombreCompletoArmadoLo,Long idSupervisoraEmpresa,String razonSocialSE,String nombreConsorcioSE,Long idEstadoProceso,
            String identificadorEstado,String nombreEstado,Long iteracion,String flagCumplimiento,String motivoReasignacion, 
            /* OSINE_SFS-480 - RSIS 44 - Inicio */
            Long idPeticion, String descripcionPeticion, String codigoPeticion, Long idMotivo, String comentarioDevolucion,
            /* OSINE_SFS-791 - RSIS 33 - Inicio */
            String flagConfirmaTipoAsignacion) {
    		/* OSINE_SFS-791 - RSIS 33 - Fin */
        this.idOrdenServicio = idOrdenServicio;
        this.fechaCreacion = fechaCreacion;
        this.numeroOrdenServicio=numeroOrdenServicio;
        this.idEstadoProceso=new PghEstadoProceso(idEstadoProceso,identificadorEstado,nombreEstado);
        this.idTipoAsignacion=idTipoAsignacion;
        this.idLocador=new MdiLocador(idLocador,nombreCompletoArmadoLo);
        this.idSupervisoraEmpresa=new MdiSupervisoraEmpresa(idSupervisoraEmpresa,razonSocialSE,nombreConsorcioSE);
        this.iteracion = iteracion;
        this.flagCumplimiento = flagCumplimiento;
        this.motivoReasignacion=motivoReasignacion;
        this.idPeticion = new MdiMaestroColumna(idPeticion, descripcionPeticion, codigoPeticion);
        this.idMotivo = idMotivo;
        this.comentarioDevolucion=comentarioDevolucion;
        /* OSINE_SFS-791 - RSIS 33 - Inicio */ 
        this.flagConfirmaTipoAsignacion=flagConfirmaTipoAsignacion;
        /* OSINE_SFS-791 - RSIS 33 - Fin */ 
    } 
    /* OSINE_SFS-480 - RSIS 44 - Fin */

    public String getMotivoReasignacion() {
        return motivoReasignacion;
    }

    public void setMotivoReasignacion(String motivoReasignacion) {
        this.motivoReasignacion = motivoReasignacion;
    }

    public Long getIdOrdenServicio() {
        return idOrdenServicio;
    }

    public void setIdOrdenServicio(Long idOrdenServicio) {
        this.idOrdenServicio = idOrdenServicio;
    }

    public Long getIdTipoAsignacion() {
        return idTipoAsignacion;
    }

    public void setIdTipoAsignacion(Long idTipoAsignacion) {
        this.idTipoAsignacion = idTipoAsignacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaFiscalizacion() {
        return fechaFiscalizacion;
    }

    public void setFechaFiscalizacion(Date fechaFiscalizacion) {
        this.fechaFiscalizacion = fechaFiscalizacion;
    }

    public String getNumeroOrdenServicio() {
        return numeroOrdenServicio;
    }

    public void setNumeroOrdenServicio(String numeroOrdenServicio) {
        this.numeroOrdenServicio = numeroOrdenServicio;
    }

//  public PghObligacionTipo getIdObligacionTipo() {
//  return idObligacionTipo;
//}
//
//  public void setIdObligacionTipo(PghObligacionTipo idObligacionTipo) {
//      this.idObligacionTipo = idObligacionTipo;
//  }

  public PghExpediente getIdExpediente() {
      return idExpediente;
  }

  public void setIdExpediente(PghExpediente idExpediente) {
      this.idExpediente = idExpediente;
  }

    public PghEstadoProceso getIdEstadoProceso() {
        return idEstadoProceso;
    }

    public void setIdEstadoProceso(PghEstadoProceso idEstadoProceso) {
        this.idEstadoProceso = idEstadoProceso;
    }

    public MdiSupervisoraEmpresa getIdSupervisoraEmpresa() {
        return idSupervisoraEmpresa;
    }

    public void setIdSupervisoraEmpresa(MdiSupervisoraEmpresa idSupervisoraEmpresa) {
        this.idSupervisoraEmpresa = idSupervisoraEmpresa;
    }

    public MdiLocador getIdLocador() {
        return idLocador;
    }

    public void setIdLocador(MdiLocador idLocador) {
        this.idLocador = idLocador;
    }
    
    //Roy - inicio
    public Long getIteracion() {
        return iteracion;
    }

    public void setIteracion(Long iteracion) {
        this.iteracion = iteracion;
    }

    public String getFlagCumplimiento() {
        return flagCumplimiento;
    }

    public void setFlagCumplimiento(String flagCumplimiento) {
        this.flagCumplimiento = flagCumplimiento;
    }

    public String getFlagPresentoDescargo() {
        return flagPresentoDescargo;
    }

    public void setFlagPresentoDescargo(String flagPresentoDescargo) {
        this.flagPresentoDescargo = flagPresentoDescargo;
    }
        
    public PghSupervision getIdSupervision() {
		return idSupervision;
	}

	public void setIdSupervision(PghSupervision idSupervision) {
		this.idSupervision = idSupervision;
	}	
    
    public Date getFechaEstadoProceso() {
		return fechaEstadoProceso;
	}

	public void setFechaEstadoProceso(Date fechaEstadoProceso) {
		this.fechaEstadoProceso = fechaEstadoProceso;
	}

	@XmlTransient
    @JsonIgnore
    public List<PghDocumentoAdjunto> getPghDocumentoAdjuntoList() {
        return pghDocumentoAdjuntoList;
    }

    public void setPghDocumentoAdjuntoList(List<PghDocumentoAdjunto> pghDocumentoAdjuntoList) {
        this.pghDocumentoAdjuntoList = pghDocumentoAdjuntoList;
    }

    public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public MdiMaestroColumna getIdPeticion() {
        return idPeticion;
    }

    public void setIdPeticion(MdiMaestroColumna idPeticion) {
        this.idPeticion = idPeticion;
    }

    public Long getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(Long idMotivo) {
        this.idMotivo = idMotivo;
    }

    public String getComentarioDevolucion() {
        return comentarioDevolucion;
    }

    public void setComentarioDevolucion(String comentarioDevolucion) {
        this.comentarioDevolucion = comentarioDevolucion;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrdenServicio != null ? idOrdenServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghOrdenServicio)) {
            return false;
        }
        PghOrdenServicio other = (PghOrdenServicio) object;
        if ((this.idOrdenServicio == null && other.idOrdenServicio != null) || (this.idOrdenServicio != null && !this.idOrdenServicio.equals(other.idOrdenServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.PghOrdenServicio[ idOrdenServicio=" + idOrdenServicio + " ]";
    }

    @XmlTransient
    @JsonIgnore
    public List<PghHistoricoEstado> getPghHistoricoEstadoList() {
        return pghHistoricoEstadoList;
    }

    public void setPghHistoricoEstadoList(List<PghHistoricoEstado> pghHistoricoEstadoList) {
        this.pghHistoricoEstadoList = pghHistoricoEstadoList;
    }
    /* OSINE_SFS-480 - RSIS 17 - Inicio */
    public Date getFechaIniPlazoDescargo() { 
		return fechaIniPlazoDescargo;
	}

	public void setFechaIniPlazoDescargo(Date fechaIniPlazoDescargo) {
		this.fechaIniPlazoDescargo = fechaIniPlazoDescargo;
	}

	public Date getFechaFinPlazoDescargo() {
		return fechaFinPlazoDescargo;
	}

	public void setFechaFinPlazoDescargo(Date fechaFinPlazoDescargo) {
		this.fechaFinPlazoDescargo = fechaFinPlazoDescargo;
	} 
    
    public Long getIdArchivoPlazoDescargo() {
		return idArchivoPlazoDescargo;
	}

	public void setIdArchivoPlazoDescargo(Long idArchivoPlazoDescargo) {
		this.idArchivoPlazoDescargo = idArchivoPlazoDescargo;
	}
	
	public String getIdDepartPlazoDescargo() {
		return IdDepartPlazoDescargo;
	}

	public void setIdDepartPlazoDescargo(String idDepartPlazoDescargo) {
		IdDepartPlazoDescargo = idDepartPlazoDescargo;
	} 
    /* OSINE_SFS-480 - RSIS 17 - Fin */
	
	/* OSINE_SFS-791 - RSIS 33 - Inicio */ 
	public String getFlagConfirmaTipoAsignacion() {
		return flagConfirmaTipoAsignacion;
	}

	public void setFlagConfirmaTipoAsignacion(String flagConfirmaTipoAsignacion) {
		this.flagConfirmaTipoAsignacion = flagConfirmaTipoAsignacion;
	}	
	/* OSINE_SFS-791 - RSIS 33 - Fin */ 
}
