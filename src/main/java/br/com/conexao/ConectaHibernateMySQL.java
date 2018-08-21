package br.com.conexao;

import org.hibernate.Session;
public class ConectaHibernateMySQL {
	public static void main(String[] args) {
		Session sessao = null;
		sessao = HibernateUtil.getSessionFactory().openSession();
		System.out.println("Parábens, você fez conexão no Hibernate");
		sessao.close();
	}
}
