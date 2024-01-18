package pos_java_hibernate;

import java.util.List;

import org.junit.Test;

import dao.DaoGeneric;
import model.TelefoneUser;
import model.UsuarioPessoa;

public class TestHibernate {

	@Test
	public void testHibernateUtil() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		UsuarioPessoa pessoa = new UsuarioPessoa();
		
		pessoa.setNome("Fulano");
		pessoa.setSobrenome("de Tal");
		pessoa.setIdade(46);
		pessoa.setLogin("fulano@gmail.com");
		pessoa.setLogin("fulanodetal235");
		
		daoGeneric.salvar(pessoa);
	}
	
	@Test
	public void testBuscar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		UsuarioPessoa pessoa = daoGeneric.pesquisar(2L, UsuarioPessoa.class);
		
		pessoa.setId(2L);
		
		System.out.println(pessoa);
	}
	
	@Test
	public void testAtualizar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		UsuarioPessoa pessoa = new UsuarioPessoa();
		
		pessoa.setNome("Nome atualizado");
		pessoa.setIdade(23);
		pessoa = daoGeneric.atualizarMerge(pessoa);
		
		System.out.println(pessoa);
	}
	
	@Test
	public void testDelete() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		UsuarioPessoa pessoa = daoGeneric.pesquisar(52L, UsuarioPessoa.class);

		daoGeneric.deletarById(pessoa);
		
		System.out.println(pessoa);
		System.out.println("Usuário deletado !");
	}
	
	@Test
	public void testLitarTodos() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> lista = daoGeneric.listarTodos(UsuarioPessoa.class);
	
		for(UsuarioPessoa usuarioPessoa : lista) {
			System.out.println(usuarioPessoa);
			System.out.println("__________________________________________");
		}
	}

	//QUERY ESPECÍFICAS UTILIZANDO O ENTITY MANAGER
	
	@Test
	public void testQueryList() {//listar todos por comando HQL
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> lista = daoGeneric.getEntityManager()
		.createQuery("from UsuarioPessoa").getResultList();
		
		for(UsuarioPessoa usuarioPessoa : lista) {
			System.out.println(usuarioPessoa);
		}
	}

	@Test
	public void testQueryListMaxResult() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> lista = daoGeneric.getEntityManager()
		.createQuery("from UsuarioPessoa order by nome").setMaxResults(10)//retorna 10 pessoas da lista ordenado pelo nome, ou qualquer outro atributo
		.getResultList();
		
		for(UsuarioPessoa usuarioPessoa : lista) {
			System.out.println(usuarioPessoa);
		}
	}

	@Test
	public void testQueryListParameter() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> lista = daoGeneric.getEntityManager()
		.createQuery("from UsuarioPessoa where nome = :nome or sobrenome = :sobrenome")
		.setParameter("nome", "Fulano")
		.setParameter("sobrenome", "de Tal").getResultList();
		
		for(UsuarioPessoa usuarioPessoa : lista) {
			System.out.println(usuarioPessoa);
		}
	}
	
	@Test
	public void testSomaIdade() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		Long somaIdade = (Long) daoGeneric.getEntityManager()
		.createQuery("select sum(u.idade) from UsuarioPessoa u").getSingleResult();
		
		System.out.println("Soma de todas as idade é: "+somaIdade);
	}
	
	//Relacionando UsuarioPessoas com o tributo telefone
	
	@Test
	public void testSaveTelefone() {
		DaoGeneric daoGeneric = new DaoGeneric();
		UsuarioPessoa usuarioPessoa = (UsuarioPessoa) daoGeneric.pesquisar(2L, UsuarioPessoa.class);
		TelefoneUser telefone = new TelefoneUser();
		
		telefone.setNumero("(99)9999-99999");
		telefone.setTipoTelefone("Celular");
		telefone.setUsuarioPessoa(usuarioPessoa);
		
		daoGeneric.salvar(telefone);
	}

	@Test
	public void testConsultaTelefone() {
		DaoGeneric daoGeneric = new DaoGeneric();
		UsuarioPessoa usuarioPessoa = (UsuarioPessoa) daoGeneric.pesquisar(2L, UsuarioPessoa.class);

		for(TelefoneUser telefone : usuarioPessoa.getTelefones()) {
			System.out.println("Número: "+telefone.getNumero());
			System.out.println("Tipo: "+telefone.getTipoTelefone());
			System.out.println("Usuário: "+telefone.getUsuarioPessoa().getNome());
			System.out.println("_____________________________________");
		}
	}
}