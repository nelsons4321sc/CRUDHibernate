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
	
	//portanto o m�todo save da sessao, � o m�todo pelo qual o hibernate uma vez em conex�o com o banco de dados
	//atrav�s do mapeamento da classe usando os anotattions, realiza o insert no banco, observe que o objeto contato 
	// � repassado como par�metro.
	public void salvar(Contato contato) {
		sessao.save(contato); 
	}
	// o m�todo update, gera o update no banco
	public void atualizar(Contato contato) {
		sessao.update(contato); 
	}
	
	// o m�todo delete, gera o delete no banco
	public void excluir(Contato contato) {
		sessao.delete(contato); 
	}
	
	// A query no hibernate � respons�vel por montar consultas, n�o precisamos usar o select *. Usamos a consulta 
	//a partir do from contato, por que estamos trabalhando com objetos e n�o com as linhas das tabelas. em resumo 
	// refere-se � nossa classe, por isso a consulta � feita de forma diferente, e ent�o o list acabaretornando 
	//a lista de objetos consultados
	@SuppressWarnings("unchecked")
	public List<Contato> listar() { 
		Query consulta = sessao.createQuery("from Contato");
		return consulta.list();
	}
	
	//Aqui � montado uma consulta parametrizada no exemplo da chave prim�ria da tabela
	public Contato buscaContato(int valor) {
		
		Query consulta = sessao.createQuery("from Contato where codigo = :parametro"); 
		consulta.setInteger("parametro", valor); 
		//este metodo retorna um �nico objeto, isso � esperado para uma chave prim�ria
		return (Contato) consulta.uniqueResult(); 
	}
	
	
	public static void main(String[] args) {
		//atrav�s da classe HibernateUtil recuperamos a inst�ncia do sessionFactory
		//para usarmos o metodo openSession, que abre uma sess�o do banco
		Session sessao = HibernateUtil.getSessionFactory().openSession(); 
		Transaction transacao = sessao.beginTransaction(); 
		ContatoCrudAnnotations contatoCrud = new ContatoCrudAnnotations(sessao);
		//� notorio perceber que ser� inserido, atualizado e depois excluido um dos contatos
		
		Contato contato1 = new Contato();
		contato1.setNome("Solanu");
		contato1.setTelefone("(47) 3333-4444");
		contato1.setEmail("solanu@javaparaweb.com.br");
		contato1.setDataCadastro(new Date(System.currentTimeMillis()));
		contato1.setObservacao("Novo cliente");
		//aqui salvamos o primeiro contato deste exemplo
		contatoCrud.salvar(contato1); 
		contato1.setObservacao("Retomar contato");
		//aqui atualizamos, usando o metodo update da sess�o informada nas linhas acimas
		contatoCrud.atualizar(contato1); 

		Contato contato2 = new Contato();
		contato2.setNome("Lunare");
		contato2.setTelefone("(47) 7777-5555");
		contato2.setEmail("lunare@javaparaweb.com.br");
		contato2.setDataCadastro(new Date(System.currentTimeMillis()));
		contato2.setObservacao("Cliente em dia");
		contatoCrud.salvar(contato2); 
		//Aqui listamos a quantidade de registros existentes
		//Onde os mesmos ser�o impressos em tela
		System.out.println("Total de registros cadastrados: " + contatoCrud.listar().size()); 
		contatoCrud.excluir(contato2); 
		transacao.commit(); 
		System.out.println("Total de registros cadastrados: " + contatoCrud.listar().size()); 
	}

}
