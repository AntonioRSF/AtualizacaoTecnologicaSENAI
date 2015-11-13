package br.com.senai.entity;

import static javax.persistence.GenerationType.IDENTITY;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import br.com.senai.util.DateUtil;

@Entity
@Table(name = "oferta", catalog = "licitanetdb")
@NamedQueries(
		@NamedQuery(name="getOfertasParaAgendamentoStartSistema", query = "from Oferta o where o.dataInicio <= current_date and o.dataFim >= current_date ")
)
public class Oferta implements Serializable {

	private static final long serialVersionUID = 1709654117944775784L;
	private Integer id;
	private Produto produto;
	private int quantidade;
	private Date dataInicio;
	private Date dataFim;
	private String descricao;
	private StatusOferta status;
	private List<Lance> lance;
	private Double menorLance;
	private Double menorLanceUsuario;
	private Usuario usuarioVencedor;

	public Oferta() {
	}
	
	public Oferta(int id, Date dataInicio, Date dataFim){
		setId(id);
		setDataInicio(dataInicio);
		setDataFim(dataFim);
	}

	public Oferta(Produto produto, int quantidade, Date dataInicio,
			Date dataFim, String descricao) {
		this.produto = produto;
		this.quantidade = quantidade;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.descricao = descricao;
	}

	public Oferta(Integer id, Integer idProduto, String nomeProduto, Date dataInicio, Date dataFim, Integer qtde, String descricao, Double menorLance, Double menorLanceUsuario, StatusOferta status){
		setId(id);
		if(getProduto() == null){
			setProduto(new Produto());
		}
		getProduto().setId(idProduto);
		getProduto().setNome(nomeProduto);

		setDataInicio(dataInicio);
		setDataFim(dataFim);
		setQuantidade(qtde);
		setDescricao(descricao);

		setMenorLance(menorLance);
		setMenorLanceUsuario(menorLanceUsuario);
		setStatus(status);
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idProduto")
	public Produto getProduto() {
		return this.produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@Column(name = "quantidade", nullable = false)
	public int getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(int quatidade) {
		this.quantidade = quatidade;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dataInicio", nullable = false, length = 19)
	public Date getDataInicio() {
		return this.dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dataFim", nullable = false, length = 19)
	public Date getDataFim() {
		return this.dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	@Column(name = "descricao", nullable = false, length = 200)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Enumerated(EnumType.ORDINAL)
	public StatusOferta getStatus() {
		return status;
	}
	
	public void setStatus(StatusOferta status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy="oferta")
	public List<Lance> getLance() {
		return this.lance;
	}

	public void setLance(List<Lance> lance) {
		this.lance = lance;
	}
	
	@Transient
	public String getDataInicioStr(){
		return DateUtil.getDataFormatada(this.getDataInicio());
	}
	
	@Transient
	public String getDataFimStr(){
		return DateUtil.getDataFormatada(this.getDataFim());
	}

	@Transient
	public Double getMenorLance() {
		return menorLance;
	}

	public void setMenorLance(Double menorLance) {
		this.menorLance = menorLance;
	}

	@Transient
	public Double getMenorLanceUsuario() {
		return menorLanceUsuario;
	}

	public void setMenorLanceUsuario(Double menorLanceUsuario) {
		this.menorLanceUsuario = menorLanceUsuario;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idUsuarioVencedor")
	public Usuario getUsuarioVencedor() {
		return usuarioVencedor;
	}

	public void setUsuarioVencedor(Usuario usuarioVencedor) {
		this.usuarioVencedor = usuarioVencedor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataFim == null) ? 0 : dataFim.hashCode());
		result = prime * result
				+ ((dataInicio == null) ? 0 : dataInicio.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lance == null) ? 0 : lance.hashCode());
		result = prime * result
				+ ((menorLance == null) ? 0 : menorLance.hashCode());
		result = prime
				* result
				+ ((menorLanceUsuario == null) ? 0 : menorLanceUsuario
						.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + quantidade;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((usuarioVencedor == null) ? 0 : usuarioVencedor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Oferta other = (Oferta) obj;
		if (dataFim == null) {
			if (other.dataFim != null)
				return false;
		} else if (!dataFim.equals(other.dataFim))
			return false;
		if (dataInicio == null) {
			if (other.dataInicio != null)
				return false;
		} else if (!dataInicio.equals(other.dataInicio))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lance == null) {
			if (other.lance != null)
				return false;
		} else if (!lance.equals(other.lance))
			return false;
		if (menorLance == null) {
			if (other.menorLance != null)
				return false;
		} else if (!menorLance.equals(other.menorLance))
			return false;
		if (menorLanceUsuario == null) {
			if (other.menorLanceUsuario != null)
				return false;
		} else if (!menorLanceUsuario.equals(other.menorLanceUsuario))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (quantidade != other.quantidade)
			return false;
		if (status != other.status)
			return false;
		if (usuarioVencedor == null) {
			if (other.usuarioVencedor != null)
				return false;
		} else if (!usuarioVencedor.equals(other.usuarioVencedor))
			return false;
		return true;
	}
}
