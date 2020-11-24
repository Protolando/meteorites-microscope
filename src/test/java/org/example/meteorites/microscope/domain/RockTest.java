package org.example.meteorites.microscope.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.example.meteorites.microscope.web.rest.TestUtil;

public class RockTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rock.class);
        Rock rock1 = new Rock();
        rock1.setId(1L);
        Rock rock2 = new Rock();
        rock2.setId(rock1.getId());
        assertThat(rock1).isEqualTo(rock2);
        rock2.setId(2L);
        assertThat(rock1).isNotEqualTo(rock2);
        rock1.setId(null);
        assertThat(rock1).isNotEqualTo(rock2);
    }
}
