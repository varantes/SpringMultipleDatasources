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
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({MyDbCConfiguration.class, TestDbCConfiguration.class})
class DatabasesTest {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Test
    void validateMyDb() {
        Assertions.assertTrue(pessoaRepository.count() > 0);
    }

    @Test
    void validateTestDb() {
        Assertions.assertTrue(cursoRepository.count() > 0);
    }
}
