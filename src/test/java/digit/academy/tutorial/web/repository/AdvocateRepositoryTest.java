package digit.academy.tutorial.web.repository;

import digit.academy.tutorial.repository.AdvocateRepositoryImpl;
import digit.academy.tutorial.repository.AdvocateRowMapper;
import digit.academy.tutorial.web.models.Advocate;
import digit.academy.tutorial.web.models.AdvocateSearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class AdvocateRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AdvocateRepositoryImpl advocateRepository;  // ✅ Fix: Use real implementation

    private AdvocateSearchCriteria criteria;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        criteria = new AdvocateSearchCriteria();
        criteria.setBarRegistrationNumber("BR123");
    }

    @Test
    void testSearchAdvocates() {
        String expectedQuery = "SELECT * FROM advocate WHERE 1=1 AND LOWER(bar_registration_number) ILIKE ? ORDER BY created_time ASC";

        when(jdbcTemplate.query(
                eq(expectedQuery), // ✅ Fix: Ensure exact query match
                any(AdvocateRowMapper.class),
                eq("%br123%") // ✅ Fix: Ensure correct parameter
        )).thenReturn(Collections.singletonList(new Advocate()));

        List<Advocate> result = advocateRepository.search(criteria);

        assertFalse(result.isEmpty()); // ✅ Now checking for expected non-empty result
    }


}