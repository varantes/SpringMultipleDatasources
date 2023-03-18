package br.com.neto.springmultipledatasources;

import br.com.neto.springmultipledatasources.pot.mydb.MyDbCConfiguration;
import br.com.neto.springmultipledatasources.pot.mydb.PessoaRepository;
import br.com.neto.springmultipledatasources.pot.testdb.CursoRepository;
import br.com.neto.springmultipledatasources.pot.testdb.TestDbCConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@DataJpaTest(properties = {"prop-neto=prop-neto-value"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({MyDbCConfiguration.class, TestDbCConfiguration.class})
class DatabasesTest {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private Environment env;

    @Autowired
    private ApplicationContext appContext;

    @Test
    void dataJpaTestProperties() {
        Assertions.assertEquals("prop-neto-value", env.getProperty("prop-neto"));
    }

    @Test
    void applicationContextTest() {
        appContext.getBeansWithAnnotation(EnableJpaRepositories.class).forEach((s, o) ->
                System.out.printf("%s -> %s\n", s, o));

        Assertions.assertFalse(appContext.getBeansWithAnnotation(EnableJpaRepositories.class).isEmpty());
    }

    @Test
    void validateMyDb() {
        Assertions.assertTrue(pessoaRepository.count() > 0);
    }

    @Test
    void validateTestDb() {
        Assertions.assertTrue(cursoRepository.count() > 0);
    }
}
