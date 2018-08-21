package br.com.crudannotations;

import java.sql.Date;
import java.util.List;
import org.hibernate.*;
import br.com.conexao.HibernateUtil;

public class ContatoCrudAnnotations {
	private Session sessao;
	
	//este Session faz parte do hibernate
	public ContatoCrudAnnotations(Session sessao) {
		this.sessao = sessao;
	}
	
	//portanto o método save da sessao, é o método pelo qual o hibernate uma vez em conexão com o banco de dados
	//através do mapeamento da classe usando os anotattions, realiza o insert no banco, observe que o objeto contato 
	// é repassado como parâmetro.
	public void salvar(Contato contato) {
		sessao.save(contato); 
	}
	// o método update, gera o update no banco
	public void atualizar(Contato contato) {
		sessao.update(contato); 
	}
	
	// o método delete, gera o delete no banco
	public void excluir(Contato contato) {
		sessao.delete(contato); 
	}
	
	// A query no hibernate é responsável por montar consultas, não precisamos usar o select *. Usamos a consulta 
	//a partir do from contato, por que estamos trabalhando com objetos e não com as linhas das tabelas. em resumo 
	// refere-se á nossa classe, por isso a consulta é feita de forma diferente, e então o list acabaretornando 
	//a lista de objetos consultados
	@SuppressWarnings("unchecked")
	public List<Contato> listar() { 
		Query consulta = sessao.createQuery("from Contato");
		return consulta.list();
	}
	
	//Aqui é montado uma consulta parametrizada no exemplo da chave primária da tabela
	public Contato buscaContato(int valor) {
		
		Query consulta = sessao.createQuery("from Contato where codigo = :parametro"); 
		consulta.setInteger("parametro", valor); 
		//este metodo retorna um único objeto, isso é esperado para uma chave primária
		return (Contato) consulta.uniqueResult(); 
	}
	
	
	public static void main(String[] args) {
		//através da classe HibernateUtil recuperamos a instância do sessionFactory
		//para usarmos o metodo openSession, que abre uma sessão do banco
		Session sessao = HibernateUtil.getSessionFactory().openSession(); 
		Transaction transacao = sessao.beginTransaction(); 
		ContatoCrudAnnotations contatoCrud = new ContatoCrudAnnotations(sessao);
		//É notorio perceber que será inserido, atualizado e depois excluido um dos contatos
		
		Contato contato1 = new Contato();
		contato1.setNome("Solanu");
		contato1.setTelefone("(47) 3333-4444");
		contato1.setEmail("solanu@javaparaweb.com.br");
		contato1.setDataCadastro(new Date(System.currentTimeMillis()));
		contato1.setObservacao("Novo cliente");
		//aqui salvamos o primeiro contato deste exemplo
		contatoCrud.salvar(contato1); 
		contato1.setObservacao("Retomar contato");
		//aqui atualizamos, usando o metodo update da sessão informada nas linhas acimas
		contatoCrud.atualizar(contato1); 

		Contato contato2 = new Contato();
		contato2.setNome("Lunare");
		contato2.setTelefone("(47) 7777-5555");
		contato2.setEmail("lunare@javaparaweb.com.br");
		contato2.setDataCadastro(new Date(System.currentTimeMillis()));
		contato2.setObservacao("Cliente em dia");
		contatoCrud.salvar(contato2); 
		//Aqui listamos a quantidade de registros existentes
		//Onde os mesmos serão impressos em tela
		System.out.println("Total de registros cadastrados: " + contatoCrud.listar().size()); 
		contatoCrud.excluir(contato2); 
		transacao.commit(); 
		System.out.println("Total de registros cadastrados: " + contatoCrud.listar().size()); 
	}

}
