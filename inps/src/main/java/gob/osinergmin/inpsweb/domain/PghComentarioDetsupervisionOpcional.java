/**
 * Resumen 
 * Objeto				: PghComentarioDetsupervisionOpcional.java 
 * Descripción	        : Entidad de tabla PGH_COMENTARIO_DETSUP_OPC. 
 * Fecha de Creación	: 19/06/2017.
 * PR de Creación		: OSINE_MAN_DSR_0037.
 * Autor				: Carlos Quijano Chavez::ADAPTER.
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones   Fecha             Nombre               Descripción
 * ---------------------------------------------------------------------------------------------------
 *
 */

package gob.osinergmin.inpsweb.domain;


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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author carlos
 */
@Entity
@Table(name = "PGH_COMENTARIO_DETSUP_OPC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghComentarioDetsupervisionOpcional.findByIdDetSupervisionAndIdEscenario", query = "SELECT p FROM PghComentarioDetsupervisionOpcional p WHERE p.idEscenarioIncumplido=:p1 AND p.idDetalleSupervision=:p2")
})
public class PghComentarioDetsupervisionOpcional extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_COMENTARIO_DETSUP_OPC")
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "PGH_COMENTARIO_DETSUP_OPC_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idComentarioDetsupervisionOpcional;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "ID_ESCENARIO_INCUMPLIDO", referencedColumnName = "ID_ESCENARIO_INCUMPLIDO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghEscenarioIncumplido idEscenarioIncumplido;
    @JoinColumn(name = "ID_DETALLE_SUPERVISION", referencedColumnName = "ID_DETALLE_SUPERVISION")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghDetalleSupervision idDetalleSupervision;

    public PghComentarioDetsupervisionOpcional() {
    }

	public PghComentarioDetsupervisionOpcional(	Long idComentarioDetsupervisionOpcional) {
		this.idComentarioDetsupervisionOpcional = idComentarioDetsupervisionOpcional;
	}

	public PghComentarioDetsupervisionOpcional(
			Long idComentarioDetsupervisionOpcional, String estado,
			String descripcion, PghEscenarioIncumplido idEscenarioIncumplido,
			PghDetalleSupervision idDetalleSupervision) {
		super();
		this.idComentarioDetsupervisionOpcional = idComentarioDetsupervisionOpcional;
		this.estado = estado;
		this.descripcion = descripcion;
		this.idEscenarioIncumplido = idEscenarioIncumplido;
		this.idDetalleSupervision = idDetalleSupervision;
	}

	public PghComentarioDetsupervisionOpcional(String estado,String descripcion, PghEscenarioIncumplido idEscenarioIncumplido,PghDetalleSupervision idDetalleSupervision) {
		
		this.estado = estado;
		this.descripcion = descripcion;
		this.idEscenarioIncumplido = idEscenarioIncumplido;
		this.idDetalleSupervision = idDetalleSupervision;
	}

	public Long getIdComentarioDetsupervisionOpcional() {
		return idComentarioDetsupervisionOpcional;
	}

	public void setIdComentarioDetsupervisionOpcional(
			Long idComentarioDetsupervisionOpcional) {
		this.idComentarioDetsupervisionOpcional = idComentarioDetsupervisionOpcional;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public PghEscenarioIncumplido getIdEscenarioIncumplido() {
		return idEscenarioIncumplido;
	}

	public void setIdEscenarioIncumplido(PghEscenarioIncumplido idEscenarioIncumplido) {
		this.idEscenarioIncumplido = idEscenarioIncumplido;
	}

	public PghDetalleSupervision getIdDetalleSupervision() {
		return idDetalleSupervision;
	}
	public void setIdDetalleSupervision(PghDetalleSupervision idDetalleSupervision) {
		this.idDetalleSupervision = idDetalleSupervision;
	}
}

