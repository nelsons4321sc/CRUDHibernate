package br.com.crudannotations;

import java.sql.Date;
import javax.persistence.*;
//aqui são feitas annotations para verificar o hibernate trabalhando
//com o banco de dados MySQL
@Entity
//temos aqui a tabela que foi criada no banco de dados
@Table(name = "contato")
public class Contato {
	@Id
	@GeneratedValue
	//e vamos especificar os dados da tabela a ser utilizada
	//também através de annotations
	//este recurso facilita muito e o desempenho é melhorado
	@Column(name = "codigo")
	private Integer	codigo;
	//aqui definimos as propriedades de cada coluna durante a criação
	@Column(name = "nome", length = 50, nullable = true)
	private String	nome;
	@Column(name = "telefone", length = 50, nullable = true)
	private String	telefone;
	@Column(name = "email", length = 50, nullable = true)
	private String	email;
	@Column(name = "dt_cad", nullable = true)
	private Date		dataCadastro;
	@Column(name = "obs", nullable = true)
	private String	observacao;
	// geramos os gets e sets automáticamente
	// clicando com o botão direito 
	// com a opção source - generated Getters e Setters
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	
}
