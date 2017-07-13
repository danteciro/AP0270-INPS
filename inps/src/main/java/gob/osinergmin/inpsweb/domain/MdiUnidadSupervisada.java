/**
* Resumen		
* Objeto		: MdiUnidadSupervisada.java
* Descripción		: Clase del modelo de dominio MdiUnidadSupervisada
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     19/05/2016      Luis García Reyna           Crear la interfaz "devolver asignaciones" de acuerdo a especificaciones.
* OSINE791          26/08/2016      Gullet Alvites Pisco        Agregar el campo idActividad al constructor por parametros de la clase.
* OSINE_SFS-791     11/10/2016      Mario Dioses Fernandez      Crear la tarea automática que notifique vía correo que se debe elaborar el oficio por obligaciones incumplidas sin subsanar
* OSINE_SFS-791     11/10/2016      Mario Dioses Fernandez      Crear la tarea automática que cancele el registro de hidrocarburos
* 
*/ 

package gob.osinergmin.inpsweb.domain;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Table(name = "MDI_UNIDAD_SUPERVISADA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdiUnidadSupervisada.findAll", query = "SELECT m FROM MdiUnidadSupervisada m"),
})
public class MdiUnidadSupervisada extends Auditoria {
	
    @Size(max = 12)
    @Column(name = "CODIGO_OSINERGMIN")
    private String codigoOsinergmin;
    @Size(max = 200)
    @Column(name = "NOMBRE_UNIDAD")
    private String nombreUnidad;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UNIDAD_SUPERVISADA")
    private Long idUnidadSupervisada;
    @Column(name = "ID_TIPO_UNIDAD")
    private Long idTipoUnidad;
    @Column(name = "ESTADO")
    private String estado;
    @Size(max = 250)
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "ZONA")
    private String zona;
    @Size(max = 100)
    @Column(name = "UTM")
    private String utm;
    @Size(max = 10)
    @Column(name = "RESOLUCION_DIRECTORAL")
    private String resolucionDirectoral;
    @Column(name = "ID_TIPO_ACTIVIDAD")
    private Long idTipoActividad;
    @Size(max = 1180)
    @Column(name = "OBSERVACION")
    private String observacion;
    @Column(name = "ID_ZONA")
    private Long idZona;
    @Column(name = "ID_TIPO_DIRECCION_ACTIVIDAD")
    private Long idTipoDireccionActividad;
    @Column(name = "ID_ETAPA")
    private Long idEtapa;
    @JoinColumn(name = "ID_EMPRESA_SUPERVISADA", referencedColumnName = "ID_EMPRESA_SUPERVISADA")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiEmpresaSupervisada idEmpresaSupervisada;
    @JoinColumn(name = "ID_ACTIVIDAD", referencedColumnName = "ID_ACTIVIDAD")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiActividad idActividad;
    @OneToMany(mappedBy = "idUnidadSupervisada", fetch = FetchType.LAZY)
    private List<PghExpediente> pghExpedienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUnidadSupervisada", fetch = FetchType.LAZY)
    private List<MdiRegistroHidrocarburo> mdiRegistroHidrocarburoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUnidadSupervisada", fetch = FetchType.LAZY)
    private List<MdiDirccionUnidadSuprvisada> mdiDirccionUnidadSuprvisadaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUnidadSupervisada", fetch = FetchType.LAZY)
    private List<PghUnidObliSubTipo> pghUnidObliSubTipoList;
    @Size(max = 11)
    @Column(name = "RUC")
    private String ruc;
    @Size(max = 200)
    @Column(name = "NOMBRE_COMERCIAL")
    private String nombreComercial;

    @Transient
    private String numeroRegistroHidrocarburo;
    
    public MdiUnidadSupervisada() {
    }

    public MdiUnidadSupervisada(Long idUnidadSupervisada) {
        this.idUnidadSupervisada = idUnidadSupervisada;
    }
    /* OSINE_SFS-791 - RSIS 46-47 - Inicio */ 
    public MdiUnidadSupervisada(String codigoOsinergmin, Long idUnidadSupervisada, String numeroRegistroHidrocarburo) {
        this.idUnidadSupervisada = idUnidadSupervisada;
        this.numeroRegistroHidrocarburo = numeroRegistroHidrocarburo;
        this.codigoOsinergmin = codigoOsinergmin;
    }
    public MdiUnidadSupervisada(String codigoOsinergmin, Long idUnidadSupervisada, String numeroRegistroHidrocarburo, Long idActividad) {
        this.idUnidadSupervisada = idUnidadSupervisada;
        this.numeroRegistroHidrocarburo = numeroRegistroHidrocarburo;
        this.codigoOsinergmin = codigoOsinergmin;
        this.idActividad = new MdiActividad(idActividad);
    }
    /* OSINE_SFS-791 - RSIS 46-47 - Fin */ 
    public MdiUnidadSupervisada(Long idUnidadSupervisada,String codigoOsinergmin,String nombreUnidad) {
        this.idUnidadSupervisada = idUnidadSupervisada;
        this.codigoOsinergmin=codigoOsinergmin;
        this.nombreUnidad=nombreUnidad;
    }
    /* OSINE_SFS-480 - RSIS 38 - Inicio */    
    /* OSINE791 - RSIS 21 - Inicio */ 
    //public MdiUnidadSupervisada(Long idUnidadSupervisada,String codigoOsinergmin,String nombreUnidad,String numeroRegistroHidrocarburo, String nombreActividad
    public MdiUnidadSupervisada(Long idUnidadSupervisada,String codigoOsinergmin,String nombreUnidad,String numeroRegistroHidrocarburo, String nombreActividad, Long idActividad, String ruc
    /* OSINE791 - RSIS 21 - Fin */ 
    ) {
        this.idUnidadSupervisada = idUnidadSupervisada;
        this.codigoOsinergmin=codigoOsinergmin;
        this.nombreUnidad=nombreUnidad;
        this.numeroRegistroHidrocarburo=numeroRegistroHidrocarburo;        
        /* OSINE791 - RSIS 21 - Inicio */
        //this.idActividad= new MdiActividad(nombreActividad);
        this.idActividad= new MdiActividad(idActividad,nombreActividad);
        /* OSINE791 - RSIS 21 - Fin */
        this.ruc=ruc;
    }
    
    public MdiUnidadSupervisada(Long idUnidadSupervisada,String nombreUnidad,String codigoOsinergmin ,String direccion ,String estado ,
//      Long idEmpresaSupervisada , String razonSocial , String numeroIdentificacion,String nombreComercial, String telefono, String ruc ,String correoElectronico ,
        String nombreComercial,String ruc,    
        Long idActividad , String Actnombre ,String Actcodigo,String numeroRegistroHidrocarburo
//            ,Long idMaestroColumna, String codigo,String descripcion
    ){
        this.idUnidadSupervisada = idUnidadSupervisada;
        this.nombreUnidad = nombreUnidad;
        this.codigoOsinergmin = codigoOsinergmin;
        this.direccion = direccion;
        this.estado = estado;
        this.ruc = ruc;
        this.nombreComercial = nombreComercial;
//        this.idEmpresaSupervisada = new MdiEmpresaSupervisada(idEmpresaSupervisada , razonSocial , numeroIdentificacion,nombreComercial, telefono, ruc ,correoElectronico,idMaestroColumna,codigo,descripcion);
        this.idActividad =  new MdiActividad(idActividad,Actnombre,Actcodigo);
        this.numeroRegistroHidrocarburo = numeroRegistroHidrocarburo;        
    }

    public String getNumeroRegistroHidrocarburo() {
        return numeroRegistroHidrocarburo;
    }

    public void setNumeroRegistroHidrocarburo(String numeroRegistroHidrocarburo) {
        this.numeroRegistroHidrocarburo = numeroRegistroHidrocarburo;
    }
    /* OSINE_SFS-480 - RSIS 38 - Fin */
    
    public String getCodigoOsinergmin() {
        return codigoOsinergmin;
    }

    public void setCodigoOsinergmin(String codigoOsinergmin) {
        this.codigoOsinergmin = codigoOsinergmin;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public Long getIdUnidadSupervisada() {
        return idUnidadSupervisada;
    }

    public void setIdUnidadSupervisada(Long idUnidadSupervisada) {
        this.idUnidadSupervisada = idUnidadSupervisada;
    }

    public Long getIdTipoUnidad() {
        return idTipoUnidad;
    }

    public void setIdTipoUnidad(Long idTipoUnidad) {
        this.idTipoUnidad = idTipoUnidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getUtm() {
        return utm;
    }

    public void setUtm(String utm) {
        this.utm = utm;
    }

    public String getResolucionDirectoral() {
        return resolucionDirectoral;
    }

    public void setResolucionDirectoral(String resolucionDirectoral) {
        this.resolucionDirectoral = resolucionDirectoral;
    }

    public Long getIdTipoActividad() {
        return idTipoActividad;
    }

    public void setIdTipoActividad(Long idTipoActividad) {
        this.idTipoActividad = idTipoActividad;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Long getIdZona() {
        return idZona;
    }

    public void setIdZona(Long idZona) {
        this.idZona = idZona;
    }

    public Long getIdTipoDireccionActividad() {
        return idTipoDireccionActividad;
    }

    public void setIdTipoDireccionActividad(Long idTipoDireccionActividad) {
        this.idTipoDireccionActividad = idTipoDireccionActividad;
    }

    public Long getIdEtapa() {
        return idEtapa;
    }

    public void setIdEtapa(Long idEtapa) {
        this.idEtapa = idEtapa;
    }

    public MdiEmpresaSupervisada getIdEmpresaSupervisada() {
        return idEmpresaSupervisada;
    }

    public void setIdEmpresaSupervisada(MdiEmpresaSupervisada idEmpresaSupervisada) {
        this.idEmpresaSupervisada = idEmpresaSupervisada;
    }

    public MdiActividad getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(MdiActividad idActividad) {
        this.idActividad = idActividad;
    }

    @XmlTransient
    @JsonIgnore
    public List<PghExpediente> getPghExpedienteList() {
        return pghExpedienteList;
    }

    public void setPghExpedienteList(List<PghExpediente> pghExpedienteList) {
        this.pghExpedienteList = pghExpedienteList;
    }

    @XmlTransient
    public List<MdiRegistroHidrocarburo> getMdiRegistroHidrocarburoList() {
        return mdiRegistroHidrocarburoList;
    }

    public void setMdiRegistroHidrocarburoList(List<MdiRegistroHidrocarburo> mdiRegistroHidrocarburoList) {
        this.mdiRegistroHidrocarburoList = mdiRegistroHidrocarburoList;
    }
    
    @XmlTransient
    public List<MdiDirccionUnidadSuprvisada> getMdiDirccionUnidadSuprvisadaList() {
        return mdiDirccionUnidadSuprvisadaList;
    }

    public void setMdiDirccionUnidadSuprvisadaList(List<MdiDirccionUnidadSuprvisada> mdiDirccionUnidadSuprvisadaList) {
        this.mdiDirccionUnidadSuprvisadaList = mdiDirccionUnidadSuprvisadaList;
    }
    
    @XmlTransient
    public List<PghUnidObliSubTipo> getPghUnidObliSubTipoList() {
		return pghUnidObliSubTipoList;
	}

	public void setPghUnidObliSubTipoList(List<PghUnidObliSubTipo> pghUnidObliSubTipoList) {
		this.pghUnidObliSubTipoList = pghUnidObliSubTipoList;
	}
        
    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idUnidadSupervisada != null ? idUnidadSupervisada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdiUnidadSupervisada)) {
            return false;
        }
        MdiUnidadSupervisada other = (MdiUnidadSupervisada) object;
        if ((this.idUnidadSupervisada == null && other.idUnidadSupervisada != null) || (this.idUnidadSupervisada != null && !this.idUnidadSupervisada.equals(other.idUnidadSupervisada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.MdiUnidadSupervisada[ idUnidadSupervisada=" + idUnidadSupervisada + " ]";
    }
    
}
