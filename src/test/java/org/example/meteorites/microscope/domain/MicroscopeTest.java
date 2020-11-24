package org.example.meteorites.microscope.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.example.meteorites.microscope.web.rest.TestUtil;

public class MicroscopeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Microscope.class);
        Microscope microscope1 = new Microscope();
        microscope1.setId(1L);
        Microscope microscope2 = new Microscope();
        microscope2.setId(microscope1.getId());
        assertThat(microscope1).isEqualTo(microscope2);
        microscope2.setId(2L);
        assertThat(microscope1).isNotEqualTo(microscope2);
        microscope1.setId(null);
        assertThat(microscope1).isNotEqualTo(microscope2);
    }
}
